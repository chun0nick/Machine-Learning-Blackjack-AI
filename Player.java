import java.util.ArrayList;
/** A class representing an AI and its memory. */

public class Player {
    /** ArrayList representing this player's hand. */
    private ArrayList<Integer> _hand;
    /** ArrayLists holding the previous decisions a player has made with hands. */
    public ArrayList<DecisionTree> _Decisions;
    public ArrayList<AceTree> _aceDecisions;
    /** The current Decision/AceTrees a player is using to make decisions. */
    private DecisionTree currentTree;
    private AceTree currentAceTree;
    /** The head Decision/AceTrees a player is using to make decisions. */
    private DecisionTree headTree;
    private AceTree headAceTree;
    /** The current AceNode a player is using to make decisions if they have an ace. */
    private AceNode decisionMaker;

    /** Constructor for a player. */
    public Player() {
        _hand = new ArrayList<>();
        _Decisions = new ArrayList<>();
        _aceDecisions = new ArrayList<>();
    }
    /** Wipe a player's hand and head/current Trees. */
    public void wipe() {
        _hand = new ArrayList<>();
        currentTree = null;
        headTree = null;
        currentAceTree = null;
        headAceTree = null;
    }
    /** Set a decisionMaker. */
    public void setDecisionMaker(AceNode decision) {
        decisionMaker = decision;
    }
    /** Get the current decisionMaker in use by this player. */
    public AceNode getDecisionMaker() {
        return decisionMaker;
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
        headTree = T;
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
        return headTree;
    }
    /** Get this player's current DecisionTree. */
    public DecisionTree getCurrentTree() {
        return currentTree;
    }
    /** Get this player's head AceTree. */
    public AceTree getHeadAceTree() {
        return headAceTree;
    }
    /** Get this player's current AceTree. */
    public AceTree getCurrentAceTree() {
        return currentAceTree;
    }
    /** Set this player's head AceTree. */
    public void setHeadAceTree(AceTree Tree) {
        headAceTree = Tree;
    }
    /** Set this player's current DecisionTree. */
    public void setCurrentTree(DecisionTree T) {
        currentTree = T;
    }
    /** Set this player's current AceTree. */
    public void setCurrentAceTree(AceTree T) {
        currentAceTree = T;
    }
}
