package io.github.andrey.rest.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TokenDTO {
    private String login;
    private String token;

    public TokenDTO(String login, String token) {
    }
}
