package kr.mashup.branding.facade.login;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;

import kr.mashup.branding.ui.applicant.vo.ApplicantResponse;
import kr.mashup.branding.ui.applicant.vo.LoginResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;

import kr.mashup.branding.config.jwt.JwtService;
import kr.mashup.branding.domain.applicant.Applicant;
import kr.mashup.branding.service.applicant.ApplicantService;
import kr.mashup.branding.domain.applicant.vo.JoinRequestVo;
import kr.mashup.branding.domain.exception.UnauthorizedException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class LoginFacadeService {

    private final ApplicantService applicantService;
    private final JwtService jwtService;

    @Value("${oauth.google.client.id}")
    private String clientId;

    @Transactional
    public LoginResponse login(String googleToken) {

        final GoogleIdToken googleIdToken = verifyToken(googleToken);

        final Applicant applicant = getOrCreateApplicant(googleIdToken);
        final ApplicantResponse applicantResponse = ApplicantResponse.from(applicant);
        final String accessToken = jwtService.encode(applicant.getApplicantId());

        return LoginResponse.of(accessToken, applicantResponse);
    }

    private GoogleIdToken verifyToken(String googleIdToken) {
        final GoogleIdTokenVerifier googleIdTokenVerifier
            = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), GsonFactory.getDefaultInstance())
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
