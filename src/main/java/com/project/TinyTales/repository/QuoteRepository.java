package com.project.TinyTales.repository;

import com.project.TinyTales.model.Quote;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuoteRepository extends JpaRepository<Quote, Long> {

    List<Quote> findByUsedInCycleFalse();
}
