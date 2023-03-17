package com.lib.service;

import com.lib.domain.Role;
import com.lib.domain.enums.RoleType;
import com.lib.exception.ResourceNotFoundException;
import com.lib.exception.message.ErrorMessage;
import com.lib.repository.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoleService {

    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }


    public Role findByType(RoleType type) {

        Role role =  roleRepository.findByRoleType(type).orElseThrow(
                ()->new ResourceNotFoundException(String.format(ErrorMessage.ROLE_NOT_FOUNT_EXCEPTION, type.name())));
        return role;

    }


}
