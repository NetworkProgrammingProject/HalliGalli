import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class StatusRes implements Serializable {

    private static final long serialVersionUID = 1L;
    Boolean waiting;
    Card[] openCards;
    ArrayList<Integer> users;
    ArrayList<Integer> remainingCards;
    int bellUser;
    int turn;

    public StatusRes(Boolean waiting) {
        this.waiting = !waiting;
    }

    public StatusRes(Card[] openCards, ArrayList<Integer> users, ArrayList<Integer> remainingCards, int turn) {
        this.waiting = false;
        this.openCards = openCards;
        this.users = users;
        this.remainingCards = remainingCards;
        this.bellUser = -1;
        this.turn = turn;
    }

    public StatusRes(Card[] openCards, ArrayList<Integer> users, ArrayList<Integer> remainingCards, int turn, int bellUser) {
        this.waiting = false;
        this.openCards = openCards;
        this.users = users;
        this.remainingCards = remainingCards;
        this.bellUser = bellUser;
        this.turn = turn;
    }
}
