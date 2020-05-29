import javax.rmi.ssl.SslRMIClientSocketFactory;
import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class HalligalliServer {
	int port = 1099;
	public HalligalliServer(String server, String servname){
		
	try {
		Registry registry = LocateRegistry.createRegistry(port,
				new SslRMIClientSocketFactory(),
				new SslRmiServerSocketFactory());

		Halligalli h = new HalligalliImpl(port);
		registry.bind(servname,h);
		//Naming.rebind("rmi://"+server + ":1099/"+servname, h);
	} catch (Exception e) {
		System.out.println("Trouble: " + e);
	}
}	

public static void main(String args[]) {
	if(args.length !=2) {
		System.out.println("Usage: Classname ServerName ServName");
		System.exit(1);
	}
	String mServer = args[0];
	String mServName = args[1];
	
	System.out.println("started at " + mServer + " and use default port(1099), Service name : " + mServName);
	new HalligalliServer(mServer, mServName);
	
}
}
