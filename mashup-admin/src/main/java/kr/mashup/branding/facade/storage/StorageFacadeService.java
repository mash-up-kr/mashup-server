package kr.mashup.branding.facade.storage;

import kr.mashup.branding.service.storage.StorageService;
import kr.mashup.branding.ui.storage.response.KeyResponse;
import kr.mashup.branding.ui.storage.response.StorageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StorageFacadeService {

    private final StorageService storageService;

    public StorageResponse upsert(String keyString, Map<String, Object> valueMap) {
        return StorageResponse.from(storageService.upsert(keyString, valueMap));
    }

    public StorageResponse findByKeyString(String keyString) {
        return StorageResponse.from(storageService.findByKey(keyString));
    }

    public List<String> findAllKeys() {
        return storageService.findAllKeys();
    }
}
