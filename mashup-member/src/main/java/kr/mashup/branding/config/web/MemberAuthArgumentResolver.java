package kr.mashup.branding.config.web;

import kr.mashup.branding.domain.ResultCode;
import kr.mashup.branding.domain.exception.BadRequestException;
import kr.mashup.branding.security.JwtService;
import kr.mashup.branding.security.MemberAuth;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

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
        Assert.notNull(authorization, "Authorization Header must be not null");
        if(!authorization.startsWith("Bearer ")){
            throw new BadRequestException(ResultCode.BAD_REQUEST, "유효하지 않은 토큰입니다.");
        }
        String token = authorization.substring(7);
        Long memberId = jwtService.decode(token);
        if(memberId==null){
            throw new BadRequestException(ResultCode.BAD_REQUEST, "유효하지 않은 토큰입니다.");
        }
        return MemberAuth.of(memberId);
    }
}
