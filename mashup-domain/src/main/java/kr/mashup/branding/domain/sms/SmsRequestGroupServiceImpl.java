package kr.mashup.branding.domain.sms;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
class SmsRequestGroupServiceImpl implements SmsRequestGroupService{

    private final SmsRequestGroupRepository smsRequestGroupRepository;

    @Override
    public SmsRequestGroup getRequestGroup(Long id) {
        return smsRequestGroupRepository.findById(id).orElseThrow(RuntimeException::new);
    }

    @Override
    public SmsRequestGroup createAndSave(SmsRequestGroupVo smsRequestGroupVo) {
        SmsRequestGroup smsRequestGroup = SmsRequestGroup
                .builder()
                .description(smsRequestGroupVo.getDescription())
                .build();
        return smsRequestGroupRepository.save(smsRequestGroup);
    }
}
