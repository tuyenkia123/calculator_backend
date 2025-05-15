package vn.salary.calculator.authentication.domain.repo;

import vn.salary.calculator.authentication.domain.User;

import java.util.Optional;

public interface UserRepository {

    Optional<User> findByUsername(String username);

    void save(User user);
}
