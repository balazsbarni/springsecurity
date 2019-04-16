package com.spring.securitydemo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api")
public class ExampleController {

    @GetMapping("user")
    public ResponseEntity getUser() {
        return new ResponseEntity<>("usercontroller", HttpStatus.OK);
    }

    @GetMapping("admin")
    public ResponseEntity getAdmin() {
        return new ResponseEntity<>("admincontroller", HttpStatus.OK);
    }
}
