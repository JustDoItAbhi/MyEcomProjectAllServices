package com.ecommer.userservices.roles.roleservice;

import com.ecommer.userservices.entity.Roles;
import com.ecommer.userservices.exceptions.RoleNotFoundExceptions;
import com.ecommer.userservices.repository.RoleRepository;
import com.ecommer.userservices.repository.UserRepository;
import com.ecommer.userservices.roles.RoleMapper.RoleMappers;
import com.ecommer.userservices.roles.roledtos.RoleRequestDto;
import com.ecommer.userservices.roles.roledtos.RoleResponseDto;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service// service annotation
public class RoleServiceImpl implements RoleService{

    private RoleRepository roleRepository;
    private UserRepository userRepository;

    public RoleServiceImpl(RoleRepository roleRepository, UserRepository userRepository) {// constructor
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
    }

    @Override
    public RoleResponseDto createRole(RoleRequestDto requestDto) {
        Optional<Roles>rolelist=roleRepository.findByRoleType(requestDto.getRole());// fetch tole from database
        if(rolelist.isPresent()){// role validation
            throw new RoleNotFoundExceptions("ROLE ALREADY EXISTES "+ requestDto.getRole());// if role already exists in database then return error
        }
        Roles roles=new Roles();// create new role
        roles.setRoleType(requestDto.getRole());
        roleRepository.save(roles);
        return RoleMappers.fromEntity(roles);
    }

    @Override
    @Transactional
    public boolean deleteRole(long roleId,long userId) {// delete role
         roleRepository.deleteById(roleId);
         userRepository.deleteById(userId);
         return true;
    }

    @Override
    public List<RoleResponseDto> getAllRoles() {// get all roles
        List<Roles>rolesList=roleRepository.findAll();
        List<RoleResponseDto>dtos=new ArrayList<>();
        for(Roles roles:rolesList){
            dtos.add(RoleMappers.fromEntity(roles));
        }
        return dtos;
    }
}
