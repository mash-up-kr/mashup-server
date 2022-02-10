package kr.mashup.branding.ui.api;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import kr.mashup.branding.ui.ApiResponse;
import kr.mashup.branding.ui.schedule.RecruitmentScheduleCreateRequest;
import kr.mashup.branding.ui.schedule.RecruitmentScheduleResponse;
import kr.mashup.branding.ui.schedule.RecruitmentScheduleUpdateRequest;

public class RecruitmentScheduleApi {
    private final MockMvc mockMvc;
    private final ObjectMapper objectMapper;

    public RecruitmentScheduleApi(MockMvc mockMvc, ObjectMapper objectMapper) {
        this.mockMvc = mockMvc;
        this.objectMapper = objectMapper;
    }

    public ApiResponse<List<RecruitmentScheduleResponse>> getAll() throws Exception {
        MvcResult mvcResult = mockMvc.perform(
            get("/api/v1/recruitment-schedules")
        ).andReturn();
        return objectMapper.readValue(
            mvcResult.getResponse().getContentAsByteArray(),
            new TypeReference<ApiResponse<List<RecruitmentScheduleResponse>>>() {
            }
        );
    }

    public ApiResponse<RecruitmentScheduleResponse> create(RecruitmentScheduleCreateRequest request) throws Exception {
        MvcResult mvcResult = mockMvc.perform(
            post("/api/v1/recruitment-schedules")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(request))
        ).andReturn();
        return objectMapper.readValue(
            mvcResult.getResponse().getContentAsByteArray(),
            new TypeReference<ApiResponse<RecruitmentScheduleResponse>>() {
            }
        );
    }

    public ApiResponse<RecruitmentScheduleResponse> update(
        Long recruitmentScheduleId,
        RecruitmentScheduleUpdateRequest request
    ) throws Exception {
        MvcResult mvcResult = mockMvc.perform(
            put("/api/v1/recruitment-schedules/{recruitmentScheduleId}", recruitmentScheduleId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(request))
        ).andReturn();
        return objectMapper.readValue(
            mvcResult.getResponse().getContentAsByteArray(),
            new TypeReference<ApiResponse<RecruitmentScheduleResponse>>() {
            }
        );
    }

    public ApiResponse<?> remove(Long recruitmentScheduleId) throws Exception {
        MvcResult mvcResult = mockMvc.perform(
            delete("/api/v1/recruitment-schedules/{recruitmentScheduleId}", recruitmentScheduleId)
        ).andReturn();
        return objectMapper.readValue(
            mvcResult.getResponse().getContentAsByteArray(),
            new TypeReference<ApiResponse<?>>() {
            }
        );
    }
}
