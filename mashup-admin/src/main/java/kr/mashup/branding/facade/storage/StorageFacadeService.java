package kr.mashup.branding.facade.storage;

import kr.mashup.branding.service.storage.StorageService;
import kr.mashup.branding.ui.storage.response.StorageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StorageFacadeService {

    private final StorageService storageService;

    public StorageResponse upsert(String keyString, String valueMap) {
        return StorageResponse.from(storageService.upsert(keyString, valueMap));
    }

    public StorageResponse findByKeyString(String keyString) {
        return StorageResponse.from(storageService.findByKey(keyString));
    }
}
