import javax.rmi.ssl.SslRMIClientSocketFactory;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class HalligalliImpl extends  UnicastRemoteObject implements Halligalli {
	
	private static final long serialVersionUID = 1L;
	
	public HalligalliImpl(int port) throws Exception {
		super(port,new SslRMIClientSocketFactory(), new SslRmiServerSocketFactory());
	}

	public String getStatus() throws RemoteException{
		return "test";
	}
	public void openCard() throws RemoteException{

	}
	public void hitBell() throws RemoteException{

	}
}
