package kr.mashup.branding.facade.rnb;


import kr.mashup.branding.domain.generation.Generation;
import kr.mashup.branding.domain.generation.exception.GenerationNotFoundException;
import kr.mashup.branding.domain.member.Member;
import kr.mashup.branding.domain.member.MemberGeneration;
import kr.mashup.branding.domain.rnb.RnbMeta;
import kr.mashup.branding.domain.rnb.RnbPolicy;
import kr.mashup.branding.service.generation.GenerationService;
import kr.mashup.branding.service.member.MemberService;
import kr.mashup.branding.service.rnb.RnbMetaService;
import kr.mashup.branding.ui.rnb.response.RnbMetaResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RnbMetaFacadeService {
    private final GenerationService generationService;
    private final MemberService memberService;
    private final RnbMetaService rnbMetaService;

    @Transactional(readOnly = true)
    public RnbMetaResponse getRnbMetaData(final Long memberId){

        final Member member = memberService.findMemberById(memberId);

        final Generation latestGenerationOfMember =
                memberService.findMemberGenerationByMember(member)
                        .stream()
                        .map(MemberGeneration::getGeneration)
                        .max(Comparator.comparing(Generation::getNumber))
                        .orElseThrow(GenerationNotFoundException::new);

        final Generation latestGeneration = generationService.getLatestGeneration();

        final RnbPolicy appliedPolicy =
                rnbMetaService.getAppliedPolicyGeneration(latestGeneration, latestGenerationOfMember);

        final List<String> menus = rnbMetaService.findAllByPolicyWithAll(appliedPolicy)
                .stream()
                .sorted(Comparator.comparing(RnbMeta::getMenuOrder))
                .map(RnbMeta::getMenuName)
                .collect(Collectors.toList());

        return RnbMetaResponse.of(menus);
    }
}
