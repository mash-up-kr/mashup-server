package kr.mashup.branding.service.mashong;

import kr.mashup.branding.domain.ResultCode;
import kr.mashup.branding.domain.exception.NotFoundException;
import kr.mashup.branding.domain.mashong.PlatformMashongLevel;
import kr.mashup.branding.repository.mashong.PlatformMashongLevelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PlatformMashongLevelService {

    private final PlatformMashongLevelRepository platformMashongLevelRepository;

    public PlatformMashongLevel findByLevel(int level) {
        return platformMashongLevelRepository.findByLevel(level)
                .orElseThrow(() -> new NotFoundException(ResultCode.PLATFORM_MASHONG_LEVEL_NOT_FOUND));
    }
}
