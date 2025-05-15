package vn.salary.calculator.summary.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import vn.salary.calculator.shared.snowflake.Snowflake;
import vn.salary.calculator.summary.domain.enumaration.Type;

import java.time.LocalDateTime;

@Entity
@Table(name = "`transaction`")
@Getter
@Setter
public class Transaction {

    @Id
    private Long id;

    @Column(nullable = false)
    private Long money;

    @Column(nullable = false)
    private String content;

    @Enumerated(EnumType.STRING)
    private Type type;

    @Column(nullable = false)
    private String username;

    private LocalDateTime createdAt = LocalDateTime.now();

    public static Transaction create(String author, Long money, String content, Type type) {
        Transaction transaction = new Transaction();
        transaction.setId(Snowflake.poll());
        transaction.setUsername(author);
        transaction.setMoney(money);
        transaction.setContent(content);
        transaction.setType(type);
        return transaction;
    }
}
