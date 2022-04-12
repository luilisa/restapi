package com.example.rest.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Table(name = "stock")
public class Stock {

    private long id;
    private Timestamp date;
    private String ticker;
    private Company company;
    private double price;
    private String figi;
    private String currency;

    private List<Portfolio> portfolios;

//    public Stock(Timestamp date, String symbol, double openPrice, double closePrice, double highPrice, double lowPrice, double currentPrice, Company company, int volume, List<Portfolio> portfolios) {
//        this.date = date;
//        this.symbol = symbol;
//        this.openPrice = openPrice;
//        this.closePrice = closePrice;
//        this.highPrice = highPrice;
//        this.lowPrice = lowPrice;
//        this.currentPrice = currentPrice;
//        this.company = company;
//        this.volume = volume;
//        this.portfolios = portfolios;
//    }

        public Stock(Timestamp date, String ticker, double price, String currency, String figi, List<Portfolio> portfolios) {
        this.date = date;
        this.ticker = ticker;
        this.currency = currency;
        this.price = price;
        this.figi = figi;
        this.portfolios = portfolios;
    }

    public Stock() {
    }

    public Stock(String ticker, double price, String figi, String currency) {
        this.ticker = ticker;
        this.currency = currency;
        this.price = price;
        this.figi = figi;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }

    @Column(name = "ticker")
    public String getTicker() {
        return ticker;
    }
    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    @Column(name = "date")
    public Timestamp getDate() {
        return date;
    }
    public void setDate(Timestamp date) {
        this.date = date;
    }

    @Column(name = "figi")
    public String getFigi() {
        return figi;
    }
    public void setFigi(String figi) {
        this.figi = figi;
    }

    @Column(name = "price")
    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
    }

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "company_id", referencedColumnName = "id")
    @JsonIgnore
    public Company getCompany() {
        return company;
    }
    public void setCompany(Company company) {
        this.company = company;
    }

    @JsonIgnore
    @ManyToMany(mappedBy = "stocks")
    public List<Portfolio> getPortfolios() {
        return portfolios;
    }
    public void setPortfolios(List<Portfolio> portfolios) {
        this.portfolios = portfolios;
    }

    @Column(name = "currency")
    public String getCurrency() {
        return currency;
    }
    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
