package com.ebms.ebms_spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ebms.ebms_spring.model.Login;

public interface LoginRepository extends JpaRepository<Login, Long> {

}
