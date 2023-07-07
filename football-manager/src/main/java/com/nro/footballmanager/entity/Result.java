package com.nro.footballmanager.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Result {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Integer goalsTeamOne;

    @Column
    private Integer goalsTeamTwo;

    @OneToOne(mappedBy = "result")
    private Game game;
}
