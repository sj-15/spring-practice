package com.practice.rest_crud.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.practice.rest_crud.model.CloudVendor;
import com.practice.rest_crud.repository.CloudVendorRepository;

@Service
public class CloudVendorServiceImpl implements CloudVendorService{
	
	@Autowired
	private CloudVendorRepository cloudVendorRepository;

//	public CloudVendorServiceImpl(CloudVendorRepository cloudVendorRepository) {
//		super();
//		this.cloudVendorRepository = cloudVendorRepository;
//	}

	@Override
	public String createCloudVendor(CloudVendor cloudVendor) {
		// TODO Auto-generated method stub
		cloudVendorRepository.save(cloudVendor);
		return "Success";
	}

	@Override
	public String updateCloudVendor(CloudVendor cloudVendor) {
		// TODO Auto-generated method stub
		cloudVendorRepository.save(cloudVendor);
		return "Success";
	}

	@Override
	public String deleteCloudVendor(String cloudVendorId) {
		// TODO Auto-generated method stub
		cloudVendorRepository.deleteById(cloudVendorId);
		return "Success";
	}

	@Override
	public CloudVendor getCloudVendor(String cloudVendorId) {
		// TODO Auto-generated method stub
		return cloudVendorRepository.findById(cloudVendorId).get();
	}

	@Override
	public List<CloudVendor> getAllCloudVendors() {
		// TODO Auto-generated method stub
		
		return cloudVendorRepository.findAll();
	}

}
