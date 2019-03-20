package br.com.api.sales.java.service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.api.sales.java.model.Product;
import br.com.api.sales.java.repository.ProductJpaRepository;

public @Service class ProductService {

    private @Autowired ProductJpaRepository repository;

    @Transactional
    public Product create(Product product) {

        validateBusiness(product);

        return repository.save(product);
    }

    @Transactional
    public Product update(Long id, Product product) {

        repository
            .findById(id).
            orElseThrow( () -> new IllegalArgumentException("Product is invalid"));

        product.setId(id);

        return create(product);
    }

    public List<Product> getAllProducts() {
        return repository.findAll();
    }

    final void validateBusiness(Product product) {
        Optional
        .ofNullable(product)
        .filter(Objects::nonNull)
        .orElseThrow(() -> new IllegalArgumentException("Product invalid"));    
    }
}