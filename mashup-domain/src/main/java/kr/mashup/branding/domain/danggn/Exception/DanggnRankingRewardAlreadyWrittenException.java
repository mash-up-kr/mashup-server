package kr.mashup.branding.domain.danggn.Exception;

import kr.mashup.branding.domain.ResultCode;
import kr.mashup.branding.domain.exception.BadRequestException;

public class DanggnRankingRewardAlreadyWrittenException extends BadRequestException {
    public DanggnRankingRewardAlreadyWrittenException() {
        super(ResultCode.RANKING_REWARD_ALREADY_WRITTEN);
    }
}
