package com.qdtas.security;

import com.qdtas.entity.User;
import com.qdtas.exception.UserNotFoundException;
import com.qdtas.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CustomUserDetailsService {

    @Autowired
    private UserRepository urp;

    public  UserDetailsService userDetailsService(){
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
                User user = urp.findByEmail(email);
                if (user==null) {
                    throw new UserNotFoundException("No user exists with this email.");
                } else {
                    return new CustomUserDetails(user);
                }
            }
        };
    }

}
