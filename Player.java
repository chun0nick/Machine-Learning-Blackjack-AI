import java.util.ArrayList;
/** A class representing an AI and its memory. */

public class Player {
    /** ArrayList representing this player's hand. */
    private ArrayList<Integer> _hand;
    /** ArrayLists holding the previous decisions a player has made with hands. */
    public ArrayList<DecisionTree> _Decisions;
    public ArrayList<AceTree> _aceDecisions;
    /** The current Decision/AceTrees a player is using to make decisions. */
    private DecisionTree _currentTree;
    private AceTree _currentAceTree;
    /** The head Decision/AceTrees a player is using to make decisions. */
    private DecisionTree _headTree;
    private AceTree _headAceTree;
    /** The current AceNode a player is using to make decisions if they have an ace. */
    private AceNode _decisionMaker;

    /** Constructor for a player. */
    public Player() {
        _hand = new ArrayList<>();
        _Decisions = new ArrayList<>();
        _aceDecisions = new ArrayList<>();
    }
    /** Wipe a player's hand and head/current Trees. */
    public void wipe() {
        _hand = new ArrayList<>();
        _currentTree = null;
        _headTree = null;
        _currentAceTree = null;
        _headAceTree = null;
    }
    /** Set a decisionMaker. */
    public void setDecisionMaker(AceNode decision) {
        _decisionMaker = decision;
    }
    /** Get the current decisionMaker in use by this player. */
    public AceNode getDecisionMaker() {
        return _decisionMaker;
    }
    /** Add a card to this player's hand. */
    public void addCard(int card) {
        _hand.add(card);
    }
    /** Return hand value of this player's hand. */
    public int handValue() {
        return Utils.sum(_hand);
    }
    /** Get this player's hand. */
    public ArrayList<Integer> getHand() {
        return _hand;
    }
    /** See if this player has a given hand in their memory. */
    public boolean containsHand(int hand) {
        for (DecisionTree Tree : _Decisions) {
            if (Tree.getHand() == hand) {
                return true;
            }
        }
        return false;
    }
    /** Add a new DecisionTree to this player's memory. */
    public void addTree(DecisionTree Tree) {
        _Decisions.add(Tree);
    }
    /** Get a DecisionTree from this player's memory given a hand value. */
    DecisionTree getTree(int hand) {
        for (DecisionTree Tree: _Decisions) {
            if (hand == Tree.getHand()) {
                return Tree;
            }
        }
        return null;
    }
    /** Set the head DecisionTree of this player. */
    public void setHeadTree(DecisionTree T) {
        _headTree = T;
    }
    /** Check if this player's memory contains a corresponding AceTree. */
    public boolean containsAceHand(int hand) {
        for (AceTree Tree : _aceDecisions) {
            if (Tree.getHand() == hand) {
                return true;
            }
        }
        return false;
    }
    /** Add an AceTree to this player's memory. */
    public void addAceTree(AceTree Tree) {
        _aceDecisions.add(Tree);
    }
    /** Get an AceTree from this player's memory, given a hand value. */
    AceTree getAceTree(int hand) {
        for (AceTree T: _aceDecisions) {
            if (T.getHand() == hand) {
                return T;
            }
        }
        return null;
    }
    /** Get this player's head DecisionTree. */
    public DecisionTree getHeadTree() {
        return _headTree;
    }
    /** Get this player's current DecisionTree. */
    public DecisionTree getCurrentTree() {
        return _currentTree;
    }
    /** Get this player's head AceTree. */
    public AceTree getHeadAceTree() {
        return _headAceTree;
    }
    /** Get this player's current AceTree. */
    public AceTree getCurrentAceTree() {
        return _currentAceTree;
    }
    /** Set this player's head AceTree. */
    public void setHeadAceTree(AceTree Tree) {
        _headAceTree = Tree;
    }
    /** Set this player's current DecisionTree. */
    public void setCurrentTree(DecisionTree T) {
        _currentTree = T;
    }
    /** Set this player's current AceTree. */
    public void setCurrentAceTree(AceTree T) {
        _currentAceTree = T;
    }
}
