package kr.mashup.branding.domain.danggn.Exception;

import kr.mashup.branding.domain.ResultCode;
import kr.mashup.branding.domain.exception.NotFoundException;

public class DanggnRankingRoundNotFoundException extends NotFoundException {
    public DanggnRankingRoundNotFoundException() {
        super(ResultCode.RANKING_ROUND_NOT_FOUND);
    }
}
