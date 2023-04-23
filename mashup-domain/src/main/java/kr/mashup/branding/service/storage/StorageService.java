package kr.mashup.branding.service.storage;

import kr.mashup.branding.domain.storage.Storage;
import kr.mashup.branding.domain.storage.StorageNotFoundException;
import kr.mashup.branding.repository.storage.StorageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class StorageService {
    private final StorageRepository storageRepository;

    @Transactional
    public Storage upsert(String keyString, Map<String, Object> valueMap) {
        Storage newStorage = storageRepository.findByKeyString(keyString).map(storage -> {
            storage.setValueMap(valueMap);
            return storage;
        }).orElse(Storage.of(keyString, valueMap));
        return storageRepository.save(newStorage);
    }

    @Transactional(readOnly = true)
    public Storage findByKey(String keyString) {
        return storageRepository.findByKeyString(keyString).orElseThrow(StorageNotFoundException::new);
    }
}
