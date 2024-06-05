package org.example.Models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.security.Principal;

@Data
@AllArgsConstructor
@Builder
public class CreateUser {
    private String success;
    private String user;
    private String accessToken;
    private String refreshToken;
    private String message;

}
