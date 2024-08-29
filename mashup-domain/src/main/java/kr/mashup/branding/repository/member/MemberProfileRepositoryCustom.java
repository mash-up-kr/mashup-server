package kr.mashup.branding.repository.member;

import kr.mashup.branding.domain.generation.Generation;
import kr.mashup.branding.service.member.MemberBirthdayDto;

import java.time.MonthDay;
import java.util.List;

public interface MemberProfileRepositoryCustom {

    List<MemberBirthdayDto> retrieveByBirthDateBetween(MonthDay startDate, MonthDay endDate, Generation generation);
}
