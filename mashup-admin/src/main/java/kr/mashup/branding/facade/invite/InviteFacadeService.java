package kr.mashup.branding.facade.invite;

import kr.mashup.branding.domain.generation.Generation;
import kr.mashup.branding.domain.invite.Invite;
import kr.mashup.branding.domain.member.Platform;
import kr.mashup.branding.service.generation.GenerationService;
import kr.mashup.branding.service.invite.InviteService;
import kr.mashup.branding.ui.invite.InviteCodeResponse;
import kr.mashup.branding.ui.invite.InviteCreateRequest;
import kr.mashup.branding.ui.invite.InviteModifyRequest;
import kr.mashup.branding.util.DateRange;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class InviteFacadeService {

    private final GenerationService generationService;
    private final InviteService inviteService;

    public List<InviteCodeResponse> getAllByGeneration(final Integer generationNumber) {
        final Generation generation = generationService.getByNumberOrThrow(generationNumber);
        final List<Invite> invites = inviteService.getAllByGeneration(generation);

        return invites
                .stream()
                .map(InviteCodeResponse::from)
                .collect(Collectors.toList());
    }

    @Transactional
    public void createInvite(final InviteCreateRequest request) {

        final Platform targetPlatform = request.getPlatform();

        final Integer generationNumber = request.getGenerationNumber();
        final Generation generation = generationService.getByNumberOrThrow(generationNumber);

        final LocalDateTime validStartedAt = request.getValidStartedAt();
        final LocalDateTime validEndedAt = request.getValidEndedAt();
        final DateRange validDateRange = DateRange.of(validStartedAt, validEndedAt);

        inviteService.create(targetPlatform, generation, validDateRange);
    }

    @Transactional
    public void modifyInvite(final Long inviteCodeId, final InviteModifyRequest request) {
        final Invite invite = inviteService.getOrThrow(inviteCodeId);

        final LocalDateTime validStartedAt = request.getValidStartedAt();
        final LocalDateTime validEndedAt = request.getValidEndedAt();
        final DateRange validCodeDateRange = DateRange.of(validStartedAt, validEndedAt);

        invite.modifyValidCodeDateRange(validCodeDateRange);
    }

    @Transactional
    public void deleteInvite(final Long inviteCodeId) {
        final Invite invite = inviteService.getOrThrow(inviteCodeId);
        inviteService.deleteCode(invite);
    }
}
