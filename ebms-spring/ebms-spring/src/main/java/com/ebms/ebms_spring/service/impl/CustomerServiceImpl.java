package com.ebms.ebms_spring.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ebms.ebms_spring.model.Customer;
import com.ebms.ebms_spring.model.Login;
import com.ebms.ebms_spring.repository.CustomerRepository;
import com.ebms.ebms_spring.repository.LoginRepository;
import com.ebms.ebms_spring.service.CustomerService;

@Service
public class CustomerServiceImpl implements CustomerService {
	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	private LoginRepository loginRepository;
	
	@Override
	public Customer registerCustomer(String name, String email, String password, String contactNo, String address) {
		
		//Save customer to the customer table
		Customer customer = new Customer(name, email, contactNo, address);
		customer = customerRepository.save(customer);
		
		// Save customer to the login table
		Login login = new Login(email, password, "Active", "Customer", customer);
		loginRepository.save(login);
		
		return customer;
	}
}

