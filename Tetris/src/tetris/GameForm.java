package tetris;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameForm extends JFrame implements ActionListener {
    private Rectangle gameBounds;
    private GameThread gameThread;
    private GameArea gameArea;
    private JLabel scoreLabel;
    private JLabel levelLabel;
    private JButton goBackToMenuButton;
    public GameForm(){
        gameBounds = new Rectangle(100,50,300,510);
        this.setBounds(0,0,500,800);
        this.setTitle("Simplistic Tetris!");
        this.setLayout(null);

        gameArea = new GameArea(gameBounds, 10);
        this.add(gameArea);

        scoreLabel = new JLabel();
        scoreLabel.setBounds(100,15, 100, 20);
        scoreLabel.setText("Score: " + 0);
//        scoreLabel.setFont(Font.);
        this.add(scoreLabel);

        levelLabel = new JLabel();
        levelLabel.setBounds(250,15, 100, 20);
        levelLabel.setText("Level: 1");
        this.add(levelLabel);

        goBackToMenuButton = new JButton();
        goBackToMenuButton.setBounds(100,610,300,50);
        goBackToMenuButton.setText("Main menu");
        goBackToMenuButton.addActionListener(this);
        goBackToMenuButton.setFocusable(false);
        this.add(goBackToMenuButton);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        initControls();
    }
    public void actionPerformed(ActionEvent e){
        gameThread.interrupt();
        this.setVisible(false);
        Main.goToMainMenu();
    }
    public void startGame(){
        gameArea.initBackgroudArray();
        gameThread = new GameThread(gameArea, this);
        gameThread.start();
    }
    public void updateScore(int score){
        scoreLabel.setText("Score: " + score);
    }
    public void updateLevel(int level){
        levelLabel.setText("Level: " + level);
    }

    private void initControls(){
        InputMap inputMap = this.getRootPane().getInputMap();
        ActionMap actionMap = this.getRootPane().getActionMap();

        inputMap.put(KeyStroke.getKeyStroke("RIGHT"),"right");
        inputMap.put(KeyStroke.getKeyStroke("LEFT"), "left");
        inputMap.put(KeyStroke.getKeyStroke("UP"),"up");
        inputMap.put(KeyStroke.getKeyStroke("DOWN"),"down");

        actionMap.put("right", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameArea.moveBlockRight();
            }
        });
        actionMap.put("left", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameArea.moveBlockLeft();
            }
        });
        actionMap.put("up", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameArea.rotateBlock();
            }
        });
        actionMap.put("down", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameArea.dropBlock();
            }
        });
    }
}
