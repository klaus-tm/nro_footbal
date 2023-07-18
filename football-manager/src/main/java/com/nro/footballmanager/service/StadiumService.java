package com.nro.footballmanager.service;

import com.nro.footballmanager.entity.Stadium;

import java.util.List;
import java.util.Optional;

public interface StadiumService {
    List<Stadium> findAllStadiums();

    Optional<Stadium> getStadiumByID(Long stadiumID);

    Stadium saveStadium(Stadium stadium);

    Stadium updateStadium(Stadium oldStadium, Stadium newStadium);

    Boolean stadiumExists(Long stadiumID);

    void deleteStadiumByID(Long stadiumID);

}
