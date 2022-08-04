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
    public Job plainTextJob(Step plainTextStep) {
        return jobBuilderFactory.get("plainTextJob")
            .incrementer(new RunIdIncrementer())
            .start(plainTextStep)
            .build();
    }

    @JobScope
    @Bean
    public Step plainTextStep(
        ItemReader plainTextReader,
        ItemProcessor plainTextProcessor,
        ItemWriter plainTextWriter) {
        return stepBuilderFactory.get("plainTextStep")
            .<Member, ScoreHistory>chunk(5)
            .reader(plainTextReader)
            .processor(plainTextProcessor)
            .writer(plainTextWriter)
            .build();
    }

    @StepScope
    @Bean
    public RepositoryItemReader<Member> plainTextReader() {
        return new RepositoryItemReaderBuilder<Member>()
            .name("plainTextReader")
            .repository(memberRepository)
            .methodName("findBy")
            .pageSize(5)
            .arguments(List.of())
            .sorts(Collections.singletonMap("id", Sort.Direction.DESC))
            .build();
    }

    @StepScope
    @Bean
    public ItemProcessor<Member, ScoreHistory> plainTextProcessor() {
        return member -> {
            Schedule schedule = scheduleRepository.findFirstByOrderByCreatedAt();

            List<Attendance> attendances = attendanceRepository.findAllByMember(member);
            List<Attendance> attendanceList = attendances.stream()
                .filter(attendance -> attendance.getEvent().getSchedule() == schedule)
                .collect(Collectors.toList());

            return scoreHistoryService.calculateAttendanceToScoreHistory(attendanceList, member, schedule);
        };
    }

    @StepScope
    @Bean
    public ItemWriter<ScoreHistory> plainTextWriter() {
        return scoreHistories -> {
            scoreHistories.forEach(
                scoreHistory -> scoreHistoryService.save(scoreHistory)
            );
        };
    }
}
