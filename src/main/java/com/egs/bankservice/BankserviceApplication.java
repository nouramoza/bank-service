package com.egs.bankservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.egs.*"})
public class BankserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(BankserviceApplication.class, args);
	}

}
