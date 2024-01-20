package com.example.hanami.repositories;

import com.example.hanami.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    @Query("""
SELECT u FROM User u WHERE u.email =:email
""")
    User findByEmail(String email);
   @Query("""
 SELECT u FROM User u WHERE u.resetPasswordToken =:token
""")
    User findByAndResetPasswordToken(String token) ;

}

