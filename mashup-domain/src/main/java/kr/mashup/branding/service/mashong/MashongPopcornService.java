package kr.mashup.branding.service.mashong;

import kr.mashup.branding.domain.mashong.MashongPopcorn;
import kr.mashup.branding.repository.mashong.MashongPopcornRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MashongPopcornService {
    private final MashongPopcornRepository mashongPopcornRepository;

    public void increasePopcorn(Long mashongPopcornId, Long value) {
        mashongPopcornRepository.increasePopcorn(mashongPopcornId, value);
    }

    public MashongPopcorn findByMemberGenerationId(Long memberGenerationId) {
        return mashongPopcornRepository.findByMemberGenerationId(memberGenerationId)
            .orElseGet(() -> mashongPopcornRepository.save(MashongPopcorn.of(memberGenerationId)));
    }
}
