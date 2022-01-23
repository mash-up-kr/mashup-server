package kr.mashup.branding.domain.application;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

interface ApplicationRepository extends JpaRepository<Application, Long> {
//    boolean findByMember_memberIdAndApplicationId(Long memberId, Long applicationId);
//    List<Application> findByMember_memberId(Long memberId);
}
