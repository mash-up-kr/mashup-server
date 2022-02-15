package kr.mashup.branding.config.security;

import java.util.Arrays;
import java.util.stream.Collectors;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

import kr.mashup.branding.config.jwt.JwtService;
import kr.mashup.branding.domain.adminmember.AdminMemberService;
import kr.mashup.branding.domain.adminmember.Position;
import kr.mashup.branding.ui.ApiResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    public static final String[] AUTHORITY_NAMES = Arrays.stream(Position.values()).map(Enum::name)
        .collect(Collectors.toList()).toArray(new String[Position.values().length]);

    private final ObjectMapper objectMapper;
    private final AdminMemberService adminMemberService;
    private final JwtService jwtService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.antMatcher("/api/v1/**")
            .authorizeRequests()
            .antMatchers("/api/v1/admin-members/login").permitAll()
            .anyRequest().hasAnyAuthority(AUTHORITY_NAMES)
            .and()
            .csrf().disable()
            .logout().disable()
            .formLogin().disable()
            .httpBasic().disable()
            .requestCache().disable()
            .addFilterAt(tokenPreAuthFilter(), AbstractPreAuthenticatedProcessingFilter.class)
            .sessionManagement().disable()
            .cors().disable()
            .exceptionHandling()
            .authenticationEntryPoint((request, response, authException) -> {
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                objectMapper.writeValue(
                    response.getOutputStream(),
                    //TODO ReturnCode 관리 방법 논의
                    ApiResponse.failure("Unauthorized", "인증에 필요한 요청입니다")
                );
            })
            .accessDeniedHandler((request, response, accessDeniedException) -> {
                response.setStatus(HttpStatus.FORBIDDEN.value());
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                objectMapper.writeValue(
                    response.getOutputStream(),
                    //TODO ReturnCode 관리 방법 논의
                    ApiResponse.failure("Forbidden", "허용되지 않은 접근입니다.")
                );
            });
    }

    @Override
    public void configure(WebSecurity webSecurity) {
        webSecurity.ignoring().antMatchers(
            "/h2-console/**",
            "/error",
            "/favicon.ico",
            "/swagger-ui/**",
            "/swagger-resources/**",
            "/v2/api-docs",
            "/hello"
        );
    }

    @Bean
    TokenPreAuthFilter tokenPreAuthFilter() {
        TokenPreAuthFilter tokenPreAuthFilter = new TokenPreAuthFilter();
        tokenPreAuthFilter.setAuthenticationManager(new ProviderManager(preAuthTokenProvider()));
        return tokenPreAuthFilter;
    }

    @Bean
    PreAuthTokenProvider preAuthTokenProvider() {
        return new PreAuthTokenProvider(
            adminMemberService,
            jwtService
        );
    }
}