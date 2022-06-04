package kr.mashup.branding.domain.attendance;


import com.sun.istack.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseEntity {

    @NotNull
    private String name;

    @NotNull
    private String identification;
    @NotNull
    private String password;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Team team;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "generation_id")
    private Generation generation;

    @OneToMany(mappedBy = "member")
    private final List<Attendance> attendances = new ArrayList<>();

    @NotNull
    private Boolean privatePolicyAgreed;

    public static Member of(
            String name,
            String identification,
            String rawPassword,
            PasswordEncoder encoder,
            Team team,
            Generation generation,
            Boolean privatePolicyAgreed){
        return new Member(name,identification,rawPassword,encoder, team, generation, privatePolicyAgreed);
    }

    private Member(String name, String identification, String rawPassword, PasswordEncoder encoder, Team team, Generation generation, Boolean privatePolicyAgreed) {

        checkAgreePrivacyPolicy(privatePolicyAgreed);
        checkValidName(name);
        checkValidID(identification);

        this.name = name;
        this.identification = identification;
        this.password = encoder.encode(rawPassword);
        this.team = team;
        this.generation = generation;
        this.privatePolicyAgreed = privatePolicyAgreed;
    }



    public boolean isMatchPassword(String rawPassword, PasswordEncoder encoder){
        return encoder.matches(rawPassword, this.password);
    }


    public void changePassword(String rawPassword, String newPassword, PasswordEncoder encoder) {
        if (!encoder.matches(rawPassword, this.password)) {
            throw new IllegalArgumentException();
        }
        this.password = encoder.encode(newPassword);
    }

    private void checkValidID(String identification) {
        if (!StringUtils.hasText(identification)) {
            throw new IllegalArgumentException("아이디는 비어있을 수 없습니다.");
        }
        if (identification.length() < MIN_ID_LENGTH) {
            throw new IllegalArgumentException("아이디는 " + MIN_ID_LENGTH + "글자 이상이어야 합니다.");
        }
        if (identification.length() > MAX_ID_LENGTH) {
            throw new IllegalArgumentException("아이디는 " + MAX_ID_LENGTH + "글자 이하이어야 합니다.");
        }
        if (!Pattern.matches("^[a-zA-Z]*$", identification)) {
            throw new IllegalArgumentException("아이디는 영어 대소문자로만 구성되어야 합니다.");
        }
    }

    private void checkValidName(String name) {
        if (!StringUtils.hasText(name)) {
            throw new IllegalArgumentException("이름이 비어있을 수 없습니다.");
        }
        if (name.length() < 3 || name.length() > 4) {
            throw new IllegalArgumentException("이름은 세글자 혹은 네글자이어야 합니다.");
        }
        if (!Pattern.matches("^[가-힣]*$", name)) {
            throw new IllegalArgumentException("이름은 완성된 한글로만 이루어져야 합니다.");
        }
    }

    private void checkAgreePrivacyPolicy(Boolean privatePolicyAgreed) {
        if(!privatePolicyAgreed){
            throw new IllegalArgumentException("개인정보 이용에 동의해야만 가입할 수 있습니다.");
        }
    }

    private static final int MIN_ID_LENGTH = 5;
    private static final int MAX_ID_LENGTH = 15;

}
