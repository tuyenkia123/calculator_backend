package vn.salary.calculator.shared.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {

    UNKNOWN_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "Lỗi hệ thống"),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "Bạn không có quyền truy cập tài nguyên này"),
    FORBIDDEN(HttpStatus.FORBIDDEN, "Bạn không có quyền truy cập dữ liệu của người khác"),
    NOT_FOUND_ENTITY(HttpStatus.NOT_FOUND, "Entity không tồn tại"),
    DUPLICATE_ENTITY(HttpStatus.CONFLICT, "Đã tồn tại entity"),
    NOT_MATCH(HttpStatus.BAD_REQUEST, "Không khớp thông tin"),
    MISSING_PARAM(HttpStatus.BAD_REQUEST, "Trường dữ liệu không được để trống"),
    ;

    private final HttpStatus httpStatus;
    private final String message;

    ErrorCode(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }
}
