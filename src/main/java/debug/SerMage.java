/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package debug;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;
import javax.imageio.ImageIO;

/**
 * <p>Parse save/load ser-images.</p>
 * @author rash4
 */
public class SerMage implements Serializable{
    private final static long serialVersionUID = 1L;
    
    private byte[] data;
    SerMage(BufferedImage image) throws IOException{
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ImageIO.write(image, "png", out);
        this.data = out.toByteArray();
    }
    SerMage(graphicker.Painter prr){
//        this(graphicker.MageCons.printOn(0, 0, prr));
    }
    public BufferedImage getImage() throws IOException {
        return ImageIO.read(new ByteArrayInputStream(this.data));
    }
}