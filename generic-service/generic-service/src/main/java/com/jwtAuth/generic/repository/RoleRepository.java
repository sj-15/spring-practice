package com.jwtAuth.generic.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jwtAuth.generic.constant.ERole;
import com.jwtAuth.generic.entity.RoleEntity;

public interface RoleRepository extends JpaRepository<RoleEntity, Long>{
	Optional<RoleEntity> findByName(ERole name);
}
