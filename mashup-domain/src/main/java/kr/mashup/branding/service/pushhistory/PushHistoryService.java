package kr.mashup.branding.service.pushhistory;

import kr.mashup.branding.domain.member.Member;
import kr.mashup.branding.domain.pushhistory.PushHistory;
import kr.mashup.branding.repository.pushhistory.PushHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PushHistoryService {
    private PushHistoryRepository pushHistoryRepository;

    @Transactional
    public List<PushHistory> save(final List<Member> members, String title, String body){
        final List<PushHistory> histories = members
                .stream()
                .map(it -> PushHistory.of(it.getId(), title, body))
                .collect(Collectors.toList());
        return pushHistoryRepository.saveAll(histories);
    }

    @Transactional(readOnly = true)
    public List<PushHistory> getAllByMember(final Member member, final Pageable pageable){

        return pushHistoryRepository.findAllByMemberId(member.getId(), pageable);
    }

}
