package kr.mashup.branding.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Map;

@Slf4j
@Service
public class JwtService {
    @Value("${jwt.secretKey}")
    private String secretKey;

    public static final String CLAIM_NAME_MEMBER_ID = "MemberId";
    public static final String CLAIM_NAME_MEMBER_GENERATION_ID = "MemberGenerationId";

    private Algorithm algorithm;
    private JWTVerifier jwtVerifier;

    public String encode(Long memberId, Long memberGenerationId) {

        LocalDateTime expiredAt = LocalDateTime.now().plusWeeks(4L);

        Date date = java.sql.Timestamp.valueOf(expiredAt);

        return JWT.create()
            .withClaim(CLAIM_NAME_MEMBER_ID, memberId)
            .withClaim(CLAIM_NAME_MEMBER_GENERATION_ID, memberGenerationId)
            .withExpiresAt(date)
            .sign(algorithm);
    }

    public Map<String, Long> decode(String token) {
        try {
            DecodedJWT jwt = jwtVerifier.verify(token);
            return Map.of(
                CLAIM_NAME_MEMBER_ID, jwt.getClaim(CLAIM_NAME_MEMBER_ID).asLong(),
                CLAIM_NAME_MEMBER_GENERATION_ID, jwt.getClaim(CLAIM_NAME_MEMBER_GENERATION_ID).asLong()
            );
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