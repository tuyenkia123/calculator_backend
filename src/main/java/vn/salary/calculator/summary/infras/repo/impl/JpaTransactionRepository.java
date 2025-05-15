package vn.salary.calculator.summary.infras.repo.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import vn.salary.calculator.shared.DateUtils;
import vn.salary.calculator.summary.domain.Transaction;
import vn.salary.calculator.summary.domain.enumaration.Type;
import vn.salary.calculator.summary.domain.repo.TransactionRepository;
import vn.salary.calculator.summary.infras.repo.SpringJpaTransactionRepository;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Component
public class JpaTransactionRepository implements TransactionRepository {

    private final SpringJpaTransactionRepository springJpaTransactionRepository;

    public JpaTransactionRepository(SpringJpaTransactionRepository springJpaTransactionRepository) {
        this.springJpaTransactionRepository = springJpaTransactionRepository;
    }

    @Override
    public List<Long> saveAll(List<Transaction> entities) {
        var result = springJpaTransactionRepository.saveAll(entities);
        return result
                .stream()
                .map(Transaction::getId)
                .toList();
    }

    @Override
    public Page<Transaction> search(String author, LocalDate fromDate, LocalDate toDate, String type, Pageable pageable) {
        var now = LocalDate.now();
        var startDate = Objects.isNull(fromDate) ?
                DateUtils.atStartOfDay(now.withDayOfMonth(1)) :
                DateUtils.atStartOfDay(fromDate);
        var endDate = Objects.isNull(toDate) ?
                DateUtils.atStartOfDay(now.withDayOfMonth(now.lengthOfMonth())) :
                DateUtils.atStartOfDay(toDate);
        List<Type> types;
        if (!StringUtils.hasText(type) || "ALL".equals(type)) {
            types = Arrays.stream(Type.values()).toList();
        } else {
            types = Collections.singletonList(Type.valueOf(type));
        }
        return springJpaTransactionRepository.findByUsernameAndCreatedAtBetweenAndTypeInOrderByCreatedAtDesc(
                author, startDate, endDate, types, pageable
        );
    }
}
