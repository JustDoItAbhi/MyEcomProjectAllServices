package cartservice.controller;

import cartservice.entity.UserDetails;
import cartservice.service.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserServices userServices;
    @GetMapping("/getUserByid/{email}")
    public ResponseEntity<UserDetails> getByiD(@PathVariable("email")String email) {
        return ResponseEntity.ok(userServices.createUser(email));
    }
    @GetMapping("/")
    public ResponseEntity<List<UserDetails>>getAllUsers(){
        return ResponseEntity.ok(userServices.getAllUsers());
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean>deleteUserByID(@PathVariable("id")long id){
        return ResponseEntity.ok(userServices.deleteUser(id));
    }
    @DeleteMapping("/")
    public ResponseEntity<String>deleteDuplicates(){
        return ResponseEntity.ok(userServices.deleteIfEmailEquslToEmail());
    }
}
