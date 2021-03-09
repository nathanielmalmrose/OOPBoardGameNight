package models;

public enum BJCardSuit {
    HEARTS(" Hearts"),
    SPADES(" Spades"),
    DIAMONDS(" Diamonds"),
    CLUBS(" Clubs");

    private final String suitText;

    BJCardSuit(String suitText){
        this.suitText = suitText;
    }

}
