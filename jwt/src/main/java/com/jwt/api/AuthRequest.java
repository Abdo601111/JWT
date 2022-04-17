package com.jwt.api;

import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;

@Data
@Builder
public class AuthRequest {

    @Email
    @Length(min = 5,max = 50)
    private String email;
    @Length(min = 5,max = 10)
    private String password;
}
