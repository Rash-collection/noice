/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package noice.interact;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

/**
 *
 * @author rash4
 */
public abstract class Inpux<T extends Inpux<T>> implements KeyListener,
        MouseListener, MouseMotionListener, MouseWheelListener{
    private Visu.Pane container;
    public Inpux(Visu.Pane container){
        if(container == null)throw new IllegalArgumentException(
                "object 'container' must not be NULL.");
        this.container = container;
    }
    protected Visu.Pane pane(){return this.container;}
    public abstract void initialize();
    public void dispose(){
        this.removeit();
        this.container = null;
    }
    public void register(){
        this.container.addKeyListener(this);
        this.container.addMouseListener(this);
        this.container.addMouseMotionListener(this);
        this.container.addMouseWheelListener(this);
    }
    public void removeit(){
        this.container.removeKeyListener(this);
        this.container.removeMouseListener(this);
        this.container.removeMouseMotionListener(this);
        this.container.removeMouseWheelListener(this);
    }
    @Override public void keyTyped(KeyEvent e) {
    }
    @Override public void keyPressed(KeyEvent e) {
    }
    @Override public void keyReleased(KeyEvent e) {
    }
    @Override public void mouseClicked(MouseEvent e) {
    }
    @Override public void mousePressed(MouseEvent e) {
    }
    @Override public void mouseReleased(MouseEvent e) {
    }
    @Override public void mouseEntered(MouseEvent e) {
    }
    @Override public void mouseExited(MouseEvent e) {
    }
    @Override public void mouseDragged(MouseEvent e) {
    }
    @Override public void mouseMoved(MouseEvent e) {
    }
    @Override public void mouseWheelMoved(MouseWheelEvent e) {
    }
}