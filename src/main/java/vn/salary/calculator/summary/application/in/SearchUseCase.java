package vn.salary.calculator.summary.application.in;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import vn.salary.calculator.shared.exception.ErrorCode;
import vn.salary.calculator.shared.exception.ErrorCodeException;

import java.time.LocalDate;

public interface SearchUseCase {

    Page<SearchResponse> search(SearchCommand command);

    record SearchCommand(
            String username,
            LocalDate fromDate,
            LocalDate toDate,
            String type,
            Integer page,
            Integer size
    ) {

        public void validate(String author) {
            if (username == null) {
                throw new ErrorCodeException(ErrorCode.MISSING_PARAM, "Thông tin người dùng không được để trống");
            }
            if (!username.equals(author)) {
                throw new ErrorCodeException(ErrorCode.FORBIDDEN);
            }
        }

        public Pageable pageable() {
            if (page == null || size == null) {
                return PageRequest.of(0, 50);
            }
            return PageRequest.of(page, size);
        }
    }

    record SearchResponse(
            Long money,
            String content,
            String type,
            String createdAt
    ) {
    }
}
