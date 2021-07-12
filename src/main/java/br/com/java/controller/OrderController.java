package br.com.java.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import br.com.java.common.ApiResponse;
import br.com.java.exception.AuthenticationFailException;
import br.com.java.exception.ProductNotExistException;
import br.com.java.model.User;
import br.com.java.service.AuthenticationService;
import br.com.java.service.OrderService;

@Controller
@RequestMapping("/order")
public class OrderController {
    
    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private OrderService orderService;

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> placeOrder(@RequestParam("token") String token, @RequestParam("sessionId") String sessionId) throws ProductNotExistException, AuthenticationFailException {
        authenticationService.authenticate(token);

        User user = authenticationService.getUser(token);
        orderService.placeOrder(user, sessionId);

        return new ResponseEntity<List<Order>>(orderDtoList, HttpStatus.OK);

    }
}
