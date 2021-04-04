package com.kupchyk.javaee.dto;

import lombok.Data;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
public class UserDto {
    @Pattern(regexp = "\\w+", message = "Only Latin letters and numbers are allowed!")
    private String username;

    @Size(min = 8, max = 20, message = "The password must be at least 8 chars and at most 20.")
    private CharSequence password;

    private String email;


}