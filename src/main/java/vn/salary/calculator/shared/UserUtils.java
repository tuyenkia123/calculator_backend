package vn.salary.calculator.shared;

import org.springframework.security.core.context.SecurityContextHolder;

public final class UserUtils {

    private UserUtils() {
    }

    public static UserInfo getUserInfo() {
        return (UserInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
