import java.util.ArrayList;
import java.util.Random;

/** A class representing a deck of cards */

public class Deck {
    /** ArrayList representing cards in this deck. */
    private ArrayList<Integer> _cards;
    /** Random used to draw a random card. */
    private Random r = new Random();
    /** Constructor generating a new deck to draw from. */
    Deck() {
        _cards = new ArrayList<>();
        constructDeck();
    }
    /** Construct the cards for this deck. */
    private void constructDeck() {
        for (int i = 2; i < 12; i += 1) {
            if (i == 10) {
                for (int added = 0; added < 16; added += 1) {
                    _cards.add(i);
                }
            } else {
                for (int added = 0; added < 4; added += 1) {
                    _cards.add(i);
                }
            }
        }
    }
    /** Draw a card from this deck. */
    int draw() {
        constructDeck();
        int cardInd = r.nextInt(_cards.size());
        int card = _cards.get(cardInd);
        _cards.remove(cardInd);
        return card;
    }
}
