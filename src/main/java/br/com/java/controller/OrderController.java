package br.com.java.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import br.com.java.common.ApiResponse;
import br.com.java.exception.AuthenticationFailException;
import br.com.java.exception.OrderNotFoundException;
import br.com.java.exception.ProductNotExistException;
import br.com.java.model.Order;
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

        return new ResponseEntity<ApiResponse>(new ApiResponse(true, "O pedido foi feito"), HttpStatus.CREATED);
    }

    @GetMapping("/")
    public ResponseEntity<List<Order>> getAllOrders(@RequestParam("token") String token) throws AuthenticationFailException {
        authenticationService.authenticate(token);
        User user = authenticationService.getUser(token);
        List<Order> orderDtoList = orderService.listOrders(user);

        return new ResponseEntity<List<Order>>(orderDtoList, HttpStatus.OK);
    }

    @PostMapping("/{id}")
    public ResponseEntity<Object> getAllOrders(@PathVariable("id") Integer id, @RequestParam("token") String token) throws AuthenticationFailException {
        authenticationService.authenticate(token);

        User user = authenticationService.getUser(token);

        try {
            Order order = orderService.getOrder(id);

            return new ResponseEntity<>(order, HttpStatus.OK);
        } catch (OrderNotFoundException e) {
            //TODO: handle exception

            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
