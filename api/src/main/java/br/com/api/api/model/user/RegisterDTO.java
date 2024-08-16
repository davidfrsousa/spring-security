package br.com.api.api.model.user;

public record RegisterDTO(String login, String password, UserRole role) {
    
}
