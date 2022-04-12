package com.example.rest.controller;


import com.example.rest.entity.Stock;
import com.example.rest.entity.StockDto;
import com.example.rest.exception.ResourceNotFoundException;
import com.example.rest.repository.StockDtoRepository;
import com.example.rest.repository.StockRepository;
import com.example.rest.repository.mini.StockMini;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class StockController {
    private final StockRepository stockRepository;
    private final StockDtoRepository stockDtoRepository;
    Logger logger = LoggerFactory.getLogger(StockController.class);

    public StockController(StockRepository stockRepository, StockDtoRepository stockDtoRepository) {
        this.stockRepository = stockRepository;
        this.stockDtoRepository = stockDtoRepository;
    }
    
    public List<StockMini> getAllStocks() {
        return stockRepository.findAllStock();
    }


    @GetMapping("/stocks")
    public List<? extends Object> getAllStocks(@RequestParam(required = false) Object expand) {
        if (expand != null)  {
            return stockRepository.findAll();
        }
        return stockRepository.findAllStock();
    }

    @GetMapping("/stocks/lessThan")
    public List<? extends Object> getAllStocksLessThan(@RequestParam(required = false) Object expand, @RequestParam(required = false) Double price) {
        if (expand != null && price == null) {
            return stockRepository.findAll();
        } else if (expand == null && price == null){
            return stockRepository.findAllStock();
        } else if (expand != null && price != null) {
            return stockRepository.findAllByPriceIsLessThan(price);
        } else {
            return stockRepository.findAllStockLessThan(price);
        }
    }

    @GetMapping("/stocks/{id}")
    public ResponseEntity<Stock> getStockById(@PathVariable(value = "id") Long stockId) throws ResourceNotFoundException {
        Stock stock = stockRepository.findById(stockId)
                .orElseThrow(() -> new ResourceNotFoundException("Stock not found for this id :: " + stockId));
            return ResponseEntity.ok().body(stock);
    }


    @PostMapping("/stocks")
    public List<Stock> createStock(@RequestBody List<Stock> listStocks) {
//        StockDto stockDto = new StockDto();
        List<Stock> newStocks = new ArrayList<>();
        for (int i=0; i<listStocks.size(); i++)
        {
            Stock stock1 = listStocks.get(i);
            Stock stock = new Stock(
                    stock1.getTicker(),
                    stock1.getPrice(),
                    stock1.getFigi(),
                    stock1.getCurrency()
            );
            newStocks.add(i,stock);
        }
        return stockRepository.saveAll(newStocks);
    }

    @PutMapping("/stocks/{id}")
    public ResponseEntity<Stock> updateStock(@PathVariable(value = "id") Long stockId,
                                                 @RequestBody Stock stockDetails) throws ResourceNotFoundException {
        Stock stock = stockRepository.findById(stockId)
                .orElseThrow(() -> new ResourceNotFoundException("Stock not found for this id :: " + stockId));

        stock.setTicker(stockDetails.getTicker());
        stock.setPrice(stockDetails.getPrice());
        stock.setCompany(stockDetails.getCompany());
        stock.setCurrency(stockDetails.getCurrency());
        stock.setFigi(stockDetails.getFigi());
        stock.setDate(stockDetails.getDate());
        final Stock updatedStock = stockRepository.save(stock);
        return ResponseEntity.ok(updatedStock);
    }

    @DeleteMapping("/stocks/{id}")
    public Map<String, Boolean> deleteStock(@PathVariable(value = "id") Long stockId)
            throws ResourceNotFoundException {
        Stock stock = stockRepository.findById(stockId)
                .orElseThrow(() -> new ResourceNotFoundException("Stock not found for this id :: " + stockId));

        stockRepository.delete(stock);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }
}
