package kr.mashup.branding.facade.danggn;

import kr.mashup.branding.domain.danggn.DanggnRankingReward;
import kr.mashup.branding.domain.danggn.DanggnRankingRound;
import kr.mashup.branding.domain.danggn.DanggnScore;
import kr.mashup.branding.domain.generation.Generation;
import kr.mashup.branding.domain.generation.exception.GenerationNotFoundException;
import kr.mashup.branding.service.danggn.DanggnRankingRewardService;
import kr.mashup.branding.service.danggn.DanggnRankingRoundService;
import kr.mashup.branding.service.danggn.DanggnScoreService;
import kr.mashup.branding.service.generation.GenerationService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DanggnRankingSchedulerFacadeService {
    private final DanggnRankingRoundService danggnRankingRoundService;

    private final DanggnRankingRewardService danggnRankingRewardService;

    private final DanggnScoreService danggnScoreService;

    private final GenerationService generationService;

    @Scheduled(cron = "0 0 0/1 * * *")
    @Transactional
    public void updateFirstMember() {
        LocalDateTime now = LocalDateTime.now();
        Generation currentGeneration = generationService.getAllActiveInAt(LocalDate.now()).stream().findFirst()
            .orElseThrow(GenerationNotFoundException::new);
        danggnRankingRoundService.getAllSorted().stream()
            .filter(danggnRankingRound -> danggnRankingRound.getEndedAt().isBefore(now))
            .forEach(danggnRankingRound -> {
            if (!danggnRankingRewardService.existsByDanggnRankingRoundId(danggnRankingRound.getId())) {
                List<DanggnScore> danggnScoreList = danggnScoreService.getDanggnScoreOrderedList(currentGeneration.getNumber(), danggnRankingRound.getId());
                DanggnScore firstScore = danggnScoreList.stream().findFirst().orElse(null);
                if (firstScore != null) {
                    DanggnRankingReward danggnRankingReward = DanggnRankingReward.from(firstScore.getMemberGeneration().getMember().getId(), currentGeneration.getId());
                    danggnRankingRewardService.save(danggnRankingReward);
                }
            }
        });
    }

    @Scheduled(cron = "0 0 0/1 * * *")
    @Transactional
    public void updateNextRound() {
        LocalDateTime now = LocalDateTime.now();
        Generation currentGeneration = generationService.getAllActiveInAt(LocalDate.now()).stream().findFirst()
            .orElseThrow(GenerationNotFoundException::new);
        List<DanggnRankingRound> danggnRankingRoundList = danggnRankingRoundService.getAllSorted();
        List<DanggnRankingRound> afterDanggnRankingRoundList = danggnRankingRoundList.stream().filter(danggnRankingRound -> !danggnRankingRound.getEndedAt().isBefore(now)).collect(Collectors.toList());
        DanggnRankingRound lastDanggnRankingRound = danggnRankingRoundList.stream().findFirst().orElse(null);

        if (afterDanggnRankingRoundList.size() < 2 && lastDanggnRankingRound != null) {
            DanggnRankingRound newDanggnRankingRound = DanggnRankingRound.of(
                lastDanggnRankingRound.getNumber() + 1,
                lastDanggnRankingRound.getEndedAt().truncatedTo(ChronoUnit.HOURS),
                lastDanggnRankingRound.getEndedAt().truncatedTo(ChronoUnit.HOURS).plusDays(14),
                currentGeneration.getId()
            );
            danggnRankingRoundService.save(newDanggnRankingRound);
        }
    }
}
