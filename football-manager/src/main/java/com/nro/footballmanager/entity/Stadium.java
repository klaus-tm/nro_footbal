package com.nro.footballmanager.entity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Stadium {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    private String location;

    @JsonIgnore
    @OneToMany(mappedBy = "stadium")
    private List<Game> games;

    @PreRemove
    private void setNull(){
        for (Game game: this.games)
            game.setStadium(null);
    }
}
