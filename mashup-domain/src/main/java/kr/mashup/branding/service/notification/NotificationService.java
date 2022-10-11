package kr.mashup.branding.service.notification;

import kr.mashup.branding.domain.adminmember.entity.AdminMember;
import kr.mashup.branding.domain.applicant.Applicant;
import kr.mashup.branding.domain.notification.Notification;
import kr.mashup.branding.domain.notification.exception.NotificationNotFoundException;
import kr.mashup.branding.domain.notification.exception.NotificationRequestInvalidException;
import kr.mashup.branding.domain.notification.sms.SmsRequest;
import kr.mashup.branding.domain.notification.sms.vo.SmsSendRequestVo;
import kr.mashup.branding.domain.notification.sms.vo.SmsSendResultRecipientVo;
import kr.mashup.branding.domain.notification.sms.vo.SmsSendResultVo;
import kr.mashup.branding.repository.notification.NotificationRepository;
import kr.mashup.branding.repository.notification.sms.SmsRequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class NotificationService {
    private final NotificationRepository notificationRepository;
    private final SmsRequestRepository smsRequestRepository;

    @Transactional
    public Notification createSmsNotification(
        AdminMember adminMember,
        List<Applicant> recipientApplicants,
        SmsSendRequestVo smsSendRequestVo) {

        Assert.notNull(smsSendRequestVo, "'smsSendVo' must not be null");
        validateSmsRequest(adminMember, recipientApplicants, smsSendRequestVo);

        Notification notification = notificationRepository.save(Notification.sms(adminMember, smsSendRequestVo));

        List<SmsRequest> smsRequests = recipientApplicants.stream()
            .map(it -> SmsRequest.of(notification, it))
            .collect(Collectors.toList());

        notification.setSmsRequests(smsRequests); // cascade all

        return notification;
    }

    @Transactional
    public Notification updateSmsStatus(
        Long notificationId,
        SmsSendResultVo smsSendResultVo) {

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

        return notification;
    }

    public Page<Notification> getNotifications(Long adminMemberId, @Nullable String searchWord, Pageable pageable) {
        Assert.notNull(pageable, "'pageable' must not be null");
        pageable = PageRequest.of(
            pageable.getPageNumber(),
            pageable.getPageSize(),
            pageable.getSortOr(Sort.by(Sort.Order.desc("sentAt")))
        );

        if (searchWord == null) {
            return notificationRepository.findAll(pageable);
        }
        return notificationRepository.findByNameContainsOrSenderValueContains(searchWord, searchWord, pageable);
    }


    public Notification getNotification(Long notificationId) {
        Assert.notNull(notificationId, "'notificationId' must not be null");
        return notificationRepository.findById(notificationId)
            .orElseThrow(NotificationNotFoundException::new);
    }

    public List<SmsRequest> getSmsRequestsByApplicantId(Long applicantId) {
        Assert.notNull(applicantId, "'applicantId' must not be null");
        return smsRequestRepository.findByRecipientApplicant_applicantId(applicantId);
    }

    /**
     * ========= Private Methods ===============
     */
    private void validateSmsRequest(
        AdminMember adminMember,
        List<Applicant> recipientApplicants,
        SmsSendRequestVo smsSendRequestVo) {

        validatedNotDuplicatedNotificationName(smsSendRequestVo);
        validateNotEmptyContents(smsSendRequestVo);
        validateNotEmptyRecipient(recipientApplicants);
        validateAdminPhoneNumberRegisteredToExteranlService(adminMember);
        validateNotEmptyAdminPhoneNumber(adminMember);
    }

    private void validateNotEmptyAdminPhoneNumber(AdminMember adminMember) {
        if (!StringUtils.hasText(adminMember.getPhoneNumber())) {
            throw new NotificationRequestInvalidException("Sender's phoneNumber must not be null, empty or blank");
        }
    }

    private void validateAdminPhoneNumberRegisteredToExteranlService(AdminMember adminMember) {
        if (adminMember.getPhoneNumberRegistered() != Boolean.TRUE) {
            throw new NotificationRequestInvalidException(
                "Sender's phoneNumber must be registered to NHN Cloud Notification Service");
        }
    }

    private void validateNotEmptyRecipient(List<Applicant> recipientApplicants) {
        if (recipientApplicants.isEmpty()) {
            throw new NotificationRequestInvalidException("'recipientApplicantIds' must not be empty or null");
        }
    }

    private void validateNotEmptyContents(SmsSendRequestVo smsSendRequestVo) {
        if (!StringUtils.hasText(smsSendRequestVo.getContent())) {
            throw new NotificationRequestInvalidException("'content' must not be null, empty or blank");
        }
    }

    private void validatedNotDuplicatedNotificationName(SmsSendRequestVo smsSendRequestVo) {
        if (notificationRepository.existsByName(smsSendRequestVo.getName())) {
            throw new NotificationRequestInvalidException(
                "Notification name duplicated. name: " + smsSendRequestVo.getName());
        }
    }
}
