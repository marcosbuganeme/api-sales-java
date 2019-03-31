package br.com.api.sales.java.model;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import br.com.api.sales.java.model.shared.DomainAbstract;

@Entity
@Table(name = "customer")
public class Customer extends DomainAbstract<Long> {

	private Long id;
	private String name;
	private String mail;
	private Address address;

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
		this.name = cleanString(name);
	}

	@NotBlank(message = "Mail is required")
	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = cleanString(mail);
	}

	@Embedded
	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}
}