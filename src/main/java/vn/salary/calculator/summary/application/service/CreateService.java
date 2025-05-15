package vn.salary.calculator.summary.application.service;

import org.springframework.stereotype.Service;
import vn.salary.calculator.shared.UserUtils;
import vn.salary.calculator.summary.application.in.CreateUseCase;
import vn.salary.calculator.summary.domain.Transaction;
import vn.salary.calculator.summary.domain.repo.TransactionRepository;

import java.util.List;

@Service
public class CreateService implements CreateUseCase {
    
    private final TransactionRepository transactionRepository;

    public CreateService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Override
    public List<Long> create(List<CreateUseCase.CreateCommand> commands) {
        var author = UserUtils.getUserInfo().username();
        commands.forEach(CreateUseCase.CreateCommand::validate);
        var entities = commands.stream()
                .map(command -> command.of(
                        command.content(),
                        command.type())
                )
                .map(command -> Transaction.create(
                        author,
                        command.money(),
                        command.content(),
                        command.type()
                        )
                )
                .toList();
        return transactionRepository.saveAll(entities);
    }
}
