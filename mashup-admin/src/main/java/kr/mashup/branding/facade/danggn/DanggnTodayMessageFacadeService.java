package kr.mashup.branding.facade.danggn;

import kr.mashup.branding.domain.danggn.DanggnTodayMessage;
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
        List<DanggnTodayMessage> todayMessages = danggnTodayMessageService.findAll();

        return todayMessages.stream().map(TodayMessageResponse::from).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public TodayMessageResponse readTodayMessage(Long id) {
        return TodayMessageResponse.from(danggnTodayMessageService.readTodayMessage(id));
    }

    @Transactional(rollbackFor = Exception.class)
    public TodayMessageResponse createTodayMessage(TodayMessageRequest todayMessageRequest) {
        DanggnTodayMessage todayMessage =
                danggnTodayMessageService.createTodayMessage(todayMessageRequest.getMessage());

        return TodayMessageResponse.from(todayMessage);
    }

    @Transactional(rollbackFor = Exception.class)
    public TodayMessageResponse updateTodayMessage(Long id, TodayMessageRequest todayMessageRequest) {
        DanggnTodayMessage updateMessage =
                danggnTodayMessageService.updateTodayMessage(id, todayMessageRequest.getMessage());

        return TodayMessageResponse.from(updateMessage);
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteTodayMessage(Long id) {
        danggnTodayMessageService.deleteTodayMessage(id);
    }
}
