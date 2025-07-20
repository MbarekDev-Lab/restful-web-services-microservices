package com.rest_api_webservices.microservices.resources;

import com.rest_api_webservices.microservices.daoImp.UserDaoService;
import com.rest_api_webservices.microservices.entities.Post;
import com.rest_api_webservices.microservices.exceptions.UserNotFoundException;
import com.rest_api_webservices.microservices.interfaces.jpa.PostRepository;
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
@RequestMapping("/jpa")
public class UserJpaResource {
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    public UserJpaResource(UserRepository userRepository, PostRepository postRepository) {
        this.userRepository = userRepository;
        this.postRepository = postRepository;
    }

    @GetMapping("/users")
    public List<User> retrieveAllUsers(){
        return userRepository.findAll();
    }

    @GetMapping("/users/{id}")
    public EntityModel<User> retrieveUsersById(@PathVariable int id) throws UserNotFoundException {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("id:" + id));
        EntityModel<User> entityModel = EntityModel.of(user);
        WebMvcLinkBuilder linkToUsers = linkTo(methodOn(this.getClass()).retrieveAllUsers());
        entityModel.add(linkToUsers.withRel("all_users"));
        return entityModel;
    }

    @PostMapping("/users")
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
        User savedUser = userRepository.save(user);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedUser.getId())
                .toUri();
        return ResponseEntity.created(location).body(savedUser);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable int id) throws UserNotFoundException {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("id:" + id));
        userRepository.delete(user);
        return ResponseEntity.noContent().build();
    }

    // POST /jpa/users/{id}/posts
    @PostMapping("/users/{id}/posts")
    public ResponseEntity<Post> createPostForUser2(@PathVariable int id, @Valid @RequestBody Post post) throws UserNotFoundException {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("id: " + id));
        post.setUser(user); // Set the relationship
        Post savedPost = postRepository.save(post);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{postId}")
                .buildAndExpand(savedPost.getId())
                .toUri();
        return ResponseEntity.created(location).body(savedPost);
    }

    // GET /jpa/users/{id}/posts
    @GetMapping("/users/{id}/posts")
    public List<Post> retrievePostsForUser(@PathVariable int id) throws UserNotFoundException {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("id: " + id));
        return user.getPosts();
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
