package br.com.api.sales.java.service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.api.sales.java.exception.ResourceDuplicateException;
import br.com.api.sales.java.exception.ResourceNotFoundException;
import br.com.api.sales.java.model.Customer;
import br.com.api.sales.java.model.Ordered;
import br.com.api.sales.java.repository.CustomerJpaRepository;
import br.com.api.sales.java.repository.OrderedJpaRepository;

public @Service class CustomerService {

	private final OrderedJpaRepository orderJpa;
	private final CustomerJpaRepository customerJpa;

    @Autowired
    public CustomerService(OrderedJpaRepository orderJpa,
    					   CustomerJpaRepository customerJpa) {

    	this.orderJpa = orderJpa;
        this.customerJpa = customerJpa;
    }

	@Transactional(rollbackFor = Exception.class)
	public Customer create(Customer customer) {

		validatedAfterSave(customer);

		return customerJpa.save(customer);
	}

	@Transactional(rollbackFor = ResourceNotFoundException.class)
	public void update(Long id, Customer customer) {

    	buildCustomerForUpdate(id, customer);

        customerJpa.save(customer);
	}

	@Transactional(rollbackFor = ResourceNotFoundException.class)
	public void delete(Long id) {

    	checkCustomerExists(id);

    	List<Ordered> orders = orderJpa.findAllByCustomerId(id);

    	Optional
    		.ofNullable(orders)
    		.filter(verify -> !orders.isEmpty())
    		.orElseThrow(() -> new IllegalArgumentException("There are purchases for this consumer"));

        customerJpa.deleteById(id);
	}

	public Customer findById(Long id) {

		Customer customer = customerJpa
				.findById(id)
				.orElse(null);
		return customer;
	}

	public Page<Customer> findAllCustomers(Pageable pageable) {
		return customerJpa.findAll(pageable);
	}

	private void validatedAfterSave(Customer customer) {

        Customer findCustomer = customerJpa.findByMailAllIgnoreCase(customer.getMail());

        if (Objects.nonNull(findCustomer))
        	throw new ResourceDuplicateException("Customer exists");
	}

	private void buildCustomerForUpdate(Long id, Customer customerUpdate) {

		Customer customer = checkCustomerExists(id);

		customerUpdate.setId(id);
		customerUpdate.setCreated(customer.getCreated());
		customerUpdate.setModified(customer.getModified());
		
		if (Objects.nonNull(customer.getAddress())) {
			
		}
	}

	private Customer checkCustomerExists(Long id) {

		if (Objects.isNull(id)) {
			throw new ResourceNotFoundException("Customer not found");
		}

		Customer customer = findById(id);

		if (Objects.isNull(customer)) {
			throw new ResourceNotFoundException("Customer not found");
		}

		return customer;
	}
}