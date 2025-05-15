package vn.salary.calculator.summary.domain.enumaration;

import lombok.Getter;

@Getter
public enum Type {

    RECEIVER("Nhận"),
    BORROW("Vay"),
    PAID("Thanh toán");

    private final String value;

    Type(String value) {
        this.value = value;
    }
}
