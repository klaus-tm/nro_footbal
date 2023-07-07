package com.nro.footballmanager.entity;
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

    @OneToMany(mappedBy = "stadium")
    private List<Game> games;
}
