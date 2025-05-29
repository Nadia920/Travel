package com.java.Travel.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;


@Entity
@Table(name = "Users")
public class UserEntity extends BaseEntity {

    @NotNull
    @Column(name = "login", length = 15)
    private String login;

    @Column(name = "email", length = 40)
    private String email;

    @NotNull
    @Column(name = "password", length = 15)
    private String password;

    @NotNull
    @Column(name = "first_name", length = 15)
    private String firstName;

    @NotNull
    @Column(name = "last_name", length = 15)
    private String lastName;

    @NotNull
    @Column(name = "patronymic", length = 15)
    private String patronymic;

    @NotNull
    @Column(name = "phone_number", length = 15)
    private String phoneNumber;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private Set<OrderEntity> orders;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "wallet_id")
    private WalletEntity wallet;


    @ManyToOne(optional = false, cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id")
    private RoleEntity roleEntity;


    public UserEntity() {
    }

    public UserEntity(UserEntity user) {
        this.setId(user.getId());
        this.login = user.getLogin();
        this.password = user.getPassword();
        this.roleEntity = user.getRoleEntity();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.patronymic = user.getPatronymic();
        this.phoneNumber = user.getPhoneNumber();

    }


    public UserEntity(@NotNull String login, String email, @NotNull String password, @NotNull String firstName, @NotNull String lastName, @NotNull String patronymic, @NotNull String phoneNumber, Set<OrderEntity> orders, WalletEntity wallet, RoleEntity roleEntity) {
        this.login = login;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.patronymic = patronymic;
        this.phoneNumber = phoneNumber;
        this.orders = orders;
        this.wallet = wallet;
        this.roleEntity = roleEntity;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public WalletEntity getWallet() {
        return wallet;
    }

    public void setWallet(WalletEntity wallet) {
        this.wallet = wallet;
    }

    public RoleEntity getRoleEntity() {
        return roleEntity;
    }

    public void setRoleEntity(RoleEntity role) {
        this.roleEntity = role;
    }

    public Set<OrderEntity> getOrders() {
        return orders;
    }

    public void setOrders(Set<OrderEntity> orders) {
        this.orders = orders;
    }


}