package kr.mashup.branding.facade.birthday;

import kr.mashup.branding.domain.generation.Generation;
import kr.mashup.branding.domain.member.Member;
import kr.mashup.branding.domain.pushnoti.vo.BirthdayRecipientVo;
import kr.mashup.branding.domain.pushnoti.vo.BirthdaySenderVo;
import kr.mashup.branding.infrastructure.pushnoti.PushNotiEventPublisher;
import kr.mashup.branding.service.birthday.BirthdayService;
import kr.mashup.branding.service.generation.GenerationService;
import kr.mashup.branding.service.member.MemberBirthdayDto;
import kr.mashup.branding.service.member.MemberProfileService;
import kr.mashup.branding.service.member.MemberService;
import kr.mashup.branding.ui.member.response.MemberBirthdaysResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BirthdayFacadeService {
    private final MemberProfileService memberProfileService;
    private final MemberService memberService;
    private final GenerationService generationService;
    private final BirthdayService birthdayService;

    private final PushNotiEventPublisher pushNotiEventPublisher;

    /**
     * 다가오는 생일 멤버 정보를 조회 <p>
     * 이미 생일 카드를 보낸 멤버들의 ID를 조회
     *
     * @param memberId 조회하는 멤버 ID
     * @param days     다가오는 생일을 확인할 기간 (일 수)
     * @return 다가오는 생일자 정보
     */
    @Transactional(readOnly = true)
    public MemberBirthdaysResponse getUpcomingBirthdays(Long memberId, Integer days) {
        Member member = memberService.findMemberById(memberId);
        Generation generation = generationService.getCurrentGeneration(member);

        Map<LocalDate, List<MemberBirthdayDto>> upcomingBirthdays = calculateUpcomingBirthdays(member, days, generation);
        Set<Long> sentMemberIds = birthdayService.getSentBirthdayCardMemberIds(memberId, generation.getId());

        return MemberBirthdaysResponse.of(sentMemberIds, upcomingBirthdays);
    }

    private Map<LocalDate, List<MemberBirthdayDto>> calculateUpcomingBirthdays(Member member, Integer days, Generation generation) {
        LocalDate now = LocalDate.now();
        return memberProfileService.findByBirthDateBetween(now, now.plusDays(days), generation)
            .stream()
            .filter(birthdayDto -> birthdayDto.getMemberId() != member.getId())
            .collect(Collectors.groupingBy(MemberBirthdayDto::getBirthDate));
    }

    /**
     * 생일자인 경우, 생일 리마인드 푸시
     */
    @Scheduled(cron = "0 0 24 * * ?")
    @Transactional(readOnly = true)
    public void sendBirthdayPushNotiForRecipient() {
        generationService.getAllActiveInAt(LocalDate.now())
            .forEach(generation -> pushNotiEventPublisher.publishPushNotiSendEvent(
                new BirthdayRecipientVo(memberService.getAllByBirthdayRecipient(generation))
            ));
    }

    /**
     * 생일자가 아닌 경우, 생일 축하 독려 푸시
     */
    @Scheduled(cron = "0 0 12 * * ?")
    @Transactional(readOnly = true)
    public void sendBirthdayPushNotiForSender() {
        generationService.getAllActiveInAt(LocalDate.now())
            .forEach(generation -> pushNotiEventPublisher.publishPushNotiSendEvent(
                new BirthdaySenderVo(memberService.getAllByBirthdaySender(generation))
            ));
    }
}
