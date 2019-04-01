package br.com.api.sales.java.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import br.com.api.sales.java.model.shared.DomainAbstract;

@Entity
@Table(name = "product")
public final class Product extends DomainAbstract<Long> {

	private Long id;
    private String name;
    private Double price;

    public Product() {
        super();
    }

    public Product(String name, Double price) {
        this.name = name;
        this.price = price;
    }

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
    
    @NotBlank(message = "Name is required")
    public String getName() {
        return name;
    }

    public void setName(String name) {

        this.name = cleanAndTransformString(name);
    }

    @NotNull(message = "Price is required")
    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}