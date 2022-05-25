package kr.mashup.branding.domain.attendance;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import java.util.regex.Pattern;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseEntity{

    private String name;

    private String identification;

    private String password;

    @Enumerated(EnumType.STRING)
    private Team team;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "generation_id")
    private Generation generation;

    private boolean privatePolicyAgreed;

    public Member(String name, String identification, String password, Team team, Generation generation, boolean privatePolicyAgreed){

        Assert.notNull(team, "플랫폼은 비어있을 수 없습니다.");
        Assert.notNull(generation, "기수는 비어있을 수 없습니다.");
        Assert.isTrue(privatePolicyAgreed, "개인정보 이용에 동의해야만 가입할 수 있습니다.");

        checkNotEmptyPassword(password);
        checkValidName(name);
        checkValidID(identification);

        this.name = name;
        this.identification = identification;
        this.password = password;
        this.team = team;
        this.generation = generation;
        this.privatePolicyAgreed = privatePolicyAgreed;
    }

    public void changePassword(String password){
        checkNotEmptyPassword(password);
        this.password = password;
    }

    private void checkValidID(String identification){
        if(!StringUtils.hasText(identification)){
            throw new IllegalArgumentException("아이디는 비어있을 수 없습니다.");
        }
        if(identification.length() < MIN_ID_LENGTH ){
            throw new IllegalArgumentException("아이디는 "+MIN_ID_LENGTH+"글자 이상이어야 합니다.");
        }
        if(identification.length() > MAX_ID_LENGTH ){
            throw new IllegalArgumentException("아이디는 "+MAX_ID_LENGTH+"글자 이하이어야 합니다.");
        }
        //TODO: 아이디에 숫자 포함 가능? 피그마엔 영어 대소문자로만 적혀있음
        //숫자도 허용할 경우 : ^[A-Za-z0-9+]*$
        if(!Pattern.matches("^[a-zA-Z]*$", identification)){
            throw new IllegalArgumentException("아이디는 영어 대소문자로만 구성되어야 합니다.");
        }
    }

    private void checkValidName(String name){
        if(!StringUtils.hasText(name)){
            throw new IllegalArgumentException("이름이 비어있을 수 없습니다.");
        }
        if(name.length() <2 ||  name.length() > 4){
            throw new IllegalArgumentException("이름은 세글자 혹은 네글자이어야 합니다.");
        }
        if(!Pattern.matches("^[가-힣]*$", name)){
            throw new IllegalArgumentException("이름은 완성된 한글로만 이루어져야 합니다.");
        }
    }

    private void checkNotEmptyPassword(String password){
        if(!StringUtils.hasText(password)){
            throw new IllegalArgumentException("비밀번호는 비어있을 수 없습니다.");
        }
    }

    private static final int MIN_ID_LENGTH = 5;
    private static final int MAX_ID_LENGTH = 15;

}
