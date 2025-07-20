package com.rest_api_webservices.microservices.versioning;

import com.rest_api_webservices.microservices.entities.Name;
import com.rest_api_webservices.microservices.entities.PersonV1;
import com.rest_api_webservices.microservices.entities.PersonV2;
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

    @GetMapping(path = "/person",params = "version=1")
    public PersonV1 getFirstVesuionOfPersonRequestParameter(){
        return new PersonV1("Bob");
    }

    @GetMapping(path = "/person",params = "version=2")
    public PersonV2 getSecondVesuionOfPersonRequestParameter(){
        return new PersonV2(new Name("Bob", "charls"));
    }

    @GetMapping(path = "/person/header",headers = "X-API-VERSION=1")
    public PersonV1 getFirstVesuionOfPersonRequestHeader(){
        return new PersonV1( "header");
    }

    @GetMapping(path = "/person/header",headers = "X-API-VERSION=2")
    public PersonV2 getSecondVesuionOfPersonRequestHeader(){
        return new PersonV2(new Name( "person","header"));
    }


    @GetMapping(path = "/person/accept", produces = "application/vnd.company.app-v1+json")
    public PersonV1 getSecondVersionOfPersonAccptsHeader() {
        return new PersonV1("accept");
    }

    @GetMapping(path = "/person/accept", produces = "application/vnd.company.app-v2+json")
    public PersonV2 getSecondVersionOfPersonAccptsHeaderV2() {
        return new PersonV2(new Name("person","accept v2"));
    }

}
