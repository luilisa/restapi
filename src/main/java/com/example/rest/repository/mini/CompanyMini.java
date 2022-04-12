package com.example.rest.repository.mini;

public interface CompanyMini {
    Long getId();
    String getSymbol();
    String getSector();
    String getName();
    Long getMarketCap();
    Integer getIpoYear();
    String getCountry();
}
