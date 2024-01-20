package com.example.hanami.services;

import com.example.hanami.model.User;
import com.example.hanami.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {
    @Autowired
    private  UserRepository userRepository;
    //method to update restPassword Token
    public void updateResetPasswordToken(String token,String email){
        //find user by email
        var user=userRepository.findByEmail(email);

        if(user != null){
            user.setResetPasswordToken(token);
            System.out.println(user);
          userRepository.save(user);
        }else{
            throw new UsernameNotFoundException("could not find the user");
        }

    }
    //method to getPasswordToken
    public User getByResetPasswordToken(String passwordToken){
        return userRepository.findByAndResetPasswordToken(passwordToken);
    }
    //method to updatePassword
    public void updatePassword(User user,String newPassword){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encodedPassword= encoder.encode(newPassword);
        user.setPassword(encodedPassword);
        user.setResetPasswordToken(null);
        userRepository.save(user);

    }

}
