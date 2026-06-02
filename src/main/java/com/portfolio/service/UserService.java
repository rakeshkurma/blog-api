package com.portfolio.service;

import com.portfolio.dtos.UserRegistrationDTO;
import com.portfolio.entity.User;
import com.portfolio.enums.Role;
import com.portfolio.repo.UserRepository;
import org.springframework.stereotype.Service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository,PasswordEncoder passwordEncoder){
        this.userRepository=userRepository;
        this.passwordEncoder=passwordEncoder;
    }
 public User registerUser(UserRegistrationDTO registrationDTO){

        String userName=registrationDTO.getUsername();
        String email=registrationDTO.getEmail();
        String password=registrationDTO.getPassword();

        if(userRepository.existsByUsername(userName)){
            throw new RuntimeException("Username already taken");
        }

     if(userRepository.existsByEmail(email)){
         throw new RuntimeException("Email is already in use");
     }

     User newUser=new User();
     newUser.setUsername(userName);
     newUser.setEmail(email);
     newUser.setRole(Role.ROLE_USER);

     String hashedPwd = passwordEncoder.encode(password);

     newUser.setPassword(hashedPwd);
     return userRepository.save(newUser);
 }

}
