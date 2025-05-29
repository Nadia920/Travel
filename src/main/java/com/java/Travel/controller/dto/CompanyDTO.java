package com.java.Travel.controller.dto;



import com.java.Travel.model.Rating;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

public class CompanyDTO {

    private Long id;

    @NotNull
    @Size(min = 3, max = 30)
    private String name;

    @NotNull
    private Rating rating;

    private List<BusDTO> busDTOList;

    public CompanyDTO(){}

    public CompanyDTO(Long id, String name, Rating rating) {
        this.id = id;
        this.name = name;
        this.rating = rating;
    }

    public CompanyDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public CompanyDTO(String name, Rating rating) {
        this.name = name;
        this.rating = rating;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Rating getRating() {
        return rating;
    }

    public void setRating(Rating rating) {
        this.rating = rating;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<BusDTO> getBusDTOList() {
        return busDTOList;
    }

    public void setBusDTOList(List<BusDTO> busDTOList) {
        this.busDTOList = busDTOList;
    }

    @Override
    public String toString() {
        return "CompanyDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", rating=" + rating +
                ", busDTOList=" + busDTOList +
                '}';
    }
}