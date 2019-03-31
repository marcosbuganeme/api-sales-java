package br.com.api.sales.java.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.api.sales.java.model.Customer;

public @Repository interface CustomerJpaRepository extends JpaRepository<Customer, Long> {

	Customer findByMailAllIgnoreCase(String mail);
}