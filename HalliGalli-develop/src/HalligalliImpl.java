import javax.rmi.ssl.SslRMIClientSocketFactory;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class HalligalliImpl extends  UnicastRemoteObject implements Halligalli, Serializable  {
	private static int id = 0;
	private static final long serialVersionUID = 1L;
	private GameManager gameManager = new GameManager();
	public HalligalliImpl(int port) throws Exception {
		super(port,new SslRMIClientSocketFactory(), new SslRmiServerSocketFactory());
	}

	public int joinGame() throws RemoteException{
		return gameManager.waitingGame();
	}
	public StatusRes getStatus(Boolean playingGame, int userId) throws RemoteException{
		// client에서는 scheduler활용하여 계속 watching
		if(playingGame)
			return gameManager.gameStatus(userId);
		else{
			return gameManager.isGameStart(userId);
		}
	}
	public void openCard(int userId) throws RemoteException{
		gameManager.openCard(userId);
	}
	public void hitBell(int userId) throws RemoteException{
		gameManager.hitBell(userId);
	}
}
