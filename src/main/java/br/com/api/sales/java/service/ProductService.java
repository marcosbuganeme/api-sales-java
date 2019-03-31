package br.com.api.sales.java.service;

import java.util.Objects;
import java.util.Optional;

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

    	buildProductForUpdate(id, product);

        repository.save(product);
    }

    @Transactional(rollbackFor =  ResourceNotFoundException.class)
	public void delete(Long id) {

    	checkProductExists(id);

        repository.deleteById(id);
	}

    public Product findById(Long id) {

    	return repository
    				.findById(id)
    				.filter(Objects::nonNull)
    				.orElse(null);
    }

    public Page<Product> findAllProducts(Pageable pageable) {
        return repository.findAll(pageable);
    }

    private void validatedAfterSave(Product product) {

        Product findProduct = repository.findByNameAllIgnoreCaseAndPrice(product.getName(), product.getPrice());

        if (Objects.nonNull(findProduct))
        	throw new ResourceDuplicateException("Product exists");
    }

	/**
	 * Pesquisa um produto, caso o encontre ele atualiza com o produto que está submetido a alteração
	 */
	private void buildProductForUpdate(Long id, Product productUpdate) {

		Product findProduct = checkProductExists(id);

        productUpdate.setId(id);
        productUpdate.setCriado(findProduct.getCriado());
        productUpdate.setModificado(findProduct.getModificado());
	}

	/**
	 * Checa a existência de um produto.
	 * Caso não encontre, uma exceção <code>ResourceNotFoundException</code> será lançada
	 * Senão retorna o produto encontrado
	 */
	private Product checkProductExists(Long id) {

		Product product = findById(id);

		Optional
	        .ofNullable(product)
	        .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

		return product;
	}
}