package br.com.java.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.java.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    
}
