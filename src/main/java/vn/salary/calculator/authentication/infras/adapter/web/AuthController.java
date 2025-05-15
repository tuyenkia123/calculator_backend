package vn.salary.calculator.authentication.infras.adapter.web;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import vn.salary.calculator.authentication.application.in.LoginUseCase;
import vn.salary.calculator.authentication.application.in.RegisterUseCase;

@RestController
public class AuthController {

    private final LoginUseCase loginUseCase;
    private final RegisterUseCase registerUseCase;

    public AuthController(LoginUseCase loginUseCase,
                          RegisterUseCase registerUseCase) {
        this.loginUseCase = loginUseCase;
        this.registerUseCase = registerUseCase;
    }

    @PostMapping("/login")
    public LoginUseCase.AuthResponse login(@RequestBody LoginUseCase.LoginCommand command) {
        return loginUseCase.login(command);
    }

    @PostMapping("/register")
    public RegisterUseCase.RegisterResponse register(@RequestBody RegisterUseCase.RegisterCommand command) {
        return registerUseCase.register(command);
    }
}
