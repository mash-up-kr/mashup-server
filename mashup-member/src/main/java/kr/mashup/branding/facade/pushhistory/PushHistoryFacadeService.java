package kr.mashup.branding.facade.pushhistory;

import kr.mashup.branding.domain.member.Member;
import kr.mashup.branding.domain.pushhistory.PushHistory;
import kr.mashup.branding.service.member.MemberService;
import kr.mashup.branding.service.pushhistory.PushHistoryService;
import kr.mashup.branding.ui.pushhistory.response.PushHistoriesResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static kr.mashup.branding.ui.pushhistory.response.PushHistoriesResponse.*;

@Service
@RequiredArgsConstructor
public class PushHistoryFacadeService {

    private final MemberService memberService;
    private final PushHistoryService pushHistoryService;

    @Transactional(readOnly = true)
    public PushHistoriesResponse getPushHistories(final Long memberId, final Pageable pageable){

        final Member member = memberService.findMemberById(memberId);
        final LocalDateTime lastPushCheckTime = member.getLastPushCheckTime();

        final List<PushHistory> pushHistories = pushHistoryService.getAllByMember(member, pageable);

        return getPushHistoriesResponse(pushHistories, lastPushCheckTime);
    }

    @Transactional
    public PushHistoriesResponse getPushHistoriesAndUpdateCheckTime(final Long memberId, final Pageable pageable){

        final Member member = memberService.findMemberById(memberId);
        final LocalDateTime lastPushCheckTime = member.getLastPushCheckTime();

        memberService.updatePushCheckTime(member);

        final List<PushHistory> pushHistories = pushHistoryService.getAllByMember(member, pageable);

        return getPushHistoriesResponse(pushHistories, lastPushCheckTime);
    }



    private PushHistoriesResponse getPushHistoriesResponse(List<PushHistory> pushHistories, LocalDateTime lastPushCheckTime) {
        final List<PushHistoryResponse> readPush =
                pushHistories.stream()
                        .filter(isRead(lastPushCheckTime))
                        .map(it -> PushHistoryResponse.of(
                                it.getPushType().name(),it.getTitle(), it.getBody(), it.getLinkType(),it.getCreatedAt()))
                        .collect(Collectors.toList());
        final List<PushHistoryResponse> unreadPush =
                pushHistories.stream()
                        .filter(isRead(lastPushCheckTime).negate())
                        .map(it -> PushHistoryResponse.of(
                                it.getPushType().name(), it.getTitle(), it.getBody(), it.getLinkType(), it.getCreatedAt()))
                        .collect(Collectors.toList());

        return PushHistoriesResponse.of(readPush, unreadPush);
    }

    private Predicate<PushHistory> isRead(final LocalDateTime lastPushCheckTime) {
        return it -> it.getCreatedAt().isBefore(lastPushCheckTime)
                || it.getCreatedAt().isEqual(lastPushCheckTime);
    }
}
