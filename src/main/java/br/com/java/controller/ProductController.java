package br.com.java.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.java.common.ApiResponse;
import br.com.java.dto.product.ProductDto;
import br.com.java.model.Category;
import br.com.java.service.CategoryService;
import br.com.java.service.ProductService;

@Controller
@RequestMapping("/product")
public class ProductController {
    
    @Autowired
    ProductService productService;

    @Autowired
    CategoryService categoryService;

    @GetMapping("/")
    public ResponseEntity<List<ProductDto>> getProducts(){
        List<ProductDto> body = productService.listProducts();

            return new ResponseEntity<List<ProductDto>>(body, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addProduct(@RequestBody ProductDto productDto) {
        Optional<Category> optionalCategory = categoryService.readCategory(productDto.getCategoryId());

        if (!optionalCategory.isPresent()) {
            
            return new ResponseEntity<ApiResponse>(new ApiResponse(false, "category is invalid"), HttpStatus.CONFLICT);
        }

        Category category = optionalCategory.get();
        productService.addProduct(productDto, category);

        return new ResponseEntity<ApiResponse>(new ApiResponse(true, "O produto foi adicionado"), HttpStatus.CREATED);
    }

    @PostMapping("/update/{productID}")
    public ResponseEntity<ApiResponse> updateProduct(@PathVariable("productID") Integer productID, @RequestBody @Valid ProductDto productDto) {
        Optional<Category> optionalCategory = categoryService.readCategory(productDto.getCategoryId());

        if (!optionalCategory.isPresent()) {
            
            return new ResponseEntity<ApiResponse>(new ApiResponse(false, "Categoria é inválida"), HttpStatus.CONFLICT);
        }

        Category category = optionalCategory.get();
        productService.updateProduct(productID, productDto, category);

        return new ResponseEntity<ApiResponse>(new ApiResponse(true, "Produto atualizado com sucesso"), HttpStatus.OK);
    }
}
