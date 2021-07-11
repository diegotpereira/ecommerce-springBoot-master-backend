package br.com.java.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.java.model.Cart;
import br.com.java.model.User;

@Repository
public interface CartRepository  extends JpaRepository<Cart, Integer>{
    
    List<Cart> findAllByUserOrderByCreatedDateDesc(User user);

    List<Cart> deleteByUser(User user);
}
