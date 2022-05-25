package kr.mashup.branding.domain.attendance;

import kr.mashup.branding.util.DateUtil;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AttendanceCode extends BaseEntity{

    //TODO: 컨텐츠 단위로 출첵하는게 맞는지?
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "content_id")
    private Content content;

    private String code;

    private LocalDateTime startedAt;

    private LocalDateTime endedAt;

    public AttendanceCode(Content content, String code, LocalDateTime startedAt, LocalDateTime endedAt){
        Assert.notNull(content,"");
        Assert.notNull(startedAt,"");
        Assert.notNull(endedAt,"");

        checkStartBeforeOrEqualEnd(startedAt, endedAt);
        checkCodeNotEmpty(code);
        //TODO: 시작시간 끝시간 스케줄 일정 안에 포함되는지 검증하기.
        this.content = content;
        this.code = code;
        this.startedAt = startedAt;
        this.endedAt = endedAt;
    }


    private void checkCodeNotEmpty(String code) {
        if(!StringUtils.hasText(code)){
            throw new IllegalArgumentException();
        }
    }

    private void checkStartBeforeOrEqualEnd(LocalDateTime startedAt, LocalDateTime endedAt) {
        if(!DateUtil.isStartBeforeOrEqualEnd(startedAt, endedAt)){
            throw new IllegalArgumentException();
        }
    }

}
