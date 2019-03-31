package br.com.api.sales.java.model.shared;

public interface IDomain<T> {

	T getId();

	void setId(T id);

	default boolean isNew() {
		return getId() == null;
	}
}