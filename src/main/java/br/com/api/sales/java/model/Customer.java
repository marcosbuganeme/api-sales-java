package br.com.api.sales.java.model;

import javax.persistence.Column;
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
public final class Customer extends DomainAbstract<Long> {

	private Long id;
	private String name;
	private String mail;
	private String phone;
	private Address address;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(nullable = false)
	@NotBlank(message = "Name is required")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = cleanAndTransformString(name);
	}

	@Column(nullable = false)
	@NotBlank(message = "Mail is required")
	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = cleanAndTransformString(mail);
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Embedded
	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}
}