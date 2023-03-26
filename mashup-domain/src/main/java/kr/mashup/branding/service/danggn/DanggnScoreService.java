package kr.mashup.branding.service.danggn;

import kr.mashup.branding.repository.danggn.DanggnScoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DanggnScoreService {
    private final DanggnScoreRepository danggnScoreRepository;
}
