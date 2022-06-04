package kr.mashup.branding.domain.attendance;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

@Disabled
class GenerationTest {

//    @Test
//    @DisplayName("기수 생성 테스트 - 성공")
//    public void createGenerationTest() throws Exception{
//        Generation generation = Generation.of(1, LocalDate.now(), LocalDate.now().plusMonths(6));
//        assertThat(generation.getNumber()).isEqualTo(1);
//    }
//
//    @Test
//    @DisplayName("기수 생성 테스트 - 실패 - 숫자가 0이하인 경우")
//    public void createGenerationTestFail1() throws Exception{
//        assertThatIllegalArgumentException().isThrownBy(()->Generation.of(0, LocalDate.now(), LocalDate.now().plusMonths(6)));
//    }
//
//    @Test
//    @DisplayName("기수 생성 테스트 - 실패 - 끝나는 날짜 후 시작하는 경우")
//    public void createGenerationTestFail2() throws Exception{
//        assertThatIllegalArgumentException().isThrownBy(()->Generation.of(1, LocalDate.now().plusMonths(6), LocalDate.now()));
//    }
//
//    @Test
//    @DisplayName("기수 생성 테스트 - 실패 - 시작날짜 변경 시 끝나는 날짜 이후인 경우")
//    public void createGenerationTestFail3() throws Exception{
//        Generation generation = Generation.of(1, LocalDate.now(), LocalDate.now().plusMonths(6));
//
//        assertThatIllegalArgumentException().isThrownBy(()->generation.changeStartDate(LocalDate.now().plusMonths(12)));
//    }
//
//    @Test
//    @DisplayName("기수 생성 테스트 - 실패 - 끝나는 날짜 변경 시 시작 날짜 이전인 경우")
//    public void createGenerationTestFail4() throws Exception{
//        Generation generation = Generation.of(1, LocalDate.now(), LocalDate.now().plusMonths(6));
//
//        assertThatIllegalArgumentException().isThrownBy(()->generation.changeEndedDate(LocalDate.now().minusDays(10)));
//    }

}
