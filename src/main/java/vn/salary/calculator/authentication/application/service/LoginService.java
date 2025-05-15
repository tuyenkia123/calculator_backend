package vn.salary.calculator.authentication.application.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import vn.salary.calculator.authentication.application.in.LoginUseCase;
import vn.salary.calculator.authentication.domain.repo.UserRepository;
import vn.salary.calculator.authentication.infras.JwtProvider;
import vn.salary.calculator.shared.UserInfo;
import vn.salary.calculator.shared.exception.ErrorCode;
import vn.salary.calculator.shared.exception.ErrorCodeException;

@Service
public class LoginService implements LoginUseCase {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    public LoginService(UserRepository userRepository,
                        PasswordEncoder passwordEncoder,
                        JwtProvider jwtProvider) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtProvider = jwtProvider;
    }

    @Override
    public AuthResponse login(LoginCommand command) {
        command.validate();
        var userOpt = userRepository.findByUsername(command.username());
        if (userOpt.isEmpty()) {
            throw new ErrorCodeException(ErrorCode.NOT_FOUND_ENTITY, "Tài khoản không tồn tại");
        }
        var user = userOpt.get();
        if (!passwordEncoder.matches(command.password(), user.getPassword())) {
            throw new ErrorCodeException(ErrorCode.NOT_MATCH, "Mật khẩu không đúng");
        }
        var userInfo = new UserInfo(user.getId(),
                user.getUsername(),
                user.getFullName(),
                user.getEmail(),
                user.getRole());
        var token = jwtProvider.generateToken(userInfo);
        return new AuthResponse(token, userInfo);
    }
}
