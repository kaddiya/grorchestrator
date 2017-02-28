package org.kaddiya.grorchestrator.unix

import okio.ByteString

/**
 * Created by Webonise on 06/02/17.
 */
public class UnixSocketUtils {

    public String encodeHostname(String path) {
        return Encoder.encode(path) + ".socket";
    }

    public String decodeHostname(InetAddress address) {
        String hostName = address.getHostName();
        return Encoder.decode(hostName.substring(0, hostName.indexOf(".socket")));
    }

    private static class Encoder {
        static String encode(String text) {
            return ByteString.encodeUtf8(text).hex();
        }

        static String decode(String hex) {
            return ByteString.decodeHex(hex).utf8();
        }
    }
}
