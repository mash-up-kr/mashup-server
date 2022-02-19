package kr.mashup.branding.ui.team;

import static org.assertj.core.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import kr.mashup.branding.domain.team.CreateTeamVo;
import kr.mashup.branding.domain.team.TeamService;
import kr.mashup.branding.ui.ApiResponse;

@AutoConfigureMockMvc
@Transactional
@SpringBootTest
class TeamControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private TeamService teamService;

    @DisplayName("로그인하지 않아도 팀 목록 조회 할 수 있음")
    @Test
    void getTeams() throws Exception {
        // given
        teamService.create(CreateTeamVo.of("Design"));
        teamService.create(CreateTeamVo.of("Android"));
        teamService.create(CreateTeamVo.of("iOS"));
        teamService.create(CreateTeamVo.of("Web"));
        teamService.create(CreateTeamVo.of("Node"));
        teamService.create(CreateTeamVo.of("Spring"));
        // when
        MvcResult mvcResult = mockMvc.perform(get("/api/v1/teams"))
            // then 1
            .andExpect(status().isOk())
            .andReturn();
        ApiResponse<List<TeamResponse>> actual = objectMapper.readValue(
            mvcResult.getResponse().getContentAsByteArray(),
            new TypeReference<ApiResponse<List<TeamResponse>>>() {
            }
        );
        // then2
        assertThat(actual.getData()).hasSize(6);
        Set<String> teamNames = actual.getData().stream().map(it -> it.getName()).collect(Collectors.toSet());
        assertThat(teamNames).contains("Design", "Android", "iOS", "Web", "Node", "Spring");
    }
}