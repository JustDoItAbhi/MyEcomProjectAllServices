package com.ecommer.userservices.roles.rolecontroller;

import com.ecommer.userservices.roles.roledtos.RoleRequestDto;
import com.ecommer.userservices.roles.roledtos.RoleResponseDto;
import com.ecommer.userservices.roles.roleservice.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/role")
public class RoleControllers {
@Autowired
    private RoleService roleService;
//@Autowired
//private OidcClientServices oidcClientServices;
//@PostMapping("/")
//public ResponseEntity<RegisterOidcClientResponseDto> registerOidcClient(@RequestBody RegisterOidcClientRequestDto requestDto){
//    return ResponseEntity.ok(oidcClientServices.registerOidcClient(requestDto));
//
//}
    @PreAuthorize("hasRole('ROLE_ADMIN')")
@PostMapping("/create")// create role post mapping
    public ResponseEntity<RoleResponseDto> createRole(@RequestBody RoleRequestDto requestDto){
    return ResponseEntity.ok(roleService.createRole(requestDto));
}
@PreAuthorize("hasRole('ROLE_ADMIN')")
@DeleteMapping("/deleteById/{roleId}/{userId}")// delete role
    public ResponseEntity<Boolean> delete(@PathVariable ("roleId")long roleId,@PathVariable ("userId")long userId){
    return ResponseEntity.ok(roleService.deleteRole(roleId,userId));
}
@PreAuthorize("hasRole('ADMIN')")
@GetMapping("/getAllRoles")// get all the roles as list
    public ResponseEntity<List<RoleResponseDto>>getllRoles(){
    return ResponseEntity.ok(roleService.getAllRoles());
}
}
