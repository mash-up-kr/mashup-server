package kr.mashup.branding.facade.member;

import kr.mashup.branding.service.member.MemberProfileService;
import kr.mashup.branding.ui.member.request.MemberProfileRequest;
import kr.mashup.branding.ui.member.response.MemberProfileResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberProfileFacadeService {

    private final MemberProfileService memberProfileService;

    @Transactional
    public Boolean updateMyProfile(Long memberId, MemberProfileRequest request) {
        memberProfileService.updateOrSave(
                memberId,
                request.getBirthDate(),
                request.getJob(),
                request.getCompany(),
                request.getIntroduction(),
                request.getResidence(),
                request.getSocialNetworkServiceLink(),
                request.getGithubLink(),
                request.getPortfolioLink(),
                request.getBlogLink(),
                request.getLinkedInLink()
        );

        return true;
    }

    @Transactional
    public MemberProfileResponse getMyProfile(Long memberId) {
        var profile = memberProfileService.findOrSave(memberId);

        return MemberProfileResponse.from(profile);
    }
}
