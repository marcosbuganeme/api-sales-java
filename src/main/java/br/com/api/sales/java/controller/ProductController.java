package br.com.api.sales.java.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.api.sales.java.model.Product;
import br.com.api.sales.java.service.ProductService;

@RestController
@RequestMapping("products")
public class ProductController {

    private @Autowired ProductService service;

    @PostMapping("save")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<?> createProduct(@Validated @RequestBody Product product, UriComponentsBuilder uriBuilder) {

        Product createdProduct = service.create(product);

        URI location = uriBuilder
                        .path("products/edit/{id}")
                        .buildAndExpand(createdProduct.getId())
                        .toUri();

        return ResponseEntity.created(location).body(createdProduct);
    }

    @PutMapping("edit/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable("id") Long id, @Validated @RequestBody Product product) {

        Product productUpdate = service.update(id, product);

        return ResponseEntity.ok(productUpdate);
    }

    @GetMapping("all")
    public ResponseEntity<?> allProducts() {
        List<Product> products = service.getAllProducts();

        Optional
            .ofNullable(products)
            .filter(product -> !products.isEmpty())
            .orElseThrow(() -> new IllegalArgumentException("Nenhum produto encontrado"));

        return ResponseEntity.ok(products);
    }
}
