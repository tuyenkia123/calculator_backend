package vn.salary.calculator.shared;

public record UserInfo(
        Long id,
        String username,
        String fullName,
        String email,
        String role
) {
}
