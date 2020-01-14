/** A class representing an AceTree.
 *  AceTree makes the decision as to whether
 *  an ace should have a value of one or eleven.
 */

class AceTree {
    /** Value of hand in this tree. */
    private int[] _hand;
    /** Probability of winning by changing the ace to a one. */
    private double _probOne;
    /** Probability of winning by changing the ace to an eleven. */
    private double _probEleven;
    /** Number of times AI has tried changing the ace to a one. */
    private int _oneTrials;
    /** Number of times AI has tried changing the ace to an eleven. */
    private int _elevenTrials;
    /** Times this aceTree has been seen (useful for computing probability in upper nodes). */
    private int _timesSeen;
    /** AceNode holding the hand value from changing ace value to an eleven. */
    private AceNode _Eleven;
    /** AceNode holding the hand value from changing ace value to a one. */
    private AceNode _One;

    public boolean _inOne;

    /** Constructor for this AceTree. Sets probabilities of winning to
     * 50% each and creates two AceNodes for setting the ace to one or eleven.
     * @param hand a hand value
     */
    AceTree(int[] hand, boolean inOne) {
        _hand = hand;
        _probOne = _probEleven = 0.5;
        _inOne = inOne;

        if (inOne) {
            _Eleven = new AceNode(Utils.replaceAce(1, 11, _hand));
            _One = new AceNode(hand);
        } else {
            _Eleven = new AceNode(hand);
            _One = new AceNode(Utils.replaceAce(11, 1, _hand));
        }
        _oneTrials = _elevenTrials = 0;
        _timesSeen = 0;
    }

    /** Function that decides whether to change the ace
     * to a one or an eleven. */
    OneorEleven makeDecision() {
        if (_oneTrials < 20) {
            _oneTrials += 1;
            return OneorEleven.ONE;
        } else if (_elevenTrials < 20) {
            _elevenTrials += 1;
            return OneorEleven.ELEVEN;
        }
        if (_probOne > _probEleven) {
            _oneTrials += 1;
            return OneorEleven.ONE;
        } else if (_probEleven > _probOne){
            _elevenTrials += 1;
            return OneorEleven.ELEVEN;
        } else {
            return OneorEleven.ONE;
        }
    }

    /** Increment the amount of times this tree has been seen. */
    void incrementSeen() {
        _timesSeen += 1;
    }
    /** Get the amount of times this tree has been seen. */
    int getTimesSeen() {
        return _timesSeen;
    }
    /** Return the max chance of winning. */
    double maxProb() {
        return Math.max(_probEleven, _probOne);
    }
    /** Get the hand value of this tree. */
    int[] getHand() {
        return _hand;
    }
    /** Get the one node from this tree. */
    AceNode getOne() {
        return _One;
    }
    /** Get the eleven node from this tree. */
    AceNode getEleven() {
        return _Eleven;
    }
    /** Recompute chances of winning using lower nodes. */
    void recompute() {
        if (!(_probOne == 0.0)) {
            _probOne = _One.recompute();
        }
        if (!(_probEleven == 0.0)) {
            _probEleven = _Eleven.recompute();
        }
    }
    /** Set this tree to a bust tree. */
    void setBusted() {
        _probOne = 0.0;
        _probEleven = 0.0;
    }

}