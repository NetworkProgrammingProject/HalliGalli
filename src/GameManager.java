import java.util.ArrayList;
import java.util.Queue;

public class GameManager {
    private ArrayList<Game> games;
    Queue<Integer> users;
    public GameManager(){
        this.games = new ArrayList<Game>();
    }

    public int waitingGame(int uid){
        this.users.add(uid);
        return 0;
    }
}
