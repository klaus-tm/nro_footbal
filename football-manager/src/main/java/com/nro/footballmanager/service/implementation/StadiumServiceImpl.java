package com.nro.footballmanager.service.implementation;

import com.nro.footballmanager.entity.Stadium;
import com.nro.footballmanager.repository.StadiumRepository;
import com.nro.footballmanager.service.StadiumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class StadiumServiceImpl implements StadiumService {
    @Autowired
    private StadiumRepository stadiumRepository;

    @Override
    public List<Stadium> findAllStadiums() {
        return stadiumRepository.findAll();
    }

    @Override
    public Optional<Stadium> getStadiumByID(Long stadiumID) {
        return stadiumRepository.findById(stadiumID);
    }

    @Override
    public Stadium saveStadium(Stadium stadium) {
        return stadiumRepository.save(stadium);
    }


    @Override
    public Stadium updateStadium(Stadium oldStadium, Stadium newStadium) {

        if (Objects.nonNull(newStadium.getName()) && !"".equalsIgnoreCase(newStadium.getName()))
            oldStadium.setName(newStadium.getName());

        if (Objects.nonNull(newStadium.getLocation()) && !"".equalsIgnoreCase(newStadium.getLocation()))
            oldStadium.setLocation(newStadium.getLocation());

        if(Objects.nonNull(newStadium.getGames()))
            oldStadium.setGames(newStadium.getGames());

        return saveStadium(oldStadium);
    }

    @Override
    public Boolean stadiumExists(Long stadiumID) {
        return stadiumRepository.existsById(stadiumID);
    }

    @Override
    public void deleteStadiumByID(Long stadiumID) {
        stadiumRepository.deleteById(stadiumID);
    }

}
