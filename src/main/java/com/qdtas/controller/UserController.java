package com.qdtas.controller;

import com.qdtas.dto.AddUserDto;
import com.qdtas.dto.JsonMessage;
import com.qdtas.dto.JwtResponse;
import com.qdtas.dto.LoginDTO;
import com.qdtas.entity.User;
import com.qdtas.security.JwtHelper;
import com.qdtas.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.*;

@RestController
@RequestMapping("/user")
@CrossOrigin
@Tag(name = "1. User")
public class UserController {

    @Autowired
    private UserService ussr;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtHelper jwt;

    @ApiOperation(value = "Add User", position = 1)
    @Operation(
            description = "the verification link will be sent on email.",
            summary = "1. Add User(by Admin)",
            responses = {
                    @ApiResponse(
                    description = "Created",
                    responseCode = "201",
                    content = @io.swagger.v3.oas.annotations.media.Content
                    ),
                    @ApiResponse(
                    description = "Email Already Registered",
                    responseCode = "400",
                    content = @io.swagger.v3.oas.annotations.media.Content
                    )
            }
    )
    @PostMapping("/add")
    public ResponseEntity<?> register(@Valid @RequestBody AddUserDto rdt) {
        User su = ussr.create(rdt);
        return new ResponseEntity<>(su, HttpStatus.CREATED);
    }

    @Operation(
            description = "paste the token in token field",
            summary = "2. Verify email",
            responses = {
                    @ApiResponse(
                            description = "Verification Successful",
                            responseCode = "200",
                            content = @io.swagger.v3.oas.annotations.media.Content

                    ),
                    @ApiResponse(
                            description = "Bad Request",
                            responseCode = "400",
                            content = @io.swagger.v3.oas.annotations.media.Content
                    ),
            }
    )
    @GetMapping("/verify/{token}")
    public ResponseEntity<?> verifyEmail(@PathVariable("token") String token) {
        ussr.verifyEmail(token);
        return new ResponseEntity<>(new JsonMessage("Verification Successfull"),HttpStatus.OK);
    }

    @Operation(
            description = "use token for authorization",
            summary = "3. Login",
            responses = {
                    @ApiResponse(
                            description = "User and authorization token",
                            responseCode = "200",
                            content = @io.swagger.v3.oas.annotations.media.Content
                    ),
                    @ApiResponse(
                            description = "Bad Credentials",
                            responseCode = "401",
                            content = @io.swagger.v3.oas.annotations.media.Content
                    ),
            }
    )
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginDTO ldt) {
        try{
            JwtResponse luser = ussr.login(ldt);
            return new ResponseEntity<>(luser, HttpStatus.OK);
        }
        catch(Exception e){
            return new ResponseEntity<>(new JsonMessage("Bad Credentials"),HttpStatus.UNAUTHORIZED);
        }
    }

    @Hidden
    @GetMapping("/{userId}")
    public ResponseEntity<?> getUserById(@PathVariable("userId") Long userId) {
        User targetUser = ussr.getById(userId);
        return new ResponseEntity<>(targetUser, HttpStatus.OK);
    }

    @Operation(
            description = "Enable User(by Admin)",
            summary = "enable a user",
            responses = {
                    @ApiResponse(
                            description = "User Enabled Successfully",responseCode = "200",content = @io.swagger.v3.oas.annotations.media.Content
                    ),
                    @ApiResponse(
                            description = "User Not Found With Id",responseCode = "400",content = @io.swagger.v3.oas.annotations.media.Content
                    )
            }
    )
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/enableUser/{userId}")
    public ResponseEntity<?> enableUserById(@PathVariable("userId") Long userId) {
        ussr.enableUser(userId);
        JsonMessage j=new JsonMessage("User Enabled Successfully");
        return new ResponseEntity<>(j, HttpStatus.OK);
    }
}
