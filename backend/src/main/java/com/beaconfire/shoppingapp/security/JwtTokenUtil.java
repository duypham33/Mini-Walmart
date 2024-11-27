package com.beaconfire.shoppingapp.security;

import java.util.Base64;
import java.util.Date;
import java.util.List;

import com.beaconfire.shoppingapp.dtos.account.user.UserDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.PostConstruct;

@Component
@Slf4j
public class JwtTokenUtil {
    @Value("${security.jwt.token.secret-key}")
    private String secretKey;

    @PostConstruct
    protected void init() {
        // this is to avoid having the raw secret key available in the JVM
        this.secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    public String createToken(UserDto userDto) {
        Date now = new Date();
        Date validity = new Date(now.getTime() + 3600000 * 24); // 1 day

        Algorithm algorithm = Algorithm.HMAC256(secretKey);
        return JWT.create()
                .withSubject(userDto.getUsername())
                .withClaim("id", userDto.getId())
                .withClaim("email", userDto.getEmail())
                .withClaim("role", userDto.getRole())
                .withIssuedAt(now)
                .withExpiresAt(validity)
                .sign(algorithm);
    }

    public AuthUserDetail getSubject(String token) {
        try{
            Algorithm algorithm = Algorithm.HMAC256(secretKey);
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT decoded = verifier.verify(token);
            var user = UserDto.builder()
                    .id(decoded.getClaim("id").asLong())
                    .username(decoded.getSubject())
                    .email(decoded.getClaim("email").asString())
                    .role(decoded.getClaim("role").asString()).build();
            return AuthUserDetail.builder()
                    .user(user)
                    .build();
        }
        catch(RuntimeException e){
            log.error("***Validate jwt error: " + e.toString());
            return null;
        }
    }
}