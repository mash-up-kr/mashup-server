package kr.mashup.branding.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Service
@RequiredArgsConstructor
public class ServerTimeService {
    private final ProfileService profileService;

    public LocalDateTime getCurrentServerTimeByProfile(){
        if(profileService.isProduction()){
            return LocalDateTime.now(ZoneId.of("Asia/Seoul"));
        }

        return LocalDateTime.now(ZoneId.of("Asia/Seoul")); // 개발 서버나 로컬환경에서 동적으로 시간 조정하면 좋을듯
    }

    public static LocalDateTime getServerTimeOfSeoul(){
        return LocalDateTime.now(ZoneId.of("Asia/Seoul"));
    }
}
