package com.jwtAuth.generic.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jwtAuth.generic.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long>{
	Optional<UserEntity> findByEmail(String email);
	Boolean existsByEmail(String email);
}
