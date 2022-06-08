package kr.mashup.branding.domain.invite;

import kr.mashup.branding.domain.BaseEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Invite extends BaseEntity {

}
