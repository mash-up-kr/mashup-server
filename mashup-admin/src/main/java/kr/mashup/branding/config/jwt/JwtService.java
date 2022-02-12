package kr.mashup.branding.config.jwt;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String secretKey;

    private final String CLAIM_ADMIN_MEMBER_ID = "AdminMemberId";
    private Algorithm algorithm;
    private JWTVerifier jwtVerifier;

    public String encode(Long adminMemberId) {
        return JWT.create()
            .withClaim(CLAIM_ADMIN_MEMBER_ID, adminMemberId)
            .sign(algorithm);
    }

    public Long decode(String token) {
        try {
            return jwtVerifier.verify(token).getClaim(CLAIM_ADMIN_MEMBER_ID).asLong();
        } catch (JWTVerificationException e) {
            log.warn("Failed to decode jwt. token: {}", token, e);
            return null;
        }
    }

    @PostConstruct
    private void init() {
        algorithm = Algorithm.HMAC256(secretKey);
        jwtVerifier = JWT.require(algorithm).build();
    }

}
