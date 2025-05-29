package com.java.Travel.controller.dto;


import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class BusDTO {

    private Long id;

    @NotNull
    @Size(min = 3, max = 30)
    private String name;

    @NotNull
    @Size(min = 3, max = 10)
    private String sideNumber;


    private CompanyDTO companyDTO;

    public BusDTO(){}

    public BusDTO(Long id, String name, String sideNumber, CompanyDTO companyDTO) {
        this.id = id;
        this.name = name;
        this.sideNumber = sideNumber;
        this.companyDTO = companyDTO;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CompanyDTO getCompanyDTO() {
        return companyDTO;
    }

    public void setCompanyDTO(CompanyDTO companyDTO) {
        this.companyDTO = companyDTO;
    }

    public String getSideNumber() {
        return sideNumber;
    }

    public void setSideNumber(String sideNumber) {
        this.sideNumber = sideNumber;
    }
}
