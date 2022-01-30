package kr.mashup.branding.domain.sms;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;

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
                .content(smsRequestGroupVo.getContent())
                .description(smsRequestGroupVo.getDescription())
                .build();
        return smsRequestGroupRepository.save(smsRequestGroup);
    }

    @Override
    public List<SmsRequestGroup> getRequestGroups() {
        // 임시변통, 추후 페이징
        List<SmsRequestGroup> allRequestGroups = smsRequestGroupRepository.findAll();
        Collections.reverse(allRequestGroups);
        return allRequestGroups;
    }

    @Override
    public void markAsComplete(SmsRequestGroup smsRequestGroup) {
        smsRequestGroup.setStatus(SmsRequestGroupStatus.COMPLETE);
    }

    @Override
    public void markAsFail(SmsRequestGroup smsRequestGroup) {
        smsRequestGroup.setStatus(SmsRequestGroupStatus.FAIL);
        smsRequestGroup.getSmsRequests().forEach(SmsRequest::markAsFail);
    }
}
