package com.qdtas.service;

import com.qdtas.dto.AddUserDto;
import com.qdtas.dto.JwtResponse;
import com.qdtas.dto.LoginDTO;
import com.qdtas.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UserService {

    public User create(AddUserDto rdt);

    public JwtResponse login(LoginDTO ld);

    public User getByEmail(String email);

    void verifyEmail(String token);

    public User getAuthenticatedUser();

    public User getById(long uId);


}
