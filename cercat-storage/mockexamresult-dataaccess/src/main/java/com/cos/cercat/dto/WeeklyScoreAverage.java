package com.cos.cercat.dto;

import com.cos.cercat.common.util.ScoreFormatter;
import com.cos.cercat.mockexamresult.ScoreData;
import com.cos.cercat.mockexamresult.WeeklyScoreData;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

@Getter
public class WeeklyScoreAverage {
    private final Double scoreAverage;
    private final int weekOfMonth;

    @QueryProjection
    public WeeklyScoreAverage(Double scoreAverage, Integer weekOfMonth) {
        this.scoreAverage = ScoreFormatter.formatScore(scoreAverage);
        this.weekOfMonth = weekOfMonth;
    }

    public ScoreData toScoreData() {
        return new WeeklyScoreData(
                scoreAverage,
                weekOfMonth
        );
    }

}
