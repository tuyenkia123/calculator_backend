package vn.salary.calculator.shared.exception;

import lombok.Getter;

@Getter
public class ErrorCodeException extends RuntimeException {

    private static final long serialVersionUID = -2231577185500549752L;
    private final ErrorCode errorCode;
    private final String message;

    public ErrorCodeException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
        this.message = errorCode.getMessage();
    }

    public ErrorCodeException(ErrorCode errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
        this.message = message;
    }
}
