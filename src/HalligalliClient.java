import javax.rmi.ssl.SslRMIClientSocketFactory;

import java.net.InetAddress;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Timer;

public class HalligalliClient {

    private static final int PORT = 1099;


    static int id = -1;

    StartGameScreen stScreen;
    Timer m_timer;
    ScheduledJob job;
    Users users;


    public HalligalliClient(Halligalli halliGalli) {
        users = new Users();
        stScreen = new StartGameScreen(halliGalli);

        //while(id<0);

        //	System.out.println(-1);

    }

    public static void main(String args[]) {
        try {
            System.setProperty("javax.net.ssl.trustStore", "trustedcerts");
            System.setProperty("javax.net.ssl.trustStorePassword", "password");

            String mServAddr = InetAddress.getLocalHost().getHostName();
            String mServName = "HalliGalli";


            // Make reference to SSL-based registry
            Registry registry = LocateRegistry.getRegistry(
                    mServAddr, PORT,
                    new SslRMIClientSocketFactory());


            // "obj" is the identifier that we'll use to refer
            // to the remote object that implements the "Hello"
            // interface
            Halligalli obj = (Halligalli) registry.lookup(mServName);

            new HalligalliClient(obj);
        } catch (Exception e) {
            System.out.println("HalliGalliClient exception: " + e.getMessage());
            e.printStackTrace();
        }
    }


}
