package vn.salary.calculator.shared.snowflake;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;

import static vn.salary.calculator.shared.Constant.BitLenMachineID;
import static vn.salary.calculator.shared.Constant.BitLenSequence;
import static vn.salary.calculator.shared.Constant.BitLenTime;

public class Snowflake {

    private final Lock mutex;
    private final long startTime;
    private long elapsedTime;
    private long sequence;
    private final long machineID;

    private Snowflake() {
        throw new UnsupportedOperationException(
                "please using Snowflake(Setting setting) constructor method");
    }

    public static final Snowflake DEFAULT = new Snowflake(Setting.empty());

    public static long poll() {
        return DEFAULT.nextID();
    }

    /* NewSnowflake returns a new Snowflake configured with the given Settings.
       NewSnowflake returns null in the following cases:
     - Settings.StartTime is ahead of the current time.
     - Settings.MachineID returns an error.
     - Settings.CheckMachineID returns false.
     */
    public Snowflake(Setting setting) {
        this.mutex = new ReentrantLock();
        this.sequence = Long.parseUnsignedLong(Long.valueOf((1 << BitLenSequence) - 1).toString());

        if (setting.getStartTime() != null && setting.getStartTime().after(new Date())) {
            throw new RuntimeException(
                    "Snowflake not created. StartTime is ahead of the current time");
        }
        var calendar = Calendar.getInstance();
        if (setting.getStartTime() == null) {
            calendar.set(2014, Calendar.SEPTEMBER, 1, 0, 0, 0);
            calendar.set(Calendar.MILLISECOND, 0);
        } else {
            calendar.setTime(setting.getStartTime());
        }

        this.startTime = SnowflakeUtil.toSnowflakeTime(calendar);

        try {
            if (setting.getMachineID() == null) {
                this.machineID = SnowflakeUtil.lower16BitPrivateIP();
            } else {
                this.machineID = setting.getMachineID().get();
            }
        } catch (Exception e) {
            throw new RuntimeException("Snowflake not created. get MachineID error", e);
        }

        if (setting.getCheckMachineID() != null && !setting
                .getCheckMachineID()
                .apply(this.machineID)) {
            throw new RuntimeException("Snowflake not created. CheckMachineID returns false");
        }
    }

    // NextID generates a next unique ID.
    // After the Snowflake time overflows, NextID returns an error.
    public long nextID() {
        final var maskSequence = Integer.toUnsignedLong((1 << BitLenSequence) - 1);

        try {
            this.mutex.lock();

            var current = currentElapsedTime(this.startTime);

            if (this.elapsedTime < current) {
                this.elapsedTime = current;
                this.sequence = 0;
            } else { // sf.elapsedTime >= current
                this.sequence = (this.sequence + 1) & maskSequence;
                if (this.sequence == 0) {
                    this.elapsedTime++;
                    var overTime = this.elapsedTime - current;
                    LockSupport.parkNanos(overTime * 10 * (long) 1e6);
                }
            }

            return toID();
        } finally {
            this.mutex.unlock();
        }
    }

    private long currentElapsedTime(long startTime) {
        return SnowflakeUtil.toSnowflakeTime(Calendar.getInstance()) - startTime;
    }


    private long toID() {
        if (this.elapsedTime >= 1L << BitLenTime) {
            throw new RuntimeException("over the time limit");
        }
        return Long.parseUnsignedLong(String.valueOf(this.elapsedTime)) << (BitLenSequence
                + BitLenMachineID) |
                Long.parseUnsignedLong(String.valueOf(this.sequence)) << BitLenMachineID |
                Long.parseUnsignedLong(String.valueOf(this.machineID));
    }
}
