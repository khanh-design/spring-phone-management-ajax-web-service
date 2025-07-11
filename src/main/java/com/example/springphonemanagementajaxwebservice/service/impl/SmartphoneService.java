package com.example.springphonemanagementajaxwebservice.service.impl;

import com.example.springphonemanagementajaxwebservice.model.Smartphone;
import com.example.springphonemanagementajaxwebservice.repository.ISmartphoneRepository;
import com.example.springphonemanagementajaxwebservice.service.ISmartphoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SmartphoneService implements ISmartphoneService<Smartphone> {
    @Autowired
    private ISmartphoneRepository iSmartphoneRepository;


    @Override
    public Iterable<Smartphone> findAll() {
        return iSmartphoneRepository.findAll();
    }

    @Override
    public Optional<Smartphone> findById(Long id) {
        return iSmartphoneRepository.findById(id);
    }

    @Override
    public void save(Smartphone smartphone) {
        iSmartphoneRepository.save(smartphone);
    }

    @Override
    public void deleteById(Long id) {
        iSmartphoneRepository.deleteById(id);
    }
}
