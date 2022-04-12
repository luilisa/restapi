package com.example.rest.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Table(name = "stockDto")
public class StockDto {
    private long id;
    private Timestamp date;
    private double price;
    private String figi;

    public StockDto(Timestamp date, double price,  String figi) {
        this.date = date;
        this.price = price;
        this.figi = figi;
    }

    public StockDto() {
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
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


}
