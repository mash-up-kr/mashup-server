package kr.mashup.branding.service.member;

import kr.mashup.branding.domain.generation.Generation;
import kr.mashup.branding.domain.member.MemberProfile;
import kr.mashup.branding.repository.member.MemberProfileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.MonthDay;
import java.util.List;

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

    public List<MemberBirthdayDto> findByBirthDateBetween(MonthDay startDate, MonthDay endDate, Generation generation) {
        return memberProfileRepository.retrieveByBirthDateBetween(startDate, endDate, generation);
    }

    public boolean isBirthdayToday(long memberId) {
        return memberProfileRepository.findByMemberId(memberId)
            .filter(memberProfile -> MonthDay.from(LocalDate.now()).equals(MonthDay.from(memberProfile.getBirthDate())))
            .isPresent();
    }
}
