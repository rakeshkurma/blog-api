package com.portfolio.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommentResponseDTO {

    private  Long Id;
    private String content;
    private Long authorId;
    private Long postId;
    private LocalDateTime createdAt;

}
