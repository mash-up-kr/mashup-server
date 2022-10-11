package kr.mashup.branding.service.adminmember;

import kr.mashup.branding.domain.adminmember.entity.AdminMember;
import kr.mashup.branding.domain.adminmember.exception.AdminMemberLoginFailedException;
import kr.mashup.branding.domain.adminmember.exception.AdminMemberNotFoundException;
import kr.mashup.branding.domain.adminmember.exception.AdminMemberUsernameDuplicatedException;
import kr.mashup.branding.domain.adminmember.vo.AdminLoginCommand;
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

    public AdminMember signUp(final AdminMemberSignUpCommand command) {

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

        return adminMember;
    }


    public AdminMember logIn(final AdminLoginCommand adminLoginCommand) {

        final AdminMember adminMember = adminMemberRepository
            .findByUsername(adminLoginCommand.getUsername())
            .orElseThrow(AdminMemberNotFoundException::new);

        checkValidPassword(adminLoginCommand, adminMember);

        return adminMember;
    }


    public AdminMember getByAdminMemberId(final Long adminMemberId) {

        final AdminMember adminMember = adminMemberRepository
            .findById(adminMemberId)
            .orElseThrow(AdminMemberNotFoundException::new);

        return adminMember;
    }


    private void checkNotDuplicatedUsername(AdminMemberSignUpCommand command) {
        if (adminMemberRepository.existsByUsername(command.getUsername())) {
            throw new AdminMemberUsernameDuplicatedException(
                "이미 사용중인 username 입니다. username: " + command.getUsername());
        }
    }

    private void checkValidPassword(AdminLoginCommand adminLoginCommand, AdminMember adminMember) {
        if (!passwordEncoder.matches(adminLoginCommand.getPassword(), adminMember.getPassword())) {
            throw new AdminMemberLoginFailedException();
        }
    }
}
