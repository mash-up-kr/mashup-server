package kr.mashup.branding.facade.notification;

import kr.mashup.branding.domain.adminmember.entity.AdminMember;
import kr.mashup.branding.domain.applicant.Applicant;
import kr.mashup.branding.domain.application.Application;
import kr.mashup.branding.domain.generation.Generation;
import kr.mashup.branding.domain.notification.Notification;
import kr.mashup.branding.domain.notification.sms.SmsRequest;
import kr.mashup.branding.domain.notification.sms.vo.SmsSendRequestVo;
import kr.mashup.branding.domain.notification.sms.vo.SmsSendResultVo;
import kr.mashup.branding.service.adminmember.AdminMemberService;
import kr.mashup.branding.service.applicant.ApplicantService;
import kr.mashup.branding.service.application.ApplicationService;
import kr.mashup.branding.service.generation.GenerationService;
import kr.mashup.branding.service.notification.NotificationEvent;
import kr.mashup.branding.service.notification.NotificationEventPublisher;
import kr.mashup.branding.service.notification.NotificationEventType;
import kr.mashup.branding.service.notification.NotificationService;
import kr.mashup.branding.ui.notification.vo.NotificationDetailResponse;
import kr.mashup.branding.ui.notification.vo.NotificationSimpleResponse;
import kr.mashup.branding.ui.notification.vo.SmsSendRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class NotificationFacadeService {
    private final AdminMemberService adminMemberService;
    private final NotificationService notificationService;
    private final ApplicantService applicantService;
    private final GenerationService generationService;
    private final NotificationEventPublisher notificationEventPublisher;
    private final ApplicationService applicationService;

    public void createSmsNotification(Long adminMemberId, Integer generationNumber, SmsSendRequest smsSendRequest) {

        final AdminMember adminMember = adminMemberService.getByAdminMemberId(adminMemberId);
        final List<Applicant> recipientApplicants = applicantService.getApplicants(smsSendRequest.getApplicantIds());

        // 요청 정보 생성 -> Notification CREATED 상태
        final SmsSendRequestVo smsSendRequestVo
            = SmsSendRequestVo.of(smsSendRequest.getName(), smsSendRequest.getContent());
        final Generation generation = generationService.getByNumberOrThrow(generationNumber);
        final Notification notification
            = notificationService.createSmsNotification(adminMember, generation, recipientApplicants, smsSendRequestVo);
        // 문자 발송 비동기 이벤트 퍼블리싱
        final NotificationEvent notificationEvent
            = NotificationEvent.of(notification.getNotificationId(), NotificationEventType.CREATED);
        notificationEventPublisher.publishNotificationEvent(notificationEvent);
    }

    public void updateSmsStatus(Long notificationId, SmsSendResultVo smsSendResultVo) {
        notificationService.updateSmsStatus(notificationId, smsSendResultVo);
    }

    public Page<NotificationSimpleResponse> getNotifications(
        Long adminMemberId,
        Integer generationNumber,
        String searchWord,
        Pageable pageable) {

        return notificationService
            .getNotifications(adminMemberId, searchWord, pageable)
            .map(NotificationSimpleResponse::from);
    }

    public NotificationDetailResponse getNotificationDetail(Long adminMemberId, Long notificationId) {

        final Notification notification = notificationService.getNotification(notificationId);

        final List<Applicant> recipients = notification
            .getSmsRequests()
            .stream()
            .map(it -> it.getRecipientApplicant()).collect(Collectors.toList());

        final Map<Applicant, Application> applicationMap = applicationService.getApplications(recipients);

        return NotificationDetailResponse.of(notification, applicationMap);
    }


    /**
     * 지원자 1명에 대한 문자 발송 이력 조회
     *
     * @param adminMemberId 어드민 멤버 식별자
     * @param applicantId   지원자 식별자
     * @return 지원자에게 발송한 문자 발송 이력
     */
    public List<SmsRequest> getSmsRequests(Long adminMemberId, Long applicantId) {

        Assert.notNull(adminMemberId, "'adminMemberId' must not be null");
        return notificationService.getSmsRequestsByApplicantId(applicantId);
    }


}
