package com.portfolio.service;

import com.portfolio.controller.UserController;
import com.portfolio.dtos.PostDTO;
import com.portfolio.dtos.PostResponseDTO;
import com.portfolio.entity.Post;
import com.portfolio.entity.User;
import com.portfolio.repo.PostRepository;
import com.portfolio.repo.UserRepository;
import org.springframework.stereotype.Service;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    private static final Logger log=LoggerFactory.getLogger(PostService.class);

    public PostService(PostRepository postRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    public List<PostResponseDTO> getAllPosts(){

        log.info("Fetching all posts");

        List<Post> allPosts=postRepository.findAll();

        return allPosts.stream().map(post -> {
            PostResponseDTO postResponseDTO = new PostResponseDTO();

            postResponseDTO.setTitle(post.getTitle());
            postResponseDTO.setContent(post.getContent());
            postResponseDTO.setId(post.getId());
            postResponseDTO.setAuthorName(post.getAuthor().getUsername());
            return postResponseDTO;
        }).collect(Collectors.toList());
    }

    public PostResponseDTO writePost(PostDTO postDTO){

        Long authorId=postDTO.getAuthorId();
        String title = postDTO.getTitle();
        String content=postDTO.getContent();

        log.info("Author Id {} ",authorId);

        User user= userRepository.findById(authorId)
                .orElseThrow(()->{

                    log.error("Post creation failed : Author ID {} not found",authorId);
                    return new RuntimeException("Author not found");
                });

        Post post=new Post();
        post.setTitle(title);
        post.setContent(content);

        post.setAuthor(user);

        Post savedPost=postRepository.save(post);

        PostResponseDTO postResponseDTO = new PostResponseDTO();

        postResponseDTO.setTitle(post.getTitle());
        postResponseDTO.setContent(post.getContent());
        postResponseDTO.setId(post.getId());
        postResponseDTO.setAuthorName(post.getAuthor().getUsername());

        log.info("Sucessfully post Created with id :",savedPost.getId());

        return postResponseDTO;
    }

}
