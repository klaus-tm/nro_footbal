package com.nro.footballmanager.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalTime;
import java.util.Date;

@Entity
@Data
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "teamOneID")
    private Team teamOne;

    @ManyToOne
    @JoinColumn(name = "teamTwoID")
    private Team teamTwo;

    @ManyToOne
    @JoinColumn(name = "stadiumID")
    private Stadium stadium;

    @Column
    private LocalTime startHour;

    @Column
    private Date date;

    @OneToOne
    @JoinColumn(name = "resultID")
    private Result result;
}
