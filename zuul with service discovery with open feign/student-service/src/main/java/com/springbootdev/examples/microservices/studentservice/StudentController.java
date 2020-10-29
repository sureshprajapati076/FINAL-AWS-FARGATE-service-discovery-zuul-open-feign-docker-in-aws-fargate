package com.springbootdev.examples.microservices.studentservice;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.ws.rs.Path;
import java.util.Optional;


@RestController
public class StudentController {

	@Autowired
	MyFeignClient myFeignClient;

	@GetMapping("/name")
	public String getControllerName() {
		return "WELCOME FROM STUDENT MICROSERVICE";
	}


	@GetMapping("/callAddressWithPathVariable/{studentId}")
	public String callAddressWithPathVariable(@PathVariable Integer studentId, @RequestParam Optional<Integer> addressId){
		return myFeignClient.callAddressWithPath(studentId, addressId.isPresent() ? addressId.get() : 0);
	}










	@GetMapping("/callAddressFromStudent")
//	@HystrixCommand(fallbackMethod = "serviceMayBeDown") // This is usually used in service class OR used where
															// restTemplate is used to communicate with another
															// micro-service
	
	
	
	@HystrixCommand(fallbackMethod = "serviceMayBeDown", commandProperties = {
			   @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "1500")
			})
	public String communicate() {

		return myFeignClient.getAddress();

	}

	public String serviceMayBeDown() {
		return "Requested Address Service May be down or too busy";
	}

}