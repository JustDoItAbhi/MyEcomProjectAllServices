package com.ecommer.userservices.roles.roleservice;

import com.ecommer.userservices.entity.Roles;
import com.ecommer.userservices.repository.RoleRepository;
import com.ecommer.userservices.repository.UserRepository;
import com.ecommer.userservices.roles.roledtos.RoleRequestDto;
import com.ecommer.userservices.roles.roledtos.RoleResponseDto;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class RoleServiceImpl implements RoleService{
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserRepository userRepository;
    @Override
    public RoleResponseDto createRole(RoleRequestDto requestDto) {
        Roles roles=new Roles();
        roles.setRoleType(requestDto.getRole());
        roleRepository.save(roles);
        return RoleResponseDto.fromEntity(roles);
    }

    @Override
    @Transactional
    public boolean deleteRole(long roleId,long userId) {
         roleRepository.deleteById(roleId);
         userRepository.deleteById(userId);
         return true;
    }

    @Override
    public List<RoleResponseDto> getAllRoles() {
        List<Roles>rolesList=roleRepository.findAll();
        List<RoleResponseDto>dtos=new ArrayList<>();
        for(Roles roles:rolesList){
            dtos.add(RoleResponseDto.fromEntity(roles));
        }
        return dtos;
    }
}
