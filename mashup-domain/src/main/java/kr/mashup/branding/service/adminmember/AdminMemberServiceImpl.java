package kr.mashup.branding.service.adminmember;

import java.util.Optional;

import javax.transaction.Transactional;

import kr.mashup.branding.domain.adminmember.AdminMember;
import kr.mashup.branding.domain.adminmember.AdminMemberLoginVo;
import kr.mashup.branding.domain.adminmember.AdminMemberNotFoundException;
import kr.mashup.branding.domain.adminmember.AdminMemberSignUpRequestInvalidException;
import kr.mashup.branding.domain.adminmember.AdminMemberUsernameDuplicatedException;
import kr.mashup.branding.domain.adminmember.AdminMemberVo;
import kr.mashup.branding.repository.adminmember.AdminMemberRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@Service
public class AdminMemberServiceImpl implements AdminMemberService {

    private final AdminMemberRepository adminMemberRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public AdminMember signUp(AdminMemberVo adminMemberVo) {
        Assert.notNull(adminMemberVo, "'adminMemberVo' must not be null");
        validate(adminMemberVo);

        AdminMember adminMember = AdminMember.of(
            adminMemberVo.getUsername(),
            passwordEncoder.encode(adminMemberVo.getPassword()),
            Optional.ofNullable(adminMemberVo.getPhoneNumber())
                .map(String::trim)
                .orElse(null),
            adminMemberVo.getPosition()
        );
        return adminMemberRepository.save(adminMember);
    }

    private void validate(AdminMemberVo adminMemberVo) {
        Assert.notNull(adminMemberVo, "'adminMemberVo' must not be null");

        if (!StringUtils.hasText(adminMemberVo.getUsername())) {
            throw new AdminMemberSignUpRequestInvalidException("'username' must not be null, empty or blank");
        }
        if (!StringUtils.hasText(adminMemberVo.getPassword())) {
            throw new AdminMemberSignUpRequestInvalidException("'password' must not be null, empty or blank");
        }
        if (adminMemberVo.getPhoneNumber() != null && adminMemberVo.getPhoneNumber().length() > 13) {
            throw new AdminMemberSignUpRequestInvalidException(
                "'phoneNumber' length must be less than or equal to 13");
        }
        if (adminMemberVo.getPhoneNumber().replaceAll("-", "").length() != 11) {
            throw new AdminMemberSignUpRequestInvalidException(
                "'phoneNumber' format must include hyphen('-').");
        }
        if (adminMemberRepository.existsByUsername(adminMemberVo.getUsername())) {
            throw new AdminMemberUsernameDuplicatedException(
                "?????? ???????????? username ?????????. username: " + adminMemberVo.getUsername());
        }
    }

    @Override
    public AdminMember signIn(AdminMemberLoginVo adminMemberLoginVo) {
        return adminMemberRepository.findByUsername(adminMemberLoginVo.getUsername())
            .orElseThrow(AdminMemberNotFoundException::new);
    }

    @Override
    public AdminMember getByAdminMemberId(Long adminMemberId) {
        return adminMemberRepository.findById(adminMemberId)
            .orElseThrow(AdminMemberNotFoundException::new);
    }
}
