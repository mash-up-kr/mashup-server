package kr.mashup.branding.util;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;

import static org.assertj.core.api.Assertions.assertThat;

public class DateUtilTest {


    /**
     *  isStartBeforeOrEqualEnd Test
     */

    @Test
    @DisplayName("isStartBeforeOrEqualEnd Test - 성공, 끝나는 시간이 시작시간과 같은 경우")
    public void isStartBeforeOrEqualEndTest1(){
        LocalDateTime startDateTime = LocalDateTime.of(2022, Month.JANUARY, 13, 0, 0);
        LocalDateTime endDateTime = LocalDateTime.of(2022, Month.JANUARY, 13, 0, 0);

        boolean isValid = DateUtil.isStartBeforeOrEqualEnd(startDateTime, endDateTime);
        assertThat(isValid).isTrue();
    }

    @Test
    @DisplayName("isStartBeforeOrEqualEnd Test - 성공, 끝나는 시간이 시작시간과 다른 경우")
    public void isStartBeforeOrEqualEndTest2() {
        LocalDateTime startDateTime = LocalDateTime.of(2022, Month.JANUARY, 13, 0, 0);
        LocalDateTime endDateTime = LocalDateTime.of(2022, Month.JANUARY, 14, 0, 0);

        boolean isValid = DateUtil.isStartBeforeOrEqualEnd(startDateTime, endDateTime);
        assertThat(isValid).isTrue();
    }

    @Test
    @DisplayName("isStartBeforeOrEqualEnd(LocalDate) Test - 성공, 끝나는 시간이 시작시간과 같은 경우")
    public void isStartBeforeOrEqualEndTest3() {
        LocalDate startDateTime = LocalDate.of(2022, Month.JANUARY, 13);
        LocalDate endDateTime = LocalDate.of(2022, Month.JANUARY, 13);

        boolean isValid = DateUtil.isStartBeforeOrEqualEnd(startDateTime, endDateTime);
        assertThat(isValid).isTrue();
    }

    @Test
    @DisplayName("isStartBeforeOrEqualEnd(LocalDate) Test - 성공, 끝나는 시간이 시작시간과 다른 경우")
    public void isStartBeforeOrEqualEndTest4() {
        LocalDate startDateTime = LocalDate.of(2022, Month.JANUARY, 13);
        LocalDate endDateTime = LocalDate.of(2022, Month.JANUARY, 14);

        boolean isValid = DateUtil.isStartBeforeOrEqualEnd(startDateTime, endDateTime);
        assertThat(isValid).isTrue();
    }

    @Test
    @DisplayName("isStartBeforeOrEqualEnd Test - 실패, 끝나는 시간이 시작 시간 전인 경우")
    public void isStartBeforeOrEqualEndTestFail1() {
        LocalDateTime startDateTime = LocalDateTime.of(2022, Month.JANUARY, 13, 0, 0);
        LocalDateTime endDateTime = LocalDateTime.of(2022, Month.JANUARY, 12, 0, 0);

        boolean isValid = DateUtil.isStartBeforeOrEqualEnd(startDateTime, endDateTime);
        assertThat(isValid).isFalse();
    }

    @Test
    @DisplayName("isStartBeforeOrEqualEnd(LocalDate) Test - 실패, 끝나는 시간이 시작 시간 전인 경우")
    public void isStartBeforeOrEqualEndTestFail2() {
        LocalDate startDateTime = LocalDate.of(2022, Month.JANUARY, 13);
        LocalDate endDateTime = LocalDate.of(2022, Month.JANUARY, 12);

        boolean isValid = DateUtil.isStartBeforeOrEqualEnd(startDateTime, endDateTime);
        assertThat(isValid).isFalse();
    }

    /**
     *  isContainDateRange Test
     */

    @Test
    @DisplayName("isContainDateRange Test - 성공, 안쪽으로 겹치는 경우")
    public void isContainDateRangeTest1() {
        LocalDateTime baseStart = LocalDateTime.of(2022, Month.JANUARY, 13, 0, 0);
        LocalDateTime baseEnd = LocalDateTime.of(2022, Month.JANUARY, 16, 0, 0);
        LocalDateTime targetStart = LocalDateTime.of(2022, Month.JANUARY, 14, 0, 0);
        LocalDateTime targetEnd = LocalDateTime.of(2022, Month.JANUARY, 15, 0, 0);

        boolean isValid = DateUtil.isContainDateRange(baseStart, baseEnd, targetStart, targetEnd);
        assertThat(isValid).isTrue();
    }

    @Test
    @DisplayName("isContainDateRange Test - 성공, 시작 시간이 일치하는 경우")
    public void isContainDateRangeTest2() {
        LocalDateTime baseStart = LocalDateTime.of(2022, Month.JANUARY, 13, 0, 0);
        LocalDateTime baseEnd = LocalDateTime.of(2022, Month.JANUARY, 16, 0, 0);
        LocalDateTime targetStart = LocalDateTime.of(2022, Month.JANUARY, 13, 0, 0);
        LocalDateTime targetEnd = LocalDateTime.of(2022, Month.JANUARY, 15, 0, 0);

        boolean isValid = DateUtil.isContainDateRange(baseStart, baseEnd, targetStart, targetEnd);
        assertThat(isValid).isTrue();
    }

    @Test
    @DisplayName("isContainDateRange Test - 성공, 끝나는 시간이 일치하는 경우")
    public void isContainDateRangeTest3() {
        LocalDateTime baseStart = LocalDateTime.of(2022, Month.JANUARY, 13, 0, 0);
        LocalDateTime baseEnd = LocalDateTime.of(2022, Month.JANUARY, 16, 0, 0);
        LocalDateTime targetStart = LocalDateTime.of(2022, Month.JANUARY, 14, 0, 0);
        LocalDateTime targetEnd = LocalDateTime.of(2022, Month.JANUARY, 16, 0, 0);

        boolean isValid = DateUtil.isContainDateRange(baseStart, baseEnd, targetStart, targetEnd);
        assertThat(isValid).isTrue();
    }

    @Test
    @DisplayName("isContainDateRange Test - 실패, 시작시간이 벗어나는 경우")
    public void isContainDateRangeTestFail1() {
        LocalDateTime baseStart = LocalDateTime.of(2022, Month.JANUARY, 13, 0, 0);
        LocalDateTime baseEnd = LocalDateTime.of(2022, Month.JANUARY, 16, 0, 0);
        LocalDateTime targetStart = LocalDateTime.of(2022, Month.JANUARY, 12, 0, 0);
        LocalDateTime targetEnd = LocalDateTime.of(2022, Month.JANUARY, 15, 0, 0);

        boolean isValid = DateUtil.isContainDateRange(baseStart, baseEnd, targetStart, targetEnd);
        assertThat(isValid).isFalse();
    }

    @Test
    @DisplayName("isContainDateRange Test - 실패, 끝나는 시간이 벗어나는 경우")
    public void isContainDateRangeTestFail2() {
        LocalDateTime baseStart = LocalDateTime.of(2022, Month.JANUARY, 13, 0, 0);
        LocalDateTime baseEnd = LocalDateTime.of(2022, Month.JANUARY, 16, 0, 0);
        LocalDateTime targetStart = LocalDateTime.of(2022, Month.JANUARY, 14, 0, 0);
        LocalDateTime targetEnd = LocalDateTime.of(2022, Month.JANUARY, 17, 0, 0);

        boolean isValid = DateUtil.isContainDateRange(baseStart, baseEnd, targetStart, targetEnd);
        assertThat(isValid).isFalse();
    }

    @Test
    @DisplayName("isContainDateRange Test - 실패, 둘 다 벗어나는 경우")
    public void isContainDateRangeTestFail4() {
        LocalDateTime baseStart = LocalDateTime.of(2022, Month.JANUARY, 13, 0, 0);
        LocalDateTime baseEnd = LocalDateTime.of(2022, Month.JANUARY, 16, 0, 0);
        LocalDateTime targetStart = LocalDateTime.of(2022, Month.JANUARY, 11, 0, 0);
        LocalDateTime targetEnd = LocalDateTime.of(2022, Month.JANUARY, 17, 0, 0);

        boolean isValid = DateUtil.isContainDateRange(baseStart, baseEnd, targetStart, targetEnd);
        assertThat(isValid).isFalse();
    }

    @Test
    @DisplayName("isContainDateRange(LocalDate) Test - 성공, 안쪽으로 겹치는 경우")
    public void isContainDateRangeTest5() {
        LocalDate baseStart = LocalDate.of(2022, Month.JANUARY, 13);
        LocalDate baseEnd = LocalDate.of(2022, Month.JANUARY, 16);
        LocalDate targetStart = LocalDate.of(2022, Month.JANUARY, 14);
        LocalDate targetEnd = LocalDate.of(2022, Month.JANUARY, 15);

        boolean isValid = DateUtil.isContainDateRange(baseStart, baseEnd, targetStart, targetEnd);
        assertThat(isValid).isTrue();
    }

    @Test
    @DisplayName("isContainDateRange(LocalDate) Test - 성공, 시작 시간이 일치하는 경우")
    public void isContainDateRangeTest6() {
        LocalDate baseStart = LocalDate.of(2022, Month.JANUARY, 13);
        LocalDate baseEnd = LocalDate.of(2022, Month.JANUARY, 16);
        LocalDate targetStart = LocalDate.of(2022, Month.JANUARY, 13);
        LocalDate targetEnd = LocalDate.of(2022, Month.JANUARY, 15);

        boolean isValid = DateUtil.isContainDateRange(baseStart, baseEnd, targetStart, targetEnd);
        assertThat(isValid).isTrue();
    }

    @Test
    @DisplayName("isContainDateRange(LocalDate) Test - 성공, 끝나는 시간이 일치하는 경우")
    public void isContainDateRangeTest7() {
        LocalDate baseStart = LocalDate.of(2022, Month.JANUARY, 13);
        LocalDate baseEnd = LocalDate.of(2022, Month.JANUARY, 16);
        LocalDate targetStart = LocalDate.of(2022, Month.JANUARY, 14);
        LocalDate targetEnd = LocalDate.of(2022, Month.JANUARY, 16);

        boolean isValid = DateUtil.isContainDateRange(baseStart, baseEnd, targetStart, targetEnd);
        assertThat(isValid).isTrue();
    }

    @Test
    @DisplayName("isContainDateRange(LocalDate) Test - 실패, 시작시간이 벗어나는 경우")
    public void isContainDateRangeTestFail5() {
        LocalDate baseStart = LocalDate.of(2022, Month.JANUARY, 13);
        LocalDate baseEnd = LocalDate.of(2022, Month.JANUARY, 16);
        LocalDate targetStart = LocalDate.of(2022, Month.JANUARY, 12);
        LocalDate targetEnd = LocalDate.of(2022, Month.JANUARY, 15);

        boolean isValid = DateUtil.isContainDateRange(baseStart, baseEnd, targetStart, targetEnd);
        assertThat(isValid).isFalse();
    }

    @Test
    @DisplayName("isContainDateRange(LocalDate) Test - 실패, 끝나는 시간이 벗어나는 경우")
    public void isContainDateRangeTestFail6() {
        LocalDate baseStart = LocalDate.of(2022, Month.JANUARY, 13);
        LocalDate baseEnd = LocalDate.of(2022, Month.JANUARY, 16);
        LocalDate targetStart = LocalDate.of(2022, Month.JANUARY, 14);
        LocalDate targetEnd = LocalDate.of(2022, Month.JANUARY, 17);

        boolean isValid = DateUtil.isContainDateRange(baseStart, baseEnd, targetStart, targetEnd);
        assertThat(isValid).isFalse();
    }

    @Test
    @DisplayName("isContainDateRange(LocalDate) Test - 실패, 둘 다 벗어나는 경우")
    public void isContainDateRangeTestFail7() {
        LocalDate baseStart = LocalDate.of(2022, Month.JANUARY, 13);
        LocalDate baseEnd = LocalDate.of(2022, Month.JANUARY, 16);
        LocalDate targetStart = LocalDate.of(2022, Month.JANUARY, 11);
        LocalDate targetEnd = LocalDate.of(2022, Month.JANUARY, 17);

        boolean isValid = DateUtil.isContainDateRange(baseStart, baseEnd, targetStart, targetEnd);
        assertThat(isValid).isFalse();
    }



}
