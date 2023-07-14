package com.nro.footballmanager.entity;

import com.nro.footballmanager.entity.enums.RoleEnum;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Player{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @Column
    private String name;

    @Column(nullable = false)
    private Integer goalsScored;

    @Column
    @Enumerated(EnumType.STRING)
    private RoleEnum role;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "teamID")
    private Team team;

    @PreRemove
    private void setNull(){
        this.setTeam(null);
    }
}
