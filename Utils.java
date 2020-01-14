import java.util.ArrayList;

/** Class that holds various utility functions useful during
 *  execution.
 */
class Utils {
    /** Sum a hand. */
    static int sum(ArrayList<Integer> hand) {
        int total = 0;
        for (Integer x: hand) {
            total += x;
        }
        return total;
    }
    static int[] arrayListToArray(ArrayList<Integer> arrayList) {
        int[] converted = new int[arrayList.size()];
        int ind = 0;
        for (int val : arrayList) {
            converted[ind] = val;
            ind += 1;
        }
        return converted;
    }

    /** Check if a hand can be "unbusted" by changing aces to ones. */
    static boolean canUnbust(ArrayList<Integer> L) {
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

    static int[] replaceAce(int toReplace, int replace, int[] arr) {
        int[] replaced = new int[arr.length];
        for (int i = 0; i < arr.length; i ++) {
            if (arr[i] == toReplace) {
                replaced[i] = replace;
            } else {
                replaced[i] = arr[i];
            }
        }
        return replaced;
    }

    static boolean equivalentHands(int[] hand1, int[] hand2) {
        if (hand1.length != hand2.length) {
            return false;
        } else {
            for (int i = 0; i < hand1.length; i ++) {
                if (hand1[i] != hand2[i]) {
                    return false;
                }
            }
            return true;
        }
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
    static boolean containsAce(ArrayList<Integer> L) {
        for (int i = 0; i < L.size(); i += 1) {
            if (L.get(i) == 11) {
                return true;
            }
        }
        return false;
    }
}
