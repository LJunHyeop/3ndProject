package com.green.thirdproject.score.score.model;

import lombok.Data;

@Data
public class RankRes extends RankReq{
    private int classRank;

    private int gradeRank;

    private int classStudentCount;

    private int gradeStudentCount;
}
