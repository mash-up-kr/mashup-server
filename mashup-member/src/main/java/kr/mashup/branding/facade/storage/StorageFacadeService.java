package kr.mashup.branding.facade.storage;

import kr.mashup.branding.domain.storage.Storage;
import kr.mashup.branding.service.storage.StorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StorageFacadeService {

    private final StorageService storageService;

    public Storage findByKeyString(String keyString) {
        return storageService.findByKey(keyString);
    }
}
