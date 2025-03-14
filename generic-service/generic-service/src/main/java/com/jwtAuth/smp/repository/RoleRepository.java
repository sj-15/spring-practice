package com.jwtAuth.smp.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jwtAuth.smp.constant.ERole;
import com.jwtAuth.smp.entity.RoleEntity;

public interface RoleRepository extends JpaRepository<RoleEntity, Long>{
	Optional<RoleEntity> findByName(ERole name);
}
