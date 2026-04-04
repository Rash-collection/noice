/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */

package noice.interact.scenes;

import java.awt.Dimension;
import java.awt.Graphics2D;
import noice.interact.scenes.functionals.*;

/**
 *
 * @author rash4
 */
public class SceneAdapter implements Paintable, Resizable, Scalable, Updatable {
    @Override public void painting(Graphics2D grr) {
    }
    @Override public void resized(Dimension containerSize) {
    }
    @Override public void scales(float scaleFactor) {
    }
    @Override public void updating(int incremental) {
    }
}