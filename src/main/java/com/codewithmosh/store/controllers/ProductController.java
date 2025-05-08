package com.codewithmosh.store.controllers;

import com.codewithmosh.store.dtos.ProductDto;
import com.codewithmosh.store.entities.Product;
import com.codewithmosh.store.mappers.ProductMapper;
import com.codewithmosh.store.repositories.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/products")
public class ProductController {
  private final ProductRepository productRepository;
  private final ProductMapper productMapper;

  @GetMapping
  public Iterable<ProductDto> getAllProducts(
      @RequestParam(required = false, name = "categoryId") Byte categoryId) {
    List<Product> products =
        categoryId == null
            ? productRepository.findAllWithCategory()
            : productRepository.findAllByCategoryId(categoryId);
    return products.stream().map(productMapper::toDto).toList();
  }

  @GetMapping("/{id}")
  public ResponseEntity<ProductDto> getProduct(@PathVariable Long id) {
    var product = productRepository.findById(id).orElse(null);
    if (product == null) {
      return ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok(productMapper.toDto(product));
  }
}
