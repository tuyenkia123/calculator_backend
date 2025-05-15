package vn.salary.calculator.summary.domain.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import vn.salary.calculator.summary.domain.Transaction;

import java.time.LocalDate;
import java.util.List;

public interface TransactionRepository {

    List<Long> saveAll(List<Transaction> entities);

    Page<Transaction> search(String author, LocalDate fromDate, LocalDate toDate, String type, Pageable pageable);
}
