package br.com.api.sales.java.service;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.api.sales.java.model.Product;
import br.com.api.sales.java.repository.ProductJpaRepository;

public @Service class ProductService {

    static final String MESSAGE_ERROR = "Product is invalid";
	static final Supplier<IllegalArgumentException> EXCEPTION_SUPPLIER = () -> new IllegalArgumentException(MESSAGE_ERROR);

    private @Autowired ProductJpaRepository repository;

    @Transactional(rollbackFor =  IllegalArgumentException.class)
    public Product create(Product product) {

        validateBusiness(product);

        return repository.save(product);
    }

    @Transactional(rollbackFor =  IllegalArgumentException.class)
    public Product update(Long id, Product product) {

        repository
            .findById(id)
            .orElseThrow(EXCEPTION_SUPPLIER);

        product.setId(id);

        return create(product);
    }

    public Page<Product> getAllProducts(Pageable pageable) {
        return repository.findAll(pageable);
    }

    final void validateBusiness(Product product) {
        Optional
            .ofNullable(product)
            .filter(Objects::nonNull)
            .orElseThrow(EXCEPTION_SUPPLIER);
    }
}