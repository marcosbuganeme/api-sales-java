package br.com.api.sales.java.controller.shared;

import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public abstract class ControllerShared<T> {

	protected Predicate<T> filterObjectIsNull() {
		return Objects::isNull;
	}

    protected Predicate<Page<T>> filterListIsEmpty() {
        return page -> page.getContent().isEmpty();
    }

    protected Function<T, ResponseEntity<T>> objectResourceNotFound() {
        return object -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(object);
    }

    protected Function<Page<T>, ResponseEntity<Page<T>>> pageResourceNotFound() {
        return page -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(page);
    }
}