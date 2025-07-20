package com.rest_api_webservices.microservices.interfaces.jpa;

import com.rest_api_webservices.microservices.entities.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Integer> {
}
