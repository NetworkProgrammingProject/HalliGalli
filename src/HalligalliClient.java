import javax.rmi.ssl.SslRMIClientSocketFactory;

import java.net.InetAddress;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class HalligalliClient {

    private static final int PORT = 1099;    
    
    Users users;
    
    
    public HalligalliClient(Halligalli halliGalli) {
    	users = new Users();
    	new StartGameScreen(halliGalli);
    }
    
    public static void main(String args[]) {
        //String mServAddr = args[0];
        //String mServName = args[1];

    
    	
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

            //메소드 변경으로 인한 주석처리
/*            String message = "Connected";
            message = obj.getStatus();
            if(message.contentEquals("Connected"))
            	new HalligalliClient(obj);
            System.out.println(message + "\n");*/
        } catch (Exception e) {
            System.out.println("HalliGalliClient exception: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
