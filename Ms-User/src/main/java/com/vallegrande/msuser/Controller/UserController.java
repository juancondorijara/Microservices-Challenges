package com.vallegrande.msuser.Controller;


import com.vallegrande.msuser.Domain.User;
import com.vallegrande.msuser.Exception.ResourceNotFoundException;
import com.vallegrande.msuser.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    UserRepository userRepository;

    @GetMapping("/users")
    public List<User> getAUsers(){
        return userRepository.findAll();
    }

    @GetMapping("/users/{UserId}")
    public ResponseEntity<User> getUserById(@PathVariable(value = "UserId") Integer userId)
        throws ResourceNotFoundException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Este usuario no existe" + userId));
        return ResponseEntity.ok().body(user);
    }

    @PostMapping("/users")
    public User createUser(@RequestBody User user){
        return userRepository.save(user);
    }

    //  @DeleteMapping("/users/{UserId}")
  //  public User deleteUser(@PathVariable(value = "UserId") Integer userId){
  //      return userRepository.deleteById(Integer userId);
  //  }

    @DeleteMapping("/users/{UserId}")
    public String remove(@PathVariable(value = "UserId") Integer userId) {
        userRepository.deleteById(userId);
        return "Se elimino el user con ID: "+userId;
    }
}
