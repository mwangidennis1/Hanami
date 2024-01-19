package com.example.hanami.services;

import com.example.hanami.model.User;
import com.example.hanami.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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

        if(user.isPresent()){
            user.get().setResetPasswordToken(token);
          // userRepository.save(user);
        }

    }
    //method to getPasswordToken
    public Optional<User> getByResetPasswordToken(String passwordToken){
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
