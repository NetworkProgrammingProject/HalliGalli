import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Halligalli extends Remote{
    public int joinGame() throws RemoteException;
    public StatusRes getStatus(Boolean playingGame, int userId) throws RemoteException;
    public void openCard(int userId) throws RemoteException;
    public void hitBell(int userId) throws RemoteException;
}
