package vn.salary.calculator.summary.application.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import vn.salary.calculator.shared.DateUtils;
import vn.salary.calculator.shared.UserUtils;
import vn.salary.calculator.summary.application.in.SearchUseCase;
import vn.salary.calculator.summary.domain.repo.TransactionRepository;

@Service
public class SearchService implements SearchUseCase {

    private final TransactionRepository transactionRepository;

    public SearchService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Override
    public Page<SearchResponse> search(SearchCommand command) {
        var author = UserUtils.getUserInfo().username();
        command.validate(author);
        var pageable = command.pageable();
        var page = transactionRepository.search(
                author,
                command.fromDate(),
                command.toDate(),
                command.type(),
                pageable);
        var result = page.getContent()
                .stream()
                .map(t -> {
                    var money = t.getMoney() < 0 ? -t.getMoney() : t.getMoney();
                    return new SearchResponse(money, t.getContent(),
                            t.getType().getValue(), DateUtils.format(t.getCreatedAt(), "dd/MM/yyyy HH:mm:ss"));
                })
                .toList();
        return new PageImpl<>(result, pageable, page.getTotalElements());
    }
}
