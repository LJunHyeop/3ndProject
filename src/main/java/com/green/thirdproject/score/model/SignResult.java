package com.green.thirdproject.score.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SignResult {
    @JsonIgnore
    private int studentPk;
    @JsonIgnore
    private  int semester;
    @JsonIgnore
    private int year;

    private int examSign;

    private String pic;
}
