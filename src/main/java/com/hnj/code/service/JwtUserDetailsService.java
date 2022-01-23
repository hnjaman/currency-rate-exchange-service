package com.hnj.code.service;

import java.util.ArrayList;

import com.hnj.code.model.Response.RegistrationResponse;
import com.hnj.code.model.request.UserRequest;
import com.hnj.code.repository.UserRepository;
import com.hnj.code.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    private static final Logger LOGGER = LoggerFactory.getLogger(JwtUserDetailsService.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder bcryptEncoder;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(email);
        if (user == null) {
            LOGGER.error(email+" User not found");
            throw new UsernameNotFoundException(email+" User not found");
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), new ArrayList<>());
    }

    public RegistrationResponse userRegistration(UserRequest userRequest) {
        RegistrationResponse registrationResponse = RegistrationResponse.builder().email(userRequest.getEmail()).status(false).build();
        User newUser = new User();
        newUser.setUsername(userRequest.getEmail());
        newUser.setPassword(bcryptEncoder.encode(userRequest.getPass()));
        newUser = userRepository.save(newUser);

        if (newUser.getId() != null) {
            registrationResponse.setMessage("Success");
            registrationResponse.setStatus(true);
        }

        return registrationResponse;
    }
}
