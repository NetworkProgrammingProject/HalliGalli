import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Halligalli extends Remote{
    public int joinGame() throws RemoteException;
    public String getStatus() throws RemoteException;
    public void openCard() throws RemoteException;
    public void hitBell() throws RemoteException;
}
