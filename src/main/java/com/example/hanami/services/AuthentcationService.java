package com.example.hanami.services;

import com.example.hanami.config.JwtService;
import com.example.hanami.dto.AuthenticationRequest;
import com.example.hanami.dto.AuthenticationResponse;
import com.example.hanami.dto.RegistrationRequest;
import com.example.hanami.model.User;
import com.example.hanami.repositories.UserRepository;
import com.example.hanami.securityconfig.UserConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthentcationService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    public static  String myJwt;

    public AuthenticationResponse register(RegistrationRequest request){
        User user= User.builder()
                .name(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .build();
        userRepository.save(user);
        var token= jwtService.generateToken(new UserConfig(user));
        return AuthenticationResponse.builder()
                .token(token)
                .build();

    }
    public AuthenticationResponse authenticate(AuthenticationRequest request){
          authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                  request.getEmail(),
                  request.getPassword()
          ));
        var user=userRepository.findByEmail(request.getEmail());
        if(user == null){
            throw new UsernameNotFoundException("could not find user");
        }
        var token = jwtService.generateToken(new UserConfig(user));
        return AuthenticationResponse.builder()
                .token(token)
                .build();

    }

}
