package vn.salary.calculator.authentication.application.in;

import org.springframework.util.StringUtils;
import vn.salary.calculator.shared.UserInfo;
import vn.salary.calculator.shared.exception.ErrorCode;
import vn.salary.calculator.shared.exception.ErrorCodeException;

public interface LoginUseCase {

    AuthResponse login(LoginCommand command);

    record LoginCommand(
        String username,
        String password
    ) {

        public void validate() {
            if (!StringUtils.hasText(username)) {
                throw new ErrorCodeException(ErrorCode.MISSING_PARAM, "Trường username không được để trống");
            }
            if (!StringUtils.hasText(password)) {
                throw new ErrorCodeException(ErrorCode.MISSING_PARAM, "Trường password không được để trống");
            }
        }
    }

    record AuthResponse(
        String token,
        UserInfo userInfo
    ) {
    }
}
