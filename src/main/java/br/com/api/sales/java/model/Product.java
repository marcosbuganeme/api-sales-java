package br.com.api.sales.java.model;

import java.math.BigDecimal;
import java.util.Optional;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import br.com.api.sales.java.model.shared.DomainAbstract;

@Entity
@Table(name = "products")
public class Product extends DomainAbstract<Long> {

    private String name;
    private BigDecimal price;

    public Product() {
        super();
    }

    public Product(String name, BigDecimal price) {
        this.name = name;
        this.price = price;
    }

    @NotBlank(message = "Name is required")
    public String getName() {
        return name;
    }

    public void setName(String name) {

        this.name = Optional
        				.ofNullable(name)
        				.filter(isNotBlank())
        				.map(stripAccents())
        				.map(toLowerCase())
        				.orElse(null);
    }

    @NotNull(message = "Price is required")
    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}