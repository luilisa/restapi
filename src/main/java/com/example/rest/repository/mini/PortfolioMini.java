package com.example.rest.repository.mini;

import java.sql.Timestamp;

public interface PortfolioMini {
    Long getId();
    String getName();
    Double getProfitability();
    Timestamp getCreationDate();
}
