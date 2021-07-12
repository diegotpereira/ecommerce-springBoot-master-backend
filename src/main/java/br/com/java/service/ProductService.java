package br.com.java.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.java.dto.product.ProductDto;
import br.com.java.exception.ProductNotExistException;
import br.com.java.model.Category;
import br.com.java.model.Product;
import br.com.java.repository.ProductRepository;

@Service
public class ProductService {
    
    @Autowired
    private ProductRepository repository;

    public List<ProductDto> listProducts() {

        List<Product> products = repository.findAll();
        List<ProductDto> productDtos = new ArrayList<>();

        for(Product product : products ) {
            ProductDto productDto = getDtoFromProduct(product);
            productDtos.add(productDto);
        }
        return productDtos;
    }

    public static ProductDto getDtoFromProduct(Product product)  {
        ProductDto productDto = new ProductDto(product);

        return productDto;
    }

    public static Product getProductFromDto(ProductDto productDto, Category category) {
        Product product = new Product(productDto, category);

        return product;
    }

    public void addProduct(ProductDto productDto, Category category) {
        Product product = getProductFromDto(productDto, category);
        repository.save(product);
    }

    public void updateProduct(Integer productID, ProductDto productDto, Category category) {
        Product product = getProductFromDto(productDto, category);
        product.setId(productID);
        repository.save(product);
    }

    public Product getProductById(Integer productId) throws ProductNotExistException {
        Optional<Product> optionalProduct = repository.findById(productId);

        if (!optionalProduct.isPresent())
            throw new ProductNotExistException("O id do Produto não é válido!." + productId);

            return optionalProduct.get();
    }
}

