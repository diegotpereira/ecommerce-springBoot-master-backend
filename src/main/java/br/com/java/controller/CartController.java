package br.com.java.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.com.java.common.ApiResponse;
import br.com.java.dto.cart.AddToCartDto;
import br.com.java.dto.cart.CartDto;
import br.com.java.exception.AuthenticationFailException;
import br.com.java.exception.CartItemNotExistException;
import br.com.java.exception.ProductNotExistException;
import br.com.java.model.*;
import br.com.java.service.AuthenticationService;
import br.com.java.service.CartService;
import br.com.java.service.ProductService;

@RestController
@RequestMapping("/cart")
public class CartController {
    
    @Autowired
    private CartService cartService;

    @Autowired
    private ProductService productService;

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addToCart(@RequestBody AddToCartDto addToCartDto, @RequestParam("token") String token) throws AuthenticationFailException, ProductNotExistException {
        authenticationService.authenticate(token);
        
        User user = authenticationService.getUser(token);
        Product product = productService.getProductById(addToCartDto.getProductId());

        System.out.println("produto para adicionar" + product.getName());
        cartService.addToCart(addToCartDto, product, user);

        return new ResponseEntity<ApiResponse>(new ApiResponse(true, "Adicionado ao carrinho"), HttpStatus.CREATED);
    }

    @GetMapping("/")
    public ResponseEntity<CartDto> getCartItems(@RequestParam("token") String token) throws AuthenticationFailException {
        authenticationService.authenticate(token);

        User user = authenticationService.getUser(token);
        CartDto cartDto = cartService.listCartItems(user);

        return new ResponseEntity<CartDto>(cartDto, HttpStatus.OK);
    }

    @PostMapping("/update/{cartItemId}")
    public ResponseEntity<ApiResponse> updateCartItem(@RequestBody @Valid AddToCartDto cartDto, @RequestParam("token") String token) throws AuthenticationFailException, ProductNotExistException {
        authenticationService.authenticate(token);
        User user = authenticationService.getUser(token);
        Product product = productService.getProductById(cartDto.getProductId());
        cartService.updateCartItem(cartDto, user, product);

        return new ResponseEntity<ApiResponse>(new ApiResponse(true, "O produto foi atualizado"), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{cartItemId}")
    public ResponseEntity<ApiResponse> deleteCartItem(@PathVariable("cartItemId") int itemID, @RequestParam("token") String token) throws AuthenticationFailException, CartItemNotExistException {
        authenticationService.authenticate(token);
        int userId = authenticationService.getUser(token).getId();
        cartService.deleteCartItem(itemID, userId);

        return new ResponseEntity<ApiResponse>(new ApiResponse(true, "O item foi removido"), HttpStatus.OK);
    }
}
