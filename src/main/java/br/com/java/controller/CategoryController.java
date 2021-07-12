package br.com.java.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.java.common.ApiResponse;
import br.com.java.model.Category;
import br.com.java.service.CategoryService;
import br.com.java.utils.Helper;
import io.swagger.v3.oas.annotations.parameters.RequestBody;

@Controller
@RequestMapping("/category")
public class CategoryController {
    
    @Autowired
    private CategoryService service;

    @GetMapping("/")
    public ResponseEntity<List<Category>> getCategories() {
        List<Category> body = service.listCategories();

        return new ResponseEntity<List<Category>>(body, HttpStatus.OK);
    }

    @PostMapping("/update/{categoryID}")
    public ResponseEntity<ApiResponse> updateCategory(@PathVariable("categoryID") Integer categoryID, @Valid @RequestBody Category category) {
        if (Helper.notNull(service.readCategory(categoryID))) {
            service.updateCategory(categoryID, category);

            return new ResponseEntity<ApiResponse>(new ApiResponse(true, "atualizou a categoria"), HttpStatus.OK);
        }

        return new ResponseEntity<ApiResponse>(new ApiResponse(false, "categoria não existe"), HttpStatus.NOT_FOUND);
    }
}
