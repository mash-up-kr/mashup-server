package kr.mashup.branding.service.notification.sms;

import java.util.List;
import java.util.stream.Collectors;

import kr.mashup.branding.domain.notification.sms.whitelist.SmsWhitelist;
import kr.mashup.branding.repository.notification.sms.SmsWhitelistRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

/**
 * 개발환경에서 실수로 SMS 발송하는걸 방지하기 위해서 whitelist 를 관리한다.
 * 개발환경인 경우 whitelist 에 등록된 번호만 실제 문자 발송요청을 보낸다.
 * 운영환경인 경우 whitelist 를 사용하지 않는다.
 */
@Profile("!production")
@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class SmsWhitelistServiceImpl implements SmsWhitelistService {
    private final SmsWhitelistRepository smsWhitelistRepository;

    /**
     * 전화번호가 whitelist 에 등록되었는지 검사
     * @param phoneNumber 전화번호
     * @return 주어진 전화번호가 whitelist 에 존재하는지 여부
     */
    @Override
    public boolean contains(String phoneNumber) {
        return smsWhitelistRepository.existsByPhoneNumber(phoneNumber);
    }

    @Override
    @Transactional
    public String add(String phoneNumber) {
        if (smsWhitelistRepository.existsByPhoneNumber(phoneNumber)) {
            return phoneNumber;
        }
        return smsWhitelistRepository.save(SmsWhitelist.of(phoneNumber))
            .getPhoneNumber();
    }

    @Override
    @Transactional
    public void remove(String phoneNumber) {
        smsWhitelistRepository.findByPhoneNumber(phoneNumber)
            .ifPresent(smsWhitelistRepository::delete);
    }

    @Override
    public List<String> getAll() {
        return smsWhitelistRepository.findAll()
            .stream()
            .map(SmsWhitelist::getPhoneNumber)
            .collect(Collectors.toList());
    }
}
