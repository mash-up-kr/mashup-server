package kr.mashup.branding.facade.generation;

import kr.mashup.branding.service.generation.GenerationService;
import kr.mashup.branding.ui.generation.response.GenerationInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class GenerationFacadeService {
    private final GenerationService generationService;

    public List<GenerationInfo> getAll(){
        return generationService
            .getAll()
            .stream()
            .map(GenerationInfo::from)
            .sorted(Comparator.comparingInt(GenerationInfo::getGenerationNumber).reversed())
            .collect(Collectors.toList());
    }
}
