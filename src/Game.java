import java.io.Serializable;
import java.util.*;

public class Game implements Serializable {
    Cards cards;
    HashMap<Integer, Queue<Card>> deck;
    ArrayList<Card> openCards;
    Card[] openCard;
    ArrayList<Integer> users;
    ArrayList<Boolean> aliveUsers;

    int turn = 0;
    int bellUser;

    public Game(int user1, int user2, int user3, int user4) {
        this.cards = new Cards();
        this.openCards = new ArrayList<Card>();
        this.openCard = new Card[4];
        this.deck = new HashMap<Integer, Queue<Card>>();
        this.users = new ArrayList<Integer>();
        this.aliveUsers = new ArrayList<Boolean>();
        this.users.add(user1);
        this.users.add(user2);
        this.users.add(user3);
        this.users.add(user4);
        this.bellUser = -1;
        Queue<Card> deck1 = new LinkedList<Card>();
        Queue<Card> deck2 = new LinkedList<Card>();
        Queue<Card> deck3 = new LinkedList<Card>();
        Queue<Card> deck4 = new LinkedList<Card>();
        for (int i = 0; i < 14; i++) {
            deck1.add(cards.getCard());
            deck2.add(cards.getCard());
            deck3.add(cards.getCard());
            deck4.add(cards.getCard());
        }
        this.deck.put(user1, deck1);
        this.deck.put(user2, deck2);
        this.deck.put(user3, deck3);
        this.deck.put(user4, deck4);
        for (int i = 0; i < 4; i++)
            this.aliveUsers.add(Boolean.TRUE);
    }

    public void openByUser(int user) {
        Card openedCard = this.deck.get(user).poll();
        this.openCards.add(openedCard);
        int userNo = this.users.indexOf(user);
        this.openCard[userNo] = openedCard;
        calTurn(user);
        this.bellUser = -1;
        //for(int i = 0; i<4; i++){
         //   System.out.println(openCard[i].fruit + " " + openCard[i].num);
        //}
    }

    public void calTurn(int user) {
        int userNo = this.users.indexOf(user);
        this.turn = -1;
        if (userNo == 3)
            userNo = -1;
        for (int i = userNo + 1; i < this.users.size(); i++)
            if (this.aliveUsers.get(i))
                if (!this.deck.get(this.users.get(i)).isEmpty()){
                    this.turn = i;
                    System.out.println(i + 1 + " 번 플레이어의 차례");
                    break;
                }
        if (this.turn == -1)
            for (int i = 0; i < 4; i++)
                if (this.aliveUsers.get(i))
                    if (!this.deck.get(this.users.get(i)).isEmpty()){
                        this.turn = i;
                        System.out.println(i + 1 + " 번 플레이어의 차례");
                        break;
                    }
    }

    public Boolean validateBell() {
        int[] fruit = new int[4];
        for (int i = 0; i < users.size(); i++) {
            Card card = this.openCard[i];
            //When the value of card is null, error occur so add if clause - SeokUng 
            if (card != null)
                fruit[card.fruit - 1] += card.num;
        }
        for (int i = 0; i < users.size(); i++)
            if (fruit[i] == 5)
                return Boolean.TRUE;
        return Boolean.FALSE;
    }

    public synchronized void hitBell(int user) {
        if (this.bellUser == -1) {
            Boolean isSuccess = this.validateBell();
            if (isSuccess) {       //성공 해서 카드 받는다
                for (int i = 0; i < openCards.size(); i++)
                    this.deck.get(user).add(openCards.get(i));
                this.openCards.clear();
                this.turn = users.indexOf(user);

                // opencard 초기화
                for (int i = 0; i < this.users.size(); i++)
                    this.openCard[i] = null;

                this.bellUser = user;
            } else {
                //실패해서 카드 한장씩 다른사람에게 준다.
                int aliveUserNum = 0;
                // 탈락 안한 유저수
                for (int i = 0; i < this.users.size(); i++)
                    if (this.aliveUsers.get(i))
                        aliveUserNum++;
                 // 잘못 친 유저의 카드수가 유저수보다 적을 때
                if (this.deck.get(user).size() < aliveUserNum)
                    aliveUserNum = this.deck.get(user).size();

                for (int i = 0; i < aliveUserNum; i++)
                    if (this.aliveUsers.get(i))
                        if(this.users.get(i)!=user)
                            this.deck.get(this.users.get(i)).add(this.deck.get(user).poll());

                this.bellUser = user * -10;
            }
            //this part allow all requests whenever the request for hit bell is coming
            //So I make this part comment and move this in if block - Seok Ung
            // this.bellUser = user;
            // 탈락처리
            for (int i = 0; i < this.users.size(); i++)
                if (this.deck.get(this.users.get(i)).isEmpty())
                    if(this.aliveUsers.get(i))
                        this.aliveUsers.set(i, Boolean.FALSE);
        }
    }


}

class Cards implements Serializable {
    List<Card> cards;

    public Cards() {
        // 1 = 바나나  2= 딸기  3= 키위 4= 자두
        this.cards = new ArrayList<Card>();
        for (int i = 1; i < 5; i++) {
            for (int j = 0; j < 5; j++)
                cards.add(new Card(i, 1));
            for (int j = 0; j < 3; j++)
                cards.add(new Card(i, 2));
            for (int j = 0; j < 3; j++)
                cards.add(new Card(i, 3));
            for (int j = 0; j < 2; j++)
                cards.add(new Card(i, 4));
            for (int j = 0; j < 1; j++)
                cards.add(new Card(i, 5));
        }
        Collections.shuffle(cards);
    }

    public Card getCard() {
        Card card = cards.get(0);
        if (card == null)
            return null;
        cards.remove(0);
        return card;
    }
}

class Card implements Serializable {
    int fruit;
    int num;

    public Card(int fruit, int num) {
        this.fruit = fruit;
        this.num = num;
    }
}
