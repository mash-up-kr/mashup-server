package kr.mashup.branding.facade.rnb;

import kr.mashup.branding.domain.generation.Generation;
import kr.mashup.branding.domain.member.Member;
import kr.mashup.branding.domain.member.MemberGeneration;
import kr.mashup.branding.domain.rnb.RnbMeta;
import kr.mashup.branding.domain.rnb.RnbPolicy;
import kr.mashup.branding.service.generation.GenerationService;
import kr.mashup.branding.service.member.MemberService;
import kr.mashup.branding.service.rnb.RnbMetaService;
import kr.mashup.branding.ui.rnb.response.RnbMetaResponse;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RnbMetaFacadeServiceTest {

    @Mock
    private GenerationService generationService;

    @Mock
    private MemberService memberService;

    @Mock
    private RnbMetaService rnbMetaService;

    @InjectMocks
    private RnbMetaFacadeService sut;


    @Test
    public void testGetRnbMetaData() {

        // GIVEN
        Member member = mock(Member.class);
        when(memberService.findMemberById(1L)).thenReturn(member);

            // 이전 활동 기수
        Generation generation1 = mock(Generation.class);
        when(generation1.getNumber()).thenReturn(1);
        MemberGeneration memberGeneration1 = mock(MemberGeneration.class);
        when(memberGeneration1.getGeneration()).thenReturn(generation1);

            // 최신 활동 기수
        Generation generation2 = mock(Generation.class);
        when(generation2.getNumber()).thenReturn(2);
        MemberGeneration memberGeneration2 = mock(MemberGeneration.class);
        when(memberGeneration2.getGeneration()).thenReturn(generation2);

        when(memberService.findMemberGenerationByMember(member))
                .thenReturn(List.of(memberGeneration1, memberGeneration2));

        Generation latestGen = mock(Generation.class);
        when(generationService.getLatestGeneration()).thenReturn(latestGen);

        when(rnbMetaService.getAppliedPolicyGeneration(latestGen, generation2))
                .thenReturn(RnbPolicy.IN_CURRENT_GENERATION_ACTIVE);

        RnbMeta rnbMeta1 = mock(RnbMeta.class);
        when(rnbMeta1.getMenuName()).thenReturn("first");
        when(rnbMeta1.getMenuOrder()).thenReturn(1);

        RnbMeta rnbMeta2 = mock(RnbMeta.class);
        when(rnbMeta2.getMenuName()).thenReturn("second");
        when(rnbMeta2.getMenuOrder()).thenReturn(2);

        when(rnbMetaService.findAllByPolicyWithAll(RnbPolicy.IN_CURRENT_GENERATION_ACTIVE))
                .thenReturn(List.of(rnbMeta1, rnbMeta2));

        // WHEN
        RnbMetaResponse response = sut.getRnbMetaData(1L);

        // THEN
        List<String> menus = response.getMenus();
        Assertions.assertThat(menus).containsExactly("first", "second");
    }
}