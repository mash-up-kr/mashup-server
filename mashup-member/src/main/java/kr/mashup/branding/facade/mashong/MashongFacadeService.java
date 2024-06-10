package kr.mashup.branding.facade.mashong;

import kr.mashup.branding.domain.mashong.MissionStrategyType;
import kr.mashup.branding.domain.member.MemberGeneration;
import kr.mashup.branding.service.mashong.MashongAttendanceService;
import kr.mashup.branding.service.member.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MashongFacadeService {
    private final MashongAttendanceService mashongAttendanceService;
    private final MashongMissionFacadeService mashongMissionFacadeService;
    private final MemberService memberService;

    @Transactional
    public Boolean attend(Long memberGenerationId) {
        MemberGeneration memberGeneration = memberService.findByMemberGenerationId(memberGenerationId);
        Boolean result = mashongAttendanceService.attend(memberGeneration);
        if (result) {
            mashongMissionFacadeService.apply(MissionStrategyType.MASHONG_ATTENDANCE_MISSION, memberGeneration, 1L);
        }
        return result;
    }

}
