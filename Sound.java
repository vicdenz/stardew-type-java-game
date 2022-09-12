import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.Clip;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineListener;
import java.io.IOException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.sound.sampled.LineUnavailableException;

/**
 * The Sound class is a either a sound effect or music played in the main program.
 * @see Main
 * @see Map
 * @author David Daniliuc
 * @version "1.8.0_322"
 */
public class Sound {
    Clip sound;
    //------------------------------------------------------------------------------         
    Sound(String soundName) {
        try {
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(new File(soundName));
            this.sound = AudioSystem.getClip();
            this.sound.open(audioStream);
        } catch (IOException ex) {
            System.out.println("Audio file not found: " + soundName);
        } catch (UnsupportedAudioFileException ex) {
            System.out.println("Unsupported file format: " + soundName);
        } catch (LineUnavailableException ex) {
            System.out.println("Audio feed already in use: " + soundName);
        }
    }
    //------------------------------------------------------------------------------         
    public Clip getSound() {
        return this.sound;
    }

    public void setSound(Clip sound) {
        this.sound = sound;
    }
    //------------------------------------------------------------------------------         
    /** 
     * Start playing the sound.
     */
    public void start() {
        this.sound.start();
    }

    /** 
     * Loop the sound.
     */
    public void loop() {
        this.sound.loop(Clip.LOOP_CONTINUOUSLY);
    }

    /** 
     * Stop playing the sound.
     */
    public void stop() {
        this.sound.stop();
    }

    /** 
     * Flush the sound data.
     */
    public void flush() {
        this.sound.flush();
    }

    /** 
     * Set the start point of the sound.
     * @param frames start frame
     */
    public void setFramePosition(int frames) {
        this.sound.setFramePosition(frames);
    }

    /** 
     * Add a line listener to the sound.
     * @param listener LineListener object
     */
    public void addLineListener(LineListener listener) {
        this.sound.addLineListener(listener);
    }

    /** 
     * Checks if the sound is currently running.
     * @return boolean
     */
    public boolean isRunning() {
        return this.sound.isRunning();
    }
    //------------------------------------------------------------------------------         
    @Override
    public String toString() {
        return "{" +
            " sound='" + getSound() + "'" +
            "}";
    }
}