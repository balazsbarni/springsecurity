package com.spring.securitydemo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.securitydemo.model.AppUser;
import com.sun.net.httpserver.Headers;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import static com.spring.securitydemo.security.SecurityConstants.*;

@RestController
@RequestMapping("api/dummy")
public class ExampleController {

    private AuthenticationManager authenticationManager;

    public ExampleController(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("login")
    public ResponseEntity login(@RequestBody AppUser loginRequest) throws IOException, ServletException {
        Authentication auth =  attemptAuthentication(loginRequest);
        if (auth.isAuthenticated()) {
            return successfulAuthentication(auth);
        }
        return null;
    }

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



    public Authentication attemptAuthentication(AppUser appUser) throws AuthenticationException {
            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(appUser.getUsername(), appUser.getPassword()));
    }


    protected ResponseEntity successfulAuthentication(Authentication authResult) throws IOException, ServletException {
        //inkabb utc
        ZonedDateTime expirationTime = ZonedDateTime.now().plus(EXPIRATION_TIME, ChronoUnit.MILLIS);
        //authResultbol kivesz uzer, es belatesz adat a tokenbe
        String token = Jwts.builder().setSubject(((User)authResult.getPrincipal()).getUsername()).claim("hello", "Barni")
                .setExpiration(Date.from(expirationTime.toInstant()))
                .signWith(SignatureAlgorithm.HS256, SECRET)
                .compact();
        //response.getWriter().write(token);
        HttpHeaders header = new HttpHeaders();
        header.add(HEADER_STRING, TOKEN_PREFIX + token);
        return new ResponseEntity(header, HttpStatus.OK);
    }
}
