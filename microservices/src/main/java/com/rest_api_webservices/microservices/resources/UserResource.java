package com.rest_api_webservices.microservices.resources;

import com.rest_api_webservices.microservices.dao.UserDaoService;
import com.rest_api_webservices.microservices.user.User;
import org.springframework.web.bind.annotation.*;


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

    // POST / users
    @PostMapping("/userts")
    public void createUser(@RequestBody User user){
        service.save(user);
    }

}
