package com.java.Travel.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Entity
@Table(name = "Bus")
public class BusEntity extends BaseEntity{

    @NotNull
    @Column(name = "name", length = 30)
    private String name;

    @NotNull
    @Column(name = "side_number", length = 10)
    private String number;


    @OneToMany(mappedBy = "bus")
    private Set<TripEntity> trips;

    @ManyToOne(optional = false, fetch=FetchType.LAZY)
    @JoinColumn(name = "company_id")
    @NotNull
    private CompanyEntity company;

    public BusEntity() {
    }

    public BusEntity(String name, String number, CompanyEntity companyEntity) {
        this.name = name;
        this.number = number;
        this.company = companyEntity;
    }

    public BusEntity(@NotNull String name, @NotNull String number) {
        this.name = name;
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<TripEntity> getTrips() {
        return trips;
    }

    public void setTrips(Set<TripEntity> trips) {
        this.trips = trips;
    }

    public CompanyEntity getCompany() {
        return company;
    }

    public void setCompany(CompanyEntity company) {
        this.company = company;
    }


    public String getSideNumber() {
        return number;
    }

    public void setSideNumber(String number) {
        this.number = number;
    }
}
