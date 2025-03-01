package com.ebms.ebms_spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ebms.ebms_spring.model.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

}
