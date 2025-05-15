package vn.salary.calculator.shared.snowflake;

import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Objects;

public class NetUtil {

    private NetUtil() {
    }

    public static String privateIPv4() throws SocketException {
        for (var interfaces = NetworkInterface.getNetworkInterfaces(); interfaces.hasMoreElements(); ) {
            var element = interfaces.nextElement();
            for (var inetAdds = element.getInetAddresses(); inetAdds.hasMoreElements(); ) {
                var inetAddr = inetAdds.nextElement();
                if (inetAddr.isLoopbackAddress()) {
                    continue;
                }
                if (isPrivateIPv4(inetAddr.getHostAddress())) {
                    return inetAddr.getHostAddress();
                }
            }
        }
        throw new RuntimeException("no private ip address");
    }

    private static boolean isPrivateIPv4(String ip) {
        if (Objects.isNull(ip) || ip.isEmpty()) {
            return false;
        }
        var ips = ip.split("\\.");
        return (ips[0].equals("10")
                || ips[0].equals("172") && (Integer.parseInt(ips[1]) >= 16 && Integer.parseInt(ips[1]) < 32)
                || ips[0].equals("192") && ips[1].equals("168"));
    }
}
