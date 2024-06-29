package kr.mashup.branding.repository.rnb;

import kr.mashup.branding.domain.rnb.RnbMeta;
import kr.mashup.branding.domain.rnb.RnbPolicy;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RnbMetaRepository extends JpaRepository<RnbMeta, Long> {

    List<RnbMeta> findAllByPolicyIn(List<RnbPolicy> policies);
}
