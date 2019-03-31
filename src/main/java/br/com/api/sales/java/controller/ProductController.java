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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.api.sales.java.controller.shared.ControllerShared;
import br.com.api.sales.java.model.Product;
import br.com.api.sales.java.service.ProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("v1/products")
@Api("Endpoint for products version 1")
public class ProductController extends ControllerShared<Product> {

    private @Autowired ProductService service;

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ApiOperation(value = "Create product for id valid", response = Product.class)
    public ResponseEntity<?> createProduct(@Valid @RequestBody Product product, UriComponentsBuilder uriBuilder) {

        Product createdProduct = service.create(product);

        URI location = uriBuilder
                        .path("v1/products/{id:\\d+}")
                        .buildAndExpand(createdProduct.getId())
                        .toUri();

        return ResponseEntity.created(location).body(createdProduct);
    }

    @PutMapping("{id:\\d+}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ApiOperation(value = "Update product for id valid", response = Product.class)
    public ResponseEntity<?> updateProduct(@PathVariable Long id, @Valid @RequestBody Product product) {

        service.update(id, product);

        return ResponseEntity.ok(product);
    }

    @DeleteMapping("{id:\\d+}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ApiOperation(value = "Delete product for id valid", response = Void.class)
    public ResponseEntity<?> deleteProduct(@PathVariable Long id) {

        service.delete(id);

        return ResponseEntity.ok().build();
    }

    @GetMapping("{id:\\d+}")
    @ApiOperation(value = "Return find product by id", response = Product.class)
    public ResponseEntity<?> findProdutById(@PathVariable Long id) {

    	Product productSearch = service.findById(id);
    	
        return Optional
                .ofNullable(productSearch)
                .filter(filterObjectIsNull())
                .map(objectResourceNotFound())
                .orElse(ResponseEntity.ok(productSearch));
    }
    
    @GetMapping
    @ApiOperation(value = "Return a list with all products", response = Product[].class)
    public ResponseEntity<?> allProducts(@PageableDefault Pageable pageable) {
        Page<Product> results = service.findAllProducts(pageable);

        return Optional
                .ofNullable(results)
                .filter(filterListIsEmpty())
                .map(pageResourceNotFound())
                .orElse(ResponseEntity.ok(results));
    }
}