package kr.mashup.branding.ui.member.response;

import kr.mashup.branding.service.member.MemberBirthdayDto;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@RequiredArgsConstructor
public class MemberBirthdaysResponse {

    private final MemberBirthdayResponse todayBirthday;

    private final List<MemberBirthdayResponse> upcomingBirthdays;

    public static MemberBirthdaysResponse of(Set<Long> sentMemberIds, Map<LocalDate, List<MemberBirthdayDto>> upcomingBirthdays) {
        List<MemberBirthdayResponse> responses = createResponses(sentMemberIds, upcomingBirthdays);

        LocalDate today = LocalDate.now();
        MemberBirthdayResponse todayBirthday = extractTodayBirthday(today, responses);
        List<MemberBirthdayResponse> sortedResponses = sortUpcomingBirthdays(today, responses);

        return new MemberBirthdaysResponse(todayBirthday, sortedResponses);
    }

    private static List<MemberBirthdayResponse> createResponses(Set<Long> sentMemberIds, Map<LocalDate, List<MemberBirthdayDto>> birthdayDtos) {
        return birthdayDtos.entrySet().stream()
            .map(entry -> new MemberBirthdayResponse(
                entry.getKey(),
                entry.getValue().stream()
                    .map(dto -> new MemberBirthdayResponse.Member(
                        dto.getMemberId(),
                        dto.getName(),
                        dto.getPlatform().getName(),
                        sentMemberIds.contains(dto.getMemberId())
                    ))
                    .collect(Collectors.toList())
            ))
            .collect(Collectors.toList());
    }

    private static MemberBirthdayResponse extractTodayBirthday(LocalDate today, List<MemberBirthdayResponse> responses) {
        return responses.stream()
            .filter(response -> today.equals(response.getDate()))
            .findFirst()
            .orElse(null);
    }

    private static List<MemberBirthdayResponse> sortUpcomingBirthdays(LocalDate today, List<MemberBirthdayResponse> responses) {
        return responses.stream()
            .filter(response -> !today.equals(response.getDate()))
            .sorted(Comparator.comparing(MemberBirthdayResponse::getDate).reversed())
            .collect(Collectors.toList());
    }
}
