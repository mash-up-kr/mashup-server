package kr.mashup.branding.repository.content;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.mashup.branding.domain.content.Content;

public interface ContentRepository extends JpaRepository<Content, Long> {
}
