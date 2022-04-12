package com.example.rest.repository;

import com.example.rest.entity.Portfolio;
import com.example.rest.repository.mini.PortfolioMini;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PortfolioRepository extends JpaRepository<Portfolio, Long> {
    List<Portfolio> findAllByUserEntityId(Long userId);
    @Query(value = "SELECT portfolio.id as id, portfolio.profitability as profitability, portfolio.creation_date as creationDate, portfolio.name as name from portfolio", nativeQuery = true)
    List<PortfolioMini> findAllPortfolio();
    @Query(value = "SELECT portfolio.id as id, portfolio.profitability as profitability, portfolio.creation_date as creationDate, portfolio.name as name from portfolio where user_id= :userId", nativeQuery = true)
    List<PortfolioMini> findAllPortfolioByUserEntityId(@Param (value = "userId") Long userId);
}
