package vn.salary.calculator.summary.infras.adapter.web;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.salary.calculator.summary.application.in.CreateUseCase;
import vn.salary.calculator.summary.application.in.SearchUseCase;

import java.util.List;

@RestController
@RequestMapping("/transaction")
public class TransactionController {

    private final CreateUseCase createUseCase;
    private final SearchUseCase searchUseCase;

    public TransactionController(CreateUseCase createUseCase,
                                 SearchUseCase searchUseCase) {
        this.createUseCase = createUseCase;
        this.searchUseCase = searchUseCase;
    }

    @PostMapping
    public List<Long> create(@RequestBody List<CreateUseCase.CreateCommand> commands) {
        return createUseCase.create(commands);
    }

    @GetMapping
    public Page<SearchUseCase.SearchResponse> search(SearchUseCase.SearchCommand command) {
        return searchUseCase.search(command);
    }
}
