package com.example.rest.entity;

import javax.persistence.*;

@Entity
@Table(name = "company")
public class Company {

    private long id;
    private String symbol;
    private String sector;
    private String name;
    private long marketCap;
    private int ipoYear;
    private String country;
    private Stock stock;

    public Company(String symbol, String sector, String name, long marketCap, int ipoYear, String country, Stock stock) {
        this.symbol = symbol;
        this.sector = sector;
        this.name = name;
        this.marketCap = marketCap;
        this.ipoYear = ipoYear;
        this.country = country;
        this.stock = stock;
    }

    public Company() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Company [id=" + id + ", name=" + name + ", IPO year=" + ipoYear + " ]";
    }

    @Column(name = "symbol", nullable = false)
    public String getSymbol() {
        return symbol;
    }
    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    @Column(name = "sector", nullable = false)
    public String getSector() {
        return sector;
    }
    public void setSector(String sector) {
        this.sector = sector;
    }
    @Column(name = "name", nullable = false)
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "market_cap", nullable = false)
    public long getMarketCap() {
        return marketCap;
    }
    public void setMarketCap(long marketCap) {
        this.marketCap = marketCap;
    }

    @Column(name = "ipo_year", nullable = false)
    public int getIpoYear() {
        return ipoYear;
    }
    public void setIpoYear(int ipoYear) {
        this.ipoYear = ipoYear;
    }

    @Column(name = "country", nullable = false)
    public String getCountry() {
        return country;
    }
    public void setCountry(String country) {
        this.country = country;
    }

    @OneToOne(mappedBy = "company")
    public Stock getStock() {
        return stock;
    }
    public void setStock(Stock stock) {
        this.stock = stock;
    }

}
