import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

public class GameManager {
    private ArrayList<Game> games;
    Queue<Integer> waitingUser;
    HashMap<Integer, Integer> gameNo;
    int todayUserNum = 0;

    public GameManager() {
        this.games = new ArrayList<Game>();
        this.waitingUser = new LinkedList<Integer>();
        this.gameNo = new HashMap<Integer, Integer>();
    }

    public int waitingGame() {
        this.waitingUser.offer(++todayUserNum);
        if (waitingUser.size() >= 4)
            startGame();
        return todayUserNum;
    }

    private void startGame() {
        int user1 = waitingUser.poll();
        int user2 = waitingUser.poll();
        int user3 = waitingUser.poll();
        int user4 = waitingUser.poll();
        int gameNum = this.games.size();
        gameNo.put(user1, gameNum);
        gameNo.put(user2, gameNum);
        gameNo.put(user3, gameNum);
        gameNo.put(user4, gameNum);
        games.add(new Game(user1, user2, user3, user4));
    }

    public StatusRes isGameStart(int userId) {
        if (gameNo.containsKey(userId))
            return new StatusRes();             // 시작했을 때
        else
            return new StatusRes();             // 아직 대기중일때
    }

    public StatusRes gameStatus(int userId) {
        Game game = getGameByUser(userId);
        // 현재 상태 파악하는 코드 작성
        return new StatusRes();
    }

    private Game getGameByUser(int userId) {
        return games.get(gameNo.get(userId));
    }

    public void openCard(int userId) {
        this.getGameByUser(userId).openByUser(userId);
    }

    public void hitBell(int userId) {
        getGameByUser(userId).hitBell(userId);
    }
}
