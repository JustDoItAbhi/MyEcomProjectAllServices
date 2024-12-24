package com.ecommer.userservices.roles.rolecontroller;

import com.ecommer.userservices.roles.roledtos.RoleRequestDto;
import com.ecommer.userservices.roles.roledtos.RoleResponseDto;
import com.ecommer.userservices.roles.roleservice.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
@PostMapping("/create")
    public ResponseEntity<RoleResponseDto> createRole(@RequestBody RoleRequestDto requestDto){
    return ResponseEntity.ok(roleService.createRole(requestDto));
}
@DeleteMapping("/deleteById/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable ("id")long id){
    return ResponseEntity.ok(roleService.deleteRole(id));
}

}
