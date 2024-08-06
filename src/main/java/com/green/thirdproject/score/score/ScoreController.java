package com.green.thirdproject.score.score;

import com.green.fefu.common.ResultDto;
import com.green.fefu.score.model.Dto;
import com.green.fefu.score.model.InsScoreReq;
import com.green.fefu.score.model.StuGetRes;

public interface ScoreController {
    public ResultDto<Long> postScore(InsScoreReq p);
    ResultDto<Dto> getScore( StuGetRes p);
}
