package com.razvanpotra.hr_app;

import org.springframework.boot.SpringApplication;

public class TestHrAppApplication {

	public static void main(String[] args) {
		SpringApplication.from(HrAppApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
