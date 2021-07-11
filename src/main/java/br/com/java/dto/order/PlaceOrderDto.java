package br.com.java.dto.order;

import javax.validation.constraints.NotNull;

import br.com.java.model.User;

public class PlaceOrderDto {

    private Integer id;
    
    private @NotNull User user;

    private @NotNull Double totalPrice;

    public PlaceOrderDto() {
    }

    public PlaceOrderDto(Integer id, User user, @NotNull Double totalPrice) {
        this.id = id;
        this.user = user;
        this.totalPrice = totalPrice;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }
}
