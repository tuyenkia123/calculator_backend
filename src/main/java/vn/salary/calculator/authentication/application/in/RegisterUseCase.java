package vn.salary.calculator.authentication.application.in;

import org.springframework.util.StringUtils;
import vn.salary.calculator.shared.exception.ErrorCode;
import vn.salary.calculator.shared.exception.ErrorCodeException;

public interface RegisterUseCase {

    RegisterResponse register(RegisterCommand command);

    record RegisterCommand(
            String username, String password, String rePassword, String fullName
    ) {
        public void validate() {
            if (!StringUtils.hasText(username)) {
                throw new ErrorCodeException(ErrorCode.MISSING_PARAM, "Trường tài khoản không được để trống");
            }
            if (!StringUtils.hasText(password)) {
                throw new ErrorCodeException(ErrorCode.MISSING_PARAM, "Trường mật khẩu không được để trống");
            }
            if (!StringUtils.hasText(rePassword)) {
                throw new ErrorCodeException(ErrorCode.MISSING_PARAM, "Trường xác nhận mật khẩu không được để trống");
            }
            if (!StringUtils.hasText(fullName)) {
                throw new ErrorCodeException(ErrorCode.MISSING_PARAM, "Trường fullName không được để trống");
            }
            if (!password.equals(rePassword)) {
                throw new ErrorCodeException(ErrorCode.NOT_MATCH, "Mật khẩu không khớp");
            }
        }
    }

    record RegisterResponse(
            String username,
            String fullName
    ) {
    }
}
