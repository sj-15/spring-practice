package com.jwtAuth.generic;

import com.jwtAuth.generic.constant.ERole;
import com.jwtAuth.generic.entity.RoleEntity;
import com.jwtAuth.generic.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class StartUp implements CommandLineRunner {
    @Autowired
    private RoleRepository roleRepository;


    @Override
    public void run(String... args) throws Exception {
        Optional<RoleEntity> optRoleAdmin = roleRepository.findByName(ERole.ROLE_ADMIN);
        if(optRoleAdmin.isEmpty()){
            RoleEntity role = new RoleEntity();
            role.setName(ERole.ROLE_ADMIN);
            roleRepository.save(role);
        }

        Optional<RoleEntity> optRoleMod = roleRepository.findByName(ERole.ROLE_MODERATOR);
        if(optRoleMod.isEmpty()){
            RoleEntity role = new RoleEntity();
            role.setName(ERole.ROLE_MODERATOR);
            roleRepository.save(role);
        }

        Optional<RoleEntity> optRoleUser = roleRepository.findByName(ERole.ROLE_USER);
        if(optRoleUser.isEmpty()){
            RoleEntity role = new RoleEntity();
            role.setName(ERole.ROLE_USER);
            roleRepository.save(role);
        }
    }
}
