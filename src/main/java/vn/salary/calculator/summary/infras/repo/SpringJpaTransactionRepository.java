package vn.salary.calculator.summary.infras.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.salary.calculator.summary.domain.Transaction;
import vn.salary.calculator.summary.domain.enumaration.Type;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface SpringJpaTransactionRepository extends JpaRepository<Transaction, Long> {

//    @Query("SELECT t FROM Transaction t where t.username = :author AND YEAR(t.createdAt) = :year " +
//            "AND MONTH(t.createdAt) = :month AND t.type IN :type ORDER BY t.createdAt DESC")
    Page<Transaction> findByUsernameAndCreatedAtBetweenAndTypeInOrderByCreatedAtDesc(
            String author, LocalDateTime fromDate, LocalDateTime toDate, List<Type> type, Pageable pageable
    );
}
