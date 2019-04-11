package br.com.api.sales.java.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.api.sales.java.model.Ordered;

public @Repository interface OrderedJpaRepository extends JpaRepository<Ordered, Long> {

	List<Ordered> findAllByCustomerId(Long idCustomer);

	Page<Ordered> findByCustomerId(Long idCustomer, Pageable pageable);

	Page<Ordered> queryFirst10ByCustomerId(Long idCustomer, Pageable pageable);
}