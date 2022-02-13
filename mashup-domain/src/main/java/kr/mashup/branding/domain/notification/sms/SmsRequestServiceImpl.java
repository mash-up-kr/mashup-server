package kr.mashup.branding.domain.notification.sms;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.mashup.branding.domain.applicant.Applicant;
import kr.mashup.branding.domain.applicant.ApplicantService;
import kr.mashup.branding.domain.notification.Notification;
import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
class SmsRequestServiceImpl implements SmsRequestService {

    private final SmsRequestRepository smsRequestRepository;
    private final ApplicantService applicantService;

    @Override
    @Transactional
    public List<SmsRequest> create(Notification notification, SmsSendRequestVo smsSendRequestVo) {
        List<Applicant> applicants = applicantService.getApplicants(smsSendRequestVo.getRecipientApplicantIds());
        List<SmsRequest> smsRequests = applicants.stream()
            .map(it -> SmsRequest.of(notification, it))
            .collect(Collectors.toList());
        return smsRequestRepository.saveAll(smsRequests);
    }

    @Override
    public List<SmsRequest> getSmsRequests(Long notificationId) {
        return smsRequestRepository.findByNotification_notificationId(notificationId);
    }
}
