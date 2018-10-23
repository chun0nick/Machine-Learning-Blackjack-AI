/** A class representing an AceTree.
 *  AceTree makes the decision as to whether
 *  an ace should have a value of one or eleven.
 */

public class AceTree {
    /** Probability of winning by changing the ace to a one. */
    private double _probOne;
    /** Probability of winning by changing the ace to an eleven. */
    private double _probEleven;
    /** Number of times AI has tried changing the ace to a one. */
    private int _oneTrials;
    /** Number of times AI has tried changing the ace to an eleven. */
    private int _elevenTrials;
    /** Times this aceTree has been seen (useful for computing probability in upper nodes). */
    private int timesSeen;
    /** AceNode holding the hand value from changing ace value to an eleven. */
    private AceNode Eleven;
    /** AceNode holding the hand value from changing ace value to a one. */
    private AceNode One;
    /** Value of hand in this tree. */
    private int _hand;

    /** Constructor for this AceTree. Sets probabilities of winning to
     * 50% each and creates two AceNodes for setting the ace to one or eleven.
     * @param hand a hand value
     */
    public AceTree(int hand) {
        _hand = hand;
        _probOne = _probEleven = 0.5;
        Eleven = new AceNode(hand);
        One = new AceNode(hand - 10);
        _oneTrials = _elevenTrials = 0;
        timesSeen = 0;
    }

    /** Function that decides whether to change the ace
     * to a one or an eleven. */
    public OneorEleven makeDecision() {
        if (_oneTrials < 10) {
            _oneTrials += 1;
            return OneorEleven.ONE;
        } else if (_elevenTrials < 10) {
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
        timesSeen += 1;
    }
    /** Get the amount of times this tree has been seen. */
    int getTimesSeen() {
        return timesSeen;
    }
    /** Return the max chance of winning. */
    double maxProb() {
        return Math.max(_probEleven, _probOne);
    }
    /** Get the hand value of this tree. */
    public int getHand() {
        return _hand;
    }
    /** Get the one node from this tree. */
    public AceNode getOne() {
        return One;
    }
    /** Get the eleven node from this tree. */
    public AceNode getEleven() {
        return Eleven;
    }
    /** Recompute chances of winning using lower nodes. */
    void recompute() {
        _probOne = One.recompute();
        _probEleven = Eleven.recompute();
    }
}