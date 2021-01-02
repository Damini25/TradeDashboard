package com.trdsimul.gamemgmt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;

@EnableEurekaClient
@EnableEncryptableProperties
@SpringBootApplication
public class GameMgmtServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(GameMgmtServiceApplication.class, args);
	}

}
