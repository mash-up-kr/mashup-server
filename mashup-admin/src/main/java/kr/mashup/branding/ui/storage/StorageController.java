package kr.mashup.branding.ui.storage;

import io.swagger.annotations.ApiOperation;
import kr.mashup.branding.facade.storage.StorageFacadeService;
import kr.mashup.branding.ui.ApiResponse;
import kr.mashup.branding.ui.storage.request.StorageRequest;
import kr.mashup.branding.ui.storage.response.StorageResponse;
import kr.mashup.branding.util.JsonUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/storage")
@RequiredArgsConstructor
public class StorageController {
    private final StorageFacadeService storageFacadeService;

    @ApiOperation("key-value 저장")
    @PostMapping
    public ApiResponse<StorageResponse> upsert(@RequestBody StorageRequest storageRequest) throws IOException {
        return ApiResponse.success(StorageResponse.from(storageFacadeService.upsert(
            storageRequest.getKeyString(),
            JsonUtil.serialize(storageRequest.getValueMap())
        )));
    }

    @ApiOperation("get value")
    @GetMapping("/{key}")
    public ApiResponse<StorageResponse> getValue(@PathVariable String key) throws IOException {
        return ApiResponse.success(StorageResponse.from(storageFacadeService.findByKeyString(key)));
    }
}
