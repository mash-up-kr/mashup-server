package kr.mashup.branding.service.rnb;

import kr.mashup.branding.domain.generation.Generation;
import kr.mashup.branding.domain.rnb.RnbMeta;
import kr.mashup.branding.domain.rnb.RnbPolicy;
import kr.mashup.branding.repository.rnb.RnbMetaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RnbMetaServiceTest {

    @Mock
    private RnbMetaRepository rnbMetaRepository;

    @InjectMocks
    private RnbMetaService sut;

    @Test
    void findAllByPolicyWithAll_included_all() {
        RnbPolicy policy = RnbPolicy.IN_CURRENT_GENERATION_ACTIVE;
        RnbMeta rnbMeta1 = mock(RnbMeta.class);// 예시로 적절한 RnbMeta 객체를 생성하세요.
        RnbMeta rnbMeta2 = mock(RnbMeta.class); // 예시로 적절한 RnbMeta 객체를 생성하세요.

        when(rnbMetaRepository.findAllByPolicyIn(List.of(policy, RnbPolicy.ALL)))
                .thenReturn(List.of(rnbMeta1, rnbMeta2));

        List<RnbMeta> result = sut.findAllByPolicyWithAll(policy);

        assertEquals(2, result.size());
        verify(rnbMetaRepository, times(1)).findAllByPolicyIn(List.of(policy, RnbPolicy.ALL));
    }

    @Test
    void findAllByPolicyWithAll_IN_CURRENT_GENERATION_ACTIVE() {
        Generation latestGeneration = mock(Generation.class);
        Generation latestMemberGeneration = mock(Generation.class);
        LocalDate nowDate = LocalDate.now();

        when(latestGeneration.getNumber()).thenReturn(1);
        when(latestMemberGeneration.getNumber()).thenReturn(1);
        when(latestGeneration.isInProgress(nowDate)).thenReturn(true);

        RnbPolicy result = sut.getAppliedPolicyGeneration(latestGeneration, latestMemberGeneration);

        assertEquals(RnbPolicy.IN_CURRENT_GENERATION_ACTIVE, result);
    }

    @Test
    void findAllByPolicyWithAll_not_in_progress() {
        Generation latestGeneration = mock(Generation.class);
        Generation latestMemberGeneration = mock(Generation.class);

        when(latestGeneration.getNumber()).thenReturn(1);
        when(latestMemberGeneration.getNumber()).thenReturn(1);
        when(latestGeneration.isInProgress(any())).thenReturn(false);

        RnbPolicy result = sut.getAppliedPolicyGeneration(latestGeneration, latestMemberGeneration);

        assertEquals(RnbPolicy.ELSE, result);
    }

    @Test
    void indAllByPolicyWithAll_diff_gen() {
        Generation latestGeneration = mock(Generation.class);
        Generation latestMemberGeneration = mock(Generation.class);

        when(latestGeneration.getNumber()).thenReturn(1);
        when(latestMemberGeneration.getNumber()).thenReturn(2);

        RnbPolicy result = sut.getAppliedPolicyGeneration(latestGeneration, latestMemberGeneration);

        assertEquals(RnbPolicy.ELSE, result);
    }

}