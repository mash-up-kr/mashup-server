package kr.mashup.branding.domain.attendance;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

class MemberTest {

    static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Test
    @DisplayName("멤버 생성 테스트 - 성공")
    public void createMemberTest1() throws Exception {
        Generation generation = new Generation(11, LocalDate.now(), LocalDate.now().plusMonths(6));
        Member member = new Member("테스트",
                "testtest",
                passwordEncoder.encode("test"),
                Team.SPRING,
                generation,
                true);

        assertThat(member.getName()).isEqualTo("테스트");
    }

    @Test
    @DisplayName("멤버 생성 테스트 - 실패 - 이름이 3글자인 미만인 경우")
    public void createMemberTestFail1() throws Exception {
        Generation generation = new Generation(11, LocalDate.now(), LocalDate.now().plusMonths(6));

        assertThatIllegalArgumentException()
                .isThrownBy(() -> new Member("테스", "testtest", passwordEncoder.encode("test"), Team.SPRING, generation, true));

    }

    @Test
    @DisplayName("멤버 생성 테스트 - 실패 - 이름이 4글자 초과하는 경우")
    public void createMemberTestFail2() throws Exception {
        Generation generation = new Generation(11, LocalDate.now(), LocalDate.now().plusMonths(6));

        assertThatIllegalArgumentException()
                .isThrownBy(() -> new Member("테스트테스", "testtest", passwordEncoder.encode("test"), Team.SPRING, generation, true));
    }

    @Test
    @DisplayName("멤버 생성 테스트 - 실패 - 이름이 완성된 한글이 아닌 경우")
    public void createMemberTestFail3() throws Exception {
        Generation generation = new Generation(11, LocalDate.now(), LocalDate.now().plusMonths(6));

        assertThatIllegalArgumentException()
                .isThrownBy(() -> new Member("ㅌㅅㅌ", "testtest", passwordEncoder.encode("test"), Team.SPRING, generation, true));
    }

    @Test
    @DisplayName("멤버 생성 테스트 - 실패 - 아이디에 숫자가 들어가는 경우")
    public void createMemberTestFail4() throws Exception {
        Generation generation = new Generation(11, LocalDate.now(), LocalDate.now().plusMonths(6));

        assertThatIllegalArgumentException()
                .isThrownBy(() -> new Member("테스트", "testtest1", passwordEncoder.encode("test"), Team.SPRING, generation, true));
    }

    @Test
    @DisplayName("멤버 생성 테스트 - 실패 - 아이디가 5글자 미만인 경우")
    public void createMemberTestFail5() throws Exception {
        Generation generation = new Generation(11, LocalDate.now(), LocalDate.now().plusMonths(6));

        assertThatIllegalArgumentException()
                .isThrownBy(() -> new Member("테스트", "test", passwordEncoder.encode("test"), Team.SPRING, generation, true));
    }

    @Test
    @DisplayName("멤버 생성 테스트 - 실패 - 아이디가 15글자 초과인 경우")
    public void createMemberTestFail6() throws Exception {
        Generation generation = new Generation(11, LocalDate.now(), LocalDate.now().plusMonths(6));

        assertThatIllegalArgumentException()
                .isThrownBy(() -> new Member("테스트", "testtesttesttest", passwordEncoder.encode("test"), Team.SPRING, generation, true));
    }

    @Test
    @DisplayName("멤버 생성 테스트 - 실패 - 개인정보 이용 동의하지 않는 경우")
    public void createMemberTestFail7() throws Exception {
        Generation generation = new Generation(11, LocalDate.now(), LocalDate.now().plusMonths(6));

        assertThatIllegalArgumentException()
                .isThrownBy(() -> new Member("테스트", "testtesttesttest", passwordEncoder.encode("test"), Team.SPRING, generation, false));
    }

}
