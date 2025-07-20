package com.rest_api_webservices.microservices.interfaces.jpa;

import com.rest_api_webservices.microservices.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {







}
