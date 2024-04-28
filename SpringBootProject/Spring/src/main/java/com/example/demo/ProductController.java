package com.example.demo;

import org.hibernate.query.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/products")
public class ProductController {
 @Autowired
 private ProductRepository productRepository;


 @GetMapping
 public Page getAllProducts(@RequestParam(defaultValue = "0") int page,
                                     @RequestParam(defaultValue = "10") int size) {
     return (Page) productRepository.findAll(PageRequest.of(page, size));
 }


 @PostMapping
 public ResponseEntity<Product> createProduct(@RequestBody Product product) {
     Product savedProduct = productRepository.save(product);
     return new ResponseEntity<>(savedProduct, HttpStatus.CREATED);
 }


 @GetMapping("/{id}")
 public ResponseEntity<Product> getProductById(@PathVariable("id") Long id) {
     return productRepository.findById(id)
             .map(product -> new ResponseEntity<>(product, HttpStatus.OK))
             .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
 }


 @PutMapping("/{id}")
 public ResponseEntity<Product> updateProduct(@PathVariable("id") Long id, @RequestBody Product product) {
     if (productRepository.existsById(id)) {
         product.setId(id);
         Product updatedProduct = productRepository.save(product);
         return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
     } else {
         return new ResponseEntity<>(HttpStatus.NOT_FOUND);
     }
 }

 
 @DeleteMapping("/{id}")
 public ResponseEntity<Void> deleteProduct(@PathVariable("id") Long id) {
     productRepository.deleteById(id);
     return new ResponseEntity<>(HttpStatus.NO_CONTENT);
 }
}
