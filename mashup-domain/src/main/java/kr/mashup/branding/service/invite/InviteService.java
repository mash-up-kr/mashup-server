package kr.mashup.branding.service.invite;

import kr.mashup.branding.domain.generation.Generation;
import kr.mashup.branding.domain.invite.Invite;
import kr.mashup.branding.domain.invite.exception.InviteNotFoundException;
import kr.mashup.branding.domain.member.Platform;
import kr.mashup.branding.repository.invite.InviteRepository;
import kr.mashup.branding.util.DateRange;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class InviteService {
    private final InviteRepository inviteRepository;

    public Invite create(
        final Platform platform,
        final Generation generation,
        final DateRange dateRange) {

        final Invite invite = Invite.of(platform, generation, dateRange);

        return inviteRepository.save(invite);
    }

    public Invite getOrThrow(final String inviteCode) {
        return inviteRepository.findByCode(inviteCode)
            .orElseThrow(InviteNotFoundException::new);
    }

    public Invite getOrThrow(final Long inviteCodeId){
        return inviteRepository.findById(inviteCodeId).orElseThrow(InviteNotFoundException::new);
    }

    public List<Invite> getAllByGeneration(final Generation generation){
        return inviteRepository.findAllByGeneration(generation);
    }

    public Invite getOrThrow(final Platform platform, final Generation generation){
        return inviteRepository.findByPlatformAndGeneration(platform, generation)
                .orElseThrow(InviteNotFoundException::new);
    }

    public Optional<Invite> getOrNull(final String inviteCode) {
        return inviteRepository.findByCode(inviteCode);
    }

    public void deleteCode(final Invite invite) {
        inviteRepository.delete(invite);
    }
}
