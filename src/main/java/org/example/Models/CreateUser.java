package org.example.Models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class CreateUserResponse {
    private String success;
    private String user;
    private String accessToken;
    private String refreshToken;
    private String message;
}
