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
import br.com.api.sales.java.model.Customer;
import br.com.api.sales.java.service.CustomerService;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("v1/customers")
public class CustomerController extends ControllerShared<Customer> {

	private @Autowired CustomerService service;

	@PostMapping
    @ApiOperation(value = "Create customer for id valid", response = Customer.class)
	public ResponseEntity<?> createCustomer(@Valid @RequestBody Customer customer, UriComponentsBuilder uriBuilder) {

		Customer createdCustomer = service.create(customer);

        URI location = uriBuilder
				        .path("v1/customers/{id:\\d+}")
				        .buildAndExpand(createdCustomer.getId())
				        .toUri();

		return ResponseEntity.created(location).body(createdCustomer);
	}

	@PutMapping("{id:\\d+}")
	@PreAuthorize("hasRole('ROLE_USER')")
	@ApiOperation(value = "Update customer for id valid", response = Customer.class)
	public ResponseEntity<?> updateCustomer(@PathVariable Long id, @Valid @RequestBody Customer customer) {

        service.update(id, customer);

        return ResponseEntity.ok(customer);
	}

    @DeleteMapping("{id:\\d+}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ApiOperation(value = "Delete customer for id valid", response = Void.class)
	public ResponseEntity<?> deleteCustomer(@PathVariable Long id) {

        service.delete(id);

        return ResponseEntity.ok().build();
    }

    @GetMapping("{id:\\d+}")
    @ApiOperation(value = "Return find customer by id", response = Customer.class)
    public ResponseEntity<?> findCustomerById(@PathVariable Long id) {

    	Customer findCustomer = service.findById(id);

        return Optional
                .ofNullable(findCustomer)
                .filter(filterObjectIsNull())
                .map(objectResourceNotFound())
                .orElse(ResponseEntity.ok(findCustomer));
    }

    @GetMapping
    @ApiOperation(value = "Return a list with all customers", response = Customer[].class)
    public ResponseEntity<?> allProducts(@PageableDefault Pageable pageable) {
        Page<Customer> results = service.findAllCustomers(pageable);

        return Optional
                .ofNullable(results)
                .filter(filterListIsEmpty())
                .map(pageResourceNotFound())
                .orElse(ResponseEntity.ok(results));
    }
}