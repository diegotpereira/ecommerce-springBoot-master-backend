package br.com.java.controller;

import java.util.List;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.java.dto.product.ProductDto;
import br.com.java.model.WishList;
import br.com.java.service.AuthenticationService;
import br.com.java.service.ProductService;
import br.com.java.service.WishListService;

@RestController
@RequestMapping("/wishlist")
public class WishListController {
    
    @Autowired
    private WishListService wishListService;

    @Autowired
    private AuthenticationService authenticationService;

    @GetMapping("/{token}")
    public ResponseEntity<List<ProductDto>> getWishList(@PathVariable("token") String token) {
        int user_id = authenticationService.getUser(token).getId();
        List<WishList> body = wishListService.readWishList(user_id);
        List<ProductDto> products = new ArrayList<ProductDto>();

        for(WishList wishList : body) {
            products.add(ProductService.getDtoFromProduct(wishList.getProduct()));
        }

        return new ResponseEntity<List<ProductDto>>(products, HttpStatus.OK);
    }
}
