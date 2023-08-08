package com.lanre.school_management.service;

import com.lanre.school_management.appEnums.Role;
import com.lanre.school_management.configuration.JwtService;
import com.lanre.school_management.dto.AuthenticationResponse;
import com.lanre.school_management.dto.AuthenticationRequest;
import com.lanre.school_management.dto.RegisterRequest;


import com.lanre.school_management.entity.AppUser;
import com.lanre.school_management.repository.AppUserRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final AppUserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;


    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );
        var user = userRepository.findByEmail(request.getEmail()).orElseThrow(() -> new UsernameNotFoundException("The User with this email not found"));
        var jwtToken = jwtService.generateToken((UserDetails) user);

        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse register(RegisterRequest request) {
        var user = AppUser
                .builder()
                .email(request.getEmail())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();
        userRepository.save(user);

        var jwtToken = jwtService.generateToken((UserDetails) user);

        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
}
