package com.example.hanami.services;

import com.example.hanami.model.User;
import com.example.hanami.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {
    @Autowired
    private  UserRepository userRepository;
    public void updateResetPasswordToken(String token,String email){
        //find user by email
        Optional<User> user=userRepository.findByEmail(email);
        if(user.isPresent()){
            user.get().setResetPasswordToken(token);
        }

    }
}
