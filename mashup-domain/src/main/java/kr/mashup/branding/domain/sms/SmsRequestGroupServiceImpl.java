package kr.mashup.branding.domain.sms;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
class SmsRequestGroupServiceImpl {

    private final SmsRequestGroupRepository smsRequestGroupRepository;

    SmsRequestGroup getRequestGroup(Long id) {
        return smsRequestGroupRepository.findById(id).orElseThrow(RuntimeException::new);
    }

    SmsRequestGroup create(SmsRequestGroupVo smsRequestGroupVo) {
        SmsRequestGroup smsRequestGroup = SmsRequestGroup
                .builder()
                .description(smsRequestGroupVo.getDescription())
                .build();
        return smsRequestGroupRepository.save(smsRequestGroup);
    }
}
