package vn.salary.calculator.summary.application.in;

import org.springframework.util.StringUtils;
import vn.salary.calculator.shared.exception.ErrorCode;
import vn.salary.calculator.shared.exception.ErrorCodeException;
import vn.salary.calculator.summary.domain.enumaration.Type;

import java.util.List;

public interface CreateUseCase {

    List<Long> create(List<CreateCommand> commands);

    record CreateCommand(
            Long money,
            String content,
            Type type
    ) {
        public void validate() {
            if (money == null || money.equals(0L)) {
                throw new ErrorCodeException(ErrorCode.MISSING_PARAM, "Trường tiền không được để trống");
            }
            if (!StringUtils.hasText(content)) {
                throw new ErrorCodeException(ErrorCode.MISSING_PARAM, "Trường nội dung không được để trống");
            }
            if (type == null) {
                throw new ErrorCodeException(ErrorCode.MISSING_PARAM, "Trường loại không được để trống");
            }
        }

        public CreateCommand of(String content, Type type) {
            return new CreateCommand(getMoney(), content, type);
        }

        private Long getMoney() {
            switch (type) {
                case PAID -> {
                    return -money;
                }
                case RECEIVER, BORROW -> {
                    return money;
                }
            }
            return 0L;
        }
    }
}
