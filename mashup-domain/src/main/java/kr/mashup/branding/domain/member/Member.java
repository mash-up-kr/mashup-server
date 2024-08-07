package kr.mashup.branding.domain.member;

import kr.mashup.branding.domain.BaseEntity;
import kr.mashup.branding.domain.attendance.Attendance;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToMany;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseEntity {

    private static final int MIN_NAME_LENGTH = 2;
    private static final int MAX_NAME_LENGTH = 4;

    private static final int MIN_ID_LENGTH = 5;
    private static final int MAX_ID_LENGTH = 15;

    //@Size(min = 2, max = 4, message = "이름은 두글자 이상 네글자 이하이어야 합니다.")
    private String name;

    @Size(min = 5, max = 15, message = "아이디는 "+MIN_ID_LENGTH+"글자에서 "+MAX_ID_LENGTH+"글자이어야 합니다.")
    private String identification;

    @NotBlank(message = "비밀번호는 비어있을 수 없습니다.")
    private String password;

    @OneToMany(mappedBy = "member")
    private final List<MemberGeneration> memberGenerations = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private final List<Attendance> attendances = new ArrayList<>();

    @AssertTrue(message = "개인정보 이용에 동의해야만 가입할 수 있습니다.")
    private Boolean privatePolicyAgreed;

    @Enumerated(EnumType.STRING)
    private MemberStatus status;

    @Enumerated(EnumType.STRING)
    private OsType osType;

    private String fcmToken;

    private Boolean newsPushNotificationAgreed = true;          // 매시업 소식 알림 동의 여부

    private Boolean danggnPushNotificationAgreed = true;        // 당근 흔들기 알림 동의 여부

    private LocalDateTime lastPushCheckTime = LocalDateTime.now();

    public boolean isMatchPassword(String rawPassword, PasswordEncoder encoder) {
        return encoder.matches(rawPassword, this.password);
    }

    public void changePassword(String rawPassword, String newPassword, PasswordEncoder encoder) {
        if (!encoder.matches(rawPassword, this.password)) {
            throw new IllegalArgumentException();
        }
        checkValidPassword(rawPassword);
        this.password = encoder.encode(newPassword);
    }

    public void activate(){
        this.status = MemberStatus.ACTIVE;
    }

    public static Member of(
            String name,
            String identification,
            String rawPassword,
            PasswordEncoder encoder,
            Boolean privatePolicyAgreed,
            OsType osType,
            String fcmToken) {
        return new Member(name, identification, rawPassword, encoder, privatePolicyAgreed, osType, fcmToken);
    }

    private Member(
            String name,
            String identification,
            String rawPassword,
            PasswordEncoder encoder,
            Boolean privatePolicyAgreed,
            OsType osType,
            String fcmToken) {

        checkAgreePrivacyPolicy(privatePolicyAgreed);
        checkValidName(name);
        checkValidID(identification);
        checkValidPassword(rawPassword);

        this.name = name;
        this.identification = identification;
        this.password = encoder.encode(rawPassword);
        this.privatePolicyAgreed = privatePolicyAgreed;
        this.status = MemberStatus.ACTIVE;
        //this.status = MemberStatus.PENDING;
        this.osType = osType;
        this.fcmToken = fcmToken;
    }

    private void checkValidID(String identification) {
        if (!StringUtils.hasText(identification)) {
            throw new IllegalArgumentException("아이디는 비어있을 수 없습니다.");
        }
        if (identification.length() < MIN_ID_LENGTH || identification.length() > MAX_ID_LENGTH) {
            throw new IllegalArgumentException("아이디는 "+MIN_ID_LENGTH+"글자에서 "+MAX_ID_LENGTH+"글자이어야 합니다.");
        }
    }

    private void checkValidName(String name) {
        if (!StringUtils.hasText(name)) {
            throw new IllegalArgumentException("이름이 비어있을 수 없습니다.");
        }
    }

    private void checkAgreePrivacyPolicy(Boolean privatePolicyAgreed) {
        if (!privatePolicyAgreed) {
            throw new IllegalArgumentException("개인정보 이용에 동의해야만 가입할 수 있습니다.");
        }
    }

    private void checkValidPassword(String password){
        if (!StringUtils.hasText(password)) {
            throw new IllegalArgumentException("비밀번호는 비어있을 수 없습니다.");
        }
    }


    public void addMemberGenerations(MemberGeneration memberGeneration) {
        this.memberGenerations.add(memberGeneration);
    }

    public void updatePushNotificationInfo(OsType osType, String fcmToken) {
        this.osType = osType;
        this.fcmToken = fcmToken;
    }

    public void updatePushNotificationAgreed(Boolean newsPushNotificationAgreed, Boolean danggnPushNotificationAgreed) {
        this.newsPushNotificationAgreed = newsPushNotificationAgreed;
        this.danggnPushNotificationAgreed = danggnPushNotificationAgreed;
    }

    public void setPassword(String newPassword, PasswordEncoder passwordEncoder) {
        this.password = passwordEncoder.encode(newPassword);
    }

    public void setStatus(MemberStatus status) {
        this.status = status;
    }

    public void updatePushCheckTime(LocalDateTime checkTime){
        this.lastPushCheckTime = checkTime;
    }
}
