package com.nro.footballmanager.service.implementation;

import com.nro.footballmanager.entity.Stadium;
import com.nro.footballmanager.repository.StadiumRepository;
import com.nro.footballmanager.service.StadiumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class StadiumServiceImpl implements StadiumService {
    @Autowired
    private StadiumRepository stadiumRepository;
    @Override
    public Stadium saveStadium(Stadium stadium) {
        return stadiumRepository.save(stadium);
    }

    @Override
    public List<Stadium> fetchStadiumsList() {
        return stadiumRepository.findAll();
    }

    @Override
    public Stadium updateStadium(Stadium stadium, Long stadiumID) {
        Stadium old = stadiumRepository.findById(stadiumID).get();

        if (Objects.nonNull(stadium.getName()) && !"".equalsIgnoreCase(stadium.getName()))
            old.setName(stadium.getName());

        if (Objects.nonNull(stadium.getLocation()) && !"".equalsIgnoreCase(stadium.getLocation()))
            old.setLocation(stadium.getLocation());

        return stadiumRepository.save(old);
    }

    @Override
    public Boolean stadiumExistance(Long stadiumID) {
        return stadiumRepository.existsById(stadiumID);
    }

    @Override
    public void deleteStadiumByID(Long stadiumID) {
        stadiumRepository.deleteById(stadiumID);
    }

    @Override
    public void deleteStadiums() {
        stadiumRepository.deleteAll();
    }
}
