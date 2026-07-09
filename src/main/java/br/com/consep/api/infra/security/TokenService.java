package br.com.consep.api.infra.security;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import br.com.consep.api.user.entity.User;

@Service
public class TokenService {
    @Value("${api.security.token.secret}")
    private String secret;

    public String validateToken(String token) {
        Algorithm algorithm = Algorithm.HMAC256(secret);
        return JWT
                .require(algorithm)
                .withIssuer("consep-api")
                .build()
                .verify(token)
                .getSubject();
    }

    public String createToken(User user) {
        Algorithm algorithm = Algorithm.HMAC256(secret);
        return JWT
                .create()
                .withIssuer("consep-api")
                .withSubject(user.getLogin())
                .withClaim("role", user.getRole().toString())
                .withExpiresAt(genExpirationDate())
                .sign(algorithm);
    }

    private Instant genExpirationDate() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }
}
