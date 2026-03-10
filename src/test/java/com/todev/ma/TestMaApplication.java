package com.todev.ma;

import org.springframework.boot.SpringApplication;

public class TestMaApplication {

	public static void main(String[] args) {
		SpringApplication.from(MaApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
