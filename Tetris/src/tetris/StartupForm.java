package tetris;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StartupForm extends JFrame implements ActionListener {
    private JButton startButton;
    private JButton leaderboardButton;
    private JButton quitButton;
    public StartupForm(){
        this.setBounds(0,0,500,800);
        this.setLayout(null);

        startButton = new JButton();
        startButton.setBounds(100,100,300,100);
        startButton.setText("Start");
        startButton.addActionListener(this);
        this.add(startButton);

        leaderboardButton = new JButton();
        leaderboardButton.setBounds(100,250,300,100);
        leaderboardButton.setText("Leaderboard");
        leaderboardButton.addActionListener(this);
        this.add(leaderboardButton);

        quitButton = new JButton();
        quitButton.setBounds(100,400,300,100);
        quitButton.setText("Quit");
        quitButton.addActionListener(this);
        this.add(quitButton);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    @Override
    public void actionPerformed(ActionEvent e){
        if(e.getSource()==startButton){
            this.setVisible(false);
            Main.startTetris();
        }
        if(e.getSource()==leaderboardButton){
            this.setVisible(false);
            Main.showLeaderboard();
        }
        if(e.getSource()==quitButton){
            System.exit(0);
        }
    }
}
