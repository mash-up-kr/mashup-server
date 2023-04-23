package kr.mashup.branding.service.danggn;

import kr.mashup.branding.domain.danggn.DanggnTodayMessage;
import kr.mashup.branding.repository.danggn.DanggnTodayMessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DanggnTodayMessageService {
    private final DanggnTodayMessageRepository danggnTodayMessageRepository;

    public List<DanggnTodayMessage> findAll() {
        return danggnTodayMessageRepository.findAll();
    }
}
