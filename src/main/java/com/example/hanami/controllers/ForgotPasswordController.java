package com.example.hanami.controllers;

import com.example.hanami.dto.AuthenticationRequest;
import com.example.hanami.model.User;
import com.example.hanami.services.GmailNotification;
import com.example.hanami.services.UserService;
import com.example.hanami.utility.EmailNotification;
import com.example.hanami.utility.Utility;
import jakarta.servlet.http.HttpServletRequest;
import jdk.jfr.Percentage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.Optional;


@RestController
public class ForgotPasswordController {


    @Autowired
    private UserService userService;
    @Autowired
    private GmailNotification gmailNotification;
    @PostMapping("/forgot_password/{email}")
    public  void processForgottenPassword(@PathVariable String email, HttpServletRequest request){
        String passwordToken=RandomStringUtils.randomAlphanumeric(45);

        userService.updateResetPasswordToken(passwordToken,email);
        String resetPasswordLink= Utility.getSiteURL(request)+"/reset_password?token="+passwordToken;
        gmailNotification.sendEmail(email,resetPasswordLink);

    }
    @GetMapping("/reset_password")
    public ResponseEntity<String> resetPassword(@Param(value = "token") String token, @RequestBody AuthenticationRequest request){
        User user = userService.getByResetPasswordToken(token);
        if(user !=null){
            String newPassword=request.getPassword();
            userService.updatePassword(user,newPassword);
            ResponseEntity.ok("its good");
        }
        return ResponseEntity.ok("not found");
    }

}
