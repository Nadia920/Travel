package com.java.Travel.controller.dto;


import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

public class CountryDTO {

    private  Long id;

    @NotNull
    @Size(min = 3,max = 40)
    private  String name;

    private Set<CityDTO> cityDTOSet;

    public CountryDTO(){

    }

    public CountryDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<CityDTO> getCityDTOSet() {
        return cityDTOSet;
    }

    public void setCityDTOSet(Set<CityDTO> cityDTOSet) {
        this.cityDTOSet = cityDTOSet;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
