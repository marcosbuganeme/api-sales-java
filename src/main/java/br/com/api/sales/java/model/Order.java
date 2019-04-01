package br.com.api.sales.java.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

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

	public void calculateTotalPrice() {

		itens.forEach(item -> {
			totalPrice.add(new BigDecimal(item.totalItemPrice()));
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

	@ManyToOne(optional = false)
	@NotNull(message = "Customer is required")
	@JoinColumn(name = "id_customer", nullable = false)
	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	@OneToMany(cascade = CascadeType.PERSIST)
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
