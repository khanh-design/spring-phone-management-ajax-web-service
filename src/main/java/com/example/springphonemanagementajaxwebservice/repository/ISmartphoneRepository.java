package com.example.springphonemanagementajaxwebservice.repository;

import com.example.springphonemanagementajaxwebservice.model.Smartphone;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ISmartphoneRepository extends JpaRepository<Smartphone, Long> {
}
