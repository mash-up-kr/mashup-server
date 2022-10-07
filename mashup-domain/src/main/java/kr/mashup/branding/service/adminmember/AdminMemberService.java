package kr.mashup.branding.service.adminmember;

import kr.mashup.branding.domain.adminmember.entity.AdminMember;
import kr.mashup.branding.domain.adminmember.exception.AdminMemberLoginFailedException;
import kr.mashup.branding.domain.adminmember.exception.AdminMemberNotFoundException;
import kr.mashup.branding.domain.adminmember.exception.AdminMemberUsernameDuplicatedException;
import kr.mashup.branding.domain.adminmember.vo.AdminMemberLoginCommand;
import kr.mashup.branding.domain.adminmember.vo.AdminMemberSignUpCommand;
import kr.mashup.branding.domain.adminmember.vo.AdminMemberVo;
import kr.mashup.branding.repository.adminmember.AdminMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.transaction.Transactional;
import java.util.Optional;

@Validated
@RequiredArgsConstructor
@Transactional
@Service
public class AdminMemberService {

    private final AdminMemberRepository adminMemberRepository;
    private final PasswordEncoder passwordEncoder;

    public AdminMemberVo signUp(final AdminMemberSignUpCommand command) {

        checkNotDuplicatedUsername(command);

        final AdminMember adminMember = AdminMember.of(
            command.getUsername(),
            passwordEncoder.encode(command.getPassword()),
            Optional.ofNullable(command.getPhoneNumber())
                .map(String::trim)
                .orElse(null),
            command.getPosition()
        );

        adminMemberRepository.save(adminMember);

        return AdminMemberVo.from(adminMember);
    }


    public AdminMemberVo logIn(final AdminMemberLoginCommand adminMemberLoginCommand) {

        final AdminMember adminMember = adminMemberRepository.findByUsername(adminMemberLoginCommand.getUsername())
            .orElseThrow(AdminMemberNotFoundException::new);

        checkValidPassword(adminMemberLoginCommand, adminMember);
        return AdminMemberVo.from(adminMember);
    }


    public AdminMemberVo getByAdminMemberId(final Long adminMemberId) {

        final AdminMember adminMember = adminMemberRepository.findById(adminMemberId)
            .orElseThrow(AdminMemberNotFoundException::new);

        return AdminMemberVo.from(adminMember);
    }


    private void checkNotDuplicatedUsername(AdminMemberSignUpCommand command) {
        if (adminMemberRepository.existsByUsername(command.getUsername())) {
            throw new AdminMemberUsernameDuplicatedException(
                "이미 사용중인 username 입니다. username: " + command.getUsername());
        }
    }


    private void checkValidPassword(AdminMemberLoginCommand adminMemberLoginCommand, AdminMember adminMember) {
        if (!passwordEncoder.matches(adminMemberLoginCommand.getPassword(), adminMember.getPassword())) {
            throw new AdminMemberLoginFailedException();
        }
    }
}
