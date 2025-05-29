package com.java.Travel.controller.dto;

public class WalletDTO {

    private Long id;
    private Double sum;
    private UserDTO userDTO;

    public WalletDTO(Long id, Double sum, UserDTO userDTO) {
        this.id = id;
        this.sum = sum;
        this.userDTO = userDTO;
    }

    public WalletDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getSum() {
        return sum;
    }

    public void setSum(Double sum) {
        this.sum = sum;
    }

    public UserDTO getUserDTO() {
        return userDTO;
    }

    public void setUserDTO(UserDTO userDTO) {
        this.userDTO = userDTO;
    }
}