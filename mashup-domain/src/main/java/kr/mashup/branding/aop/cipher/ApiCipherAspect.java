package kr.mashup.branding.aop.cipher;

import kr.mashup.branding.domain.exception.BadRequestException;
import kr.mashup.branding.util.CipherUtil;
import kr.mashup.branding.util.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Aspect
@Slf4j
@Component
public class ApiCipherAspect {

    @Value("${cipherKey}")
    private String cipherKey;

    @Value("${cipherTime}")
    private String cipherTime;

    @Before(value = "@annotation(checkApiCipherTime)")
    public void checkApiCipherTime(CheckApiCipherTime checkApiCipherTime){
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String encryptedKey = request.getHeader("cipher");

        if(checkApiCipherTime.alwaysRequired()){
            if(!StringUtils.hasText(encryptedKey)){
                throw new BadRequestException();
            }
            checkClientTimeDifference(encryptedKey);
        }else{
            if(StringUtils.hasText(encryptedKey)){
                checkClientTimeDifference(encryptedKey);
            }
        }
    }

    private void checkClientTimeDifference(String auth){

        final String clientEpochTime = CipherUtil.decryptAES128(auth, cipherKey);
        final LocalDateTime clientTime = DateUtil.fromEpochString(clientEpochTime);
        final LocalDateTime serverTime = LocalDateTime.now();
        final Long timeDifference = ChronoUnit.SECONDS.between(clientTime, serverTime);
        log.info(timeDifference.toString());
        if(timeDifference > Long.parseLong(cipherTime)) {
            throw new BadRequestException();
        }
    }

}
