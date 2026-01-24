package Music;

import java.io.File;
import java.io.IOException;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Music {

    private Clip clip;
    private String filePath;

    public Music() {

        this.filePath = "C:\\Users\\Syrian Technology\\Downloads\\Telegram Desktop\\InventoryManagementSystemFinal\\src\\Music\\jazz.wav";

        try {
            playAutomatically();
        } catch (Exception e) {
            System.out.println("Error playing audio: " + e.getMessage());
        }
    }

    private void playAutomatically() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        File file = new File(filePath);
        AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);

        clip = AudioSystem.getClip();
        clip.open(audioStream);

        clip.start();

        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }
}