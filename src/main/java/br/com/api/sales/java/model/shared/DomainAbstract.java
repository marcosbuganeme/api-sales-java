package br.com.api.sales.java.model.shared;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class DomainAbstract<T extends Serializable> extends DomainFunctions<T> implements IDomain<T> {

	private LocalDateTime criado;
	private LocalDateTime modificado;

	@CreatedDate
	@Column(nullable = false, updatable = false)
	public LocalDateTime getCriado() {
		return criado;
	}

	public void setCriado(LocalDateTime criado) {
		this.criado = criado;
	}

	@LastModifiedDate
	@Column(insertable = false)
	public LocalDateTime getModificado() {
		return modificado;
	}

	public void setModificado(LocalDateTime modificado) {
		this.modificado = modificado;
	}
}