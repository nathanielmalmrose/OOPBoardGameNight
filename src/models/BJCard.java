package models;

public class BJCard {
    private final BJCardFace face;
    private final BJCardSuit suit;
    //default card which needs face and suit to be a card (Constructor)
    BJCard(BJCardFace face, BJCardSuit suit){
        this.face = face;
        this.suit = suit;
    }
    public int getFaceValue(){
        return face.getFaceValue();
    }

    @Override
    public String toString() {
        return face+" of "+ suit;
    }
}
