package br.com.api.sales.java.model;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import br.com.api.sales.java.model.shared.DomainAbstract;

@Entity
@Table(name = "order_item")
public final class OrderItem extends DomainAbstract<Long> {

	private Long id;
	private Ordered ordered;
	private Product product;
	private Integer quantity;

	{
		ordered = new Ordered();
	}

	public Double totalItemPrice() {

		verifyAmountIsValid();
		verifyThatProductIsValid();
		Double unitPrice = product.getPrice();

		return unitPrice * quantity;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@ManyToOne
	@JoinColumn(foreignKey=@ForeignKey(name = "fk_id_ordered"), name = "id_ordered", nullable = false)
	public Ordered getOrdered() {
		return ordered;
	}

	public void setOrdered(Ordered ordered) {
		this.ordered = ordered;
	}

	@OneToOne
	@JoinColumn(foreignKey = @ForeignKey(name = "fk_id_product"), name = "id_product", nullable = false)
	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	@Column(nullable = false)
	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	private void verifyAmountIsValid() {

		Optional
			.ofNullable(quantity)
			.filter(nonNull()
			   .and(quantityGreaterThanZero()))
			.orElseThrow(() -> new IllegalArgumentException("Amount invalid"));
	}

	private Predicate<Integer> nonNull() {
		return Objects::nonNull;
	}

	private Predicate<Integer> quantityGreaterThanZero() {
		return quantity -> quantity > 0;
	}

	private void verifyThatProductIsValid() {

		Optional
			.ofNullable(product)
			.filter(Objects::nonNull)
			.map(Product::getPrice)
			.filter(Objects::nonNull)
			.orElseThrow(() -> new IllegalArgumentException("Product is required"));
	}
}