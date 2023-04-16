package kr.mashup.branding.ui.invite;

import kr.mashup.branding.EmptyResponse;
import kr.mashup.branding.facade.invite.InviteFacadeService;
import kr.mashup.branding.ui.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/invite-code")
@RequiredArgsConstructor
public class InviteController {


    private final InviteFacadeService inviteFacadeService;

    @GetMapping
    public ApiResponse<?> getInviteCodes(
            @RequestParam(defaultValue = "13", required = false) Integer generationNumber
    ) {

        final List<InviteCodeResponse> responses = inviteFacadeService.getAllByGeneration(generationNumber);

        return ApiResponse.success(responses);
    }

    @PostMapping
    public ApiResponse<EmptyResponse> createInviteCode(
            @RequestBody InviteCreateRequest inviteCreateRequest
    ) {

        inviteFacadeService.createInvite(inviteCreateRequest);

        return ApiResponse.success();
    }

    @PostMapping("{inviteCodeId}")
    public ApiResponse<EmptyResponse> modifyInviteCode(
            @PathVariable Long inviteCodeId,
            @RequestBody InviteModifyRequest inviteModifyRequest
    ) {

        inviteFacadeService.modifyInvite(inviteCodeId, inviteModifyRequest);

        return ApiResponse.success();
    }

    @DeleteMapping("{inviteCodeId}")
    public ApiResponse<EmptyResponse> deleteInviteCode(
            @PathVariable Long inviteCodeId
    ) {

        inviteFacadeService.deleteInvite(inviteCodeId);

        return ApiResponse.success();
    }
}
