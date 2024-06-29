package kr.mashup.branding.service.birthday;

import kr.mashup.branding.domain.birthday.BirthdayCard;
import kr.mashup.branding.repository.birthday.BirthdayCardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BirthdayService {

    private final BirthdayCardRepository birthdayCardRepository;

    public Set<Long> getSentBirthdayCardMemberIds(Long senderMemberId, Long generationId) {
        return birthdayCardRepository.findAllBySenderMemberIdAndGenerationId(senderMemberId, generationId)
            .stream()
            .map(BirthdayCard::getRecipientMemberId)
            .collect(Collectors.toSet());
    }
}
