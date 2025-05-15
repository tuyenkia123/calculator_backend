package vn.salary.calculator.shared.exception;

public record ErrorResponse(
        String error,
        String message
) {
}
