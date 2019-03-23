package br.com.api.sales.java.controller.shared;

import java.util.function.Function;
import java.util.function.Predicate;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ControllerShared<T> {

    protected Predicate<Page<T>> filterResultIsEmpty() {
        return page -> page.getContent().isEmpty();
    }

    protected Function<Page<T>, ResponseEntity<Page<T>>> mapResourceNotFound() {
        return page -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(page);
    }
}