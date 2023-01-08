package kr.mashup.branding.ui.generation;

import io.swagger.annotations.ApiOperation;
import kr.mashup.branding.EmptyResponse;
import kr.mashup.branding.domain.member.Platform;
import kr.mashup.branding.facade.generation.GenerationFacadeService;
import kr.mashup.branding.ui.ApiResponse;
import kr.mashup.branding.ui.generation.request.GenerationCreateRequest;
import kr.mashup.branding.ui.generation.request.GenerationDeleteRequest;
import kr.mashup.branding.ui.generation.request.GenerationUpdateRequest;
import kr.mashup.branding.ui.generation.response.GenerationInfo;
import kr.mashup.branding.ui.member.response.MemberResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/v1/generations")
@RestController
public class GenerationController {

    private final GenerationFacadeService generationFacadeService;

    @ApiOperation("기수 목록 조회")
    @GetMapping
    public ApiResponse<List<GenerationInfo>> getAllByGeneration(){

        List<GenerationInfo> response = generationFacadeService.getAll();

        return ApiResponse.success(response);
    }

    @ApiOperation("기수 생성")
    @PostMapping
    public ApiResponse<EmptyResponse> createGeneration(
        @Valid @RequestBody GenerationCreateRequest request)
    {
        generationFacadeService.create(request);

        return ApiResponse.success(EmptyResponse.of());
    }

    @ApiOperation("기수 정보 변경")
    @PostMapping("update")
    public ApiResponse<EmptyResponse> updateGeneration(
        @Valid @RequestBody GenerationUpdateRequest request){

        generationFacadeService.update(request);

        return ApiResponse.success(EmptyResponse.of());
    }

}
