package kr.mashup.branding.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class QrGeneratorTest {

    @Test
    @DisplayName("문자열을 받아 QR 코드를 생성한다.")
    public void generateTest() {
        //given
        String code = "mash-up";
        String expected = "https://chart.apis.google.com/chart?cht=qr&chs=300x300&chl=mash-up";

        //when
        String actual = QrGenerator.generate(code);

        //then
        assertEquals(expected, actual);
    }
}
