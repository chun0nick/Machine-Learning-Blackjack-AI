import java.util.ArrayList;

/** A class representing a node in an AceTree.
 *  This class makes the decision to hit or stay given
 *  a hand with an ace, and holds AceTrees for hit decisions.
 */
class AceNode {

    /** Hand value. */
    private int _hand;
    /** Probability of winning if one hits. */
    private double _probHit;
    /** Probability of winning if one passes. */
    private double _probPass;
    /** Number of times that the AI has hit with this hand. */
    private int _hitTrials;
    /** Number of times that the AI has passed with this hand. */
    private int _passTrials;
    /** ArrayList holding trees that correspond to possible draws off a hit. */
    private ArrayList<AceTree> _draws;

    /** Constructor sets probabilities to 50% and creates a new list of possible hits. */
    AceNode(int hand) {
        _probHit = _probPass = 0.5;
        _hand = hand;
        _hitTrials = _passTrials = 1;
        _draws = new ArrayList<>(11);
    }

    /** Check if a hand has been seen in the memory of this Node. */
    boolean handSeen(int hand) {
        for (AceTree Tree : _draws) {
            if (hand == Tree.getHand()) {
                return true;
            }
        }
        return false;
    }
    /** Get a seen hand from this node's memory. */
    AceTree getSeen(int hand) {
        for (AceTree Tree : _draws) {
            if (hand == Tree.getHand())  {
                Tree.incrementSeen();
                return Tree;
            }
        }
        return null;
    }

    /** Compute the probability of winning with a hit, using times
     *  its nodes have been seen, and their corresponding chances of winning.
     */
    void computeProbHit() {
        double prob = 0;
        int total = 0;
        for (AceTree Tree: _draws) {
            prob += (Tree.maxProb() * Tree.getTimesSeen());
            total += Tree.getTimesSeen();
        }
        _probHit = prob / total;
    }

    /** Check if this AceNode has no hit trees in the memory. */
    boolean nothingSeen() {
        for (AceTree T: _draws) {
            if (T != null) {
                return false;
            }
        }
        return true;
    }

    /** Recompute probability of nodes below and return this node's
     *  max probability of winning to the AceTree above. */
    double recompute() {
        if (!nothingSeen()) {
            for (AceTree T: _draws) {
                T.recompute();
            }
            computeProbHit();
            return Math.max(_probHit, _probPass);
        } else {
            return Math.max(_probHit, _probPass);
        }
    }

    /** Add a new AceTree to this nodes' memory of previous hits. */
    void addSeen(AceTree T) {
        _draws.add(T);
        T.incrementSeen();
    }

    /** Re-average the win with a hit chance of this node. */
    void reaverageHit(double won) {
        _probHit = _probHit + ((won - _probHit) / _hitTrials);
    }
    /** Re-average the win with a pass chance of this node. */
    void reaveragePass(double won) {
        _probPass = _probPass + ((won - _probPass) / _passTrials);
    }
    /** Make a decision (hit or stay) based on higher chance of winning play. */
    Decisions makeDecision() {
        if (_passTrials < 3) {
            _passTrials += 1;
            return Decisions.STAY;
        }else if (_hitTrials < 3) {
            _hitTrials += 1;
            return Decisions.HIT;
        } else if (_probHit > _probPass) {
            _hitTrials += 1;
            return Decisions.HIT;
        } else if (_probPass > _probHit) {
            _passTrials += 1;
            return Decisions.STAY;
        } else {
            _passTrials += 1;
            return Decisions.STAY;
        }
    }
}
