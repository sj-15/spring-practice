package com.ebms.ebms_spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ebms.ebms_spring.DTO.CustomerRegistrationDTO;
import com.ebms.ebms_spring.model.Customer;
import com.ebms.ebms_spring.model.Login;
import com.ebms.ebms_spring.service.CustomerService;

@Controller
@RequestMapping("/auth")
public class AuthController {
	@Autowired
	private CustomerService customerService;
	
	@PostMapping("/register")
	@ResponseBody
	public Customer register(@RequestBody CustomerRegistrationDTO customerRegistrationDTO) {
		return customerService.registerCustomer(customerRegistrationDTO.getName(), customerRegistrationDTO.getEmail(), customerRegistrationDTO.getPassword(), customerRegistrationDTO.getContactNo(), customerRegistrationDTO.getAddress());
	}
	
	@PostMapping("/login")
	public Login login(@RequestParam String email, @RequestParam String password, @RequestParam String userType) {
		return null;
	}
}
