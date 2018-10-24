import java.util.ArrayList;
/** A class representing a Player. */

class Player {
    /** ArrayList representing this player's hand. */
    protected ArrayList<Integer> _hand;

    /** Constructor for a player. */
    Player() {
        _hand = new ArrayList<>();
    }
    /** Add a card to this player's hand. */
    void addCard(int card) {
        _hand.add(card);
    }
    /** Return hand value of this player's hand. */
    int handValue() {
        return Utils.sum(_hand);
    }
    /** Get this player's hand. */
    ArrayList<Integer> getHand() {
        return _hand;
    }
    /** Clear this player's hand. */
    void clearHand() {
        _hand = new ArrayList<>();
    }
    /** Get a card in this player's hand. */
    Integer get(int ind) {
        return _hand.get(ind);
    }
}
