package kr.mashup.branding.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

import kr.mashup.branding.ui.ApiResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final String ROLE_NAME = "APPLICANT";
    private final ObjectMapper objectMapper;

    @Override
    public void configure(WebSecurity web) throws Exception {
        web
            .ignoring()
            .mvcMatchers(
                "/error",
                "/favicon.ico",
                "/swagger-ui/**",
                "/webjars/springfox-swagger-ui/**",
                "/swagger-resources/**",
                "/v1/api-docs",
                "/h2-console/**"
            );
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.antMatcher("/**")
            .authorizeRequests()
            .antMatchers("/hello").permitAll()
            .antMatchers("/api/v1/test/**").permitAll()
            .antMatchers("/api/v1/applicants/login").permitAll()
            .antMatchers("/api/v1/**").hasAuthority(ROLE_NAME);
        http.csrf().disable();
        http.logout().disable();
        http.formLogin().disable();
        http.httpBasic().disable();
        http.requestCache().disable();
        http.addFilterAt(tokenPreAuthFilter(), AbstractPreAuthenticatedProcessingFilter.class);
        http.sessionManagement().disable();
        http.cors().disable();
        http.exceptionHandling()
            .authenticationEntryPoint((request, response, authException) -> {
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                objectMapper.writeValue(
                    response.getOutputStream(),
                    ApiResponse.failure("Unauthorized", "인증에 실패하였습니다.")
                );
            })
            .accessDeniedHandler((request, response, accessDeniedException) -> {
                response.setStatus(HttpStatus.FORBIDDEN.value());
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                objectMapper.writeValue(
                    response.getOutputStream(),
                    ApiResponse.failure("Forbidden", "허용되지 않은 접근입니다.")
                );
            });
    }

    @Bean
    TokenPreAuthFilter tokenPreAuthFilter() {
        TokenPreAuthFilter tokenPreAuthFilter = new TokenPreAuthFilter();
        tokenPreAuthFilter.setAuthenticationManager(new ProviderManager(preAuthTokenProvider()));
        return tokenPreAuthFilter;
    }

    @Bean
    PreAuthTokenProvider preAuthTokenProvider() {
        return new PreAuthTokenProvider();
    }
}

