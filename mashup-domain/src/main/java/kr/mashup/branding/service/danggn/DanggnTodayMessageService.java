package kr.mashup.branding.service.danggn;

import kr.mashup.branding.domain.danggn.DanggnTodayMessage;
import kr.mashup.branding.repository.danggn.DanggnTodayMessageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class DanggnTodayMessageService {
    private final DanggnTodayMessageRepository danggnTodayMessageRepository;

    public List<DanggnTodayMessage> findAll() {
        return danggnTodayMessageRepository.findAll();
    }

    public DanggnTodayMessage readTodayMessage(Long id) {
        return danggnTodayMessageRepository.findById(id).orElse(null);
    }

    public DanggnTodayMessage createTodayMessage(String message) {
        DanggnTodayMessage newMessage = DanggnTodayMessage.of(message);
        danggnTodayMessageRepository.save(newMessage);

        return newMessage;
    }

    public DanggnTodayMessage updateTodayMessage(Long id, String message) {
        DanggnTodayMessage updateMessage = danggnTodayMessageRepository.getById(id);
        log.info("updateMessage = {}", updateMessage);
        updateMessage.updateMessage(message);

        return updateMessage;
    }

    public void deleteTodayMessage(Long id) {
        danggnTodayMessageRepository.deleteById(id);
    }
}
