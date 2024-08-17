package kr.mashup.branding.service.danggn;

import kr.mashup.branding.domain.danggn.Exception.DanggnTodayMessageNotFoundException;
import kr.mashup.branding.domain.randommessage.RandomMessage;
import kr.mashup.branding.domain.randommessage.RandomMessageType;
import kr.mashup.branding.repository.danggn.RandomMessageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class DanggnTodayMessageService {
    private final RandomMessageRepository randomMessageRepository;

    public List<RandomMessage> findAll() {
        return randomMessageRepository.findByType(RandomMessageType.DANGGN);
    }

    public RandomMessage readTodayMessage(Long id) {
        return randomMessageRepository.findById(id).
                orElseThrow(DanggnTodayMessageNotFoundException::new);
    }

    public RandomMessage createTodayMessage(String message, RandomMessageType randomMessageType) {
        RandomMessage newMessage = RandomMessage.of(message, randomMessageType);
        randomMessageRepository.save(newMessage);

        return newMessage;
    }

    public RandomMessage updateTodayMessage(RandomMessage updateMessage, String message) {
        updateMessage.updateMessage(message);

        return updateMessage;
    }

    public void deleteTodayMessage(RandomMessage message) {
        randomMessageRepository.delete(message);
    }
}
