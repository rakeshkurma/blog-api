package com.portfolio.service;
import com.portfolio.dtos.CommentDTO;
import com.portfolio.dtos.CommentResponseDTO;
import com.portfolio.dtos.PostResponseDTO;
import com.portfolio.entity.Comment;
import com.portfolio.entity.Post;
import com.portfolio.entity.User;
import com.portfolio.repo.CommentRepository;
import com.portfolio.repo.PostRepository;
import com.portfolio.repo.UserRepository;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.security.PrivateKey;
import java.time.LocalDateTime;

@Service
public class CommentService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    public CommentService(UserRepository userRepository, PostRepository postRepository, CommentRepository commentRepository) {
        this.userRepository = userRepository;
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
    }

    public CommentResponseDTO writeComment(CommentDTO commentDTO){

        Long authorId=commentDTO.getUserId();
        Long postId= commentDTO.getPostId();
        String content=commentDTO.getContent();

        User user= userRepository.findById(authorId)
                .orElseThrow(()->{
                    return new RuntimeException("User not Found");
                });
        Post post= postRepository.findById(postId)
                .orElseThrow(()->{
                    return new RuntimeException("Post not found");
                });

    Comment comment=new Comment();
    comment.setAuthor(user);
    comment.setPostId(post);
    comment.setContent(content);
    comment.setCreatedAt(LocalDateTime.now());

    Comment newcomment=commentRepository.save(comment);

        CommentResponseDTO commentResponseDTO = new CommentResponseDTO();

        commentResponseDTO.setContent(newcomment.getContent());
        commentResponseDTO.setPostId(newcomment.getPostId().getId());
        commentResponseDTO.setAuthorId(newcomment.getAuthor().getId());
        commentResponseDTO.setId(newcomment.getId());
        commentResponseDTO.setCreatedAt(newcomment.getCreatedAt());

        return commentResponseDTO;
    }


}
