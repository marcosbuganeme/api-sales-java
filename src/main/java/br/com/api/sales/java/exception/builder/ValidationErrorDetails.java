package br.com.api.sales.java.exception.builder;

public class ValidationErrorDetails extends ErrorDetails {

    private String field;
    private String fieldMessage;

    public static final class Builder {

        private String title;
        private int status;
        private String detail;
        private long timestamp;
        private String developerMessage;
        private String field;
        private String fieldMessage;

        private Builder() {}

        public static Builder newBuilder() {
            return new Builder();
        }

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder status(int status) {
            this.status = status;
            return this;
        }

        public Builder detail(String detail) {
            this.detail = detail;
            return this;
        }

        public Builder timestamp(long timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        public Builder developerMessage(String developerMessage) {
            this.developerMessage = developerMessage;
            return this;
        }

        public Builder field(String field) {
            this.field = field;
            return this;
        }

        public Builder fieldMessage(String fieldMessage) {
            this.fieldMessage = fieldMessage;
            return this;
        }

        public ValidationErrorDetails build() {

            ValidationErrorDetails details = new ValidationErrorDetails();
            details.setDeveloperMessage(developerMessage);
            details.setTitle(title);
            details.setDetail(detail);
            details.setTimestamp(timestamp);
            details.setStatus(status);
            details.fieldMessage = fieldMessage;
            details.field = field;

            return details;
        }
    }

    public String getField() {
        return field;
    }

    public String getFieldMessage() {
        return fieldMessage;
    }
}