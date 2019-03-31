package br.com.api.sales.java.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotBlank;

import br.com.api.sales.java.model.shared.DomainFunctions;

@Embeddable

public final class Address extends DomainFunctions<Long> {

	private String street;
	private String district;
	private String number;
	private String complement;
	private String city;
	private String state;
	private String zipCode;

	@Column(nullable = false)
	@NotBlank(message = "Street is required")
	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street  = cleanString(street);
	}

	@Column(nullable = false)
	@NotBlank(message = "District is required")
	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = cleanString(district);
	}

	@Column(nullable = false)
	@NotBlank(message = "Number is required")
	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = cleanString(number);
	}

	public String getComplement() {
		return complement;
	}

	public void setComplement(String complement) {
		this.complement = complement;
	}

	@Column(nullable = false)
	@NotBlank(message = "City is required")
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = cleanString(city);
	}

	@Column(nullable = false)
	@NotBlank(message = "State is required")
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = cleanString(state);
	}

	@Column(nullable = false)
	@NotBlank(message = "ZipCode is required")
	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
}