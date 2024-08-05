package com.green.thirdproject.score.model;

import lombok.Data;

@Data
public class InsScoreList {
    private String name;

    private int exam;

    private int mark;

    private Integer scoreId;

    private int subjectClassRank;

    private int subjectGradeRank;

    private int studentPk;

    private double classAvg;

    private double gradeAvg;

}