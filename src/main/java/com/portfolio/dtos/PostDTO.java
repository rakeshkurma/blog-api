package com.portfolio.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostDTO {

    @NotBlank(message = "Title should not be Empty!!")
    private String title;

    @NotBlank(message = "Content should not be Empty")
    private String content;

    @NotNull(message = "Author Id Cannot be Null")
    private Long authorId;

}
