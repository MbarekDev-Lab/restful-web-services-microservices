package com.rest_api_webservices.microservices.resources;

import com.rest_api_webservices.microservices.daoImp.UserDaoService;
import com.rest_api_webservices.microservices.exceptions.UserNotFoundException;
import com.rest_api_webservices.microservices.interfaces.jpa.UserRepository;
import com.rest_api_webservices.microservices.entities.User;
import jakarta.validation.Valid;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class UserJpaResource {
    // no more using UserDaoService
    //private final UserDaoService service;
    private final UserRepository userRepository;

    public UserJpaResource(UserDaoService service, UserRepository userRepository){
        //this.service=service;
        this.userRepository = userRepository;
    }

    //GET /users
    @GetMapping("/jpa/users")
    public List<User> retrieveAllUsers(){
        return userRepository.findAll();
    }

    //GET /users
    @GetMapping("/jpa/users/{id}")
    public EntityModel<User> retrieveUsersById(@PathVariable int id) throws UserNotFoundException {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isEmpty()) {
            throw new UserNotFoundException("id:" + id);
        }
        User user = userOptional.get();
        EntityModel<User> entityModel = EntityModel.of(user);
        WebMvcLinkBuilder linkToUsers = linkTo(
                methodOn(this.getClass()).retrieveAllUsers()
        );
        entityModel.add(linkToUsers.withRel("all_users"));
        return entityModel;
    }

    /*@GetMapping("/jpa/users/{id}")
    public EntityModel<User> retrieveUsersByIdorElseThrow(@PathVariable int id) throws UserNotFoundException {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("id:" + id));
        EntityModel<User> entityModel = EntityModel.of(user);
        WebMvcLinkBuilder link = linkTo(methodOn(this.getClass()).retrieveAllUsers());
        entityModel.add(link.withRel("all_users"));
        return entityModel;
    }*/

    //DELETE /user BY ID
    @DeleteMapping("/jpa/users/{id}")
    public void deleteUser(@PathVariable int id) throws UserNotFoundException {
        userRepository.deleteById(id);
    }

    @PostMapping("/jpa/creatusers")
    public ResponseEntity<User> creatusers(@Valid @RequestBody User user){
        userRepository.save(user);
        return ResponseEntity.created(null).build();
    }

    @PostMapping("/jpa/userss")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User savedUser = userRepository.save(user);
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
