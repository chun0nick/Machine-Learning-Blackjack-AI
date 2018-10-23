import javax.swing.*;
import java.util.ArrayList;
import java.util.Random;

/** Class that gets a corresponding image from assets
 *  given a card value.
 */
public class Image {
    /** 2D array of possible suits. */
    private ArrayList<ArrayList<Character>> suits;
    /** 2D array of possible face cards. */
    private ArrayList<ArrayList<Character>> face;
    /** Array of suits. */
    private ArrayList<Character> suit;
    /** Random used to compute a random suit to draw. */
    private Random r;
    /** Constructor that creates array lists used for deciding which cards to show. */
    Image() {
        r = new Random();
        suits = new ArrayList<>(13);
        suit = new ArrayList<>();
        suits.add(suit); suits.add(suit);
        for (int i = 2; i < 12; i += 1) {
            suit = new ArrayList<>(4);
            suit.add('c');
            suit.add('h');
            suit.add('d');
            suit.add('s');
            if (i == 11) {
                suits.set(1, suit);
            }
            suits.add(suit);
        }

        face = new ArrayList<>(4);
        for (int i = 0; i < 4; i += 1) {
            suit = new ArrayList<>(4);
            suit.add('c');
            suit.add('h');
            suit.add('d');
            suit.add('s');
            face.add(suit);
        }
    }
    /** Get and return a corresponding image from assets. */
    public ImageIcon getImage(int hand) {
        int randomInd;
        if (hand == 10) {
            int faceCard = r.nextInt(4);
            while (suits.get(faceCard).size() < 1) {
                faceCard = r.nextInt(4);
            }
            ArrayList<Character> suitsAvailable = suits.get(faceCard);
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

        ArrayList<Character> suitsAvailable = suits.get(hand);
        randomInd = r.nextInt(suitsAvailable.size());
        char type = suitsAvailable.get(randomInd);
        suitsAvailable.remove(randomInd);
        return new ImageIcon("assets/" + hand + type + ".jpg");

    }
}
