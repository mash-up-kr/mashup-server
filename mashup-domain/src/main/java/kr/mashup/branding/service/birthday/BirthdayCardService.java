package kr.mashup.branding.service.birthday;

import kr.mashup.branding.domain.birthday.BirthdayCard;
import kr.mashup.branding.domain.birthday.CardImage;
import kr.mashup.branding.domain.exception.BadRequestException;
import kr.mashup.branding.domain.member.Member;
import kr.mashup.branding.domain.member.MemberGeneration;
import kr.mashup.branding.domain.randommessage.RandomMessage;
import kr.mashup.branding.domain.randommessage.RandomMessageType;
import kr.mashup.branding.repository.birthday.BirthdayCardRepository;
import kr.mashup.branding.repository.birthday.CardImageRepository;
import kr.mashup.branding.repository.danggn.RandomMessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BirthdayCardService {

    private final CardImageRepository cardImageRepository;
    private final BirthdayCardRepository birthdayCardRepository;
    private final RandomMessageRepository randomMessageRepository;

    public List<CardImage> getDefault() {
        return cardImageRepository.findAll();
    }

    public void checkAlreadySent(Long recipientMemberId, Member senderMember, MemberGeneration memberGeneration) {
        if (birthdayCardRepository.existsBySenderMemberIdAndRecipientMemberIdAndGenerationId(senderMember.getId(), recipientMemberId, memberGeneration.getGeneration().getId())) {
            throw new BadRequestException();
        }
    }

    public void send(Long recipientMemberId, Member senderMember, MemberGeneration memberGeneration, String message, String imageUrl) {
        birthdayCardRepository.save(
            new BirthdayCard(
                recipientMemberId,
                senderMember,
                memberGeneration,
                message,
                imageUrl
            )
        );
    }

    public List<BirthdayCard> getMy(Member recipientMember, MemberGeneration memberGeneration) {

        return birthdayCardRepository.findAllByRecipientMemberIdAndGenerationId(recipientMember.getId(), memberGeneration.getGeneration().getId());
    }

    public List<RandomMessage> findAll() {
        return randomMessageRepository.findByType(RandomMessageType.BIRTHDAY);
    }
}
