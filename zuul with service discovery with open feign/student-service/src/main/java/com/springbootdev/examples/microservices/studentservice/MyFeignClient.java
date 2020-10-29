package com.springbootdev.examples.microservices.studentservice;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value="ADDRESSSERVICE")
public interface MyFeignClient {
	
	@GetMapping(value="getAddress")
	String getAddress();

	@GetMapping(value = "getWithPathVariable/{studentId}")
	String callAddressWithPath(@PathVariable Integer studentId, @RequestParam Integer addressId);

}
