package vn.salary.calculator.authentication.infras.repo.impl;

import org.springframework.stereotype.Component;
import vn.salary.calculator.authentication.domain.User;
import vn.salary.calculator.authentication.domain.repo.UserRepository;
import vn.salary.calculator.authentication.infras.repo.SpringJpaUserRepository;

import java.util.Optional;

@Component
public class JpaUserRepository implements UserRepository {

    private final SpringJpaUserRepository userRepository;

    public JpaUserRepository(SpringJpaUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public void save(User user) {
        userRepository.save(user);
    }
}
