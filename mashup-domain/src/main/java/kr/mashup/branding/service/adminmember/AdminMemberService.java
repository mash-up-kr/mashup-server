package kr.mashup.branding.service.adminmember;

import kr.mashup.branding.domain.adminmember.entity.AdminMember;
import kr.mashup.branding.domain.adminmember.exception.AdminMemberLoginFailedException;
import kr.mashup.branding.domain.adminmember.exception.AdminMemberNotFoundException;
import kr.mashup.branding.domain.adminmember.exception.AdminMemberUsernameDuplicatedException;
import kr.mashup.branding.domain.adminmember.vo.AdminLoginCommand;
import kr.mashup.branding.domain.adminmember.vo.AdminMemberSignUpCommand;
import kr.mashup.branding.domain.exception.BadRequestException;
import kr.mashup.branding.domain.exception.ForbiddenException;
import kr.mashup.branding.repository.adminmember.AdminMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
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

    private final LeaderCheckService leaderCheckService;
    private final AdminMemberRepository adminMemberRepository;
    @Qualifier("fourTimesRoundPasswordEncoder")
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

    public void resetPassword(
        final AdminMember executor,
        final AdminMember targetAdmin,
        final String resetPassword) {

        checkLeaderOrSubLeader(executor);

        targetAdmin.setPassword(passwordEncoder,resetPassword);
    }

    public void changePassword(
        final AdminMember me,
        final String currentPassword,
        final String changePassword) {

        if(!passwordEncoder.matches(currentPassword, me.getPassword())){
            throw new BadRequestException();
        }

        me.setPassword(passwordEncoder, changePassword);
    }

    private void checkLeaderOrSubLeader(final AdminMember executor) {
        final boolean isLeader = leaderCheckService.isMashUpLeader(executor);
        final boolean isSubLeader = leaderCheckService.isMashUpSubLeader(executor);

        if(!isLeader && !isSubLeader){
            throw new ForbiddenException();
        }
    }


}
