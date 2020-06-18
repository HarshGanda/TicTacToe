import javax.swing.*;
import javax.swing.plaf.ColorUIResource;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import static java.awt.Color.*;
import static javax.swing.JOptionPane.*;

class TicTacToe {
    public static void main(String[] args) {
        UIManager.getDefaults().put("Button.disabledText",new ColorUIResource(BLUE));
        new FrameEx();
    }
}

class FrameEx extends JFrame implements ActionListener {

    private final JButton[][] box = new JButton[3][3];
    private final int[][] values = new int[3][3];
    private int click=0;
    private final Label text1 = new Label("PLAYER 1");
    private final Label text2 = new Label("PLAYER 2");

    FrameEx() {

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                values[i][j] = 2;
            }
        }

        //Frame
        setTitle("Tic Tac Toe");
        setLayout(new BorderLayout(0,0));
        setBackground(WHITE);

        //Labels
        JPanel player = new JPanel();
        player.setLayout(new FlowLayout(FlowLayout.CENTER,5,5));
        player.setBackground(WHITE);
        labelStyle (player, text1, BLUE);
        labelStyle (player, text2, WHITE);

        //Buttons
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.CENTER,0,0));
        panel.setBackground(WHITE);

        //Adding Panels to the Frame
        add(player, BorderLayout.NORTH);
        add(panel, BorderLayout.CENTER);
        for(int i=0;i<3;i++)
            for(int j=0; j<3; j++){
                box[i][j]=new JButton();
                box[i][j].setPreferredSize(new Dimension(130,130));
                box[i][j].setFont(new Font("Times New Roman", Font.PLAIN, 50));
                box[i][j].addActionListener(this);
                panel.add(box[i][j]);
        }
        setSize(500,500);
        setResizable(false);
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    /* Giving styling to the Player Name panel */
    private void labelStyle(JPanel player, Label text, Color color) {
        text.setBackground(color);
        text.setForeground(WHITE);
        text.setAlignment(Label.CENTER);
        text.setFont(new Font("Arial", Font.BOLD,18));
        text.setPreferredSize(new Dimension(100,40));
        player.add(text);
    }

    /* Changing the background to highlight turn of the player */
    private void onclick(int i, int j, String s, int val, Label text1, Label text2) {
        box[i][j].setText(s);
        values[i][j] = val;
        text1.setBackground(BLUE);
        text2.setBackground(WHITE);
    }

    /* Highlighting the player's turn and checking for winner after every turn */
    @Override
    public void actionPerformed(ActionEvent e) {
        click++;
        if (e.getSource() instanceof JButton) {
            for(int i=0;i<3;i++) {
                for(int j=0;j<3;j++) {
                    if (e.getSource() == box[i][j]) {
                        if (click < 10) {
                            box[i][j].setEnabled(false);
                            if (click % 2 == 0) {
                                onclick(i, j, "0", 0, text1, text2);
                            } else {
                                onclick(i, j, "X", 1, text2, text1);
                            }
                            if (click > 4)
                                check_win();
                        }
                        if (click == 9) {
                            int result = JOptionPane.showConfirmDialog(this, "Draw", "Result", JOptionPane.OK_CANCEL_OPTION);
                            if (result == OK_OPTION) {
                                restart();
                            } else if (result == JOptionPane.CANCEL_OPTION) {
                                System.exit(0);
                            }
                        }
                    }
                }
            }
        }
    }

    /* Conditions for winning */
    private void check_win() {
        for(int i=0; i<3; i++) {
            if (values[i][0] == values[i][1] && values[i][1] == values[i][2] && values[i][2] == 0) {
                result("Player 2 Wins!");
            } else if (values[i][0] == values[i][1] && values[i][1] == values[i][2] && values[i][2] == 1) {
                result("Player 1 Wins!");
            }
        }
        for(int i=0; i<3; i++) {
            if (values[0][i] == values[1][i] && values[1][i] == values[2][i] && values[2][i] == 0) {
                result("Player 2 Wins!");
            } else if (values[0][i] == values[1][i] && values[1][i] == values[2][i] && values[2][i] == 1) {
                result("Player 1 Wins!");
            }
        }
        if(values[0][0]==values[1][1] && values[1][1]==values[2][2] && values[2][2]==0) {
            result("Player 2 Wins!");
        }
        else
        if(values[0][0]==values[1][1] && values[1][1]==values[2][2] && values[2][2]==1) {
            result("Player 1 Wins!");
        }
        else
        if(values[2][0]==values[1][1] && values[1][1]==values[0][2] && values[0][2]==0) {
            result("Player 2 Wins!");
        }
        else
        if(values[2][0]==values[1][1] && values[1][1]==values[0][2] && values[0][2]==1) {
            result("Player 1 Wins!");
        }
    }

    /* Showing result in the output window */
    private void result(String str) {
        UIManager.put("OptionPane.cancelButtonText", "Exit");
        UIManager.put("OptionPane.okButtonText", "Play Again!");
        int result = JOptionPane.showConfirmDialog(this, str,"Winner", JOptionPane.OK_CANCEL_OPTION);
        if(result == OK_OPTION) {
            restart();
        }
        else if(result == CANCEL_OPTION) {
            System.exit(0);
        }
    }

    /* Reset game to initial state */
    private void restart() {
        click=0;
        for(int i=0;i<3;i++)
            for(int j=0;j<3;j++) {
                box[i][j].setEnabled(true);
                onclick(i, j,"", 2, text1, text2);
            }
    }
}
