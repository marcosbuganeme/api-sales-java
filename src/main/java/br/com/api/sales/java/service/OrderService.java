package br.com.api.sales.java.service;

import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.api.sales.java.exception.ResourceNotFoundException;
import br.com.api.sales.java.model.Customer;
import br.com.api.sales.java.model.Order;
import br.com.api.sales.java.repository.CustomerJpaRepository;
import br.com.api.sales.java.repository.OrderJpaRepository;

public @Service class OrderService {

	private final OrderJpaRepository orderJpa;
	private final CustomerJpaRepository customerJpa;

	@Autowired
	public OrderService(OrderJpaRepository orderJpa, 
						CustomerJpaRepository customerJpa) {

		this.orderJpa = orderJpa;
		this.customerJpa = customerJpa;
	}

	@Transactional(rollbackFor = Exception.class)
	public Order create(Order order) {

		checkIfConsumerIsRegistered(order);
		order.calculateTotalPrice();

		return orderJpa.save(order);
	}

	public Order findById(Long id) {

		return orderJpa
					.findById(id)
					.orElse(null);
	}

	public Page<Order> findFirstTenByCostumer(Long idCustomer, Pageable pageable) {
		return orderJpa.queryFirst10ByCustomerId(idCustomer, pageable);
	}

	public Page<Order> findAllByCustomer(Long idCustomer, Pageable pageable) {
		return orderJpa.findByCustomerId(idCustomer, pageable);
	}

	private void checkIfConsumerIsRegistered(Order order) {

		Optional
			.ofNullable(order)
			.filter(Objects::nonNull)
			.map(Order::getCustomer)
			.filter(Objects::nonNull)
			.orElseThrow(() -> new IllegalArgumentException("Order invalid"));

		Customer customerSearch = customerJpa
									.findById(order.getCustomer().getId())
									.orElse(null);

		Optional
			.ofNullable(customerSearch)	
			.orElseThrow(() -> new ResourceNotFoundException("Customer not found"));
	}
}