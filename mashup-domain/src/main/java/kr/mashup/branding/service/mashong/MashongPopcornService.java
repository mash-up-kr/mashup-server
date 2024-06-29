package kr.mashup.branding.service.mashong;

import kr.mashup.branding.domain.mashong.MashongPopcorn;
import kr.mashup.branding.repository.mashong.MashongPopcornRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
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
}
