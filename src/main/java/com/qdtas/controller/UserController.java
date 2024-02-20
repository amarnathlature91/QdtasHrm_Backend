package com.qdtas.controller;

import com.qdtas.dto.AddUserDto;
import com.qdtas.dto.JwtResponse;
import com.qdtas.dto.LoginDTO;
import com.qdtas.entity.User;
import com.qdtas.security.JwtHelper;
import com.qdtas.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@CrossOrigin
public class UserController {

    @Autowired
    private UserService ussr;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    JwtHelper jwt;

    @PostMapping("/add")
    public ResponseEntity<?> register(@RequestBody AddUserDto rdt) {
        User su = ussr.create(rdt);
        return new ResponseEntity<>(su, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO ldt) {
        try{
            JwtResponse luser = ussr.login(ldt);
            return new ResponseEntity<>(luser, HttpStatus.OK);
        }
        catch(Exception e){
            return new ResponseEntity<>("Bad Credentials",HttpStatus.FORBIDDEN);
        }
    }
    @GetMapping("/verify/{token}")
    public ResponseEntity<?> verifyEmail(@PathVariable("token") String token) {
        ussr.verifyEmail(token);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<?> getUserById(@PathVariable("userId") Long userId) {
        User targetUser = ussr.getById(userId);
        return new ResponseEntity<>(targetUser, HttpStatus.OK);
    }

}
