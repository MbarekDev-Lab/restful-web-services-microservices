package com.rest_api_webservices.microservices.versioning;

import com.rest_api_webservices.microservices.user.Name;
import com.rest_api_webservices.microservices.user.Person;
import com.rest_api_webservices.microservices.user.PersonV1;
import com.rest_api_webservices.microservices.user.PersonV2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VersioningController {

    @GetMapping("/v1/person")
    public PersonV1 getFirtsVersionOfPerson(){
       return new PersonV1("Mbarek Ben");
    }

    @GetMapping("/v2/person")
    public PersonV2 getSecondVersionOfPerson(){
       return new PersonV2(new Name(" Person", "Second"));
    }




}
