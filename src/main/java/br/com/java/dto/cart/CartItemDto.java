package br.com.java.dto.cart;

import javax.validation.constraints.NotNull;

import br.com.java.model.Cart;
import br.com.java.model.Product;

public class CartItemDto {
    private Integer id;

    private @NotNull Integer userId;

    private @NotNull Integer quantity;

    private @NotNull Product product;

    public CartItemDto() {
    }

    public CartItemDto(Cart cart) {
        this.setId(cart.getId());
        this.setUserId(cart.getUser().getId());
        this.setQuantity(cart.getQuantity());
        this.setProduct(cart.getProduct());
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    @Override
    public String toString() {
        return "CartItemDto [id=" + id + ", product=" + product + ", quantity=" + quantity + ", userId=" + userId + "]";
    }
}
