package kr.mashup.branding.ui.schedule;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import kr.mashup.branding.domain.schedule.RecruitmentSchedule;
import kr.mashup.branding.domain.schedule.RecruitmentScheduleRepository;

@AutoConfigureMockMvc
@Transactional
@SpringBootTest
class RecruitmentScheduleControllerTest {
    @Autowired
    private RecruitmentScheduleRepository recruitmentScheduleRepository;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @DisplayName("채용 일정 목록 조회")
    @Test
    void getAll() throws Exception {
        // given
        recruitmentScheduleRepository.saveAll(RecruitmentSchedule.get12thRecruitSchedules());
        // when
        MvcResult mvcResult = mockMvc.perform(get("/api/v1/recruitment-schedules"))
            // then 1
            .andExpect(status().isOk())
            .andReturn();

        List<RecruitmentScheduleResponse> actual = objectMapper.readValue(
            mvcResult.getResponse().getContentAsByteArray(),
            new TypeReference<List<RecruitmentScheduleResponse>>() {
            }
        );
        // then 2
        assertEquals(4, actual.size());
    }

    @DisplayName("채용 일정 추가")
    @Test
    void create() throws Exception {
        // given
        String eventName = "NEW_EVENT";
        LocalDateTime now = LocalDateTime.now();
        RecruitmentScheduleRequest request = createRecruitmentScheduleRequest(eventName, now);
        // when
        MvcResult mvcResult = mockMvc.perform(post("/api/v1/recruitment-schedules")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsBytes(request)))
            // then 1
            .andExpect(status().isOk())
            .andReturn();
        RecruitmentScheduleResponse actual = objectMapper.readValue(
            mvcResult.getResponse().getContentAsByteArray(),
            new TypeReference<RecruitmentScheduleResponse>() {
            }
        );
        // then 2
        assertEquals(eventName, actual.getEventName());
        assertEquals(now, actual.getEventOccurredAt());
    }

    @DisplayName("채용 일정 수정")
    @Test
    void update() throws Exception {
        // given
        recruitmentScheduleRepository.saveAll(RecruitmentSchedule.get12thRecruitSchedules());
        String eventName = "RECRUIT_STARTED";
        LocalDateTime now = LocalDateTime.now();
        RecruitmentScheduleRequest request = createRecruitmentScheduleRequest(eventName, now);
        // when
        MvcResult mvcResult = mockMvc.perform(post("/api/v1/recruitment-schedules")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsBytes(request)))
            // then 1
            .andExpect(status().isOk())
            .andReturn();
        RecruitmentScheduleResponse actual = objectMapper.readValue(
            mvcResult.getResponse().getContentAsByteArray(),
            new TypeReference<RecruitmentScheduleResponse>() {
            }
        );
        // then 2
        assertEquals(eventName, actual.getEventName());
        assertEquals(now, actual.getEventOccurredAt());
    }

    private RecruitmentScheduleRequest createRecruitmentScheduleRequest(
        String eventName,
        LocalDateTime eventOccurredAt
    ) {
        RecruitmentScheduleRequest request = new RecruitmentScheduleRequest();
        ReflectionTestUtils.setField(request, "eventName", eventName);
        ReflectionTestUtils.setField(request, "eventOccurredAt", eventOccurredAt);
        return request;
    }
}
