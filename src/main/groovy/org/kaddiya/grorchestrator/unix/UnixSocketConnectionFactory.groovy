package org.kaddiya.grorchestrator.unix

import okhttp3.Dns
import okhttp3.HttpUrl
import org.newsclub.net.unix.AFUNIXSocket

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
        return hostname.endsWith(".socket") ? Collections.singletonList(InetAddress.getByAddress(hostname, [0, 0, 0, 0])) : Dns.SYSTEM.lookup(hostname);
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
