package com.example;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(value="STUDENTSERVICE")
public interface MyFeignClientStudent {
	
	
	@GetMapping(value="name")
	String getStudentName();

}
