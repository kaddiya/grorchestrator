package org.kaddiya.grorchestrator.unix

import okhttp3.Dns
import okhttp3.HttpUrl
import okio.ByteString
import org.newsclub.net.unix.AFUNIXSocket
import org.newsclub.net.unix.AFUNIXSocketAddress

import javax.net.SocketFactory

/**
 * Created by Webonise on 03/02/17.
 */
public class UnixSocketConnectionFactory extends SocketFactory implements Dns {


    public UnixSocketFactory() {
        if (!AFUNIXSocket.isSupported()) {
            throw new UnsupportedOperationException("AFUNIXSocket.isSupported() == false");
        }
    }

    @Override
    public List<InetAddress> lookup(String hostname) throws UnknownHostException {
        byte [] arrayOfBytes = [0,0,0,0]
        return hostname.endsWith(".socket") ? Collections.singletonList(InetAddress.getByAddress(hostname, arrayOfBytes)) : Dns.SYSTEM.lookup(hostname);
    }

    @Override
    public Socket createSocket() throws IOException {
        return new UnixSocket();
    }

    @Override
    public Socket createSocket(String s, int i) throws IOException {
        throw new UnsupportedOperationException();
    }

    @Override
    public Socket createSocket(String s, int i, InetAddress inetAddress, int i1) throws IOException {
        throw new UnsupportedOperationException();
    }

    @Override
    public Socket createSocket(InetAddress inetAddress, int i) throws IOException {
        throw new UnsupportedOperationException();
    }

    @Override
    public Socket createSocket(InetAddress inetAddress, int i, InetAddress inetAddress1, int i1) throws IOException {
        throw new UnsupportedOperationException();
    }


    private static class UnixSocketFactory extends SocketFactory implements Dns {

        public UnixSocketFactory() {
            if (!AFUNIXSocket.isSupported()) {
                throw new UnsupportedOperationException("AFUNIXSocket.isSupported() == false");
            }
        }

        public HttpUrl urlForUnixSocketPath(String unixSocketPath, String path) {


            return new HttpUrl.Builder()
                    .scheme("http")
                    .host(UnixSocket.encodeHostname(unixSocketPath))
                    .addPathSegment(path)
                    .build();
        }

        @Override
        public List<InetAddress> lookup(String hostname) throws UnknownHostException {
            byte [] arrayOfBytes = [0,0,0,0]

            return hostname.endsWith(".socket") ? Collections.singletonList(InetAddress.getByAddress(hostname, arrayOfBytes)) : Dns.SYSTEM.lookup(hostname);
        }

        @Override
        public Socket createSocket() throws IOException {
            return new UnixSocket();
        }

        @Override
        public Socket createSocket(String s, int i) throws IOException {
            throw new UnsupportedOperationException();
        }

        @Override
        public Socket createSocket(String s, int i, InetAddress inetAddress, int i1) throws IOException {
            throw new UnsupportedOperationException();
        }

        @Override
        public Socket createSocket(InetAddress inetAddress, int i) throws IOException {
            throw new UnsupportedOperationException();
        }

        @Override
        public Socket createSocket(InetAddress inetAddress, int i, InetAddress inetAddress1, int i1) throws IOException {
            throw new UnsupportedOperationException();
        }
    }

    private static class UnixSocket extends Socket {

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
}
