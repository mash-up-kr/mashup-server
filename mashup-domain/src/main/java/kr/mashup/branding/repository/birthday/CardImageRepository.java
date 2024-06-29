package kr.mashup.branding.repository.birthday;

import kr.mashup.branding.domain.birthday.CardImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardImageRepository extends JpaRepository<CardImage, Long> {
}
