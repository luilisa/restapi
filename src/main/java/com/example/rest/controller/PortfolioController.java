package com.example.rest.controller;


import com.example.rest.entity.Portfolio;
import com.example.rest.entity.Stock;
import com.example.rest.exception.ResourceNotFoundException;
import com.example.rest.repository.PortfolioRepository;
import com.example.rest.repository.StockRepository;
import com.example.rest.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class PortfolioController {
    private final PortfolioRepository portfolioRepository;
    private final StockRepository stockRepository;
    private final UserRepository userRepository;

    public PortfolioController(PortfolioRepository portfolioRepository, StockRepository stockRepository, UserRepository userRepository) {
        this.portfolioRepository = portfolioRepository;
        this.stockRepository = stockRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/portfolios/{id}")
    public ResponseEntity<Portfolio> getPortfolioById(@PathVariable(value = "id") Long portfolioId)
            throws ResourceNotFoundException {
        Portfolio portfolio = portfolioRepository.findById(portfolioId)
                .orElseThrow(() -> new ResourceNotFoundException("portfolio not found for this id :: " + portfolioId));
        return ResponseEntity.ok().body(portfolio);
    }

    @PostMapping("/portfolios")
    public Portfolio createPortfolio(@RequestBody Portfolio portfolio) {
        return portfolioRepository.save(portfolio);
    }

    @PutMapping("/portfolios/{id}")
    public ResponseEntity<Portfolio> updatePortfolio(@PathVariable(value = "id") Long portfolioId,
                                             @RequestBody Portfolio portfoliosDetails) throws ResourceNotFoundException {
        Portfolio portfolio = portfolioRepository.findById(portfolioId)
                .orElseThrow(() -> new ResourceNotFoundException("Portfolio not found for this id :: " + portfolioId));

        portfolio.setProfitability(portfoliosDetails.getProfitability());
        portfolio.setCreationDate(portfoliosDetails.getCreationDate());
        portfolio.setName(portfoliosDetails.getName());
        portfolio.setUserEntity(portfoliosDetails.getUserEntity());
        portfolio.setStocks(portfoliosDetails.getStocks());
        final Portfolio updatedPortfolio = portfolioRepository.save(portfolio);
        return ResponseEntity.ok(updatedPortfolio);
    }

    @PutMapping("/portfolios/{id}/stocks/{stock_id}")
    public Portfolio addStockToPortfolio(@PathVariable(value = "id") Long portfolioId,
                                                     @PathVariable Long stock_id) throws ResourceNotFoundException {
        Portfolio portfolio = portfolioRepository.findById(portfolioId)
                .orElseThrow(() -> new ResourceNotFoundException("Portfolio not found for this id :: " + portfolioId));
        Stock stock = stockRepository.findById(stock_id)
                .orElseThrow(() -> new ResourceNotFoundException("Stock not found for this id :: " + stock_id));
        portfolio.addStock(stock);
        return portfolioRepository.save(portfolio);
    }

    @DeleteMapping("/portfolios/{id}")
    public Map<String, Boolean> deletePortfolio(@PathVariable(value = "id") Long portfolioId)
            throws ResourceNotFoundException {
        Portfolio portfolio = portfolioRepository.findById(portfolioId)
                .orElseThrow(() -> new ResourceNotFoundException("Portfolio not found for this id :: " + portfolioId));

        portfolioRepository.delete(portfolio);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }

    @PostMapping("/portfolios/users/{userId}")
    public Portfolio createPortfolio(@PathVariable (value = "userId") Long userId,
                                  @RequestBody Portfolio portfolio) throws ResourceNotFoundException {
        return userRepository.findById(userId).map(userEntity -> {
            portfolio.setUserEntity(userEntity);
            return portfolioRepository.save(portfolio);
        }).orElseThrow(() -> new ResourceNotFoundException("userid " + userId + " not found"));
    }


    @GetMapping("/portfolios")
    public List<? extends Object> getAllPortfolios(@RequestParam(required = false) Object expand, @RequestParam (value = "userId", required = false) Long userId) {
        if (expand != null && userId != null) {
            return portfolioRepository.findAllByUserEntityId(userId);
        }
        else if (expand == null && userId != null) {
            return portfolioRepository.findAllPortfolioByUserEntityId(userId);
        }
        else if (expand == null && userId == null) {
            return portfolioRepository.findAllPortfolio();
        }
        return portfolioRepository.findAll();
    }
}
