package com.ebms.ebms_spring.service;

import com.ebms.ebms_spring.model.Customer;

public interface CustomerService {
	public Customer registerCustomer(String name, String email, String password, String contactNo, String address);
}
