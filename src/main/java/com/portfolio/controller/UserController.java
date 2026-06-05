package com.portfolio.controller;

import com.portfolio.dtos.*;
import com.portfolio.entity.Post;
import com.portfolio.entity.User;
import com.portfolio.repo.CommentRepository;
import com.portfolio.dtos.CommentResponseDTO;
import com.portfolio.security.JwtUtil;
import com.portfolio.service.CommentService;
import com.portfolio.service.PostService;
import com.portfolio.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;
    private final CommentService commentService;
    private final PostService postService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public UserController(UserService userService, CommentService commentService, PostService postService, AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        this.userService = userService;
        this.commentService = commentService;
        this.postService = postService;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@Valid @RequestBody UserRegistrationDTO registrationDTO) {

        User savedUser = userService.registerUser(registrationDTO);
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }

    @PostMapping("/post")
    public ResponseEntity<PostResponseDTO> WritePost(@Valid @RequestBody PostDTO postDTO){
        PostResponseDTO post= postService.writePost(postDTO);
        return new ResponseEntity<>(post,HttpStatus.CREATED);
    }

    @GetMapping("/getposts")
    public ResponseEntity<List<PostResponseDTO>>  GetAllPosts(){
        List<PostResponseDTO> posts=  postService.getAllPosts();
        return new ResponseEntity<>(posts,HttpStatus.OK);
    }
@PostMapping("/postComment")
public ResponseEntity<CommentResponseDTO> PostComment(@Valid @RequestBody CommentDTO commentDTO){
        CommentResponseDTO commentResponseDTO=commentService.writeComment(commentDTO);
        return new ResponseEntity<>(commentResponseDTO,HttpStatus.OK);
}

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@Valid @RequestBody UserLoginDTO userLoginDTO) {

        String userName = userLoginDTO.getUsername();
        String password = userLoginDTO.getPassword();

        log.info("Username : " + userName);

        UsernamePasswordAuthenticationToken authToken =
                new UsernamePasswordAuthenticationToken(userName, password);
        Authentication authentication = authenticationManager.authenticate(authToken);

        String token = jwtUtil.generateToken(authentication.getName());

        log.info("Token Generated: " + token);

        return new ResponseEntity<>(token, HttpStatus.OK);
    }

}
