package vn.salary.calculator.authentication.application.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import vn.salary.calculator.authentication.application.in.RegisterUseCase;
import vn.salary.calculator.authentication.domain.User;
import vn.salary.calculator.authentication.domain.repo.UserRepository;
import vn.salary.calculator.shared.exception.ErrorCode;
import vn.salary.calculator.shared.exception.ErrorCodeException;

@Service
public class RegisterService implements RegisterUseCase {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public RegisterService(UserRepository userRepository,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public RegisterResponse register(RegisterCommand command) {
        command.validate();
        var userOpt = userRepository.findByUsername(command.username());
        if (userOpt.isPresent()) {
            throw new ErrorCodeException(ErrorCode.DUPLICATE_ENTITY, "Tài khoản đã tồn tại");
        }
        userRepository.save(User.create(command.username(),
                passwordEncoder.encode(command.password()),
                command.fullName()));
        return new RegisterResponse(command.username(), command.fullName());
    }
}
