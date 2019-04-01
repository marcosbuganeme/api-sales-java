package br.com.api.sales.java.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.api.sales.java.exception.ResourceNotFoundException;
import br.com.api.sales.java.model.Customer;
import br.com.api.sales.java.model.OrderItem;
import br.com.api.sales.java.model.Ordered;
import br.com.api.sales.java.model.Product;
import br.com.api.sales.java.repository.CustomerJpaRepository;
import br.com.api.sales.java.repository.OrderItemJpaRepository;
import br.com.api.sales.java.repository.OrderedJpaRepository;
import br.com.api.sales.java.repository.ProductJpaRepository;

public @Service class OrderedService {

	private final OrderedJpaRepository orderedJpa;
	private final ProductJpaRepository productJpa;
	private final CustomerJpaRepository customerJpa;
	private final OrderItemJpaRepository orderItemJpa;

	@Autowired
	public OrderedService(OrderedJpaRepository orderedJpa, 
						  ProductJpaRepository productJpa,
						  CustomerJpaRepository customerJpa,
						  OrderItemJpaRepository orderItemJpa) {

		this.orderedJpa = orderedJpa;
		this.productJpa = productJpa;
		this.customerJpa = customerJpa;
		this.orderItemJpa = orderItemJpa;
	}

	@Transactional(rollbackFor = Exception.class)
	public Ordered create(Ordered order) {

		checkIfConsumerIsRegistered(order);
		buildItemOrderAndSetter(order);

		return saveOrderAndItemOrder(order);
	}

	private Ordered saveOrderAndItemOrder(Ordered order) {

		order.calculateTotalPrice();
		Ordered orderedSaved = orderedJpa.save(order);

		order
			.getItens()
			.forEach(item -> item.getOrdered().setId(orderedSaved.getId()));

		saveOrderItem(order, orderedSaved);

		return orderedSaved;
	}

	private void saveOrderItem(Ordered ordered, Ordered orderedSaved) {

		List<OrderItem> itensOrder = ordered.getItens();
		List<OrderItem> itensSaved = orderItemJpa.saveAll(itensOrder);
		orderedSaved.setItens(itensSaved);
	}

	private void buildItemOrderAndSetter(Ordered order) {

		List<OrderItem> itens = new ArrayList<>();

		order.getItens().forEach(item -> {

			Product findProduct = productJpa
									.findById(item.getProduct().getId())
									.orElseThrow(() -> new ResourceNotFoundException("Product not found"));

			item.setProduct(findProduct);
			itens.add(item);
		});

		order.setItens(itens);
	}

	public Ordered findById(Long id) {

		return orderedJpa
					.findById(id)
					.orElse(null);
	}

	public Page<Ordered> findFirstTenByCostumer(Long idCustomer, Pageable pageable) {
		return orderedJpa.queryFirst10ByCustomerId(idCustomer, pageable);
	}

	public Page<Ordered> findAllByCustomer(Long idCustomer, Pageable pageable) {
		return orderedJpa.findByCustomerId(idCustomer, pageable);
	}

	private void checkIfConsumerIsRegistered(Ordered order) {

		Optional
			.ofNullable(order)
			.filter(Objects::nonNull)
			.map(Ordered::getCustomer)
			.filter(Objects::nonNull)
			.orElseThrow(() -> new IllegalArgumentException("Order invalid"));

		Customer customerSearch = customerJpa
									.findById(order.getCustomer().getId())
									.orElse(null);

		Optional
			.ofNullable(customerSearch)	
			.orElseThrow(() -> new ResourceNotFoundException("Customer not found"));

		order.setCustomer(customerSearch);
	}
}