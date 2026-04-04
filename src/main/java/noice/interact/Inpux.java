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
    private Visu container;
    public Inpux(Visu container){
        if(container == null)throw new IllegalArgumentException(
                "object 'container' must not be NULL.");
        this.container = container;
    }
    public T self(){return (T)this;}
    // either removal or turn to private
    @Deprecated(forRemoval=true)
    protected Visu container(){return this.container;}
    // these two getters are for convenience right now...
    @Deprecated(forRemoval=true)
    protected Visu.Pane pane(){return this.container.panel;}
    @Deprecated(forRemoval=true)
    protected Visu.Cont frame(){return this.container.frame;}
    // leakers..
    protected void repaint(){this.pane().repaint();}
    protected void reFocus(){this.pane().requestFocusInWindow();}
    // qnd other leakers
    public abstract T initialize();
    public void dispose(){
        this.removeit();
        this.container = null;
    }
    public T register(){
        this.pane().addKeyListener(this);
        this.pane().addMouseListener(this);
        this.pane().addMouseMotionListener(this);
        this.pane().addMouseWheelListener(this);
        return this.self();
    }
    public T removeit(){
        this.pane().removeKeyListener(this);
        this.pane().removeMouseListener(this);
        this.pane().removeMouseMotionListener(this);
        this.pane().removeMouseWheelListener(this);
        return this.self();
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