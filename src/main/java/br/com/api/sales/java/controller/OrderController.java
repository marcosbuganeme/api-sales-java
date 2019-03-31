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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.api.sales.java.controller.shared.ControllerShared;
import br.com.api.sales.java.model.Order;
import br.com.api.sales.java.service.OrderService;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("v1/orders")
public class OrderController extends ControllerShared<Order> {

	private @Autowired OrderService service;

	@PostMapping
	@PreAuthorize("hasRole('ROLE_USER')")
	@ApiOperation(value = "Create order for id valid", response = Order.class)
	public ResponseEntity<?> createOrder(@Valid @RequestBody Order order, UriComponentsBuilder uriBuilder) {

		Order createdOrder = service.create(order);

        URI location = uriBuilder
				        .path("v1/customers/{id:\\d+}")
				        .buildAndExpand(createdOrder.getId())
				        .toUri();

        return ResponseEntity.created(location).body(createdOrder);
	}

    @GetMapping("{id:\\d+}")
    @ApiOperation(value = "Return find order by id", response = Order.class)
    public ResponseEntity<?> findCustomerById(@PathVariable Long id) {

    	Order findOrder = service.findById(id);

        return Optional
                .ofNullable(findOrder)
                .filter(filterObjectIsNull())
                .map(objectResourceNotFound())
                .orElse(ResponseEntity.ok(findOrder));
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("customer/{idCustomer:\\d+}/first/ten")
    @ApiOperation(value = "Return a list content ten orders filter by customer", response = Order[].class)
    public ResponseEntity<?> firstTenOrdersByCostumer(@PathVariable Long idCostumer, 
    												  @PageableDefault Pageable pageable) {

        Page<Order> results = service.findFirstTenByCostumer(idCostumer, pageable);

        return Optional
                .ofNullable(results)
                .filter(filterListIsEmpty())
                .map(pageResourceNotFound())
                .orElse(ResponseEntity.ok(results));
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("customer/{idCustomer:\\d+}/all")
    @ApiOperation(value = "Return a list content all orders by customer", response = Order[].class)
    public ResponseEntity<?> allOrderByCostumer(@PathVariable Long idCostumer, 
    											@PageableDefault Pageable pageable) {

        Page<Order> results = service.findAllByCustomer(idCostumer, pageable);

        return Optional
                .ofNullable(results)
                .filter(filterListIsEmpty())
                .map(pageResourceNotFound())
                .orElse(ResponseEntity.ok(results));
    }
}