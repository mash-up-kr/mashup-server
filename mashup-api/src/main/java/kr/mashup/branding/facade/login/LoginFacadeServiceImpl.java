package kr.mashup.branding.facade.login;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;

import kr.mashup.branding.config.jwt.JwtService;
import kr.mashup.branding.domain.applicant.Applicant;
import kr.mashup.branding.domain.applicant.ApplicantService;
import kr.mashup.branding.domain.applicant.GoogleLoginRequestVo;
import kr.mashup.branding.domain.applicant.LoginRequestVo;
import kr.mashup.branding.domain.applicant.LoginResponseVo;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LoginFacadeServiceImpl implements LoginFacadeService {

    private final ApplicantService applicantService;
    private final JwtService jwtService;

    @Value("${oauth.google.client.id}")
    private String clientId;

    @Override
    public LoginResponseVo login(GoogleLoginRequestVo googleLoginRequestVo) {
        GoogleIdToken googleIdToken = verifyToken(googleLoginRequestVo.getGoogleIdToken());
        Applicant applicant = getOrCreateApplicant(googleIdToken);
        return LoginResponseVo.of(
            jwtService.encode(applicant.getApplicantId()),
            applicant
        );
    }

    public GoogleIdToken verifyToken(String googleIdToken) {
        GoogleIdTokenVerifier googleIdTokenVerifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(),
            JacksonFactory.getDefaultInstance())
            .setAudience(Collections.singletonList(clientId))
            .build();
        try {
            return googleIdTokenVerifier.verify(googleIdToken);
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Applicant getOrCreateApplicant(GoogleIdToken googleIdToken) {
        return applicantService.join(LoginRequestVo.of(
            googleIdToken.getPayload().getEmail(),
            googleIdToken.getPayload().getSubject()));
    }
}