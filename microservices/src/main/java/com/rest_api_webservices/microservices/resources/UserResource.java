package com.rest_api_webservices.microservices.resources;

import com.rest_api_webservices.microservices.dao.UserDaoService;
import com.rest_api_webservices.microservices.user.User;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


import java.net.URI;
import java.util.List;

@RestController
public class UserResource {
    private final UserDaoService service;

    public UserResource(UserDaoService service){
        this.service=service;
    }

    //GET /users
    @GetMapping("/users")
    public List<User> retrieveAllUsers(){
        return service.findAll();
    }

    //GET /users
    @GetMapping("/users/{id}")
    public User retrieveFirstUsers(@PathVariable int id){
        return service.findOne(id);
    }
    @PostMapping("/creatusers")
    public  ResponseEntity<User> createUser1(@RequestBody User user){
        service.save(user);
        return ResponseEntity.created(null).build();
    }


    @PostMapping("/users")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User savedUser = service.save(user);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedUser.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }



//    // POST /users
//    @PostMapping("/users")
//    public ResponseEntity<User> createUser(@RequestBody User user) {
//        User savedUser = service.save(user);
//        // Build URI to /users/{id}
//        URI location = URI.create(String.format("/users/%d", savedUser.getId()));
//        return ResponseEntity.created(location).body(savedUser);
//    }


}
