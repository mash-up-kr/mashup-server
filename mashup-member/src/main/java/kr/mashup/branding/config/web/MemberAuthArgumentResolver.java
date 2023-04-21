package kr.mashup.branding.config.web;

import kr.mashup.branding.domain.exception.UnauthorizedException;
import kr.mashup.branding.security.JwtService;
import kr.mashup.branding.security.MemberAuth;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class MemberAuthArgumentResolver implements HandlerMethodArgumentResolver {

    private final JwtService jwtService;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return MemberAuth.class.isAssignableFrom(parameter.getParameterType());
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        String authorization = webRequest.getHeader("Authorization");
        //Assert.notNull(authorization, "Authorization Header must be not null");
        if (authorization == null || !authorization.startsWith("Bearer ")) {
            throw new UnauthorizedException();
        }
        String token = authorization.substring(7);
        Map<String, Long> decodedToken = jwtService.decode(token);
        Long memberId = decodedToken.get(JwtService.CLAIM_NAME_MEMBER_ID);
        Long memberGenerationId = decodedToken.get(JwtService.CLAIM_NAME_MEMBER_GENERATION_ID);
        if (memberId == null || memberGenerationId == null) {
            throw new UnauthorizedException();
        }
        return MemberAuth.of(memberId, memberGenerationId);
    }
}
