package io.tarantool.tconsumer;

import io.tarantool.driver.api.TarantoolClient;
import io.tarantool.driver.api.conditions.Conditions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class ConsoleOutService {

    private Integer lastY = 0;

    @Autowired
    FrameRepository frameRepository;

    @Autowired
    TarantoolClient tarantoolClient;

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

            lastY++;
        } catch (Exception e) {
            System.out.println("Error: ");
            System.out.println(e.getMessage());
            System.out.println("\n");
            throw e;
        }
    }
}
