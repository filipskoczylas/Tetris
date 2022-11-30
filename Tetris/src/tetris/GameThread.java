package tetris;

import java.util.logging.Level;
import java.util.logging.Logger;

public class GameThread extends Thread{
    public int score = 0;
    public int level = 1;
    private int frameTick = 600;
    private int frameDecreasePerLevel= 20;
    private int scorePerLevel = 3;
    private GameForm gameForm;
    private GameArea gameArea;
    public GameThread(GameArea gameArea, GameForm gameForm){
        this.gameArea = gameArea;
        this.gameForm = gameForm;

        gameForm.updateLevel(level);
        gameForm.updateScore(score);
    }
    @Override
    public void run(){
        while(true) {
            gameArea.spawnBlock();
            while(gameArea.makeBlockFall()) {
                try {
                    Thread.sleep(frameTick);
                } catch (InterruptedException e) {
                    return;
                }
            }

            if(gameArea.isBlockOutOfBounds()) {
                Main.gameOver(score);
                break;
            }
            gameArea.moveBlockToBackground();
            score += gameArea.clearLines();
            gameForm.updateScore(score);

            int lvl = score/scorePerLevel +1;
            if(lvl>level){
                level = lvl;
                gameForm.updateLevel(level);
                frameTick -= frameDecreasePerLevel;
            }
        }
    }
}
