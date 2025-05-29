package com.java.Travel.model;


import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Entity
@Table(name = "Company")
public class CompanyEntity extends BaseEntity {

    @NotNull
    @Column(name = "name", length = 30)
    private String name;

    @NotNull
    @Column(name = "rating")
    @Enumerated(EnumType.STRING)
    private Rating rating;

    @OneToMany(mappedBy = "company")
    private Set<BusEntity> buses;

    public CompanyEntity() {
    }

    public CompanyEntity(Long id, String name) {
        super(id);
        this.name = name;
    }

    public CompanyEntity(String name, Rating rating) {
        this.name = name;
        this.rating = rating;
    }

    public CompanyEntity(Long id, String name, Rating rating, Set<BusEntity> buses) {
        super(id);
        this.name = name;
        this.rating = rating;
        this.buses = buses;
    }

    public CompanyEntity(Long id, String name, Rating rating) {
        super(id);
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

    public Set<BusEntity> getBuses() {
        return buses;
    }

    public void setBuses(Set<BusEntity> buses) {
        this.buses = buses;
    }

}
