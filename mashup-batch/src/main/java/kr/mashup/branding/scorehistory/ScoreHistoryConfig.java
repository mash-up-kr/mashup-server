package kr.mashup.branding.scorehistory;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.RepositoryItemReader;
import org.springframework.batch.item.data.builder.RepositoryItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Sort;

import kr.mashup.branding.domain.attendance.Attendance;
import kr.mashup.branding.domain.generation.Generation;
import kr.mashup.branding.domain.member.Member;
import kr.mashup.branding.domain.schedule.Schedule;
import kr.mashup.branding.domain.scorehistory.ScoreHistory;
import kr.mashup.branding.repository.attendance.AttendanceRepository;
import kr.mashup.branding.repository.generation.GenerationRepository;
import kr.mashup.branding.repository.member.MemberRepository;
import kr.mashup.branding.repository.schedule.ScheduleRepository;
import kr.mashup.branding.service.scorehistory.ScoreHistoryService;
import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class ScoreHistoryConfig {

    private final JobBuilderFactory jobBuilderFactory;

    private final StepBuilderFactory stepBuilderFactory;

    private final MemberRepository memberRepository;

    private final AttendanceRepository attendanceRepository;

    private final ScoreHistoryService scoreHistoryService;

    private final ScheduleRepository scheduleRepository;

    private final GenerationRepository generationRepository;

    @Bean
    public Job job(Step step) {
        return jobBuilderFactory.get("plainTextJob")
            .incrementer(new RunIdIncrementer())
            .start(step)
            .build();
    }

    @JobScope
    @Bean
    public Step step(
        ItemReader memberReader,
        ItemProcessor attendanceProcessor,
        ItemWriter scoreHistoryWriter) {
        return stepBuilderFactory.get("plainTextStep")
            .<Member, ScoreHistory>chunk(5)
            .reader(memberReader)
            .processor(attendanceProcessor)
            .writer(scoreHistoryWriter)
            .build();
    }

    @StepScope
    @Bean
    public RepositoryItemReader<Member> memberReader() {
        Generation generation = generationRepository.findTop1ByOrderByNumberDesc();

        return new RepositoryItemReaderBuilder<Member>()
            .name("memberReader")
            .repository(memberRepository)
            .methodName("findAllActiveByGeneration")
            .arguments(generation)
            .pageSize(5)
            .arguments(List.of())
            .sorts(Collections.singletonMap("id", Sort.Direction.DESC))
            .build();
    }

    @StepScope
    @Bean
    public ItemProcessor<Member, ScoreHistory> attendanceProcessor() {
        return member -> {
            Schedule schedule = scheduleRepository.findById(99999L).orElseThrow();

            List<Attendance> attendances = attendanceRepository.findAllByMember(member);
            List<Attendance> attendanceList = attendances.stream()
                .filter(attendance -> attendance.getEvent().getSchedule() == schedule)
                .collect(Collectors.toList());

            return scoreHistoryService.convertAttendanceToScoreHistory(attendanceList, member, schedule);
        };
    }

    @StepScope
    @Bean
    public ItemWriter<ScoreHistory> scoreHistoryWriter() {
        return scoreHistories -> {
            scoreHistories.forEach(
                scoreHistoryService::save
            );
        };
    }
}
