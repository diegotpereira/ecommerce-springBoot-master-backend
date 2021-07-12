package br.com.java.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.java.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
    Category findByCategoryName(String categoryName);
}
