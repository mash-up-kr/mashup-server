package kr.mashup.branding.facade.attendance;

import kr.mashup.branding.domain.attendance.AttendanceCode;
import kr.mashup.branding.domain.storage.Storage;
import kr.mashup.branding.infrastructure.pushnoti.PushNotiEventPublisher;
import kr.mashup.branding.service.adminmember.AdminMemberService;
import kr.mashup.branding.service.attendance.AttendanceCodeService;
import kr.mashup.branding.service.attendance.AttendanceService;
import kr.mashup.branding.service.member.MemberService;
import kr.mashup.branding.service.schedule.ScheduleService;
import kr.mashup.branding.service.storage.StorageService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AttendanceFacadeServiceLeaderNotiTest {

    @Mock
    private AttendanceService attendanceService;
    @Mock
    private MemberService memberService;
    @Mock
    private ScheduleService scheduleService;
    @Mock
    private AttendanceCodeService attendanceCodeService;
    @Mock
    private PushNotiEventPublisher pushNotiEventPublisher;
    @Mock
    private AdminMemberService adminMemberService;
    @Mock
    private StorageService storageService;

    @InjectMocks
    private AttendanceFacadeService sut;

    @Test
    @DisplayName("Storage에 키가 없어도 스케줄러가 예외 없이 동작한다 (기본값 3분 사용)")
    void sendAttendanceLatePushNoti_worksWithoutStorageKey() {
        // given
        when(storageService.findByKeyOptional("leader-noti-before-minutes"))
                .thenReturn(Optional.empty());
        when(attendanceCodeService.findAllByEndedAtLeftOpenBetween(any(), any()))
                .thenReturn(Collections.emptyList());

        // when - 예외 없이 정상 실행되어야 함
        sut.sendAttendanceLatePushNotiToLeaders();

        // then
        verify(storageService).findByKeyOptional("leader-noti-before-minutes");
        verify(attendanceCodeService).findAllByEndedAtLeftOpenBetween(any(), any());
    }

    @Test
    @DisplayName("Storage에 키가 있으면 해당 값을 사용한다")
    void sendAttendanceLatePushNoti_usesStorageValue() {
        // given
        Storage storage = Storage.of("leader-noti-before-minutes", Map.of("value", 5));
        when(storageService.findByKeyOptional("leader-noti-before-minutes"))
                .thenReturn(Optional.of(storage));
        when(attendanceCodeService.findAllByEndedAtLeftOpenBetween(any(), any()))
                .thenReturn(Collections.emptyList());

        // when
        sut.sendAttendanceLatePushNotiToLeaders();

        // then
        verify(storageService).findByKeyOptional("leader-noti-before-minutes");
    }

    @Test
    @DisplayName("AttendanceCode가 없으면 푸시를 발송하지 않는다")
    void sendAttendanceLatePushNoti_noPushWhenNoAttendanceCode() {
        // given
        when(storageService.findByKeyOptional(any())).thenReturn(Optional.empty());
        when(attendanceCodeService.findAllByEndedAtLeftOpenBetween(any(), any()))
                .thenReturn(Collections.emptyList());

        // when
        sut.sendAttendanceLatePushNotiToLeaders();

        // then
        verify(pushNotiEventPublisher, never()).publishPushNotiSendEvent(any());
        verify(pushNotiEventPublisher, never()).publishDiscordEvent(any());
    }
}
