package br.com.api.sales.java.model.shared;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class DomainAbstract<T extends Serializable> extends DomainFunctions<T> implements IDomain<T> {

	private T id;
	private Integer versao;
	private LocalDateTime criado;
	private LocalDateTime modificado;

	@Id
	@Override
	@GeneratedValue(strategy = GenerationType.AUTO)
	public T getId() {
		return id;
	}

	@Override
	public void setId(T id) {
		this.id = id;
	}

	@Version
	@Override
	public Integer getVersao() {
		return versao;
	}

	public void setVersao(Integer versao) {
		this.versao = versao;
	}

	@CreatedDate
	@Column(nullable = false)
	public LocalDateTime getCriado() {
		return criado;
	}

	public void setCriado(LocalDateTime criado) {
		this.criado = criado;
	}

	@LastModifiedDate
	@Column(nullable = false)
	public LocalDateTime getModificado() {
		return modificado;
	}

	public void setModificado(LocalDateTime modificado) {
		this.modificado = modificado;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public String toString() {
		return "{\"id\":\"" + id + "\", \"versao\":\"" + versao + "\", \"criado\":\"" + criado + "\", \"modificado\":\""
				+ modificado + "\"}";
	}
}