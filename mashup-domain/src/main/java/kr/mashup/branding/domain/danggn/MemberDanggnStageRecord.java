package kr.mashup.branding.domain.danggn;

import kr.mashup.branding.domain.member.MemberGeneration;

import javax.persistence.*;

public class MemberDanggnStageRecord {
    @Id
    @GeneratedValue
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "member_generation_id")
    private MemberGeneration memberGeneration;
}
