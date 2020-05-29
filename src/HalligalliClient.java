import javax.rmi.ssl.SslRMIClientSocketFactory;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class HalligalliClient {

    private static final int PORT = 1099;

    public static void main(String args[]) {
        String mServAddr = args[0];
        String mServName = args[1];
        try {
            System.setProperty("javax.net.ssl.trustStore", "trustedcerts");
            System.setProperty("javax.net.ssl.trustStorePassword", "password");
            // Make reference to SSL-based registry
            Registry registry = LocateRegistry.getRegistry(
                    mServAddr, PORT,
                    new SslRMIClientSocketFactory());

            // "obj" is the identifier that we'll use to refer
            // to the remote object that implements the "Hello"
            // interface
            Halligalli obj = (Halligalli) registry.lookup(mServName);

            String message = "blank";
            message = obj.getStatus();
            System.out.println(message + "\n");
        } catch (Exception e) {
            System.out.println("HelloClient exception: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
