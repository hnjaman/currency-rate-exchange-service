package com.hnj.code.controller;

import com.hnj.code.config.JwtTokenUtil;
import com.hnj.code.model.Response.JwtResponse;
import com.hnj.code.model.Response.RegistrationResponse;
import com.hnj.code.model.request.UserRequest;
import com.hnj.code.service.JwtUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
public class JwtAuthenticationController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private JwtUserDetailsService userDetailsService;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public JwtResponse createAuthenticationToken(@RequestBody UserRequest userRequest) throws Exception {
        authenticate(userRequest.getEmail(), userRequest.getPass());
        final UserDetails userDetails = userDetailsService.loadUserByUsername(userRequest.getEmail());
        final String token = jwtTokenUtil.generateToken(userDetails);

        return JwtResponse.builder().accessToken(token).build();
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public RegistrationResponse saveUser(@RequestBody UserRequest userRequest) throws Exception {
        return userDetailsService.userRegistration(userRequest);
    }

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
}
