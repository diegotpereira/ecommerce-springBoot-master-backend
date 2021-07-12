package br.com.java.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.java.model.Order;
import br.com.java.model.User;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer>  {
    List<Order> findAllByUserOrderByCreatedDateDesc(User user);
}
