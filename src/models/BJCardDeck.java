package models;

import java.util.ArrayList;
import java.util.Collections;

public class BJCardDeck {
    private ArrayList<BJCard> cards;


    public BJCardDeck() {
        cards = new ArrayList<BJCard>();
        // populate deck with cards
        for (BJCardSuit suit : BJCardSuit.values()) {
            for (BJCardFace face : BJCardFace.values()) {
                cards.add(new BJCard(face, suit));
            }
        }
    }
        public void shuffle(){
            Collections.shuffle(cards);
        }

    public BJCard draw(BJCardDeck comingFrom) {
        this.cards.add(comingFrom.getCard(0));

        return cards.remove(0);
    }
    public BJCard getCard(int i) {
        return (BJCard)this.cards.get(i);
    }
    public void removeCard(int i) {
        this.cards.remove(i);
    }

    public void addCard(BJCard aCard){
        this.cards.add(aCard);
    }





}
