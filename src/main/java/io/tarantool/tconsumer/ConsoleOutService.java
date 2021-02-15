package io.tarantool.tconsumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConsoleOutService {

    private Integer lastY = 0;

    @Autowired
    FrameRepository frameRepository;

    @Scheduled(fixedDelay = 100)
    void printFrame() {
        try {
            List<Frame> frames = frameRepository.getFrames(lastY, lastY + 1);
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
