package tetris;

import javax.swing.*;

public class Main {
    private static GameForm gf;
    private static StartupForm sf;
    private static LeaderboardForm lf;

    private static SoundPlayer sp;
    public static void startTetris(){
        gf.setVisible(true);
        gf.startGame();
    }
    public static void goToMainMenu(){
        sf.setVisible(true);
    }

    public static  void showLeaderboard(){
        lf.setVisible(true);
    }

    public static void gameOver(int score){
        playGameOver();

        String playerName = JOptionPane.showInputDialog("Game over! \n Please enter your name: ");
        gf.setVisible(false);
        lf.addPlayer(playerName, score);
    }

    public static void playClearLines(){
        sp.playClearLineSound();
    }

    public static void playGameOver(){
        sp.playGameOverSound();
    }

    public static void main(String[] args) {
        gf = new GameForm();
        sf = new StartupForm();
        lf = new LeaderboardForm();

        sp = new SoundPlayer();

        sf.setVisible(true);
    }
}