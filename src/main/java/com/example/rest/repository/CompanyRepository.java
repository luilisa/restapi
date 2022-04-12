package com.example.rest.repository;

import com.example.rest.entity.Company;
import com.example.rest.repository.mini.CompanyMini;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {
    @Query(value = "SELECT company.id as id, company.symbol as symbol, company.sector as sector, company.name as name, company.market_cap as marketCap, company.ipo_year as ipoYear, company.country as country FROM company", nativeQuery = true)
    List<CompanyMini> findAllCompany();
    @Query(value = "SELECT company.id as id, company.symbol as symbol, company.sector as sector, company.name as name, company.market_cap as marketCap, company.ipo_year as ipoYear, company.country as country FROM company where id= :companyId", nativeQuery = true)
    CompanyMini findCompanyById(@Param(value = "companyId")Long companyId);
}
