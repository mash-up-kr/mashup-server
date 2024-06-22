package kr.mashup.branding.service.mashong;

import kr.mashup.branding.domain.mashong.MashongAttendance;
import kr.mashup.branding.domain.member.MemberGeneration;
import kr.mashup.branding.domain.member.Platform;
import kr.mashup.branding.repository.mashong.MashongAttendanceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MashongAttendanceService {
    private final int MAX_ATTEND = 4;

    private final MashongAttendanceRepository mashongAttendanceRepository;

    public Boolean attend(MemberGeneration memberGeneration) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime start = now.with(LocalTime.of(0, 0, 0));
        LocalDateTime end = start.plusDays(1L);
        List<MashongAttendance> mashongAttendanceList = mashongAttendanceRepository.findAllByMemberGenerationIdAndAttendanceAtBetween(memberGeneration.getId(), start, end);
        if (isNextAttendEnable(mashongAttendanceList, now)) {
            mashongAttendanceRepository.save(MashongAttendance.of(memberGeneration, now));
            return true;
        }
        return false;
    }

    public Long distinctPlatformMemberAttendanceCount(Platform platform) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime start = now.with(LocalTime.of(0, 0, 0));
        LocalDateTime end = start.plusDays(1L);
        List<MashongAttendance> mashongAttendanceList = mashongAttendanceRepository.findAllByMemberGeneration_PlatformAndAttendanceAtBetween(platform, start, end);
        return mashongAttendanceList.stream().map(mashongAttendance -> mashongAttendance.getMemberGeneration().getId()).distinct().count();
    }

    private Boolean isNextAttendEnable(List<MashongAttendance> mashongAttendanceList, LocalDateTime now) {
        Optional<MashongAttendance> latestAttendance = mashongAttendanceList.stream().max(Comparator.comparing(MashongAttendance::getAttendanceAt));
        return mashongAttendanceList.size() < MAX_ATTEND &&
            latestAttendance.map(mashongAttendance -> Duration.between(mashongAttendance.getAttendanceAt(), now).toMinutes() > 30).orElse(true);
    }
}
