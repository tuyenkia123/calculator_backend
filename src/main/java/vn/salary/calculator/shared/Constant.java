package vn.salary.calculator.shared;

public class Constant {

    public static final int BitLenTime      = 39;                               // bit length of time
    public static final int BitLenSequence  = 8;                                // bit length of sequence number
    public static final int BitLenMachineID = 63 - BitLenTime - BitLenSequence; // bit length of machine id
    public static final long SnowflakeTimeUnit = 10 * 1000 * 1000; // nsec, i.e. 10 msec
}
