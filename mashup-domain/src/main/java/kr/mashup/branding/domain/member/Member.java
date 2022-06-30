package kr.mashup.branding.domain.member;

import kr.mashup.branding.domain.BaseEntity;
import kr.mashup.branding.domain.attendance.Attendance;
import kr.mashup.branding.domain.generation.Generation;
import kr.mashup.branding.dto.member.MemberUpdateDto;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;
import javax.persistence.*;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseEntity {

    private static final int MIN_NAME_LENGTH = 2;
    private static final int MAX_NAME_LENGTH = 4;

    private static final int MIN_ID_LENGTH = 5;
    private static final int MAX_ID_LENGTH = 15;

    @Size(min = 2, max = 4, message = "이름은 두글자 이상 네글자 이하이어야 합니다.")
    private String name;

    @Size(min = 5, max = 15, message = "아이디는 "+MIN_ID_LENGTH+"글자에서 "+MAX_ID_LENGTH+"글자이어야 합니다.")
    private String identification;

    @NotBlank(message = "비밀번호는 비어있을 수 없습니다.")
    private String password;

    @NotNull(message = "플랫폼은 비어있을 수 없습니다.")
    @Enumerated(EnumType.STRING)
    private Platform platform;

    @NotNull(message = "기수는 비어있을 수 없습니다.")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "generation_id")
    private Generation generation;

    @OneToMany(mappedBy = "member")
    private final List<Attendance> attendances = new ArrayList<>();

    @AssertTrue(message = "개인정보 이용에 동의해야만 가입할 수 있습니다.")
    private Boolean privatePolicyAgreed;

    // TODO: soft delete 방식 필요?

    public void updateInfo(MemberUpdateDto memberUpdateDto){

        final String name = memberUpdateDto.getName();
        final Generation generation = memberUpdateDto.getGeneration();
        final Platform platform = memberUpdateDto.getPlatform();

        checkValidName(name);

        this.name = name;
        this.generation = generation;
        this.platform = platform;
    }

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

    public static Member of(
            String name,
            String identification,
            String rawPassword,
            PasswordEncoder encoder,
            Platform platform,
            Generation generation,
            Boolean privatePolicyAgreed) {
        return new Member(name, identification, rawPassword, encoder, platform, generation, privatePolicyAgreed);
    }

    private Member(
            String name,
            String identification,
            String rawPassword,
            PasswordEncoder encoder,
            Platform platform,
            Generation generation,
            Boolean privatePolicyAgreed) {

        checkAgreePrivacyPolicy(privatePolicyAgreed);
        checkValidName(name);
        checkValidID(identification);
        checkValidPassword(rawPassword);

        this.name = name;
        this.identification = identification;
        this.password = encoder.encode(rawPassword);
        this.platform = platform;
        this.generation = generation;
        this.privatePolicyAgreed = privatePolicyAgreed;
    }

    private void checkValidID(String identification) {
        if (!StringUtils.hasText(identification)) {
            throw new IllegalArgumentException("아이디는 비어있을 수 없습니다.");
        }
        if (identification.length() < MIN_ID_LENGTH || identification.length() > MAX_ID_LENGTH) {
            throw new IllegalArgumentException("아이디는 "+MIN_ID_LENGTH+"글자에서 "+MAX_ID_LENGTH+"글자이어야 합니다.");
        }

        if (!Pattern.matches("^[a-zA-Z]*$", identification)) {
            throw new IllegalArgumentException("아이디는 영어 대소문자로만 구성되어야 합니다.");
        }
    }

    private void checkValidName(String name) {
        if (!StringUtils.hasText(name)) {
            throw new IllegalArgumentException("이름이 비어있을 수 없습니다.");
        }
        if (name.length() < MIN_NAME_LENGTH || name.length() > MAX_NAME_LENGTH) {
            throw new IllegalArgumentException("이름은 "+MIN_NAME_LENGTH+"글자에서 "+MAX_NAME_LENGTH+"글자이어야 합니다.");
        }
        if (!Pattern.matches("^[가-힣]*$", name)) {
            throw new IllegalArgumentException("이름은 완성된 한글로만 이루어져야 합니다.");
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


}
