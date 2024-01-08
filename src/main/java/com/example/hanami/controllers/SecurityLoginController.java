package com.example.hanami.controllers;

import com.example.hanami.dto.AuthenticationRequest;
import com.example.hanami.dto.AuthenticationResponse;
import com.example.hanami.dto.RegistrationRequest;
import com.example.hanami.services.AuthentcationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class SecurityLoginController {


    @Autowired
    private final AuthentcationService authentcationService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody
                                                           RegistrationRequest request){

       return  ResponseEntity.ok(authentcationService.register(request));
    }
    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> response(@RequestBody
                                                           AuthenticationRequest request){
       return ResponseEntity.ok(authentcationService.authenticate(request))
               ;
    }


}
