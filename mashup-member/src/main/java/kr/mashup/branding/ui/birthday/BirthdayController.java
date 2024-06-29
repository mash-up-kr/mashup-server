package kr.mashup.branding.ui.birthday;

import io.swagger.annotations.ApiOperation;
import kr.mashup.branding.facade.birthday.BirthdayFacadeService;
import kr.mashup.branding.security.MemberAuth;
import kr.mashup.branding.ui.ApiResponse;
import kr.mashup.branding.ui.member.response.MemberBirthdaysResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

@Slf4j
@RestController
@RequestMapping("api/v1/birthdays")
@RequiredArgsConstructor
public class BirthdayController {
    private final BirthdayFacadeService birthdayFacadeService;

    @ApiOperation("다가오는 생일자 조회")
    @GetMapping("/upcoming")
    public ApiResponse<MemberBirthdaysResponse> getUpcomingBirthdays(
        @ApiIgnore MemberAuth memberAuth,
        @RequestParam(defaultValue = "7", required = false) Integer days
    ) {
        final MemberBirthdaysResponse response
            = birthdayFacadeService.getUpcomingBirthdays(memberAuth.getMemberId(), days);

        return ApiResponse.success(response);
    }
}
