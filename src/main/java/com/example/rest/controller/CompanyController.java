package com.example.rest.controller;

import com.example.rest.entity.Company;
import com.example.rest.exception.ResourceNotFoundException;
import com.example.rest.repository.CompanyRepository;
import com.example.rest.repository.mini.CompanyMini;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class CompanyController {
    private final CompanyRepository companyRepository;

    public CompanyController(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    @GetMapping("/companies")
    public List<? extends Object> getAllCompanies(@RequestParam(required = false) Object expand) {
        if (expand != null) {
            return companyRepository.findAll();
        } else {
            return companyRepository.findAllCompany();
        }
    }

    @GetMapping("/companies/{id}")
    public ResponseEntity<Object> getCompanyById(@PathVariable(value = "id") Long companyId, @RequestParam(required = false) Object expand)
            throws ResourceNotFoundException {
        Company expandCompany = companyRepository.findById(companyId)
                .orElseThrow(() -> new ResourceNotFoundException("Company not found for this id :: " + companyId));
        CompanyMini company = companyRepository.findCompanyById(companyId);
        if (expand != null) {
            return ResponseEntity.ok().body(expandCompany);
        } else {
            return ResponseEntity.ok().body(company);
        }
    }

    @PostMapping("/companies")
    public Company createCompany(@RequestBody Company company) {
        return companyRepository.save(company);
    }

    @PutMapping("/companies/{id}")
    public ResponseEntity<Company> updateCompany(@PathVariable(value = "id") Long companyId,
                                                 @RequestBody Company companyDetails) throws ResourceNotFoundException {
        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new ResourceNotFoundException("Company not found for this id :: " + companyId));

        company.setSymbol(companyDetails.getSymbol());
        company.setSector(companyDetails.getSector());
        company.setName(companyDetails.getName());
        company.setMarketCap(companyDetails.getMarketCap());
        company.setIpoYear(companyDetails.getIpoYear());
        company.setCountry(companyDetails.getCountry());
        final Company updatedCompany = companyRepository.save(company);
        return ResponseEntity.ok(updatedCompany);
    }

    @DeleteMapping("/companies/{id}")
    public Map<String, Boolean> deleteCompany(@PathVariable(value = "id") Long companyId)
            throws ResourceNotFoundException {
        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new ResourceNotFoundException("Company not found for this id :: " + companyId));

        companyRepository.delete(company);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }
}
