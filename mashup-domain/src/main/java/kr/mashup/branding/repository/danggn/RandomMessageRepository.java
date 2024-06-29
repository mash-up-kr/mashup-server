package kr.mashup.branding.repository.danggn;

import kr.mashup.branding.domain.randommessage.RandomMessage;
import kr.mashup.branding.domain.randommessage.RandomMessageType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RandomMessageRepository extends JpaRepository<RandomMessage, Long> {

    List<RandomMessage> findByType(RandomMessageType type);
}
