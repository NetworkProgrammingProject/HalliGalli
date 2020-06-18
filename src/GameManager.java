import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

public class GameManager implements Serializable {
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
            return this.gameStatus(userId);
        else
            return new StatusRes(false);             // 아직 대기중일때
    }

    public StatusRes gameStatus(int userId) {
        Game game = getGameByUser(userId);
        ArrayList<Integer> remainingCards = new ArrayList<Integer>();
        for (int i = 0; i < 4; i++) {
            remainingCards.add(game.deck.get(game.users.get(i)).size());
        }
        if (game.bellUser == -1)
            return new StatusRes(game.openCard, game.users, remainingCards, game.turn);
        else
            return new StatusRes(game.openCard, game.users, remainingCards, game.turn, game.bellUser);
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
