/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package noice.interact;

import noice.interact.scenes.functionals.Resizable;
import graphicker.Painter;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author rash4
 */
public class Visu {
    final Cont frame;
    final Pane panel;
    Visu(String title, int w, int h){
        this.panel = new Pane(w, h);
        this.frame = new Cont(title);
        this.frame.setContentPane(this.panel);
        this.frame.pack();
    }
    public void initialize(){
        this.frame.initialize();
        this.panel.initialize();
    }
    // this is it for now.
    public void dispose(int operation){
        this.frame.setDefaultCloseOperation(operation);
        this.frame.dispose();
    }
    public Visu setPaints(Painter grr){
        if(grr == null)throw new IllegalArgumentException("Painter is NULL.");
        this.panel.paints = grr;
        return this;
    }
    public Visu setResizing(Resizable resize){
        if(resize == null)throw new IllegalArgumentException("Resizable is NULL.");
        this.panel.resizing = resize;
        return this;
    }
    class Cont extends JFrame{
        Cont(String title){
            super.setTitle(title);
        }
        void initialize(){
            super.addWindowFocusListener(new WindowFocusListener(){
                @Override public void windowGainedFocus(WindowEvent e) {
                    Visu.this.panel.requestFocusInWindow();
                }
                @Override public void windowLostFocus(WindowEvent e) {}
            });
            super.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            super.setLocationRelativeTo(null);
            super.setResizable(true);
            super.setVisible(true);
        }
    }
    class Pane extends JPanel{
        private Resizable resizing = siz->{};
        private Painter paints = grr->{};
        Pane(int w, int h){
            super.setPreferredSize(new Dimension(w, h));
            super.setLayout(null);
        }
        @Override protected void paintComponent(Graphics g){
            super.paintComponent(g);
            this.paints.paint((Graphics2D)g);
        }
        void initialize(){
            super.addComponentListener(new ComponentAdapter(){
                @Override public void componentResized(ComponentEvent e){
                    Pane.this.resizing.resized(Pane.this.getSize());
                }
            });
        }
    }
}