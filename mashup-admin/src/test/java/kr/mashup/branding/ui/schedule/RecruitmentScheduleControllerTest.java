package kr.mashup.branding.ui.schedule;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import kr.mashup.branding.config.jpa.SpringSecurityAuditorAware;
import kr.mashup.branding.domain.ResultCode;
import kr.mashup.branding.domain.recruitmentschedule.RecruitmentSchedule;
import kr.mashup.branding.repository.recruitmentschedule.RecruitmentScheduleRepository;
import kr.mashup.branding.ui.ApiResponse;
import kr.mashup.branding.ui.api.RecruitmentScheduleApi;

@AutoConfigureMockMvc
@Transactional
@SpringBootTest
class RecruitmentScheduleControllerTest {
    private static final String AUTHORITY_NAME = "MASHUP_LEADER";

    @Autowired
    private RecruitmentScheduleRepository recruitmentScheduleRepository;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @MockBean(name = "auditorAware")
    private SpringSecurityAuditorAware springSecurityAuditorAware;

    private RecruitmentScheduleApi recruitmentScheduleApi;

    @BeforeEach
    void setUp() {
        recruitmentScheduleApi = new RecruitmentScheduleApi(mockMvc, objectMapper);
    }

    @WithMockUser(authorities = {AUTHORITY_NAME})
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

        ApiResponse<List<RecruitmentScheduleResponse>> actual = objectMapper.readValue(
            mvcResult.getResponse().getContentAsByteArray(),
            new TypeReference<ApiResponse<List<RecruitmentScheduleResponse>>>() {
            }
        );
        // then 2
        assertEquals(4, actual.getData().size());
    }

    @WithMockUser(authorities = {AUTHORITY_NAME})
    @DisplayName("채용 일정 생성 성공")
    @Test
    void create() throws Exception {
        // given
        String eventName = "NEW_EVENT";
        LocalDateTime now = LocalDateTime.now();
        RecruitmentScheduleCreateRequest request = createRecruitmentScheduleCreateRequest(eventName, now);
        // when
        MvcResult mvcResult = mockMvc.perform(
                post("/api/v1/recruitment-schedules")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsBytes(request)))
            // then 1
            .andExpect(status().isOk())
            .andReturn();
        ApiResponse<RecruitmentScheduleResponse> actual = objectMapper.readValue(
            mvcResult.getResponse().getContentAsByteArray(),
            new TypeReference<ApiResponse<RecruitmentScheduleResponse>>() {
            }
        );
        // then 2
        assertEquals(ResultCode.SUCCESS, actual.getCode());
        assertEquals(eventName, actual.getData().getEventName());
        assertEquals(now, actual.getData().getEventOccurredAt());

        List<RecruitmentScheduleResponse> data = recruitmentScheduleApi.getAll().getData();
        assertEquals(1, data.size());
        assertEquals(actual.getData().getRecruitmentScheduleId(), data.get(0).getRecruitmentScheduleId());
    }

    @WithMockUser(authorities = {AUTHORITY_NAME})
    @DisplayName("채용 일정 생성 실패: 이름이 이미 사용중인경우")
    @Test
    void create_failed_whenNameDuplicated() throws Exception {
        // given
        recruitmentScheduleRepository.saveAll(RecruitmentSchedule.get12thRecruitSchedules());
        String eventName = "RECRUITMENT_STARTED";
        LocalDateTime now = LocalDateTime.now();
        RecruitmentScheduleCreateRequest request = createRecruitmentScheduleCreateRequest(eventName, now);
        // when
        MvcResult mvcResult = mockMvc.perform(
                post("/api/v1/recruitment-schedules")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsBytes(request)))
            // then 1
            .andExpect(status().isBadRequest())
            .andReturn();
        ApiResponse<RecruitmentScheduleResponse> actual = objectMapper.readValue(
            mvcResult.getResponse().getContentAsByteArray(),
            new TypeReference<ApiResponse<RecruitmentScheduleResponse>>() {
            }
        );
        // then 2
        assertEquals(ResultCode.RECRUITMENT_SCHEDULE_DUPLICATED, actual.getCode());
    }

    @WithMockUser(authorities = {AUTHORITY_NAME})
    @DisplayName("채용 일정 수정 성공")
    @Test
    void update() throws Exception {
        // given
        List<RecruitmentSchedule> recruitmentSchedules = recruitmentScheduleRepository.saveAll(
            RecruitmentSchedule.get12thRecruitSchedules());
        Long recruitmentScheduleId = recruitmentSchedules.get(0).getRecruitmentScheduleId();
        LocalDateTime now = LocalDateTime.now();
        RecruitmentScheduleUpdateRequest request = createRecruitmentScheduleUpdateRequest(now);
        // when
        MvcResult mvcResult = mockMvc.perform(
                put("/api/v1/recruitment-schedules/{recruitmentScheduleId}", recruitmentScheduleId)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsBytes(request)))
            // then 1
            .andExpect(status().isOk())
            .andReturn();
        ApiResponse<RecruitmentScheduleResponse> actual = objectMapper.readValue(
            mvcResult.getResponse().getContentAsByteArray(),
            new TypeReference<ApiResponse<RecruitmentScheduleResponse>>() {
            }
        );
        // then 2
        assertEquals(recruitmentScheduleId, actual.getData().getRecruitmentScheduleId());
        assertEquals(now, actual.getData().getEventOccurredAt());
    }

    @WithMockUser(authorities = {AUTHORITY_NAME})
    @DisplayName("채용 일정 수정 실패: not found")
    @Test
    void update_failed_whenNotFound() throws Exception {
        // given
        Long recruitmentScheduleId = 0L;
        LocalDateTime now = LocalDateTime.now();
        RecruitmentScheduleUpdateRequest request = createRecruitmentScheduleUpdateRequest(now);
        // when
        MvcResult mvcResult = mockMvc.perform(
                put("/api/v1/recruitment-schedules/{recruitmentScheduleId}", recruitmentScheduleId)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsBytes(request)))
            // then 1
            .andExpect(status().isNotFound())
            .andReturn();
        ApiResponse<RecruitmentScheduleResponse> actual = objectMapper.readValue(
            mvcResult.getResponse().getContentAsByteArray(),
            new TypeReference<ApiResponse<RecruitmentScheduleResponse>>() {
            }
        );
        // then 2
        assertEquals(ResultCode.RECRUITMENT_SCHEDULE_NOT_FOUND, actual.getCode());
    }

    @WithMockUser(authorities = {AUTHORITY_NAME})
    @DisplayName("채용 일정 삭제 성공")
    @Test
    void remove() throws Exception {
        // given
        List<RecruitmentSchedule> recruitmentSchedules = recruitmentScheduleRepository.saveAll(
            RecruitmentSchedule.get12thRecruitSchedules());
        Long recruitmentScheduleId = recruitmentSchedules.get(0).getRecruitmentScheduleId();
        // when
        MvcResult mvcResult = mockMvc.perform(
                delete("/api/v1/recruitment-schedules/{recruitmentScheduleId}", recruitmentScheduleId))
            // then 1
            .andExpect(status().isOk())
            .andReturn();
        ApiResponse<?> actual = objectMapper.readValue(
            mvcResult.getResponse().getContentAsByteArray(),
            new TypeReference<ApiResponse<?>>() {
            }
        );
        // then 2
        assertEquals(ResultCode.SUCCESS, actual.getCode());
        Set<Long> recruitmentScheduleIds = recruitmentScheduleApi.getAll()
            .getData()
            .stream()
            .map(RecruitmentScheduleResponse::getRecruitmentScheduleId)
            .collect(Collectors.toSet());
        assertFalse(recruitmentScheduleIds.contains(recruitmentScheduleId));
    }

    @WithMockUser(authorities = {AUTHORITY_NAME})
    @DisplayName("채용 일정 삭제 성공: 삭제 대상이 없어도 성공으로 응답")
    @Test
    void remove_returnSuccess_whenNotFound() throws Exception {
        // given
        Long recruitmentScheduleId = -1L;
        // when
        MvcResult mvcResult = mockMvc.perform(
                delete("/api/v1/recruitment-schedules/{recruitmentScheduleId}", recruitmentScheduleId))
            // then 1
            .andExpect(status().isOk())
            .andReturn();
        ApiResponse<?> actual = objectMapper.readValue(
            mvcResult.getResponse().getContentAsByteArray(),
            new TypeReference<ApiResponse<?>>() {
            }
        );
        // then 2
        assertEquals(ResultCode.SUCCESS, actual.getCode());
        Set<Long> recruitmentScheduleIds = recruitmentScheduleApi.getAll()
            .getData()
            .stream()
            .map(RecruitmentScheduleResponse::getRecruitmentScheduleId)
            .collect(Collectors.toSet());
        assertFalse(recruitmentScheduleIds.contains(recruitmentScheduleId));
    }

    private RecruitmentScheduleCreateRequest createRecruitmentScheduleCreateRequest(
        String eventName,
        LocalDateTime eventOccurredAt
    ) {
        RecruitmentScheduleCreateRequest request = new RecruitmentScheduleCreateRequest();
        ReflectionTestUtils.setField(request, "eventName", eventName);
        ReflectionTestUtils.setField(request, "eventOccurredAt", eventOccurredAt);
        return request;
    }

    private RecruitmentScheduleUpdateRequest createRecruitmentScheduleUpdateRequest(
        LocalDateTime eventOccurredAt
    ) {
        RecruitmentScheduleUpdateRequest request = new RecruitmentScheduleUpdateRequest();
        ReflectionTestUtils.setField(request, "eventOccurredAt", eventOccurredAt);
        return request;
    }
}
