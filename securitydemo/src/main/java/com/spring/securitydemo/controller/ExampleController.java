package com.spring.securitydemo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/dummy")
public class ExampleController {

    @GetMapping("try")
    public ResponseEntity getTry() {
        return new ResponseEntity<>("try no auth", HttpStatus.OK);
    }

    @GetMapping("user")
    public ResponseEntity getUser() {
        //User adathoz hozzafer
        System.out.println(SecurityContextHolder.getContext().getAuthentication());
        return new ResponseEntity<>("usercontroller", HttpStatus.OK);
    }

    @GetMapping("super")
    @PreAuthorize("hasAnyRole('USER')") //endpointra kulon role
    public ResponseEntity getUserSuper() {
        return new ResponseEntity<>("superusercontroller", HttpStatus.OK);
    }

    @GetMapping("admin")
    public ResponseEntity getAdmin() {
        return new ResponseEntity<>("admincontroller", HttpStatus.OK);
    }
}
