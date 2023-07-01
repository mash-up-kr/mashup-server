package kr.mashup.branding.ui.storage;

import io.swagger.annotations.ApiOperation;
import kr.mashup.branding.facade.storage.StorageFacadeService;
import kr.mashup.branding.ui.ApiResponse;
import kr.mashup.branding.ui.storage.request.StorageRequest;
import kr.mashup.branding.ui.storage.response.KeyResponse;
import kr.mashup.branding.ui.storage.response.StorageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/storage")
@RequiredArgsConstructor
public class StorageController {
    private final StorageFacadeService storageFacadeService;

    @ApiOperation("key-value 저장")
    @PostMapping
    public ApiResponse<StorageResponse> upsert(@Valid @RequestBody StorageRequest storageRequest) {
        return ApiResponse.success(storageFacadeService.upsert(
            storageRequest.getKeyString(),
            storageRequest.getValueMap()
        ));
    }

    @ApiOperation("get value")
    @GetMapping("/key/{key}")
    public ApiResponse<StorageResponse> getValue(@PathVariable String key) {
        return ApiResponse.success(storageFacadeService.findByKeyString(key));
    }

    @ApiOperation("key 리스트 가져오기")
    @GetMapping("/keys")
    public ApiResponse<List<String>> getKeys() {
        return ApiResponse.success(storageFacadeService.findAllKeys());
    }
}
