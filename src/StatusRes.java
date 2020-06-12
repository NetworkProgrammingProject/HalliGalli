import java.util.ArrayList;
import java.util.HashMap;

public class StatusRes {
    Boolean waiting;
    Card[] openCards;
    ArrayList<Integer> users;
    ArrayList<Integer> remainingCards;
    int bellUser;

    public StatusRes(Boolean waiting){
        this.waiting = !waiting;
    }

    public StatusRes(Card[] openCards, ArrayList<Integer>users,ArrayList<Integer> remainingCards){
        this.waiting = false;
        this.openCards = openCards;
        this.users = users;
        this.remainingCards = remainingCards;
        this.bellUser = -1;
    }

    public StatusRes(Card[] openCards, ArrayList<Integer>users,ArrayList<Integer> remainingCards, int bellUser){
        this.waiting = false;
        this.openCards = openCards;
        this.users = users;
        this.remainingCards = remainingCards;
        this.bellUser = bellUser;
    }
}
