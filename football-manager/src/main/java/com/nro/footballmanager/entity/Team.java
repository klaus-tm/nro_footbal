package com.nro.footballmanager.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
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

}
