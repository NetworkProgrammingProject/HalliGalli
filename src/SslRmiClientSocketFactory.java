import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import java.io.IOException;
import java.io.Serializable;
import java.net.Socket;
import java.rmi.server.RMIClientSocketFactory;

public class SslRmiClientSocketFactory implements RMIClientSocketFactory, Serializable {

    public Socket createSocket(String host, int port) throws IOException {
        SSLSocketFactory factory =
                (SSLSocketFactory)SSLSocketFactory.getDefault();
        SSLSocket socket = (SSLSocket)factory.createSocket(host, port);
        return socket;
    }

    public int hashCode() {
        return getClass().hashCode();
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        } else if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        return true;
    }
}
