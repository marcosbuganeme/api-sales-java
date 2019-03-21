package br.com.api.sales.java.service;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.api.sales.java.exception.ResourceNotFoundException;
import br.com.api.sales.java.model.Product;
import br.com.api.sales.java.repository.ProductJpaRepository;

public @Service class ProductService {

    private @Autowired ProductJpaRepository repository;

    @Transactional(rollbackFor =  Exception.class)
    public Product create(Product product) {

        return repository.save(product);
    }

    @Transactional(rollbackFor =  ResourceNotFoundException.class)
    public Product update(Long id, Product product) {

        repository
            .findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Resource not found"));

        product.setId(id);

        return create(product);
    }

    public Page<Product> getAllProducts(Pageable pageable) {
        return repository.findAll(pageable);
    }
}