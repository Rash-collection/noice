/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package noice.interact.scenes;

import noice.entities.Entity;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.util.List;
import noice.interact.scenes.functionals.Resizable;
import noice.interact.scenes.functionals.Scalable;
import noice.utils.Boundary;
import noice.utils.Victory;

/**
 *
 * @author rash4
 */
public class Viewer implements Resizable, Scalable{
    private float scaler = 1F;
    protected final Boundary realView;
    protected final Rectangle panelView;
    public Viewer(int x, int y, int w, int h){
        this.panelView = new Rectangle(x, y, w, h);
        this.realView = new Boundary(x, y, w/2, h/2);
    }
    public Viewer(Rectangle rect){
        this.panelView = rect;
        this.realView = new Boundary(rect.x, rect.y, rect.width/2, rect.height/2);
    }
    protected Victory getDelta(){
        return this.realView.getTopLeft().sub(this.panelView.x, this.panelView.y);
    }
    public void painting(java.awt.Graphics2D grr, List<Entity<?>> entities){
        if(entities.isEmpty())return;
        final var delta = this.getDelta().toDimension();
        final var onScrn = this.panelView;
        final var ofScrn = this.realView;
        final var oldC = grr.getClip();
        grr.setClip(onScrn);
        synchronized(entities){for(var entity : entities){
            final var bnd = entity.getBounds();
            if(!ofScrn.contains(bnd.center()))continue;
            final var scaled = bnd.scale(this.scaler);
            scaled.setBounds(
                    scaled.x - delta.width,
                    scaled.y - delta.height,
                    scaled.width, scaled.height);
            entity.paint(grr, scaled);
        }}
        grr.setClip(oldC);
    }
    @Override public synchronized void resized(Dimension containerSize) {
        // default behavior..
        this.panelView.setSize(
                (containerSize.width/2)  - this.panelView.x,
                (containerSize.height/2) - this.panelView.y
        );
    }
    @Override public synchronized void scales(float scaleFactor) {
        final float noise = 1e-3F;
        if(scaleFactor <= noise || Math.abs(this.scaler - scaleFactor) < noise)return;
        this.realView.setRange(this.realView.range().scale(1F/scaleFactor));
        this.scaler = scaleFactor;
    }
}