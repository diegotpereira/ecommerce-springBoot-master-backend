package br.com.java.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.java.model.OrderItem;

@Repository
public interface OrderItemsRepository extends JpaRepository<OrderItem, Integer> {
    
}
