package com.example.springphonemanagementajaxwebservice.service;

import org.springframework.expression.spel.ast.OpAnd;

import java.util.Optional;

public interface ISmartphoneService<T> {
    Iterable<T> findAll();

    Optional<T> findById(Long id);

    void save(T t);
    void deleteById(Long id);
}
