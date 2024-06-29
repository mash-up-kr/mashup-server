package kr.mashup.branding.service.generation;

import kr.mashup.branding.domain.ResultCode;
import kr.mashup.branding.domain.exception.BadRequestException;
import kr.mashup.branding.domain.exception.NotFoundException;
import kr.mashup.branding.domain.generation.Generation;
import kr.mashup.branding.domain.generation.exception.GenerationNotFoundException;
import kr.mashup.branding.domain.member.Member;
import kr.mashup.branding.domain.member.MemberGeneration;
import kr.mashup.branding.domain.member.exception.InactiveGenerationException;
import kr.mashup.branding.repository.generation.GenerationRepository;
import kr.mashup.branding.service.generation.vo.GenerationCreateVo;
import kr.mashup.branding.service.generation.vo.GenerationUpdateVo;
import kr.mashup.branding.util.DateRange;
import kr.mashup.branding.util.DateUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@Validated
@RequiredArgsConstructor
public class GenerationService {

    private final GenerationRepository generationRepository;

    public Generation getByIdOrThrow(final Long generationId) {
        return generationRepository.findById(generationId)
            .orElseThrow(() -> new NotFoundException(ResultCode.GENERATION_NOT_FOUND));
    }

    public Generation getByNumberOrThrow(final Integer number) {
        return generationRepository.findByNumber(number)
            .orElseThrow(() -> new NotFoundException(ResultCode.GENERATION_NOT_FOUND));
    }

    public List<Generation> getAll() {
        return generationRepository.findAll();
    }

    public Generation getLatestGeneration(){
        return generationRepository.findTop1ByOrderByNumberDesc();
    }

    public Generation create(@Valid final GenerationCreateVo createVo){

        final Integer generationNumber = createVo.getGenerationNumber();

        final boolean existsByNumber = generationRepository.existsByNumber(generationNumber);
        if(existsByNumber){
            throw new BadRequestException(ResultCode.GENERATION_ALREADY_EXISTS);
        }

        final DateRange generationDateRange
            = DateRange.of(createVo.getStatedAt(), createVo.getEndedAt());

        final Generation generation = Generation.of(generationNumber, generationDateRange);

        generationRepository.save(generation);

        return generation;
    }

    public Generation update(@Valid final GenerationUpdateVo updateVo) {

        final Long generationId = updateVo.getGenerationId();

        final Generation generation = generationRepository
            .findById(generationId)
            .orElseThrow(GenerationNotFoundException::new);

        final DateRange generationDateRange
            = DateRange.of(updateVo.getStatedAt(), updateVo.getEndedAt());

        generation.changeDate(generationDateRange);

        return generation;
    }

    public List<Generation> getAllActiveInAt(LocalDate at) {
        return generationRepository.findAll()
                .stream()
                .filter(generation -> DateUtil.isInTime(generation.getStartedAt(), generation.getEndedAt(), at))
                .collect(Collectors.toList());
    }

    public Generation getCurrentGeneration(Member member) {
        return member.getMemberGenerations()
            .stream()
            .map(MemberGeneration::getGeneration)
            .max(Comparator.comparingInt(Generation::getNumber))
            .filter(generation -> generation.isInProgress(LocalDate.now()))
            .orElseThrow(InactiveGenerationException::new);
    }
}
