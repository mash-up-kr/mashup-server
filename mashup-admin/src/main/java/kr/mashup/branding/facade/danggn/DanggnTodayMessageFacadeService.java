package kr.mashup.branding.facade.danggn;

import kr.mashup.branding.domain.randommessage.RandomMessage;
import kr.mashup.branding.service.danggn.DanggnTodayMessageService;
import kr.mashup.branding.ui.danggn.TodayMessageRequest;
import kr.mashup.branding.ui.danggn.TodayMessageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DanggnTodayMessageFacadeService {
    private final DanggnTodayMessageService danggnTodayMessageService;

    @Transactional(readOnly = true)
    public List<TodayMessageResponse> readTodayMessages() {
        List<RandomMessage> todayMessages = danggnTodayMessageService.findAll();

        return todayMessages.stream().map(TodayMessageResponse::from).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public TodayMessageResponse readTodayMessage(Long id) {
        return TodayMessageResponse.from(danggnTodayMessageService.readTodayMessage(id));
    }

    @Transactional(rollbackFor = Exception.class)
    public TodayMessageResponse createTodayMessage(TodayMessageRequest todayMessageRequest) {
        RandomMessage todayMessage =
                danggnTodayMessageService.createTodayMessage(todayMessageRequest.getMessage(), todayMessageRequest.getRandomMessageType());

        return TodayMessageResponse.from(todayMessage);
    }

    @Transactional(rollbackFor = Exception.class)
    public TodayMessageResponse updateTodayMessage(Long id, TodayMessageRequest todayMessageRequest) {
        RandomMessage updateMessage = danggnTodayMessageService.readTodayMessage(id);
        danggnTodayMessageService.updateTodayMessage(updateMessage, todayMessageRequest.getMessage());

        return TodayMessageResponse.from(updateMessage);
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteTodayMessage(Long id) {
        RandomMessage deleteMessage = danggnTodayMessageService.readTodayMessage(id);
        danggnTodayMessageService.deleteTodayMessage(deleteMessage);
    }
}
