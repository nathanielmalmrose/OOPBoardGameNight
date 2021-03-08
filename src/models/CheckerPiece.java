package models;

public class CheckerPiece {

    private Player player;
    private boolean isKing = false;
    private String color;

    public CheckerPiece() {

    }

    public CheckerPiece(Player player, String color) {
        setPlayer(player);
        setColor(color);
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public boolean isKing() {
        return isKing;
    }

    public void setKing(boolean king) {
        isKing = king;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return getColor();
    }
}
