package kr.mashup.branding.service.pushhistory;

import kr.mashup.branding.domain.member.Member;
import kr.mashup.branding.domain.pushhistory.PushHistory;
import kr.mashup.branding.domain.pushnoti.vo.PushNotiSendVo;
import kr.mashup.branding.repository.pushhistory.PushHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class PushHistoryService {

    private final PushHistoryRepository pushHistoryRepository;

    public List<PushHistory> save(final PushNotiSendVo event){
        final List<PushHistory> histories = event.getMembers()
                .stream()
                .map(it -> PushHistory.of(it.getId(),event.getPushType(), event.getTitle(), event.getBody()))
                .collect(Collectors.toList());
        return pushHistoryRepository.saveAll(histories);
    }

    public List<PushHistory> getAllByMember(final Member member, final Pageable pageable){

        return pushHistoryRepository.findAllByMemberId(member.getId(), pageable);
    }

}
