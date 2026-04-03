/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package noice.configs;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 * <p>Resources handler class.</p>
 * @author rash4
 */
public final class SRC {
    private SRC(){}
    public static BufferedImage getImage(String relativeToImages){
        try(InputStream is = SRC.class.getResourceAsStream(("%s%s.png")
                .formatted(ResType.Image.rootTrack(), relativeToImages))){
            if(is == null)throw new RuntimeException("image not found..");
            return ImageIO.read(is);
        }catch(IOException e){
            throw new RuntimeException("faild loading (img) from resources.", e);
        }
    }
    public static String getText(String relativeToTexts){
        try(InputStream is = SRC.class.getResourceAsStream(("%s%s.txt")
                .formatted(ResType.Texts.rootTrack(), relativeToTexts))){
            if(is == null)throw new RuntimeException("text file not found..");
            return new String(is.readAllBytes(), StandardCharsets.UTF_8);
        }catch(IOException e){
            throw new RuntimeException("faild loading (txt) from resources.", e);
        }
    }
    public static Clip getAudio(String relativeToAudios){
        try(InputStream is = SRC.class.getResourceAsStream(("%s%s.wav")
                .formatted(ResType.Audio, relativeToAudios));
                BufferedInputStream bis = new BufferedInputStream(is)){
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(bis);
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            return clip;
        }catch(IOException e){
            throw new RuntimeException("failed loading (wav) from resources.", e);
        } catch (UnsupportedAudioFileException e) {
            throw new RuntimeException("not supported audio or corrupted!!", e);
        } catch (LineUnavailableException e) {
            throw new RuntimeException("couldn't get the clip properly!", e);
        }
    }
}