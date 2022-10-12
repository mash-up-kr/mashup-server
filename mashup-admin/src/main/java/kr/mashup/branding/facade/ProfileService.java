package kr.mashup.branding.facade;

import java.util.Arrays;

import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ProfileService {
    private final static String LOCAL = "local";
    private final static String DEVELOP = "develop";
    private final static String PRODUCTION = "production";

    private final Environment environment;

    public boolean isLocal() {
        return Arrays.asList(environment.getActiveProfiles()).contains(LOCAL);
    }

    public boolean isDevelop() {
        return Arrays.asList(environment.getActiveProfiles()).contains(DEVELOP);
    }

    public boolean isProduction() {
        return Arrays.asList(environment.getActiveProfiles()).contains(PRODUCTION);
    }
}
