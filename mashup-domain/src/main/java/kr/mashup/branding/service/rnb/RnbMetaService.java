package kr.mashup.branding.service.rnb;


import kr.mashup.branding.domain.generation.Generation;
import kr.mashup.branding.domain.rnb.RnbMeta;
import kr.mashup.branding.domain.rnb.RnbPolicy;
import kr.mashup.branding.repository.rnb.RnbMetaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class RnbMetaService {
    private final RnbMetaRepository rnbMetaRepository;

    public List<RnbMeta> findAllByPolicyWithAll(final RnbPolicy policy){
        return rnbMetaRepository.findAllByPolicyIn(List.of(policy, RnbPolicy.ALL));
    }

    public RnbPolicy getAppliedPolicyGeneration(final Generation latestGeneration, final Generation latestMemberGeneration){

        final boolean isCurrentAndLatestSame =
                Objects.equals(latestGeneration.getNumber(), latestMemberGeneration.getNumber());
        final LocalDate nowDate = LocalDate.now();

        if(isCurrentAndLatestSame && latestGeneration.isInProgress(nowDate)){
            return RnbPolicy.IN_CURRENT_GENERATION_ACTIVE;
        }

        return RnbPolicy.ELSE;
    }


}
