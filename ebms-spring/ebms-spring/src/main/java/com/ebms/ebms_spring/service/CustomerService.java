package com.ebms.ebms_spring.service;

import com.ebms.ebms_spring.DTO.CustomerRegistrationDTO;
import com.ebms.ebms_spring.model.Customer;

public interface CustomerService {
	public Customer registerCustomer(CustomerRegistrationDTO cDto);
}
