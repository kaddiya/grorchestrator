package org.kaddiya.grorchestrator.unix

import net.sf.json.JSON
import okhttp3.Dns;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.ByteString;
import org.newsclub.net.unix.AFUNIXSocket;
import org.newsclub.net.unix.AFUNIXSocketAddress;

import javax.net.SocketFactory;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.UnknownHostException;
import java.util.Collections;
import java.util.List;
import okhttp3.MediaType

public class OkDocker {

    public static void main(String... args) throws Exception {
        UnixSocketFactory socketFactory = new UnixSocketFactory();
        OkHttpClient client = new OkHttpClient.Builder()
                .socketFactory(socketFactory)
                .dns(socketFactory)
                .build();

        String unixSocketPath = "/var/run/docker.sock";
        println("creating httpUrl")
        HttpUrl url = socketFactory.urlForUnixSocketPath(unixSocketPath, "/images/create?fromImage=redis:latest");
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        Request request = new Request.Builder()
                .url(url)
                .post(RequestBody.create(JSON,""))
                .build();

        Response response = client
                .newCall(request)
                .execute();

        ResponseBody body = response.body();
        System.out.println(body.string());
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
            byte [] foo = [0,0,0,0]
            System.println("Lookedup?")
            return hostname.endsWith(".socket") ? Collections.singletonList(InetAddress.getByAddress(hostname, foo)) : Dns.SYSTEM.lookup(hostname);
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