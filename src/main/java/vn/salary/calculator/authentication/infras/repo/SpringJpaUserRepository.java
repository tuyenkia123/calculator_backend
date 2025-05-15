package vn.salary.calculator.authentication.infras.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.salary.calculator.authentication.domain.User;

import java.util.Optional;

@Repository
public interface SpringJpaUserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);
}
