package kr.mashup.branding.service.member;

import kr.mashup.branding.domain.generation.Generation;
import kr.mashup.branding.domain.member.CurrentMemberStatus;
import kr.mashup.branding.domain.member.Member;
import kr.mashup.branding.domain.member.MemberGeneration;
import kr.mashup.branding.domain.member.MemberGenerationStatus;
import kr.mashup.branding.util.DateUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CurrentMemberStatusCalculationService {
    
    
    public Map<Long, CurrentMemberStatus> getCurrentStatus(
        final Generation currentGeneration,
        final List<Member> members
    ) {
        
        final Map<Long , CurrentMemberStatus> result = new HashMap<>();

        for(final Member member : members){
            
            final List<MemberGeneration> attendedGenerations =
                member
                    .getMemberGenerations()
                    .stream()
                    .sorted(Comparator.comparingInt(mg -> mg.getGeneration().getNumber()))
                    .collect(Collectors.toList());
            
            final Optional<MemberGeneration> laterAttendedGeneration =
                attendedGenerations
                    .stream()
                    .filter(mg -> mg.getGeneration().getNumber().equals(currentGeneration.getNumber()))
                    .findFirst();
            
            if(laterAttendedGeneration.isPresent()){
                result.put(member.getId(), CurrentMemberStatus.TRANSFER);
                continue;
            }
            

            final Optional<MemberGeneration> currentAttendedGeneration = 
                attendedGenerations
                    .stream()
                    .filter(mg -> mg.getGeneration().getNumber().equals(currentGeneration.getNumber()))
                    .findFirst();
            
            if(currentAttendedGeneration.isPresent()){
                final MemberGeneration memberGeneration = currentAttendedGeneration.get();
                if(memberGeneration.getStatus().equals(MemberGenerationStatus.DROP_OUT)){
                    result.put(member.getId(), CurrentMemberStatus.DROP_OUT);
                }else {
                    if(memberGeneration.getGeneration().isInProgress(LocalDate.now())){
                        result.put(member.getId(), CurrentMemberStatus.ACTIVE);
                    }else{
                        result.put(member.getId(), CurrentMemberStatus.END);
                    }
                }
            }
            
        }

        return result;
    }
}
