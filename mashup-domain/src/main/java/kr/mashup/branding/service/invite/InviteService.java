package kr.mashup.branding.service.invite;

import kr.mashup.branding.domain.generation.Generation;
import kr.mashup.branding.domain.invite.Invite;
import kr.mashup.branding.domain.invite.exception.InviteNotFoundException;
import kr.mashup.branding.domain.member.Platform;
import kr.mashup.branding.repository.invite.InviteRepository;
import kr.mashup.branding.util.DateRange;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class InviteService {
    private final InviteRepository inviteRepository;

    public Invite create(Platform platform, Generation generation, DateRange dateRange) {
        Invite invite = Invite.of(platform, generation, dateRange);

        return inviteRepository.save(invite);
    }

    public Invite getOrThrow(String inviteCode) {
        return inviteRepository.findByCode(inviteCode)
            .orElseThrow(InviteNotFoundException::new);
    }

    public Invite getOrThrow(Platform platform, Generation generation){
        return inviteRepository.findByPlatformAndGeneration(platform, generation)
                .orElseThrow(InviteNotFoundException::new);
    }

    public Optional<Invite> getOrNull(String inviteCode) {
        return inviteRepository.findByCode(inviteCode);
    }

}
