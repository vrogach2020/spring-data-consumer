package io.tarantool.tconsumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class TconsumerApplication {

	public static void main(String[] args) {
		SpringApplication.run(TconsumerApplication.class, args);
	}

}
