package org.kaddiya.grorchestrator.unix

import okio.ByteString
import org.newsclub.net.unix.AFUNIXSocket
import org.newsclub.net.unix.AFUNIXSocketAddress

/**
 * Created by Webonise on 06/02/17.
 */
 public class UnixSocket extends Socket {

    private AFUNIXSocket socket;

    @Override
    public void connect(SocketAddress endpoint, int timeout) throws IOException {
        InetAddress address = ((InetSocketAddress) endpoint).getAddress();
        String socketPath = decodeHostname(address);

        System.out.println("connect via '" + socketPath + "'...");
        File socketFile = new File(socketPath);

        socket = AFUNIXSocket.newInstance();
        socket.connect(new AFUNIXSocketAddress(socketFile), timeout);
        socket.setSoTimeout(timeout);
    }

    @Override
    public void bind(SocketAddress bindpoint) throws IOException {
        socket.bind(bindpoint);
    }

    @Override
    public boolean isConnected() {
        return socket.isConnected();
    }

    @Override
    public OutputStream getOutputStream() throws IOException {
        return socket.getOutputStream();
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return socket.getInputStream();
    }

    private static String encodeHostname(String path) {
        return Encoder.encode(path) + ".socket";
    }

    private static String decodeHostname(InetAddress address) {
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