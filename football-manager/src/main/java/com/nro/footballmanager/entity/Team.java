package com.nro.footballmanager.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @Column
    private String name;

    @Column(nullable = false)
    private Integer goalsScored;

    @Column(nullable = false)
    private Integer goalsReceived;

    @Column(nullable = false)
    private Integer victories;

    @Column(nullable = false)
    private Integer defeats;

    @Column(nullable = false)
    private Integer draws;

    @OneToMany(mappedBy = "team")
    private List<Player> players;

    @OneToMany(mappedBy = "teamOne")
    private List<Game> gamesOne;

    @OneToMany(mappedBy = "teamTwo")
    private List<Game> gamesTwo;
}
