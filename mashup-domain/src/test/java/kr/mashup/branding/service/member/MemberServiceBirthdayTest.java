package kr.mashup.branding.service.member;

import kr.mashup.branding.domain.generation.Generation;
import kr.mashup.branding.domain.member.Member;
import kr.mashup.branding.repository.member.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.MonthDay;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MemberServiceBirthdayTest {

    @Mock
    private MemberRepository memberRepository;

    @InjectMocks
    private MemberService sut;

    @Test
    @DisplayName("getAllByBirthdayRecipient는 해당 기수의 생일자만 반환한다")
    void getAllByBirthdayRecipient_delegatesToRepository() {
        // given
        Generation generation = mock(Generation.class);
        Member birthdayMember = mock(Member.class);

        when(memberRepository.retrieveByBirthDate(eq(generation), any(MonthDay.class)))
                .thenReturn(List.of(birthdayMember));

        // when
        List<Member> result = sut.getAllByBirthdayRecipient(generation);

        // then
        assertThat(result).containsExactly(birthdayMember);
        verify(memberRepository).retrieveByBirthDate(eq(generation), any(MonthDay.class));
    }

    @Test
    @DisplayName("getAllByBirthdaySender는 생일자가 없으면 빈 리스트를 반환한다")
    void getAllByBirthdaySender_emptyWhenNoBirthday() {
        // given
        Generation generation = mock(Generation.class);

        when(memberRepository.retrieveByBirthDate(eq(generation), any(MonthDay.class)))
                .thenReturn(List.of());

        // when
        List<Member> result = sut.getAllByBirthdaySender(generation);

        // then
        assertThat(result).isEmpty();
        verify(memberRepository, never()).findAllActiveByGeneration(any());
    }

    @Test
    @DisplayName("getAllByBirthdaySender는 생일자가 1명이면 발신자 목록에서 생일자를 제외한다")
    void getAllByBirthdaySender_excludesSingleRecipient() {
        // given
        Generation generation = mock(Generation.class);
        Member birthdayMember = mock(Member.class);
        Member otherMember = mock(Member.class);

        when(memberRepository.retrieveByBirthDate(eq(generation), any(MonthDay.class)))
                .thenReturn(List.of(birthdayMember));
        when(memberRepository.findAllActiveByGeneration(generation))
                .thenReturn(new ArrayList<>(List.of(birthdayMember, otherMember)));

        // when
        List<Member> result = sut.getAllByBirthdaySender(generation);

        // then
        assertThat(result).containsExactly(otherMember);
        assertThat(result).doesNotContain(birthdayMember);
    }

    @Test
    @DisplayName("getAllByBirthdaySender는 생일자가 2명 이상이면 발신자 목록에서 제외하지 않는다")
    void getAllByBirthdaySender_doesNotExcludeMultipleRecipients() {
        // given
        Generation generation = mock(Generation.class);
        Member birthdayMember1 = mock(Member.class);
        Member birthdayMember2 = mock(Member.class);
        Member otherMember = mock(Member.class);

        when(memberRepository.retrieveByBirthDate(eq(generation), any(MonthDay.class)))
                .thenReturn(List.of(birthdayMember1, birthdayMember2));
        when(memberRepository.findAllActiveByGeneration(generation))
                .thenReturn(new ArrayList<>(List.of(birthdayMember1, birthdayMember2, otherMember)));

        // when
        List<Member> result = sut.getAllByBirthdaySender(generation);

        // then
        assertThat(result).containsExactly(birthdayMember1, birthdayMember2, otherMember);
    }
}
