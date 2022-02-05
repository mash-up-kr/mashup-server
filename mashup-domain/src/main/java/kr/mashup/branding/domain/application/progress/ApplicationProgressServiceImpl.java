package kr.mashup.branding.domain.application.progress;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
@Transactional
public class ApplicationProgressServiceImpl implements ApplicationProgressService {

    private final ApplicationProgressRepository applicationProgressRepository;

    @Override
    public ApplicationProgress getByApplicationId(Long applicationId) {
        return applicationProgressRepository.findByApplication_applicationId(applicationId)
            .orElseThrow(ApplicationProgressNotFoundException::new);
    }
}
