package kr.mashup.branding.service.member;

import kr.mashup.branding.domain.member.MemberVo;
import kr.mashup.branding.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberVo save()
}
