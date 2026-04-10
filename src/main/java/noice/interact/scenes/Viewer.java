/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package noice.interact.scenes;

import java.awt.BasicStroke;
import java.awt.Color;
import noice.entities.Entity;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
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
    protected Sit some;
    public Viewer(int x, int y, int w, int h){
        final float hlfW = (float)w/2F, hlfH = (float)h/2F;
        this.panelView = new Rectangle(x, y, w, h);
        this.realView = new Boundary(x+hlfW, y+hlfH, hlfW, hlfH);
    }
    public Viewer(Rectangle rect){
        final float hlfW = (float)rect.width/2F, hlfH = (float)rect.height/2F;
        this.panelView = rect;
        this.realView = new Boundary(rect.x+hlfW, rect.y+hlfH, hlfW, hlfH);
        this.some = new Sit(this.realView);
    }
    protected Point getDelta(){
        return this.realView.getTopLeft().scale(scaler)
                .sub(this.panelView.x, this.panelView.y).toPoint();
    }
    public void moveScreen(float deltaX, float deltaY){
        this.realView.moveBy(deltaX, deltaY);
    }
    public void moveScreen(Victory delta){
        this.realView.moveBy(delta);
    }
    public void setScreen(int x, int y){
        this.realView.moveTo(x, y);
    }
    public void painting(java.awt.Graphics2D grr, List<Entity<?>> entities){
        if(entities.isEmpty())return;
        final var delta = this.getDelta();
        final var onScrn = this.panelView;
        final var ofScrn = this.realView;
        final var oldC = grr.getClip();
        grr.setColor(Color.GREEN);
        grr.fill(onScrn);
        grr.setClip(onScrn);
        synchronized(entities){for(var entity : entities){
            final var bnd = entity.getBounds();
            if(!ofScrn.intersects(bnd))continue;
            final var scaled = bnd.scale(this.scaler);
            scaled.setLocation(
                    scaled.x - delta.x,
                    scaled.y - delta.y);
            entity.paint(grr, scaled);
        }}
        grr.setClip(oldC);
        final var rct = this.some.bounds.scale(this.scaler);
        rct.setLocation(rct.x - delta.x, rct.y - delta.y);
        this.some.paint(grr, rct);
        
    }
    @Override public synchronized void resized(Dimension containerSize) {
        // default behavior..
        this.panelView.setSize(
                containerSize.width - (this.panelView.x*2),
                containerSize.height - (this.panelView.y*2)
        );
        this.realView.setRange(
                ((float)this.panelView.width/2F)/this.scaler, 
                ((float)this.panelView.height/2F)/this.scaler
        );
    }
    @Override public synchronized void scales(float scaleFactor) {
        final float noise = 1e-3F;
        if(scaleFactor <= noise || Math.abs(this.scaler - scaleFactor) < noise)return;
        this.scaler = scaleFactor;
    }
    static class Sit extends Entity<Sit>{
        private final BasicStroke stroke = new BasicStroke(5);
        private Boundary bounds;
        Sit(Boundary neo){
            this.bounds = neo;
        }
        @Override public Boundary getBounds() {
            return this.bounds;
        }
        @Override public void paint(Graphics2D grr, Rectangle adjusted) {
            grr.setColor(Color.ORANGE);
            grr.setStroke(stroke);
            grr.draw(adjusted);
        }
        @Override public void updating(int incremental) {
            
        }
    }
}