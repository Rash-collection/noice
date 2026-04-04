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
    public Inpux(){}
    public T self(){return (T)this;}
    
    // leakers..
    protected void repaint(){Channel.CAZA.panel.repaint();}
    protected void reFocus(){Channel.CAZA.panel.requestFocusInWindow();}
    // and other leakers
    
    public T register(){
        var panel = Channel.CAZA.panel;
        panel.addKeyListener(this);
        panel.addMouseListener(this);
        panel.addMouseMotionListener(this);
        panel.addMouseWheelListener(this);
        return this.self();
    }
    public T removeit(){
        var panel = Channel.CAZA.panel;
        panel.removeKeyListener(this);
        panel.removeMouseListener(this);
        panel.removeMouseMotionListener(this);
        panel.removeMouseWheelListener(this);
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