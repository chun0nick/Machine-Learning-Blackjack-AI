/** Class that actually generates the AI
 *  by playing it against itself and re-weighting
 *  the DecisionTrees.
 */

public class Engine {

    /** Generates an AI */
    public static Player getAI() {
        Player Player1, Player2;
        Player1 = new Player();
        Player2 = new Player();
        playRounds(Player1, Player2);
        return Player1;
    }

    /** Has AIs play 500,000 rounds against each other. */
    public static void playRounds(Player p1, Player p2) {
        for (int j = 1; j < 500000; j += 1) {
            Deck deck = new Deck();
            p1.wipe();
            p2.wipe();
            for (int i = 0; i < 2; i += 1) {
                p1.addCard(deck.draw());
                p2.addCard(deck.draw());
            }
            boolean p1busted;
            boolean p2busted;
            boolean p1hasAce = Utils.containsAce(p1.getHand());
            boolean p2hasAce = Utils.containsAce(p2.getHand());

            /* Check for aces and check if either player busted. */
            if (p1hasAce) {
                p1busted = aceDecision(p1, deck, true);
                if (p1busted) {
                    p1.getHeadAceTree().recompute();
                    continue;
                }
            } else {
                p1busted = playerDecision(p1, deck, true);
                p1hasAce = Utils.containsAce(p1.getHand());
                if (p1busted && !p1hasAce) {
                    p1.getHeadTree().recompute();
                    continue;
                } else if (p1busted && p1hasAce){
                    p1.getHeadAceTree().recompute();
                }
            }
            if (p2hasAce) {
                p2busted = aceDecision(p2, deck, true);
                if (p2busted) {
                    if (!p1hasAce) {
                        p1.getCurrentTree().reaveragePass(1.0);
                        p1.getHeadTree().recompute();
                    }
                    p2.getHeadAceTree().recompute();
                    continue;
                }
            } else {
                p2busted = playerDecision(p2, deck, true);
                p2hasAce = Utils.containsAce(p2.getHand());
                if (p2busted) {
                    if (p2hasAce) {
                        p2.getHeadAceTree().recompute();
                    } else {
                        p2.getHeadTree().recompute();
                    }
                    if (!p1hasAce) {
                        p1.getCurrentTree().reaveragePass(1.0);
                        p1.getHeadTree().recompute();
                    }
                    continue;
                }
            }
            /* Check for the player who won, reaverage decision/ace tree based on results. */
            if (p1.handValue() > p2.handValue()) {
                if (!p1hasAce && !p2hasAce) {
                    p1.getCurrentTree().reaveragePass(1.0);
                    p2.getCurrentTree().reaveragePass(0.0);

                    p1.getHeadTree().recompute();
                    p2.getHeadTree().recompute();
                } else if (!p1hasAce && p2hasAce) {
                    p1.getCurrentTree().reaveragePass(1.0);
                    p2.getDecisionMaker().reaveragePass(0.0);

                    p1.getHeadTree().recompute();
                    p2.getHeadAceTree().recompute();
                } else if (p1hasAce && !p2hasAce) {
                    p2.getCurrentTree().reaveragePass(0.0);
                    p1.getDecisionMaker().reaveragePass(1.0);

                    p1.getHeadAceTree().recompute();
                    p2.getHeadTree().recompute();
                } else {
                    p1.getDecisionMaker().reaveragePass(1.0);
                    p2.getDecisionMaker().reaveragePass(0.0);

                    p1.getHeadAceTree().recompute();
                    p2.getHeadAceTree().recompute();
                }

            } else if (p2.handValue() > p1.handValue()) {
                if (!p1hasAce && !p2hasAce) {
                    p1.getCurrentTree().reaveragePass(0.0);
                    p2.getCurrentTree().reaveragePass(1.0);

                    p1.getHeadTree().recompute();
                    p2.getHeadTree().recompute();
                } else if (!p1hasAce && p2hasAce) {
                    p1.getCurrentTree().reaveragePass(0.0);
                    p2.getDecisionMaker().reaveragePass(1.0);

                    p1.getHeadTree().recompute();
                    p2.getHeadAceTree().recompute();
                } else if (p1hasAce && !p2hasAce) {
                    p2.getCurrentTree().reaveragePass(1.0);
                    p1.getDecisionMaker().reaveragePass(0.0);

                    p1.getHeadAceTree().recompute();
                    p2.getHeadTree().recompute();
                } else {
                    p1.getDecisionMaker().reaveragePass(0.0);
                    p2.getDecisionMaker().reaveragePass(1.0);

                    p1.getHeadAceTree().recompute();
                    p2.getHeadAceTree().recompute();
                }

            } else {
                if (!p1hasAce && !p2hasAce) {
                    p1.getCurrentTree().reaveragePass(0.5);
                    p2.getCurrentTree().reaveragePass(0.5);

                    p1.getHeadTree().recompute();
                    p2.getHeadTree().recompute();
                } else if (p1hasAce && !p2hasAce) {
                    p2.getCurrentTree().reaveragePass(0.5);
                    p1.getDecisionMaker().reaveragePass(0.5);

                    p1.getHeadAceTree().recompute();
                    p2.getHeadTree().recompute();
                } else if (!p1hasAce && p2hasAce) {
                    p1.getCurrentTree().reaveragePass(0.5);
                    p2.getDecisionMaker().reaveragePass(0.5);

                    p1.getHeadTree().recompute();
                    p2.getHeadAceTree().recompute();
                } else {
                    p1.getDecisionMaker().reaveragePass(0.5);
                    p1.getDecisionMaker().reaveragePass(0.5);

                    p1.getHeadAceTree().recompute();
                    p2.getHeadAceTree().recompute();
                }
            }
        }
    }

    /** The decision function for a player with a hand that doesn't contain an ace. */
    public static boolean playerDecision(Player p, Deck deck, boolean building) {
        boolean busted = false;
        DecisionTree decided;
        DecisionTree head;
        Decisions dec;
        /* Check if hand is in player's memory. */
        if (p.containsHand(p.handValue())) {
            head = p.getTree(p.handValue());
        } else {
            head = new DecisionTree(p.handValue());
            p.addTree(head);
        }
        p.setHeadTree(head);
        p.setCurrentTree(head);
        while (true) {
            dec = p.getCurrentTree().makeDecision();
            if (dec == Decisions.HIT) {
                p.addCard(deck.draw());
                if (Utils.containsAce(p.getHand())) {
                    return aceDecision(p, deck, false);
                }
                if (p.getCurrentTree().handSeen(p.handValue())) {
                    decided = p.getCurrentTree().getSeen(p.handValue());
                } else {
                    decided = new DecisionTree(p.handValue());
                    p.getCurrentTree().addSeen(decided);
                }

                p.setCurrentTree(decided);

                if (p.handValue() > 21) {
                    decided.setbustedProb();
                    busted = true;
                    break;
                }

            } else {
                if (p.handValue() > 21) {
                    busted = true;
                }
                break;
            }
        }
        return busted;
    }

    /** The decision function for a player that has an ace in hand. */
    public static boolean aceDecision(Player p, Deck d, boolean building) {
        boolean pDone = false;
        boolean busted = false;
        int aceInd = 0;
        AceTree topTree;
        /* If there's more than one ace, set one to one. */
        if (Utils.aceCount(p.getHand()) > 1) {
            p.getHand().set(1, 1);
        }
        for (int i = 0; i < p.getHand().size(); i += 1) {
            if (p.getHand().get(i) == 1 || p.getHand().get(i) == 11) {
                aceInd = i;
                break;
            }
        }
        /* Check if hand is in player's memory. */
        if (!p.containsAceHand(p.handValue())) {
            topTree = new AceTree(p.handValue());
            p.addAceTree(topTree);
        } else {
            topTree = p.getAceTree(p.handValue());
        }
        p.setHeadAceTree(topTree);
        p.setCurrentAceTree(topTree);

        while (!pDone && !busted) {
            OneorEleven dec = p.getCurrentAceTree().makeDecision();
            AceNode decisionMaker;
            AceTree seen;
            Decisions decision;
            if (dec == OneorEleven.ONE) {
                decisionMaker = p.getCurrentAceTree().getOne();
                p.getHand().set(aceInd, 1);
                decision = decisionMaker.makeDecision();
                p.setDecisionMaker(decisionMaker);

                if (decision == Decisions.HIT) {
                    int card = d.draw();
                    p.addCard(card);
                    if (card == 11) {
                        p.getHand().set(p.getHand().size()-1, 1);
                    }
                    if (decisionMaker.handSeen(p.handValue())) {
                        seen = decisionMaker.getSeen(p.handValue());
                        p.setCurrentAceTree(seen);
                    } else {
                        seen = new AceTree(p.handValue());
                        decisionMaker.addSeen(seen);
                        p.setCurrentAceTree(seen);
                    }

                    if (p.handValue() > 21) {
                        decisionMaker.reaverageHit(0.0);
                        busted = true;
                    }
                } else {
                    if (p.handValue() > 21) {
                        busted = true;
                    }
                    pDone = true;
                }
            } else {
                decisionMaker = p.getCurrentAceTree().getEleven();
                p.getHand().set(aceInd, 11);
                decision = decisionMaker.makeDecision();
                p.setDecisionMaker(decisionMaker);

                if (decision == Decisions.HIT) {
                    int card = d.draw();
                    p.addCard(card);
                    if (card == 11) {
                        p.getHand().set(p.getHand().size() - 1, 1);
                    }
                    if (decisionMaker.handSeen(p.handValue())) {
                        seen = decisionMaker.getSeen(p.handValue());
                        p.setCurrentAceTree(seen);
                    } else {
                        seen = new AceTree(p.handValue());
                        decisionMaker.addSeen(seen);
                        p.setCurrentAceTree(seen);
                    }

                    if (p.handValue() > 21) {
                        decisionMaker.reaverageHit(0.0);
                        busted = true;
                    }
                } else {
                    if (p.handValue() > 21) {
                        decisionMaker.reaveragePass(0.0);
                        busted = true;
                    }
                    pDone = true;

                }
            }
        }
        return busted;
    }
}
