package kr.mashup.branding.domain.danggn;

import kr.mashup.branding.domain.generation.Generation;
import kr.mashup.branding.domain.member.Platform;

import javax.persistence.*;

public class PlatformDanggnStageRecord {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "generation_id")
    private Generation generation;

    @Enumerated(EnumType.STRING)
    private Platform platform;
}
