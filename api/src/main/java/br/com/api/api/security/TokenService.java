package br.com.api.api.security;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;

import br.com.api.api.model.user.User;

@Service
public class TokenService {
    private String secret = "Vaquinha";

    public String generateToken(User user){
        try{
            Algorithm key = Algorithm.HMAC256(secret);
            String token = JWT.create()
                .withIssuer("auth")
                .withSubject(user.getLogin())
                .withExpiresAt(genExpirationDate())
                .sign(key);
            return token;
        } catch(JWTCreationException exception){
            throw new RuntimeException("Erro de geração no token", exception);
        }
    }

    public String validateToken(String token){
        try{
            Algorithm key = Algorithm.HMAC256(secret);
            return JWT.require(key)
                .withIssuer("auth")
                .build()
                .verify(token)
                .getSubject();
        }catch(JWTVerificationException exception){
            return ""+exception;
        }
    }

    private Instant genExpirationDate(){
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }
}
