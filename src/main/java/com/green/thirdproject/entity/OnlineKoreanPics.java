package com.green.thirdproject.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class OnlineKoreanPics extends CreatedAt{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long picId;

    @ManyToOne
    @JoinColumn(name="que_id", nullable=false)
    private OnlineKorean onlineKorean;

    @Column(length=70)
    private String pic;
}
