package br.com.java.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.java.model.OrderItem;
import br.com.java.repository.OrderItemsRepository;

@Service
@Transactional
public class OrderItemsService {
    
    @Autowired
    private OrderItemsRepository repository;

    public void addOrderedProducts(OrderItem orderItem) {
        repository.save(orderItem);
    }
}
