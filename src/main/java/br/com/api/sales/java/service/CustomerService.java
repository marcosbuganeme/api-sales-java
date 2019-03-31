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
import br.com.api.sales.java.model.Customer;
import br.com.api.sales.java.repository.CustomerJpaRepository;

public @Service class CustomerService {

	private final CustomerJpaRepository repository;

    @Autowired
    public CustomerService(CustomerJpaRepository repository) {
        this.repository = repository;
    }

	@Transactional(rollbackFor = Exception.class)
	public Customer create(Customer customer) {

		validatedAfterSave(customer);

		return repository.save(customer);
	}

	@Transactional(rollbackFor = ResourceNotFoundException.class)
	public void update(Long id, Customer customer) {

    	buildCustomerForUpdate(id, customer);

        repository.save(customer);
	}

	@Transactional(rollbackFor = ResourceNotFoundException.class)
	public void delete(Long id) {

    	checkCustomerExists(id);

        repository.deleteById(id);
	}

	public Customer findById(Long id) {

		return repository
					.findById(id)
					.filter(Objects::nonNull)
					.orElse(null);
	}

	public Page<Customer> findAllCustomers(Pageable pageable) {
		return repository.findAll(pageable);
	}

	private void validatedAfterSave(Customer customer) {

        Customer findCustomer = repository.findByMailAllIgnoreCase(customer.getMail());

        if (Objects.nonNull(findCustomer))
        	throw new ResourceDuplicateException("Customer exists");
	}

	private void buildCustomerForUpdate(Long id, Customer customerUpdate) {

		Customer findCustomer = checkCustomerExists(id);

		customerUpdate.setId(id);
		customerUpdate.setCreated(findCustomer.getCreated());
		customerUpdate.setModified(findCustomer.getModified());
		
		if (Objects.nonNull(findCustomer.getAddress())) {
			
		}
	}

	private Customer checkCustomerExists(Long id) {

		Customer customer = findById(id);

		Optional
	        .ofNullable(customer)
	        .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));

		return customer;
	}
}