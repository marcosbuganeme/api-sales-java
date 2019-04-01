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
import br.com.api.sales.java.model.Ordered;
import br.com.api.sales.java.service.OrderedService;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("v1/requests")
public class OrderController extends ControllerShared<Ordered> {

	private @Autowired OrderedService service;

	@PostMapping
	@PreAuthorize("hasRole('ROLE_USER')")
	@ApiOperation(value = "Create ordered for id valid", response = Ordered.class)
	public ResponseEntity<?> createOrdered(@Valid @RequestBody Ordered ordered, UriComponentsBuilder uriBuilder) {

		Ordered createdOrder = service.create(ordered);

        URI location = uriBuilder
				        .path("v1/requests/{id:\\d+}")
				        .buildAndExpand(createdOrder.getId())
				        .toUri();

        return ResponseEntity.created(location).body(createdOrder);
	}

    @GetMapping("{id:\\d+}")
    @ApiOperation(value = "Return find ordered by id", response = Ordered.class)
    public ResponseEntity<?> findCustomerById(@PathVariable Long id) {

    	Ordered findOrder = service.findById(id);

        return Optional
                .ofNullable(findOrder)
                .filter(filterObjectIsNull())
                .map(objectResourceNotFound())
                .orElse(ResponseEntity.ok(findOrder));
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("customer/{idCustomer:\\d+}/first/ten")
    @ApiOperation(value = "Return a list content ten orders filter by customer", response = Ordered[].class)
    public ResponseEntity<?> firstTenOrdersByCostumer(@PathVariable Long idCostumer, 
    												  @PageableDefault Pageable pageable) {

        Page<Ordered> results = service.findFirstTenByCostumer(idCostumer, pageable);

        return Optional
                .ofNullable(results)
                .filter(filterListIsEmpty())
                .map(pageResourceNotFound())
                .orElse(ResponseEntity.ok(results));
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("customer/{idCustomer:\\d+}/all")
    @ApiOperation(value = "Return a list content all orders by customer", response = Ordered[].class)
    public ResponseEntity<?> allOrderByCostumer(@PathVariable Long idCostumer, 
    											@PageableDefault Pageable pageable) {

        Page<Ordered> results = service.findAllByCustomer(idCostumer, pageable);

        return Optional
                .ofNullable(results)
                .filter(filterListIsEmpty())
                .map(pageResourceNotFound())
                .orElse(ResponseEntity.ok(results));
    }
}