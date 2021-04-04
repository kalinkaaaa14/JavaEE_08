package com.kupchyk.javaee.dto;

import lombok.Data;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
public class BookDto {
    @Pattern(regexp = "((978-)|(979-)|ISBN )?\\d-\\d{2}-\\d{6}-\\d", message = "ISBN is incorrect")
    private String isbn;

    @NotBlank(message = "Title must be filled")
    private String title;

    @NotBlank(message = "Author must be filled")
    private String author;
}
