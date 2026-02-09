package com.jwtAuth.smp.controller;

import com.jwtAuth.smp.dto.ErrorDto;
import com.jwtAuth.smp.entity.CategoryEntity;
import com.jwtAuth.smp.exception.BusinessException;
import com.jwtAuth.smp.repository.CategoryRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/categories")
public class CategoryController {
    @Autowired
    private CategoryRepository categoryRepository;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<CategoryEntity> addCategory(@Valid @RequestBody CategoryEntity category) {
        category.setCreatedAt(LocalDateTime.now());
        category.setUpdatedAt(LocalDateTime.now());
        category = categoryRepository.save(category);
        return new ResponseEntity<>(category, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<CategoryEntity>> allCategories() {
        return new ResponseEntity<>(categoryRepository.findAll(), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{categoryId}")
    public ResponseEntity<CategoryEntity> updateCategory(@Valid @RequestBody CategoryEntity category, @PathVariable Long categoryId) {
        Optional<CategoryEntity> optCate = categoryRepository.findById(categoryId);
        if (optCate.isPresent()) {
            CategoryEntity categoryDb = optCate.get();
            if (category.getName() != null) {
                categoryDb.setName(category.getName());
            }
            if (category.getDescription() != null) {
                categoryDb.setDescription(category.getDescription());
            }
            categoryDb.setUpdatedAt(LocalDateTime.now());
            categoryRepository.save(categoryDb);
            return new ResponseEntity<>(categoryDb, HttpStatus.OK);
        } else {
            throw new BusinessException(List.of(new ErrorDto("CATE_NOT-FOUND", "The category to be updated does not exist")));
        }

    }
}
