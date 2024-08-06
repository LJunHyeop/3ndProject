package com.green.thirdproject.entity.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
public class OnlineEnglishListening {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ListeningPk;

    private String question;

    private String answer;

    private String file;

    private String pic;
}
