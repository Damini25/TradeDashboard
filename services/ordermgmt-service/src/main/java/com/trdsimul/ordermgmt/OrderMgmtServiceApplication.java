/**********************************************************************
Project: Order Management Service                       
**********************************************************************/
package com.trdsimul.ordermgmt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class OrderMgmtServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrderMgmtServiceApplication.class, args);
	}

}
