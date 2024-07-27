package kr.mashup.branding.ui.mashong;

import kr.mashup.branding.config.async.ThreadPoolName;
import kr.mashup.branding.facade.mashong.MashongMissionFacadeService;
import kr.mashup.branding.service.mashong.event.MashongMissionEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@RequiredArgsConstructor
@Component
public class MashongMissionEventListener {

    private final MashongMissionFacadeService mashongMissionFacadeService;

    @Async(value = ThreadPoolName.MASHONG_MISSION_THREAD_POOL)
    @Transactional(propagation = Propagation.NEVER)
    @TransactionalEventListener
    public void handleMashongMissionEvent(MashongMissionEvent event) {
        log.info("MASHONG_MISSION_{}_EVENT_LISTEN :: memberGenerationId = {}",
                event.getEventType(),
                event.getMemberGeneration().getId()
        );

        if (event.isApplyEvent()) {
            mashongMissionFacadeService.apply(
                    event.getMissionStrategyType(),
                    event.getMemberGeneration(),
                    event.getValue()
            );
            return;
        }

        mashongMissionFacadeService.setToValue(
                event.getMissionStrategyType(),
                event.getMemberGeneration(),
                event.getValue()
        );
    }
}
