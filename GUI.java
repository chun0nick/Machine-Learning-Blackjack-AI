import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Collections;
import java.util.ArrayList;

/** Class for the GUI used by this program.
 *  This GUI is (HEAVILY) inspired by the GUI at:
 *  http://faculty.washington.edu/moishe/javademos/blackjack/BlackjackGUI.java.
 *  It's better documented there than I could ever hope to do,
 *  so please go to that link for documentation.
 */

public class GUI extends JPanel{
    private JPanel topPanel = new JPanel();
    private JPanel dcardPanel = new JPanel();
    private JPanel pcardPanel = new JPanel();
    private JTextPane winlosebox = new JTextPane();
    private JButton hitbutton = new JButton();
    private JButton dealbutton = new JButton();
    private JButton staybutton = new JButton();
    private JButton playagainbutton = new JButton();
    private JLabel dealerlabel = new JLabel();
    private JLabel playerlabel = new JLabel();

    private AI AI1 = Engine.getAI();
    private Player player = new Player();

    private JLabel AICard1;
    private int AICard1Val;

    private Deck d;

    private Image Images;

    GUI() {
        topPanel.setBackground(new Color(0, 122, 0));
        dcardPanel.setBackground(new Color(0, 122, 0));
        pcardPanel.setBackground(new Color(0, 122, 0));

        topPanel.setLayout(new FlowLayout());
        winlosebox.setText(" ");
        winlosebox.setFont(new java.awt.Font("Helvetica Bold", 1, 20));
        dealbutton.setText(" Deal");
        dealbutton.addActionListener(new dealbutton());
        hitbutton.setText(" Hit");
        hitbutton.addActionListener(new hitbutton());
        hitbutton.setEnabled(false);
        staybutton.setText(" Stay");
        staybutton.addActionListener(new staybutton());
        staybutton.setEnabled(false);
        playagainbutton.setText(" Play Again");
        playagainbutton.addActionListener(new playagainbutton());
        playagainbutton.setEnabled(false);

        dealerlabel.setText(" AI: ");
        playerlabel.setText(" Player: ");

        topPanel.add(winlosebox);
        topPanel.add(dealbutton);
        topPanel.add(hitbutton);
        topPanel.add(staybutton);
        topPanel.add(playagainbutton);
        pcardPanel.add(playerlabel);
        dcardPanel.add(dealerlabel);

        setLayout(new BorderLayout());
        add(topPanel, BorderLayout.NORTH);
        add(dcardPanel, BorderLayout.CENTER);
        add(pcardPanel, BorderLayout.SOUTH);

        d = new Deck();
        Images = new Image();
    }

    public void display() {
        JFrame myFrame = new JFrame("Blackjack");
        myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        myFrame.setContentPane(this);
        myFrame.setPreferredSize(new Dimension(700, 550));
        dealbutton.doClick();
        staybutton.doClick();
        playagainbutton.doClick();

        myFrame.pack();
        myFrame.setVisible(true);
    }

    class dealbutton implements ActionListener {
        public void actionPerformed(ActionEvent e) {

            JLabel pCard1, pCard2, AICard2;
            dcardPanel.add(dealerlabel);
            pcardPanel.add(playerlabel);

            Images = new Image();

            AICard1 = new JLabel(new ImageIcon("assets/back.jpg"));

            for (int i = 0; i < 2; i += 1) {
                player.addCard(d.draw());
                AI1.addCard(d.draw());
            }
            Collections.sort(AI1.getHand());

            pCard1 = new JLabel(Images.getImage(player.get(0)));
            pCard2 = new JLabel(Images.getImage(player.get(1)));

            AICard1 = new JLabel(Images.getImage(AI1.get(0)));
            AICard1Val = AI1.get(0);

            AICard2 = new JLabel(new ImageIcon("assets/back.jpg"));

            dcardPanel.add(AICard1);
            dcardPanel.add(AICard2);

            pcardPanel.add(pCard1);
            pcardPanel.add(pCard2);

            dealerlabel.setText(" AI: ");
            playerlabel.setText(" Player: ");

            hitbutton.setEnabled(true);
            staybutton.setEnabled(true);
            dealbutton.setEnabled(false);

            add(dcardPanel, BorderLayout.CENTER);
            add(pcardPanel, BorderLayout.SOUTH);
        }
    }
    class hitbutton implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            JLabel pCardhit;
            int drawn = d.draw();
            pCardhit = new JLabel(Images.getImage(drawn));
            pcardPanel.add(pCardhit);
            pcardPanel.repaint();

            player.addCard(drawn);

            if (player.handValue() > 21) {
                if (!Utils.canUnbust(player.getHand())) {
                    winlosebox.setText("Bust");
                    hitbutton.setEnabled(false);
                    dealbutton.setEnabled(false);
                    staybutton.setEnabled(false);
                    playagainbutton.setEnabled(true);
                }
            }

            playerlabel.setText(" Player : " + player.handValue());
        }
    }



    class staybutton implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            JLabel AICardhit;
            if (player.handValue() > 21) {
                if (!Utils.canUnbust(player.getHand())) {
                    playerlabel.setText(" Player: " + player.handValue());
                }
            }
            dcardPanel.removeAll();
            dcardPanel.repaint();
            boolean AIbust;
            if (Utils.containsAce(AI1.getHand())) {
                AIbust = Engine.aceDecision(AI1, d, false);
            } else {
                AIbust = Engine.playerDecision(AI1, d, false);
            }

            dealerlabel.setText(" " + dealerlabel.getText());

            playerlabel.setText("Player: " + player.handValue());

            dcardPanel.removeAll();

            dcardPanel.add(dealerlabel);

            dealerlabel.setText("AI: " + AI1.handValue());

            boolean cardAdded = false;
            ArrayList<Integer> hand = AI1.getHand();

            for (int i = 0; i < hand.size(); i += 1) {
                if (hand.get(i) == AICard1Val && !cardAdded) {
                    dcardPanel.add(AICard1);
                    cardAdded = true;
                } else {
                    AICardhit = new JLabel(Images.getImage(hand.get(i)));
                    dcardPanel.add(AICardhit);
                }
            }
            dcardPanel.repaint();

            if (AIbust) {
                winlosebox.setText("Player");
            }
            else if (AI1.handValue() > player.handValue()) {
                winlosebox.setText("AI");
            } else if (AI1.handValue() < player.handValue()) {
                winlosebox.setText("Player");
            } else {
                winlosebox.setText("Tie");
            }
            hitbutton.setEnabled(false);
            staybutton.setEnabled(false);

            playagainbutton.setEnabled(true);

        }
    }
    class playagainbutton implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            dealerlabel.setText("AI: ");
            playerlabel.setText("Player: ");
            winlosebox.setText("");
            AI1.wipe();
            player.clearHand();
            d = new Deck();

            dcardPanel.removeAll();
            pcardPanel.removeAll();

            hitbutton.setEnabled(false);
            staybutton.setEnabled(false);
            playagainbutton.setEnabled(false);
            dealbutton.setEnabled(true);
        }
    }
}
