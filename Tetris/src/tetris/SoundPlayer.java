package tetris;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;

public class SoundPlayer {
    private String soundFolder = "tetrissounds" + File.separator;
    private String clearLinePath = soundFolder + "Clear_Line.wav";
    private String gameOverPath = soundFolder + "Game_Over.wav";

    private Clip clearLineSound;
    private Clip gameOverSound;
    public SoundPlayer(){
        try {
            clearLineSound = AudioSystem.getClip();
            gameOverSound = AudioSystem.getClip();

            clearLineSound.open(AudioSystem.getAudioInputStream(new File(clearLinePath).getAbsoluteFile()));
            gameOverSound.open(AudioSystem.getAudioInputStream(new File(gameOverPath).getAbsoluteFile()));

        } catch(LineUnavailableException e){
            throw new RuntimeException(e);
        } catch (UnsupportedAudioFileException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void playClearLineSound(){
        clearLineSound.setFramePosition(0);
        clearLineSound.start();
    }

    public void playGameOverSound(){
        gameOverSound.setFramePosition(0);
        gameOverSound.start();
    }
}
