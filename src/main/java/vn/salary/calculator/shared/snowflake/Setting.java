package vn.salary.calculator.shared.snowflake;

import lombok.Getter;

import java.util.Date;
import java.util.function.Function;
import java.util.function.Supplier;

@Getter
public class Setting {

    private Date startTime;
    private Supplier<Long> machineID;
    private Function<Long, Boolean> checkMachineID;

    private Setting() {
    }

    public static Setting empty() {
        return new Setting();
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private final Setting domain;

        private Builder() {
            domain = new Setting();
        }

        public Builder startTime(Date startTime) {
            domain.startTime = startTime;
            return this;
        }

        public Builder machineID(Supplier<Long> machineID) {
            domain.machineID = machineID;
            return this;
        }

        public Builder checkMachineID(Function<Long, Boolean> checkMachineID) {
            domain.checkMachineID = checkMachineID;
            return this;
        }

        public Setting build() {
            return domain;
        }
    }
}
