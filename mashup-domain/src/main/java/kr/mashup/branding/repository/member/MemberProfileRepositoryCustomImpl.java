package kr.mashup.branding.repository.member;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.mashup.branding.domain.generation.Generation;
import kr.mashup.branding.service.member.MemberBirthdayDto;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.List;

import static kr.mashup.branding.domain.member.QMember.member;
import static kr.mashup.branding.domain.member.QMemberGeneration.memberGeneration;
import static kr.mashup.branding.domain.member.QMemberProfile.memberProfile;

@RequiredArgsConstructor
public class MemberProfileRepositoryCustomImpl implements MemberProfileRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public List<MemberBirthdayDto> retrieveByBirthDateBetween(LocalDate startDate, LocalDate endDate, Generation generation) {
        return queryFactory
            .select(Projections.fields(
                MemberBirthdayDto.class,
                member.id.as("memberId"),
                member.name.as("name"),
                memberGeneration.platform.as("platform"),
                memberProfile.birthDate.as("birthDate")
            ))
            .from(memberProfile)
            .join(member).on(member.id.eq(memberProfile.memberId))
            .join(memberGeneration).on(member.id.eq(memberGeneration.member.id))
            .where(memberProfile.birthDate.between(startDate, endDate).and(memberGeneration.generation.eq(generation)))
            .fetch();
    }
}
