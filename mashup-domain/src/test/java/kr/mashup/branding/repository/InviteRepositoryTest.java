package kr.mashup.branding.repository;

import kr.mashup.branding.domain.generation.Generation;
import kr.mashup.branding.domain.invite.Invite;
import kr.mashup.branding.domain.member.Platform;
import kr.mashup.branding.repository.generation.GenerationRepository;
import kr.mashup.branding.repository.invite.InviteRepository;
import kr.mashup.branding.util.DateRange;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest
@Disabled
public class InviteRepositoryTest {

	@Autowired
	private InviteRepository sut;
	@Autowired
	private GenerationRepository generationRepository;

	@Test
	@DisplayName("초대코드 생성")
	public void createInvite() {
		// given
		DateRange dateRange = DateRange.of(LocalDateTime.now(), LocalDateTime.now().plusDays(2));
		Generation generation = generationRepository.save(Generation.of(17,dateRange));
		Platform platform = Platform.SPRING;
		Invite invite = Invite.of(platform, generation, dateRange);

		// when
		Invite result = sut.save(invite);

		// then
		assertThat(result.getId()).isNotNull();
		assertThat(result.getCode().length()).isEqualTo(8);
		assertThat(result.getCode().substring(0, 5)).isEqualTo("SPABH");
	}
}
