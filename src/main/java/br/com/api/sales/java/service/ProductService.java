package br.com.api.sales.java.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

        return repository.save(product);
    }

    @Transactional(rollbackFor =  ResourceNotFoundException.class)
    public void update(Long id, Product product) {

        searchProduct(id);
        product.setId(id);
        create(product);
    }

    @Transactional(rollbackFor =  ResourceNotFoundException.class)
	public void delete(Long id) {

        searchProduct(id);
        repository.deleteById(id);
	}

    public Page<Product> getAllProducts(Pageable pageable) {
        return repository.findAll(pageable);
    }

    final void searchProduct(Long id) {

        repository
            .findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
    }
}