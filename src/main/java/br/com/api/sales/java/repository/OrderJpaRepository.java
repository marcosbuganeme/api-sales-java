package br.com.api.sales.java.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.api.sales.java.model.Order;

public @Repository interface OrderJpaRepository extends JpaRepository<Order, Long> {

	List<Order> findAllByCustomerId(Long idCustomer);

	Page<Order> findByCustomerId(Long idCustomer, Pageable pageable);

	Page<Order> queryFirst10ByCustomerId(Long idCustomer, Pageable pageable);
}