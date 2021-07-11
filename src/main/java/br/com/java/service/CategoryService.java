package br.com.java.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import br.com.java.model.Category;
import br.com.java.repository.CategoryRepository;

@Service
@Transactional
public class CategoryService {
    
    private final CategoryRepository repository;

    public CategoryService(CategoryRepository repository) {
        this.repository = repository;
    }

    public List<Category> listCategories() {
        return repository.findAll();
    }

    public void createCategory(Category category) {
        repository.save(category);
    }

    public Category readCategory(String categoryName) {
        return repository.findByCategoryName(categoryName);
    }

    public Optional<Category> readCategory(Integer categoryId) {
        return repository.findById(categoryId);
    }

    public void updateCategory(Integer categoryID, Category newCategory) {
        Category category = repository.findById(categoryID).get();
        category.setCategoryName(newCategory.getCategoryName());
        category.setDescription(newCategory.getDescription());
        category.setProducts(newCategory.getProducts());
        category.setImageUrl(newCategory.getImageUrl());

        repository.save(category);
    }
}
