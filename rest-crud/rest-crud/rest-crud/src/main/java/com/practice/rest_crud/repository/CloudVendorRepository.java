package com.practice.rest_crud.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.practice.rest_crud.model.CloudVendor;

public interface CloudVendorRepository extends JpaRepository<CloudVendor, String> {

}
