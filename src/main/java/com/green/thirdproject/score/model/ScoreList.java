package com.green.thirdproject.score.model;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class ScoreList {


    @Schema(example = "국어", description = "학생Pk")
    private String name;
    @Schema(example = "1", description = "학생Pk")
    private int exam;
    @Schema(example = "1", description = "학생Pk")
    private int mark;

}
