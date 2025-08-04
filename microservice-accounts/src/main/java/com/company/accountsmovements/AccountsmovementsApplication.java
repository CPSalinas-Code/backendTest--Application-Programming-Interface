package com.company.accountsmovements;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class AccountsmovementsApplication {

	public static void main(String[] args) {
		SpringApplication.run(AccountsmovementsApplication.class, args);
	}

}
