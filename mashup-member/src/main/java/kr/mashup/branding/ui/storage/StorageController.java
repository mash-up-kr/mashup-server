package kr.mashup.branding.ui.storage;

import io.swagger.annotations.ApiOperation;
import kr.mashup.branding.facade.storage.StorageFacadeService;
import kr.mashup.branding.ui.ApiResponse;
import kr.mashup.branding.ui.storage.response.StorageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/storage")
@RequiredArgsConstructor
public class StorageController {
    private final StorageFacadeService storageFacadeService;

    @ApiOperation("get value")
    @GetMapping("/key/{key}")
    public ApiResponse<StorageResponse> getValue(@PathVariable String key) {
        return ApiResponse.success(StorageResponse.from(storageFacadeService.findByKeyString(key)));
    }
}
