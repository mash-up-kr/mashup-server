package kr.mashup.branding.ui.content;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import kr.mashup.branding.facade.content.ContentFacadeService;
import kr.mashup.branding.service.content.ContentService;
import kr.mashup.branding.ui.ApiResponse;
import kr.mashup.branding.ui.content.request.ContentCreateRequest;
import kr.mashup.branding.ui.content.response.ContentResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/api/v1/contents")
@RestController
public class ContentController {

    private final ContentFacadeService contentFacadeService;
    private final ContentService contentService;

    @ApiOperation("이벤트 세부내용 생성")
    @PostMapping()
    public ApiResponse<ContentResponse> create(
        @RequestBody ContentCreateRequest contentCreateRequest
    ) {
        ContentResponse res = contentFacadeService.create(contentCreateRequest);

        return ApiResponse.success(res);
    }
}
