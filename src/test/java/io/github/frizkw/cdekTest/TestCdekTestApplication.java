package io.github.frizkw.cdekTest;

import org.springframework.boot.SpringApplication;

public class TestCdekTestApplication {

	public static void main(String[] args) {
		SpringApplication.from(CdekTestApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
