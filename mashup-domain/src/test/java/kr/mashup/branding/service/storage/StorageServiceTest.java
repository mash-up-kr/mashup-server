package kr.mashup.branding.service.storage;

import kr.mashup.branding.domain.storage.Storage;
import kr.mashup.branding.domain.storage.StorageNotFoundException;
import kr.mashup.branding.repository.storage.StorageRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StorageServiceTest {

    @Mock
    private StorageRepository storageRepository;

    @InjectMocks
    private StorageService sut;

    @Test
    @DisplayName("findByKeyOptional은 키가 존재하면 Storage를 반환한다")
    void findByKeyOptional_returnsStorage() {
        // given
        Storage storage = Storage.of("test-key", Map.of("value", 5));
        when(storageRepository.findByKeyString("test-key")).thenReturn(Optional.of(storage));

        // when
        Optional<Storage> result = sut.findByKeyOptional("test-key");

        // then
        assertThat(result).isPresent();
        assertThat(result.get().getValueMap().get("value")).isEqualTo(5);
    }

    @Test
    @DisplayName("findByKeyOptional은 키가 없으면 빈 Optional을 반환한다 (예외 없음)")
    void findByKeyOptional_returnsEmptyWhenNotFound() {
        // given
        when(storageRepository.findByKeyString("missing-key")).thenReturn(Optional.empty());

        // when
        Optional<Storage> result = sut.findByKeyOptional("missing-key");

        // then
        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("findByKey는 키가 없으면 StorageNotFoundException을 던진다")
    void findByKey_throwsWhenNotFound() {
        // given
        when(storageRepository.findByKeyString("missing-key")).thenReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> sut.findByKey("missing-key"))
                .isInstanceOf(StorageNotFoundException.class);
    }
}
