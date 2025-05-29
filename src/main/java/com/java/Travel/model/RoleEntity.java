package com.java.Travel.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "App_role")
public class RoleEntity extends BaseEntity{

    @Column(name = "role", length = 12)
    String role;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "roleEntity")
    List<UserEntity> users;

    public RoleEntity(String role, List<UserEntity> users) {
        this.role = role;
        this.users = users;
    }

    public RoleEntity(String role) {
        this.role = role;
    }

    public RoleEntity(){}

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public List<UserEntity> getUsers() {
        return users;
    }

    public void setUsers(List<UserEntity> users) {
        this.users = users;
    }


}
