package com.portfolio.dtos;

import com.portfolio.entity.Post;
import com.portfolio.entity.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommentDTO {

    @NotBlank(message = "Content should not be null")
    private String content;

    private LocalDateTime createdAt;

    @NotNull(message="Comment must need User")
    private Long userId;

    @NotNull(message = "Post Id is Mandatory for Commenting")
    private Long postId;

}
