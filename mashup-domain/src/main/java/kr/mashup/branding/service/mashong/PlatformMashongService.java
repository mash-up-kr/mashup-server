package kr.mashup.branding.service.mashong;

import kr.mashup.branding.domain.ResultCode;
import kr.mashup.branding.domain.exception.NotFoundException;
import kr.mashup.branding.domain.generation.Generation;
import kr.mashup.branding.domain.mashong.PlatformMashong;
import kr.mashup.branding.domain.mashong.PlatformMashongLevel;
import kr.mashup.branding.domain.member.Platform;
import kr.mashup.branding.repository.mashong.PlatformMashongLevelRepository;
import kr.mashup.branding.repository.mashong.PlatformMashongRepository;
import kr.mashup.branding.service.mashong.dto.LevelUpResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PlatformMashongService {

    private final PlatformMashongRepository platformMashongRepository;

    public PlatformMashong findByPlatformAndGeneration(Platform platform, Generation generation) {
        return platformMashongRepository.findByPlatformAndGeneration(platform, generation)
                .orElseThrow(() -> new NotFoundException(ResultCode.PLATFORM_MASHONG_NOT_FOUND));
    }

    public void feedPopcorn(PlatformMashong platformMashong, Long popcornCount) {
        platformMashong.feed(popcornCount);
    }

    public LevelUpResult levelUp(Platform platform, Generation generation, PlatformMashongLevel goalLevel) {
        PlatformMashong platformMashong = findByPlatformAndGeneration(platform, generation);

        if (platformMashong.isSameLevel(goalLevel)) {
            return LevelUpResult.DUPLICATED;
        }

        return platformMashong.levelUp(goalLevel);
    }
}
