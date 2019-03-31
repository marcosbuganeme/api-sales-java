package br.com.api.sales.java.model.shared;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;

import org.apache.commons.lang3.StringUtils;

public abstract class DomainFunctions<T> {

	private static final String EMPTY = StringUtils.EMPTY;
	private static final String REGEX_ONLY_NUMBERS = "\\D";

	protected String cleanString(String string) {

		return Optional
				.ofNullable(string)
				.filter(isNotBlank())
				.map(stripAccents())
				.map(toLowerCase())
				.orElse(null);
	}
	
	protected boolean isNotBlank(String string) {

		return Optional
				.ofNullable(string)
				.filter(isNotBlank())
				.isPresent();
	}

	protected String onlyNumbers(String number) {

		return Optional
				.ofNullable(number)
				.filter(StringUtils::isNotEmpty)
				.map(applyRegexOnlyNumbers())
				.get();
	}

	protected boolean isNotNull(T object) {

		return Optional
				.ofNullable(object)
				.isPresent();
	}

	protected Predicate<String> isBlank() {
		return StringUtils::isBlank;
	}

	protected Predicate<String> isNotBlank() {
		return StringUtils::isNotBlank;
	}

	protected Predicate<T> isNotNull() {
		return Objects::nonNull;
	}

	protected Function<String, String> stripAccents() {
		return StringUtils::stripAccents;
	}
	
	protected Function<String, String> toLowerCase() {
		return String::toLowerCase;
	}

	protected Function<String, String> toUpperCase() {
		return String::toUpperCase;
	}

	private Function<String, String> applyRegexOnlyNumbers() {
		return number -> number.replaceAll(REGEX_ONLY_NUMBERS, EMPTY);
	}
}
