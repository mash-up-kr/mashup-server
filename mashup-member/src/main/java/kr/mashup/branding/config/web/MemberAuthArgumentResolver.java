package kr.mashup.branding.config.web;

import kr.mashup.branding.domain.exception.UnauthorizedException;
import kr.mashup.branding.security.JwtService;
import kr.mashup.branding.security.MemberAuth;
import kr.mashup.branding.service.member.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class MemberAuthArgumentResolver implements HandlerMethodArgumentResolver {

    private final JwtService jwtService;
    private final MemberService memberService;
    public static final String MDC_MEMBER_ID = "memberId";

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return MemberAuth.class.isAssignableFrom(parameter.getParameterType());
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        String token = extractTokenFromHeader(webRequest);
        Map<String, Long> decodedToken = jwtService.decode(token);

        Long memberId = getMemberId(decodedToken);
        Long memberGenerationId = getMemberGenerationId(memberId);

        MDC.put(MDC_MEMBER_ID, String.valueOf(memberId));
        return MemberAuth.of(memberId, memberGenerationId);
    }

    private String extractTokenFromHeader(NativeWebRequest webRequest) {
        String authorization = webRequest.getHeader(HttpHeaders.AUTHORIZATION);
        if (authorization == null || !authorization.startsWith("Bearer ")) {
            throw new UnauthorizedException();
        }
        return authorization.substring(7);
    }

    private Long getMemberId(Map<String, Long> decodedToken) {
        Long memberId = decodedToken.get(JwtService.CLAIM_NAME_MEMBER_ID);
        if (memberId == null) {
            throw new UnauthorizedException();
        }
        return memberId;
    }

    private Long getMemberGenerationId(long memberId) {
        return memberService.getLatestMemberGeneration(memberId).getId();
    }
}
