package kr.mashup.branding.repository.birthday;

import kr.mashup.branding.domain.birthday.BirthdayCard;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BirthdayCardRepository extends JpaRepository<BirthdayCard, Long> {

    List<BirthdayCard> findAllBySenderMemberIdAndGenerationId(Long senderMemberId, Long generationId);

    Boolean existsBySenderMemberIdAndRecipientMemberIdAndGenerationId(Long senderMemberId, Long recipientMemberId, Long generationId);

    List<BirthdayCard> findAllByRecipientMemberIdAndGenerationId(Long recipientMemberId, Long generationId);
}
