package kr.mashup.branding.service.mashong;

import kr.mashup.branding.domain.ResultCode;
import kr.mashup.branding.domain.exception.BadRequestException;
import kr.mashup.branding.domain.mashong.MashongPopcorn;
import kr.mashup.branding.repository.mashong.MashongPopcornRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class MashongPopcornService {
    private final MashongPopcornRepository mashongPopcornRepository;

    @Transactional
    public void givePopcorn(Long memberGenerationId, Long value) {
        MashongPopcorn mashongPopcorn = findByMemberGenerationId(memberGenerationId);
        increasePopcorn(mashongPopcorn.getId(), value);
    }

    @Transactional
    public void increasePopcorn(Long mashongPopcornId, Long value) {
        mashongPopcornRepository.increasePopcorn(mashongPopcornId, value);
    }

    @Transactional
    public MashongPopcorn findByMemberGenerationId(Long memberGenerationId) {
        return mashongPopcornRepository.findByMemberGenerationId(memberGenerationId)
            .orElseGet(() -> mashongPopcornRepository.save(MashongPopcorn.of(memberGenerationId)));
    }

    @Transactional
    public MashongPopcorn decreasePopcorn(Long memberGenerationId, Long popcornCount) {
        final MashongPopcorn mashongPopcorn = findByMemberGenerationId(memberGenerationId);

        boolean isSuccess = mashongPopcorn.decrease(popcornCount);
        if (!isSuccess) {
            throw new BadRequestException(ResultCode.MASHONG_POPCORN_INSUFFICIENT);
        }

        return mashongPopcorn;
    }
}
