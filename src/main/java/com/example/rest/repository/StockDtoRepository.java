package com.example.rest.repository;

import com.example.rest.entity.StockDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StockDtoRepository extends JpaRepository<StockDto, Long> {
}
