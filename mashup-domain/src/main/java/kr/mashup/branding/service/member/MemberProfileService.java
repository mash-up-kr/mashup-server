package kr.mashup.branding.service.member;

import kr.mashup.branding.domain.member.MemberProfile;
import kr.mashup.branding.repository.member.MemberProfileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@Slf4j
@RequiredArgsConstructor
public class MemberProfileService {

    private final MemberProfileRepository memberProfileRepository;

    public void updateOrSave(
            Long memberId,
            LocalDate birthDate,
            String job,
            String company,
            String introduction,
            String residence,
            String socialNetworkServiceLink,
            String githubLink,
            String portfolioLink,
            String blogLink,
            String linkedInLink
    ) {
        var profile = memberProfileRepository.findByMemberId(memberId)
                .orElseGet(() -> memberProfileRepository.save(MemberProfile.from(memberId)));
        profile.update(
                birthDate,
                job,
                company,
                introduction,
                residence,
                socialNetworkServiceLink,
                githubLink,
                portfolioLink,
                blogLink,
                linkedInLink
        );
    }

    public MemberProfile findOrSave(Long memberId) {
        return memberProfileRepository.findByMemberId(memberId)
                .orElseGet(() -> memberProfileRepository.save(MemberProfile.from(memberId)));
    }
}
