package Tankgame;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;

public class Audio {
    private Clip bg;
    private long microSecondPosition;
    public Audio() {
        try {
            this.bg = AudioSystem.getClip();
            AudioInputStream aio = AudioSystem.getAudioInputStream(new File("src\\BGM\\bgm.wav"));
            bg.open(aio);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    public void play() {
        bg.setMicrosecondPosition(0);
        bg.start();
    }
    public void pause() {
        microSecondPosition = bg.getMicrosecondPosition();
        bg.stop();
    }
    public void run_on() {
        bg.setMicrosecondPosition(microSecondPosition);
        bg.start();
    }
    public void end() {
        bg.stop();
        bg.close();
    }
}