package io.tarantool.tconsumer;

import io.tarantool.driver.api.TarantoolClient;
import io.tarantool.driver.api.conditions.Conditions;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class ConsoleOutService implements ApplicationContextAware {

    private ApplicationContext ctx;
    private Integer lastY = 0;

    @Autowired
    FrameRepository frameRepository;

    @Autowired
    TarantoolClient tarantoolClient;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.ctx = applicationContext;
    }

    private void terminate(Exception e) {
        System.out.println("Error: ");
        System.out.println(e.getMessage());
        System.out.println("\n");
        ((ConfigurableApplicationContext) ctx).close();
    }

    @Scheduled(fixedDelay = 100)
    void printFrame() {
        try {
            List<Frame> frames = frameRepository.getFrames("frame",
                    Conditions
                            .indexGreaterOrEquals("primary", Collections.singletonList(lastY))
                            .andIndexLessThan("primary", Collections.singletonList(lastY + 1))
                            .toProxyQuery(tarantoolClient.metadata(),
                                    tarantoolClient.metadata().getSpaceByName("frame").get()));

                    frames.forEach(f -> System.out.print(f.sym));
            lastY = lastY + 1;
        } catch (Exception e) {
            terminate(e);
        }
    }

}
