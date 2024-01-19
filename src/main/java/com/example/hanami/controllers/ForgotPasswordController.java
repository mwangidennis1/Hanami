package com.example.hanami.controllers;

import com.example.hanami.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.apache.commons.lang3.RandomStringUtils;


@RestController
public class ForgotPasswordController {
    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private UserService userService;
    @PostMapping("/forgot_password")
    public String processForgottenPassword(@PathVariable String email){
        String passwordToken=RandomStringUtils.randomAlphanumeric(45);
    }
}
