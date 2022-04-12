package com.example.rest.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Table(name = "portfolio")
public class Portfolio {

    private long id;
    private double profitability;
    private Timestamp creationDate;
    private String name;
    private UserEntity userEntity;
    private List<Stock> stocks;

    public Portfolio() {
    }


    public Portfolio(double profitability, Timestamp creationDate, String name, List<Stock> stocks, UserEntity userEntity) {
        this.profitability = profitability;
        this.creationDate = creationDate;
        this.name = name;
        this.stocks = stocks;
        this.userEntity = userEntity;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }

    @Column(name = "profitability", nullable = false)
    public double getProfitability() {
        return profitability;
    }
    public void setProfitability(double profitability) {
        this.profitability = profitability;
    }

    @Column(name = "creation_date")
    public Timestamp getCreationDate() {
        return creationDate;
    }
    public void setCreationDate(Timestamp creationDate) {
        this.creationDate = creationDate;
    }

    @Column(name = "name", nullable = false)
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    @ManyToMany
    @JoinTable(
            name = "stocks_added",
            joinColumns = @JoinColumn(name = "portfolio_id"),
            inverseJoinColumns = @JoinColumn(name = "stock_id")
    )
    public List<Stock> getStocks() {
        return stocks;
    }
    public void setStocks(List<Stock> stocks) {
        this.stocks = stocks;
    }

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    public UserEntity getUserEntity() {
        return userEntity;
    }
    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

    public void addStock(Stock stock) {
        stocks.add(stock);
    }

    public void assignUser(UserEntity userEntity) {
        this.userEntity = userEntity;
    }
}
