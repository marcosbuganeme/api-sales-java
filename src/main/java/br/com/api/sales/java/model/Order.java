package br.com.api.sales.java.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedDate;

import br.com.api.sales.java.model.shared.DomainAbstract;

@Entity
@Table(name = "order")
public final class Order extends DomainAbstract<Long> {

	private Long id;
	private Customer customer;
	private BigDecimal totalPrice;
	private List<OrderItem> itens;

	{
		itens = new ArrayList<>();
	}

	public void calculatePriceTotal() {

		itens.forEach(item -> {
			totalPrice.add(new BigDecimal(item.totalPrice()));
		});
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	@CreatedDate
	@Column(name = "data_order", nullable = false, updatable = false)
	public LocalDateTime getCreated() {
		return super.getCreated();
	}

	@OneToOne
	@JoinColumn(name = "id_customer", nullable = false)
	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	@OneToMany
	public List<OrderItem> getItens() {
		return itens;
	}

	public void setItens(List<OrderItem> itens) {
		this.itens = itens;
	}

	@Column(name = "total_price", nullable = false)
	public BigDecimal getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(BigDecimal totalPrice) {
		this.totalPrice = totalPrice;
	}
}
