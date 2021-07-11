package br.com.java.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.java.dto.cart.AddToCartDto;
import br.com.java.dto.cart.CartDto;
import br.com.java.dto.cart.CartItemDto;
import br.com.java.exception.CartItemNotExistException;
import br.com.java.model.Cart;
import br.com.java.model.Product;
import br.com.java.model.User;
import br.com.java.repository.CartRepository;

@Service
@Transactional
public class CartService {
    
    @Autowired
    private CartRepository repository;

    public CartService(){}

    public CartService(CartRepository repository) {
        this.repository = repository;
    }

    public void addToCart(AddToCartDto addToCartDto, Product product, User user){
        Cart cart = new Cart(product, addToCartDto.getQuantity(), user);
        repository.save(cart);
    }

    public CartDto listCartItems(User user) {
        List<Cart> cartList = repository.findAllByUserOrderByCreatedDateDesc(user);
        List<CartItemDto> cartItems = new ArrayList<>();

        for (Cart cart:cartList){
            CartItemDto cartItemDto = getDtoFromCart(cart);
            cartItems.add(cartItemDto);
        }
        double totalCost = 0;
        for (CartItemDto cartItemDto :cartItems){
            totalCost += (cartItemDto.getProduct().getPrice()* cartItemDto.getQuantity());
        }
        CartDto cartDto = new CartDto(cartItems,totalCost);
        return cartDto;
    }

    public static CartItemDto getDtoFromCart(Cart cart) {
        CartItemDto cartItemDto = new CartItemDto(cart);

        return cartItemDto;
    }

    public void updateCartItem(AddToCartDto cartDto, User user, Product product) {
        Cart cart = repository.getOne(cartDto.getId());
        cart.setQuantity(cartDto.getQuantity());
        cart.setCreaDate(new Date());
        repository.save(cart);
    }

    public void deleteCartItem(int id, int userId) throws CartItemNotExistException {
        if (!repository.existsById(id)) {
            throw new CartItemNotExistException("Id do carrinho inválido : " + id);

            repository.deleteById(id);
        }
    }

    public void deleteCartItem(int userId) {
        repository.deleteAll();
    }

    public void deleteUserCartItems(User user) {
        repository.deleteByUser(user);
    }
}
