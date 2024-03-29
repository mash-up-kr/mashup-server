package kr.mashup.branding.repository.storage;

import kr.mashup.branding.domain.storage.Storage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StorageRepository extends JpaRepository<Storage, Long> {
    Optional<Storage> findByKeyString(String keyString);
}