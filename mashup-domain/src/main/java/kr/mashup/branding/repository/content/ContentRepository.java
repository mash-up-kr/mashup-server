package kr.mashup.branding.repository.content;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.mashup.branding.domain.schedule.Content;

public interface ContentRepository extends JpaRepository<Content, Long> {
}
/**
 * content 연관관계
 * many to one: event
 */