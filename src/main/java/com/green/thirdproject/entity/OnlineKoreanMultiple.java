package com.green.thirdproject.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class OnlineKoreanMultiple {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long AnswerId;

    @ManyToOne
    @JoinColumn(name="que_id", nullable = false)
    private OnlineKorean onlineKorean;

    private Integer num;

    @Column(length=30, nullable = false)
    private String sentence;
}
