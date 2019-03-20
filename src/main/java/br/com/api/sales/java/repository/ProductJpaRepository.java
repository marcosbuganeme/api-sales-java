package br.com.api.sales.java.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.api.sales.java.model.Product;

public @Repository interface ProductJpaRepository extends JpaRepository<Product, Long> {
    
}