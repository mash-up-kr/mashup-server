package kr.mashup.branding.domain.mashong;

import kr.mashup.branding.domain.generation.Generation;
import kr.mashup.branding.domain.member.Platform;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PlatformMashong {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private PlatformMashongLevel level;

    @Enumerated(EnumType.STRING)
    private Platform platform;

    @ManyToOne(fetch = FetchType.LAZY)
    private Generation generation;

    private Long accumulatedPopcorn;
}
