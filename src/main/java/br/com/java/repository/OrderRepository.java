package br.com.java.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.java.model.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer>  {
    
}
