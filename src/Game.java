import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Game {
    Cards cards;
    List<Card> deck1, deck2, deck3, deck4;
    int user1, user2, user3, user4;
    public Game(int user1, int user2, int user3, int user4){
        this.cards = new Cards();
        this.deck1 = new ArrayList<Card>();
        this.deck2 = new ArrayList<Card>();
        this.deck3 = new ArrayList<Card>();
        this.deck4 = new ArrayList<Card>();
        this.user1= user1;
        this.user2= user2;
        this.user3= user3;
        this.user4= user4;
        for(int i = 0;i<14;i++){
            deck1.add(cards.getCard());
            deck2.add(cards.getCard());
            deck3.add(cards.getCard());
            deck4.add(cards.getCard());
        }
    }
}
class Cards {
    List<Card> cards;
    public Cards(){
        // 1 = 바나나  2= 딸기  3= 키위 4= 자두
        this.cards = new ArrayList<Card>();
        for(int i = 0; i<5;i++){
            for(int j= 0;j<5;j++)
                cards.add(new Card(i,1));
            for(int j= 0;j<3;j++)
                cards.add(new Card(i,2));
            for(int j= 0;j<3;j++)
                cards.add(new Card(i,3));
            for(int j= 0;j<2;j++)
                cards.add(new Card(i,4));
            for(int j= 0;j<1;j++)
                cards.add(new Card(i,5));
        }
        Collections.shuffle(cards);
    }

    public Card getCard(){
        Card card = cards.get(0);
        if(card == null)
            return null;
        cards.remove(0);
        return card;
    }
}

class Card{
    int fruit;
    int num;

    public Card(int fruit, int num){
        this.fruit = fruit;
        this.num = num;
    }
}
