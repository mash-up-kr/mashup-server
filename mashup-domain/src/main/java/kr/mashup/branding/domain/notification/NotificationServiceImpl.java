package kr.mashup.branding.domain.notification;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import kr.mashup.branding.domain.UnauthorizedException;
import kr.mashup.branding.domain.adminmember.AdminMember;
import kr.mashup.branding.domain.adminmember.AdminMemberNotFoundException;
import kr.mashup.branding.domain.adminmember.AdminMemberService;
import kr.mashup.branding.domain.notification.sms.SmsRequest;
import kr.mashup.branding.domain.notification.sms.SmsRequestService;
import kr.mashup.branding.domain.notification.sms.SmsSendRequestVo;
import kr.mashup.branding.domain.notification.sms.SmsSendResultRecipientVo;
import kr.mashup.branding.domain.notification.sms.SmsSendResultVo;
import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {
    private static final int SMS_MAX_LENGTH = 90;

    private final NotificationRepository notificationRepository;
    private final AdminMemberService adminMemberService;
    private final SmsRequestService smsRequestService;

    @Override
    @Transactional
    public NotificationDetailVo create(Long adminMemberId, SmsSendRequestVo smsSendRequestVo) {
        Assert.notNull(adminMemberId, "'adminMemberId' must not be null");
        Assert.notNull(smsSendRequestVo, "'smsSendVo' must not be null");
        AdminMember adminMember = adminMemberService.getByAdminMemberId(adminMemberId);
        validate(smsSendRequestVo, adminMember);
        Notification notification = notificationRepository.save(Notification.sms(adminMember, smsSendRequestVo));
        List<SmsRequest> smsRequests = smsRequestService.create(notification, smsSendRequestVo);
        return NotificationDetailVo.of(notification, smsRequests);
    }

    private void validate(SmsSendRequestVo smsSendRequestVo, AdminMember adminMember) {
        if (notificationRepository.existsByName(smsSendRequestVo.getName())) {
            throw new NotificationRequestInvalidException(
                "Notification name duplicated. name: " + smsSendRequestVo.getName());
        }
        if (!StringUtils.hasText(smsSendRequestVo.getContent())) {
            throw new NotificationRequestInvalidException("'content' must not be null, empty or blank");
        }
        if (smsSendRequestVo.getContent().toCharArray().length > SMS_MAX_LENGTH) {
            throw new NotificationRequestInvalidException(
                "'content' length must be less than or equal to " + SMS_MAX_LENGTH);
        }
        if (CollectionUtils.isEmpty(smsSendRequestVo.getRecipientApplicantIds())) {
            throw new NotificationRequestInvalidException("'recipientApplicantIds' must not be empty or null");
        }
        if (adminMember.getPhoneNumberRegistered() != Boolean.TRUE) {
            throw new NotificationRequestInvalidException(
                "Sender's phoneNumber must be registered to NHN Cloud Notification Service");
        }
        if (!StringUtils.hasText(adminMember.getPhoneNumber())) {
            throw new NotificationRequestInvalidException("Sender's phoneNumber must not be null, empty or blank");
        }
    }

    @Override
    @Transactional
    public NotificationDetailVo update(Long notificationId, SmsSendResultVo smsSendResultVo) {
        Assert.notNull(notificationId, "'notificationId' must not be null");
        Assert.notNull(smsSendResultVo, "'smsSendResultVo' must not be null");

        Notification notification = notificationRepository.findById(notificationId)
            .orElseThrow(() -> new NotificationNotFoundException(notificationId));

        if (smsSendResultVo == SmsSendResultVo.UNKNOWN) {
            // API 결과 못받은 경우
            notification.markToUnknown();
        }
        // API 결과 받은 경우
        notification.markResult(
            smsSendResultVo.getStatus(),
            smsSendResultVo.getResultId(),
            smsSendResultVo.getResultCode(),
            smsSendResultVo.getResultMessage()
        );
        Map<String, SmsSendResultRecipientVo> resultRecipientVoMap = smsSendResultVo.getRecipientResultVos()
            .stream()
            .collect(Collectors.toMap(SmsSendResultRecipientVo::getMessageId, it -> it));
        List<SmsRequest> smsRequests = notification.getSmsRequests();
        smsRequests.forEach(it -> it.setResult(resultRecipientVoMap.get(it.getMessageId())));
        return NotificationDetailVo.of(notification, smsRequests);
    }

    @Override
    public Page<Notification> getNotifications(Long adminMemberId, @Nullable String searchWord, Pageable pageable) {
        Assert.notNull(pageable, "'pageable' must not be null");
        try {
            adminMemberService.getByAdminMemberId(adminMemberId);
        } catch (AdminMemberNotFoundException e) {
            throw new UnauthorizedException();
        }
        if (searchWord == null) {
            return notificationRepository.findAll(pageable);
        }
        return notificationRepository.findByNameContainsOrSenderValueContains(searchWord, searchWord, pageable);
    }

    @Override
    public Notification getNotification(Long notificationId) {
        Assert.notNull(notificationId, "'notificationId' must not be null");
        return notificationRepository.findById(notificationId)
            .orElseThrow(NotificationNotFoundException::new);
    }
}
