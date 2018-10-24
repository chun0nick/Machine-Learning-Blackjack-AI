import java.util.ArrayList;
import java.util.Random;

/** Class representing a DecisionTree for
 *  a hand that does not contain aces.
 *  Makes the decision to hit or stay and
 *  holds future DecisionTrees.
 */
class DecisionTree {
    /** Probability of winning with a hit. */
    private double _probHit;
    /** Probability of winning with a pass. */
    private double _probPass;
    /** Number of times this tree has tried hitting. */
    private int _hittrials;
    /** Number of times this tree has tried passing. */
    private int _passtrials;
    /** The hand value of this tree. */
    private int _hand;
    /** The number of times this tree has been seen. */
    private int _timesSeen;
    /** ArrayList holding Trees that result from hitting. */
    private ArrayList<DecisionTree> _hitTrees;
    /** Random used to generate a random decision if chances of winning are equal. */
    private Random r;


    /** Constructor that sets probabilities to 50%
     *  and sets other attributes.
     * @param hand a hand value
     */
    DecisionTree(int hand) {
        _probHit = 0.5;
        _probPass = 0.5;
        _hand = hand;
        r = new Random();
        _hittrials = 1;
        _passtrials = 0;
        _timesSeen = 0;
        _hitTrees = new ArrayList<>();
    }

    void Print() {
        System.out.println(_probHit);
        System.out.println(_probPass);
    }

    /** Increment the number of times this tree
     *  has been seen by one.
     */
    void incrementSeen() {
        _timesSeen += 1;
    }

    /** Add a new hit Tree to this Tree's memory. */
    void addSeen(DecisionTree Tree) {
        Tree.incrementSeen();
        _hitTrees.add(Tree);
    }

    /** Check if a given hand has been seen by this Tree. */
    boolean handSeen(int hand) {
        for (DecisionTree Tree: _hitTrees) {
            if (Tree.getHand() == hand) {
                return true;
            }
        }
        return false;
    }

    /** Get a seen hand from this Tree's memory. */
    DecisionTree getSeen(int hand) {
        for (DecisionTree Tree: _hitTrees) {
            if (Tree.getHand() == hand) {
                Tree.incrementSeen();
                return Tree;
            }
        }
        return null;
    }
    /** Get the number of times this Tree has been seen. */
    int getTimesSeen() {
        return _timesSeen;
    }
    /** Recompute the chance of winning with a hit using hitTrees. */
    void recomputeHit() {
        double prob = 0.0;
        int total = 0;
        for (DecisionTree Tree: _hitTrees) {
            if (Tree.getTimesSeen() == 0) {
                continue;
            }
            prob += (Tree.maxProb() * Tree.getTimesSeen());
            total += Tree.getTimesSeen();
        }

        _probHit = prob / total;
    }
    /** Check if this tree has been hit with before. */
    boolean hasSeen() {
        if (_hitTrees.size() == 0) {
            return false;
        }
        return true;
    }

    /** Recompute the probabilities of winning for this Tree. */
    void recompute() {
        if (hasSeen()) {
            for (DecisionTree Tree : _hitTrees) {
                if (Tree.hasSeen()) {
                    Tree.recompute();
                }
            }
            recomputeHit();
        }
    }

    /** Return hand value of this tree. */
    int getHand() {
        return _hand;
    }
    /** Make a decision based on this Tree's probabilities. */
    Decisions makeDecision() {
        if (_passtrials < 25) {
            _passtrials += 1;
            return Decisions.STAY;
        }
        if (_hittrials < 40) {
            _hittrials += 1;
            return Decisions.HIT;
        }
        if (_probHit > _probPass) {
            _hittrials += 1;
            return Decisions.HIT;
        } else if (_probPass > _probHit) {
            _passtrials += 1;
            return Decisions.STAY;
        } else {
            int randomChoice = r.nextInt(2);
            if (randomChoice == 0) {
                _passtrials += 1;
                return Decisions.STAY;
            } else {
                _hittrials += 1;
                return Decisions.HIT;
            }
        }
    }
    /** Set this Tree to a "bust Tree". (Useful for computing hit probability.) */
    void setbustedProb() {
        _probHit = 0.0;
        _probPass = 0.0;
    }

    /** Re-average the chance of winning with a pass for this Tree. */
    void reaveragePass(double won) {
        _probPass = _probPass + ((won - _probPass) / _passtrials);
    }

    /** Return the maximum probability of winning from this Tree. */
    double maxProb() {
        return Math.max(_probHit, _probPass);
    }
}
