package br.com.api.sales.java.model.shared;

public interface IDomain<T> {

	T getId();

	void setId(T id);

	Integer getVersao();

	default boolean isNew() {
		return getId() == null;
	}
}