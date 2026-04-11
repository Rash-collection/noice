/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package noice.entities;

/**
 *
 * @author rash4
 */
@FunctionalInterface
public interface Animatable extends Viewable{
    // not ready yet, might add a param for a globalized index @@!?
    java.awt.image.BufferedImage currentImage();
    @Override default void paint(java.awt.Graphics2D grr, java.awt.Rectangle adjusted){
        grr.drawImage(this.currentImage(), adjusted.x, adjusted.y, adjusted.width, adjusted.height, null);
    }
}