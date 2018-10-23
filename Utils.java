import java.util.ArrayList;

/** Class that holds various utility functions useful during
 *  execution.
 */
public class Utils {
    /** Sum a hand. */
    public static int sum(ArrayList<Integer> hand) {
        int total = 0;
        for (Integer x: hand) {
            total += x;
        }
        return total;
    }
    /** Check if a hand can be "unbusted" by changing aces to ones. */
    public static boolean canUnbust(ArrayList<Integer> L) {
        if (containsAce(L)) {
            int numAces = aceCount(L);
            for (int i = 1; i <= numAces; i += 1) {
                setOne(L, i);
                if (sum(L) < 21) {
                    return true;
                }
            }
        }
        return false;
    }

    /** Count aces appearing in a given hand. */
    static int aceCount(ArrayList<Integer> L) {
        int aces = 0;
        for (int i = 0; i < L.size(); i += 1) {
            if (L.get(i) == 11) {
                aces += 1;
            }
        }
        return aces;
    }

    /** Set a given number of aces to ones. */
    private static void setOne(ArrayList<Integer> L, int numAces) {
        for (int i = 0; i < L.size(); i += 1) {
            if (L.get(i) == 11 && numAces > 0) {
                L.set(i, 1);
                numAces -= 1;
            }
        }
    }

    /** Check if a hand contains an ace. */
    public static boolean containsAce(ArrayList<Integer> L) {
        for (int i = 0; i < L.size(); i += 1) {
            if (L.get(i) == 11) {
                return true;
            }
        }
        return false;
    }
}
