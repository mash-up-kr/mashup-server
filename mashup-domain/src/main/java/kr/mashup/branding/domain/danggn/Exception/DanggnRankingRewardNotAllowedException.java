package kr.mashup.branding.domain.danggn.Exception;

import kr.mashup.branding.domain.ResultCode;
import kr.mashup.branding.domain.exception.BadRequestException;

public class DanggnRankingRewardNotAllowedException extends BadRequestException {
    public DanggnRankingRewardNotAllowedException() {
        super(ResultCode.RANKING_REWARD_NOT_ALLOWED);
    }
}
