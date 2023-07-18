package com.nro.footballmanager.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;


@Entity
@Data
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "teamOneID")
    private Team teamOne;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "teamTwoID")
    private Team teamTwo;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "stadiumID")
    private Stadium stadium;

    @Column
    private LocalTime startHour;

    @Column
    private LocalDate date;

    @OneToOne
    @JoinColumn(name = "resultID")
    private Result result;
}
