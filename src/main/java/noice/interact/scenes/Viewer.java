/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package noice.interact.scenes;

import java.awt.Color;
import noice.entities.Entity;
import java.awt.Dimension;
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
    float scaler = 1F;
    protected final Boundary realView;
    protected final Rectangle panelView;
    public Viewer(int x, int y, int w, int h){
        final float hlfW = (float)w/2F, hlfH = (float)h/2F;
        this.panelView = new Rectangle(x, y, w, h);
        this.realView = new Boundary(x+hlfW, y+hlfH, hlfW, hlfH);
    }
    public Viewer(Rectangle rect){
        final float hlfW = (float)rect.width/2F, hlfH = (float)rect.height/2F;
        this.panelView = rect;
        this.realView = new Boundary(rect.x+hlfW, rect.y+hlfH, hlfW, hlfH);
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
        final var ofScrn = this.realView;
        grr.setColor(Color.GREEN);
        synchronized(entities){for(var entity : entities){
            final var bnd = entity.getBounds();
            if(!ofScrn.intersects(bnd))continue;
            final var scaled = bnd.scale(this.scaler);
            scaled.setLocation(
                    scaled.x - delta.x,
                    scaled.y - delta.y);
            entity.paint(grr, scaled);
        }}
    }
    @Override public synchronized void resized(Dimension containerSize) {
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
}