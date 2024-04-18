package com.example.loginservice.controller;

import com.example.loginservice.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/login")
public class LoginController {
    @Autowired
    private RestTemplate restTemplate;

    private static final String REGISTER_SERVICE_URL = "http://localhost:8081/register";

//    @GetMapping("/checkLogin/{username}/{password}")
//    public ResponseEntity<String> checkLogin(@PathVariable("username") String username, @PathVariable("password") String password) {
//        String url = REGISTER_SERVICE_URL + "/users/" + username + "/" + password;
//        ResponseEntity<User> response = restTemplate.getForEntity(url, User.class);
//
//        if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
//            return ResponseEntity.ok("Login successful");
//        } else {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Login failed");
//        }
//    }
    @GetMapping("/checkLogin/{username}/{password}")
    public ResponseEntity<Object> checkLogin(@PathVariable("username") String username, @PathVariable("password") String password) {
        String url = REGISTER_SERVICE_URL + "/users/" + username + "/" + password;
        ResponseEntity<User> response = restTemplate.getForEntity(url, User.class);

        Map<String, Object> responseData = new HashMap<>();
        if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
            responseData.put("user", response.getBody());
            return ResponseEntity.ok(responseData);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(responseData);
        }
    }
    @GetMapping("/login/{username}/{password}")
    public ResponseEntity<String> login(@PathVariable("username") String username, @PathVariable("password") String password) {
        ResponseEntity<Object> checkResponse = checkLogin(username, password);
        if (checkResponse.getStatusCode() == HttpStatus.OK) {
            return ResponseEntity.ok("Login successful");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Login failed");
        }
    }
}
