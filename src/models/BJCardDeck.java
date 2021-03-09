package models;

import java.util.ArrayList;
import java.util.Collections;

public class BJCardDeck {
    private final int NumberOfCards=  52;
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
    public BJCard draw() {
        return cards.remove(0);
    }


}
