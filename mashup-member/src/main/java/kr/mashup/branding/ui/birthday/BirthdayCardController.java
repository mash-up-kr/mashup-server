package kr.mashup.branding.ui.birthday;

import io.swagger.annotations.ApiOperation;
import kr.mashup.branding.facade.birthday.BirthdayCardFacadeService;
import kr.mashup.branding.security.MemberAuth;
import kr.mashup.branding.ui.ApiResponse;
import kr.mashup.branding.ui.birthday.request.BirthdayCardRequest;
import kr.mashup.branding.ui.birthday.response.BirthdayCardDefaultImagesResponse;
import kr.mashup.branding.ui.birthday.response.BirthdayCardImageResponse;
import kr.mashup.branding.ui.birthday.response.BirthdayCardsResponse;
import kr.mashup.branding.ui.danggn.response.DanggnRandomMessageResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("api/v1/birthday-cards")
@RequiredArgsConstructor
public class BirthdayCardController {
    private final BirthdayCardFacadeService birthdayCardFacadeService;

    @ApiOperation("기본 축하 카드 조회")
    @GetMapping("/default-images")
    public ApiResponse<BirthdayCardDefaultImagesResponse> getDefault() {
        final BirthdayCardDefaultImagesResponse response
            = birthdayCardFacadeService.getDefault();

        return ApiResponse.success(response);
    }

    @ApiOperation("생일카드 이미지 업로드용 Presigned URL 발급")
    @GetMapping("/images/presigned-url")
    public ApiResponse<BirthdayCardImageResponse> generatePresignedUrl(@ApiIgnore MemberAuth memberAuth) {
        final BirthdayCardImageResponse response
            = birthdayCardFacadeService.generatePresignedUrl(memberAuth.getMemberId());

        return ApiResponse.success(response);
    }

    @ApiOperation("생일 축하 카드 보내기")
    @PostMapping
    public ApiResponse<String> send(
        @ApiIgnore MemberAuth memberAuth,
        @Valid @RequestBody BirthdayCardRequest request
    ) {
        birthdayCardFacadeService.send(memberAuth.getMemberId(), request);

        return ApiResponse.success("생일 카드가 잘 전달되었어요.");
    }

    @ApiOperation("나의 생일 축하 카드 조회")
    @GetMapping
    public ApiResponse<BirthdayCardsResponse> getMy(@ApiIgnore MemberAuth memberAuth) {
        final BirthdayCardsResponse response
            = birthdayCardFacadeService.getMy(memberAuth.getMemberId());

        return ApiResponse.success(response);
    }

    @ApiOperation(value = "랜덤 문구 메시지")
    @GetMapping("/random-message")
    public ApiResponse<DanggnRandomMessageResponse> getRandomMessage() {
        return ApiResponse.success(birthdayCardFacadeService.getRandomMessage());
    }
}
