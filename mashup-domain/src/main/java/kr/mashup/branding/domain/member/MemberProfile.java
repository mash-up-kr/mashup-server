package kr.mashup.branding.domain.member;

import kr.mashup.branding.domain.BaseEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberProfile extends BaseEntity {

    private LocalDate birthDate;            // 생년월일

    private String job;                     // 직군

    private String company;                 // 회사

    private String introduction;            // 자기소개

    private String residence;               // 거주기

    private String socialNetworkServiceLink; // 인스타그램 링크

    private String githubLink;              // 깃헙 링크

    private String portfolioLink;           // 비핸스 링크

    private String blogLink;                // 티스토리 링크

    private String linkedInLink;            // 링크드인 링크

    private Long memberId;

    public static MemberProfile from(Long memberId) {
        return new MemberProfile(memberId);
    }

    private MemberProfile(Long memberId) {
        this.memberId = memberId;
    }

    public void update(
            LocalDate birthDate,
            String job,
            String company,
            String introduction,
            String residence,
            String socialNetworkServiceLink,
            String githubLink,
            String portfolioLink,
            String blogLink,
            String linkedInLink
    ) {
        this.birthDate = birthDate;
        this.job = job;
        this.company = company;
        this.introduction = introduction;
        this.residence = residence;
        this.socialNetworkServiceLink = socialNetworkServiceLink;
        this.githubLink = githubLink;
        this.portfolioLink = portfolioLink;
        this.blogLink = blogLink;
        this.linkedInLink = linkedInLink;
    }
}
