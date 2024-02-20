package com.qdtas.service.impl;

import com.qdtas.dto.AddUserDto;
import com.qdtas.dto.JwtResponse;
import com.qdtas.dto.LoginDTO;
import com.qdtas.entity.EmailVerification;
import com.qdtas.entity.User;
import com.qdtas.exception.EmailAlreadyRegisteredException;
import com.qdtas.exception.UserNotFoundException;
import com.qdtas.repository.EmailServiceRepository;
import com.qdtas.repository.UserRepository;
import com.qdtas.security.CustomUserDetails;
import com.qdtas.security.JwtHelper;
import com.qdtas.service.UserService;
import com.qdtas.utility.FileUtils;
import com.qdtas.utility.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Date;


@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository urp;
    @Autowired
    private Environment env;
    @Autowired
    private EmailService ems;
    @Autowired
    private EmailServiceRepository erp;
    @Autowired
    private JwtHelper jwtHelper;
    @Autowired
    PasswordEncoder pnc;

    @Autowired
    private AuthenticationManager manager;

    public User getById(long uId) {
        return urp.findById(uId).orElseThrow(() -> new UserNotFoundException("User Not Found With Given ID"));
    }

    @Override
    public User getByEmail(String email) {
        System.out.println(email);
        User u = urp.findByEmail(email);
        if (u != null) {
            return u;
        } else {
            throw new UserNotFoundException("User Not Found with given Email");
        }
    }

    @Override
    public User create(AddUserDto rdt) {
        User savedU = null;
        try {
            User user = getByEmail(rdt.getEmail());
            if (user != null) {
                throw new EmailAlreadyRegisteredException("Email is Already Registered");
            }
        } catch (UserNotFoundException e) {
            User u = new User();
            u.setUsername(rdt.getUserName());
            u.setEmail(rdt.getEmail());
            u.setPassword(pnc.encode(rdt.getPassword()));
            u.setFirstName(rdt.getFirstName());
            u.setLastName(rdt.getLastName());
            u.setJoinDate(new Date());
            u.setRole(Role.ROLE_USER.name());
            u.setEmailVerified(false);
            savedU = urp.save(u);
            ems.sendVerificationEmail(savedU.getEmail());
        }
        return savedU;
    }

    public JwtResponse login(LoginDTO ld) {
        manager.authenticate(new UsernamePasswordAuthenticationToken(
                ld.getEmail(), ld.getPassword())
        );
        User loginUser = urp.findByEmail(ld.getEmail());
        CustomUserDetails userPrincipal = new CustomUserDetails(loginUser);
        return new JwtResponse(loginUser, jwtHelper.generateToken(userPrincipal));
    }

    @Override
    public void verifyEmail(String token) {
        try {
            EmailVerification emv = erp.findById(token).orElseThrow(() -> new RuntimeException("Email Not Found"));
            User u = getByEmail(emv.getEmail());
            u.setEmailVerified(true);
            urp.save(u);
            erp.deleteById(token);
        } catch (Exception e) {

        }
    }

    public User updateProfilePhoto(MultipartFile photo) {
        User targetUser = getAuthenticatedUser();
        if (!photo.isEmpty() && photo.getSize() > 0) {
            String uploadDir = env.getProperty("upload.user.profiles");
            String oldPhotoName = targetUser.getProfilePhoto();
            String newPhotoName = FileUtils.nameFile(photo);
            String newPhotoUrl = env.getProperty("upload.user.profiles") + File.separator + newPhotoName;
            try {
                if (oldPhotoName == null) {
                    FileUtils.saveNewFile(uploadDir, newPhotoName, photo);
                    targetUser.setProfilePhoto(newPhotoUrl);
                } else {
                    FileUtils.updateFile(uploadDir, oldPhotoName, newPhotoName, photo);
                    targetUser.setProfilePhoto(newPhotoUrl);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return urp.save(targetUser);
    }

    public User updateEmail(String email) {
        User u =  getAuthenticatedUser();

        if (!email.equalsIgnoreCase(u.getEmail())) {
            try {
                User duplicateUser = getByEmail(email);
                if (duplicateUser != null) {
                    throw new EmailAlreadyRegisteredException("Email Already Registered With Other User");
                } else {
                    throw new UserNotFoundException();
                }
            } catch (UserNotFoundException e) {
                u.setEmail(email);
                u.setEmailVerified(false);
                User updatedUser = urp.save(u);
                ems.sendVerificationEmail(email);
                return updatedUser;
            }
        } else {
            throw new EmailAlreadyRegisteredException("Email is Same as Previous one");
        }
    }

    public final User getAuthenticatedUser() {
        String authUserEmail = SecurityContextHolder.getContext().getAuthentication().getName().toString();
        return getByEmail(authUserEmail);
    }
}
