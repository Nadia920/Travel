package com.java.Travel.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Entity
@Table(name = "Wallet")
public class WalletEntity extends BaseEntity {

    @NotNull
    @Column(name = "sum")
    private Double sum;


    @OneToOne(mappedBy = "wallet")
    private UserEntity owner;

    public WalletEntity() {
    }

    public WalletEntity(@NotNull Double sum) {
        this.sum = sum;
    }

    public WalletEntity(@NotNull Double sum, UserEntity owner) {
        this.sum = sum;
        this.owner = owner;
    }

    public Double getSum() {
        return sum;
    }

    public void setSum(Double sum) {
        this.sum = sum;
    }

    public UserEntity getOwner() {
        return owner;
    }

    public void setOwner(UserEntity owner) {
        this.owner = owner;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WalletEntity that = (WalletEntity) o;
        return Objects.equals(sum, that.sum) &&
                Objects.equals(owner, that.owner);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sum, owner);
    }

}
