package com.example.springphonemanagementajaxwebservice.controller;

import com.example.springphonemanagementajaxwebservice.model.Smartphone;
import com.example.springphonemanagementajaxwebservice.service.impl.SmartphoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;


@RestController
@CrossOrigin("*")
@RequestMapping("/api/smartphones")
public class SmartphoneController {
    @Autowired
    private SmartphoneService smartphoneService;

    @GetMapping()
    public ResponseEntity<Iterable<Smartphone>> getAllSmartphones() {
        return new ResponseEntity<>(smartphoneService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/home")
    public ModelAndView index() {
        return new ModelAndView("/index");
    }

    @PostMapping
    public ResponseEntity<Smartphone> createSmartphone(@RequestBody Smartphone smartphone) {
        smartphoneService.save(smartphone);
        return new ResponseEntity<>(smartphone, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Smartphone> deleteSmartphone(@PathVariable Long id) {
        Optional<Smartphone> smartphoneOptional = smartphoneService.findById(id);
        if (!smartphoneOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        smartphoneService.deleteById(id);
        return new ResponseEntity<>(smartphoneOptional.get(), HttpStatus.NO_CONTENT);
    }

}
