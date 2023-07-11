package com.example.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@SpringBootApplication
public class TestApplication {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenProvider jwtProvider;
    public static void main(String[] args) {
        SpringApplication.run(TestApplication.class, args);
    }

    @RequestMapping("/hello")
    public String hello() {
        return "Hello world";
    }
    @PostMapping("/authenticate")
    public String auth(@RequestBody  @Valid User user) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
            return jwtProvider.createToken(user.getUsername());
        } catch (AuthenticationException e){
            System.out.println("Log in failed for user, " + user.getUsername());
        }

        return "";
    }

}
