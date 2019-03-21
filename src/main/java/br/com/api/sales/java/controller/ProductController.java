package br.com.api.sales.java.controller;

import java.net.URI;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> createProduct(@Valid @RequestBody Product product, UriComponentsBuilder uriBuilder) {

        Product createdProduct = service.create(product);

        URI location = uriBuilder
                        .path("products/edit/{id:\\d+}")
                        .buildAndExpand(createdProduct.getId())
                        .toUri();

        return ResponseEntity.created(location).body(createdProduct);
    }

    @PutMapping("edit/{id:\\d+}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> updateProduct(@PathVariable Long id, @Valid @RequestBody Product product) {

        Product productUpdate = service.update(id, product);

        return ResponseEntity.ok(productUpdate);
    }

    @GetMapping("all")
    public ResponseEntity<?> allProducts(@PageableDefault Pageable pageable) {
        Page<Product> products = service.getAllProducts(pageable);

        return Optional
                .ofNullable(products)
                .filter(product -> products.getContent().isEmpty())
                .map(results -> ResponseEntity.notFound().build())
                .orElse(ResponseEntity.ok(products));
    }
}
