package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AddressController
{
	@Autowired
	MyFeignClientStudent myFeignCLientStudent;
	
	
	
    @GetMapping("/getStudentName")
    public String getControllerName()
    {
    	
    	
    	
    	String response=myFeignCLientStudent.getStudentName();
    	
    	
    	
    	
    	
    	
    	
        return response;
    }
    
    @GetMapping("/getAddress")
    public String getAdd() {
    	
    	try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	
    	
    	return "THIS IS SENT FROM ADDRESS SERVICE: "+AddressServiceApplication.instanceIp;
    }

    @GetMapping("/getWithPathVariable/{studentId}")
	public String getWithPath(@PathVariable Integer studentId, @RequestParam Integer addressId){
    	return "STD ID: "+studentId.toString()+ " ADD ID : "+ addressId.toString();
	}
    
    
    
    
    
    
    
}