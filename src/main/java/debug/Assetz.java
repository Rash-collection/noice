/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package debug;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.LinearGradientPaint;
import java.awt.Point;
import java.awt.image.BufferedImage;

/**
 * <p>Java drawn images</p>
 * @author rash4
 */
public class Assetz{
    static BufferedImage[] cookies(){
        final int zis = 256;
        final var ova = new BufferedImage(zis, zis, 2);
        var grr = ova.createGraphics();
        final var clrs = new Color[]{Color.CYAN, Color.BLUE, Color.ORANGE};
        final int len = clrs.length;
        final float[] frct = new float[len];
        final float frfc = 1F/len;
        for(int i = 0; i < len; i++){
            frct[i] = (float)(i+1) * frfc;
        }
        final LinearGradientPaint raino = new LinearGradientPaint(
                new Point(0, 0), new Point(zis, zis), frct, clrs
        );
        grr.setPaint(raino);
        grr.fillOval(0, 0, zis, zis);
        grr.dispose();
        final var whity = new BufferedImage(zis, zis, 2);
        grr = whity.createGraphics();
        grr.setColor(Color.WHITE);
        grr.fillArc(0, 0, zis, zis, zis/3, zis/3);
        grr.dispose();
        return new BufferedImage[]{ova, whity};
    }
}