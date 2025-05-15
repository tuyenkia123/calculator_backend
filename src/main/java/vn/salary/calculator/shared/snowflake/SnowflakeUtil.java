package vn.salary.calculator.shared.snowflake;

import java.net.SocketException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

import static vn.salary.calculator.shared.Constant.BitLenMachineID;
import static vn.salary.calculator.shared.Constant.BitLenSequence;
import static vn.salary.calculator.shared.Constant.SnowflakeTimeUnit;

public class SnowflakeUtil {

    private SnowflakeUtil() {}

    // Decompose returns a set of Snowflake ID parts.
    public static Map<String, Long> decompose(long id) {
        final var maskSequence = Long.parseUnsignedLong(
                Long.valueOf(((1 << BitLenSequence) - 1) << BitLenMachineID).toString());
        final var maskMachineID = Long.parseUnsignedLong(
                Long.valueOf((1 << BitLenMachineID) - 1).toString());
        var msb = id >> 63;
        var time = id >> (BitLenSequence + BitLenMachineID);
        var sequence = (id & maskSequence) >> BitLenMachineID;
        var machineID = id & maskMachineID;
        var result = new HashMap<String, Long>();
        result.put("id", id);
        result.put("msb", msb);
        result.put("time", time);
        result.put("sequence", sequence);
        result.put("machine-id", machineID);
        return result;
    }

    public static long lower16BitPrivateIP() throws SocketException {
        var ip = NetUtil.privateIPv4();
        var ips = ip.split("\\.");
        int ip2 = Integer.parseInt(ips[2]);
        int ip3 = Integer.parseInt(ips[3]);
        return Long.parseUnsignedLong(String.valueOf((long) ip2 << 8))
                + Long.parseUnsignedLong(String.valueOf(ip3));
    }

    public static long toSnowflakeTime(Calendar calendar) {
        calendar.setTimeZone(TimeZone.getTimeZone("UTC"));
        return calendar.getTime().getTime() * (long) 1e6 / SnowflakeTimeUnit;
    }
}
