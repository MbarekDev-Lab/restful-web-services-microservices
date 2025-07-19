package com.rest_api_webservices.microservices.resources;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import com.rest_api_webservices.microservices.dao.UserDaoService;
import com.rest_api_webservices.microservices.exceptions.UserNotFoundException;
import com.rest_api_webservices.microservices.user.User;
import jakarta.validation.Valid;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
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
    public EntityModel<User> retrieveUsersById(@PathVariable int id) throws UserNotFoundException {
        User user = service.findOne(id);
        if (user == null){
            throw new UserNotFoundException("id:"+id);//There was an unexpected error (type=Not Found, status=404).
        }
        EntityModel<User> entityModel = EntityModel.of(user);
        WebMvcLinkBuilder link = linkTo(
                methodOn( this.getClass() ).retrieveAllUsers()
        );
        entityModel.add(link.withRel("all_users"));
        return entityModel ;
    }

    //DELETE /user BY ID
    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable int id) throws UserNotFoundException {
        service.deleteById(id);
    }

    @PostMapping("/creatusers")
    public  ResponseEntity<User> createUser1(@Valid  @RequestBody User user){
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
