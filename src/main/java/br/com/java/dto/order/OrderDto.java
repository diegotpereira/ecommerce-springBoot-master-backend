package br.com.java.dto.order;

import javax.validation.constraints.NotNull;

public class OrderDto {
    
    private Integer id;
    
    private @NotNull Integer userId;

    public OrderDto() {
    }

    public OrderDto(Integer id, @NotNull Integer userId) {
        this.id = id;
        this.userId = userId;
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
}
