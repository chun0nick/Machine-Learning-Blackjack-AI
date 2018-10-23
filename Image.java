import javax.swing.*;
import java.util.ArrayList;
import java.util.Random;

/** Class that gets a corresponding image from assets
 *  given a card value.
 */
public class Image {
    /** 2D array of possible suits. */
    private ArrayList<ArrayList<Character>> _suits;
    /** 2D array of possible face cards. */
    private ArrayList<ArrayList<Character>> _face;
    /** Array of suits. */
    private ArrayList<Character> _suit;
    /** Random used to compute a random suit to draw. */
    private Random r;
    /** Constructor that creates array lists used for deciding which cards to show. */
    Image() {
        r = new Random();
        _suits = new ArrayList<>(13);
        _suit = new ArrayList<>();
        _suits.add(_suit); _suits.add(_suit);
        for (int i = 2; i < 12; i += 1) {
            _suit = new ArrayList<>(4);
            _suit.add('c'); _suit.add('h');
            _suit.add('d'); _suit.add('s');
            if (i == 11) {
                _suits.set(1, _suit);
            }
            _suits.add(_suit);
        }

        _face = new ArrayList<>(4);
        for (int i = 0; i < 4; i += 1) {
            _suit = new ArrayList<>(4);
            _suit.add('c'); _suit.add('h');
            _suit.add('d'); _suit.add('s');
            _face.add(_suit);
        }
    }
    /** Get and return a corresponding image from assets. */
    public ImageIcon getImage(int hand) {
        int randomInd;
        if (hand == 10) {
            int faceCard = r.nextInt(4);
            while (_suits.get(faceCard).size() < 1) {
                faceCard = r.nextInt(4);
            }
            ArrayList<Character> suitsAvailable = _suits.get(faceCard);
            randomInd = r.nextInt(suitsAvailable.size());
            char type = suitsAvailable.get(randomInd);
            suitsAvailable.remove(randomInd);
            if (faceCard == 0) {
                return new ImageIcon("assets/" + "10" + type + ".jpg");
            } else if (faceCard == 1) {
                return new ImageIcon("assets/" + "jack" + type + ".jpg");
            } else if (faceCard == 2) {
                return new ImageIcon("assets/" + "queen" + type + ".jpg");
            } else {
                return new ImageIcon("assets/" + "king" + type + ".jpg");
            }
        }

        ArrayList<Character> suitsAvailable = _suits.get(hand);
        randomInd = r.nextInt(suitsAvailable.size());
        char type = suitsAvailable.get(randomInd);
        suitsAvailable.remove(randomInd);
        return new ImageIcon("assets/" + hand + type + ".jpg");

    }
}
