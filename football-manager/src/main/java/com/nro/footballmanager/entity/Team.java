package com.nro.footballmanager.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @JsonIgnore
    @OneToMany(mappedBy = "team")
    private List<Player>players;

    @JsonIgnore
    @OneToMany(mappedBy = "teamOne")
    private List<Game> gamesOne;

    @JsonIgnore
    @OneToMany(mappedBy = "teamTwo")
    private List<Game> gamesTwo;

    @PreRemove
    private void setNull(){
        for (Player player: this.players)
            player.setTeam(null);
        for (Game gameOne: this.gamesOne)
            gameOne.setTeamOne(null);
        for (Game gameTwo : this.gamesTwo)
            gameTwo.setTeamTwo(null);
    }
}
