package kr.mashup.branding.facade.login;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;

import kr.mashup.branding.config.jwt.JwtService;
import kr.mashup.branding.domain.UnauthorizedException;
import kr.mashup.branding.domain.applicant.Applicant;
import kr.mashup.branding.domain.applicant.ApplicantService;
import kr.mashup.branding.domain.applicant.JoinRequestVo;
import kr.mashup.branding.domain.applicant.LoginRequestVo;
import kr.mashup.branding.domain.applicant.LoginResponseVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class LoginFacadeServiceImpl implements LoginFacadeService {

    private final ApplicantService applicantService;
    private final JwtService jwtService;

    @Value("${oauth.google.client.id}")
    private String clientId;

    @Override
    public LoginResponseVo login(LoginRequestVo loginRequestVo) {
        GoogleIdToken googleIdToken = verifyToken(
            loginRequestVo.getGoogleIdToken()
        );
        Applicant applicant = getOrCreateApplicant(googleIdToken);
        return LoginResponseVo.of(
            jwtService.encode(applicant.getApplicantId()),
            applicant
        );
    }

    private GoogleIdToken verifyToken(String googleIdToken) {
        GoogleIdTokenVerifier googleIdTokenVerifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(),
            JacksonFactory.getDefaultInstance())
            .setAudience(Collections.singletonList(clientId))
            .build();
        try {
            return googleIdTokenVerifier.verify(googleIdToken);
        } catch (GeneralSecurityException | IOException e) {
            log.error("Failed to verify token", e);
            throw new UnauthorizedException("Failed to verify google id token", e);
        }
    }

    private Applicant getOrCreateApplicant(GoogleIdToken googleIdToken) {
        Assert.notNull(googleIdToken, "'googleIdToken' must not be null");
        Assert.notNull(googleIdToken.getPayload(), "'googleIdToken.payload' must not be null");
        return applicantService.join(JoinRequestVo.of(
            googleIdToken.getPayload().getEmail(),
            googleIdToken.getPayload().getSubject()));
    }
}