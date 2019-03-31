package br.com.api.sales.java.service;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.api.sales.java.exception.ResourceDuplicateException;
import br.com.api.sales.java.exception.ResourceNotFoundException;
import br.com.api.sales.java.model.Product;
import br.com.api.sales.java.repository.ProductJpaRepository;

public @Service class ProductService {

    private final ProductJpaRepository repository;

    @Autowired
    public ProductService(ProductJpaRepository repository) {
        this.repository = repository;
    }

    @Transactional(rollbackFor =  Exception.class)
    public Product create(Product product) {

    	validatedAfterSave(product);

        return repository.save(product);
    }

    @Transactional(rollbackFor =  ResourceNotFoundException.class)
    public void update(Long id, Product product) {

        searchProduct(id);
        product.setId(id);
        repository.save(product);
    }

    @Transactional(rollbackFor =  ResourceNotFoundException.class)
	public void delete(Long id) {

        searchProduct(id);
        repository.deleteById(id);
	}

    public Page<Product> getAllProducts(Pageable pageable) {
        return repository.findAll(pageable);
    }

    private void searchProduct(Long id) {

        repository
            .findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
    }

    private void validatedAfterSave(Product product) {

        Product searchProduct = repository.findByNameAllIgnoreCaseAndPrice(product.getName(), product.getPrice());

        if (Objects.nonNull(searchProduct))
        	throw new ResourceDuplicateException("Product exists");
    }
}