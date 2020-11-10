package com.tcs.celcom.ms.cbs.addOnProvision;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;

@SpringBootApplication
@EnableCircuitBreaker
public class MsAddOnProvisioningApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsAddOnProvisioningApplication.class, args);
	}
}
