/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package noice.utils;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;

/**
 * <p></p>
 * @author rash4
 */
public class Boundary {
    public Victory center, range;
    public Boundary(Victory center, Victory range){
        this.center = center;
        this.range = range;
    }
    public Boundary(float cx, float cy, float rx, float ry){
        this(new Victory(cx, cy), new Victory(rx, ry));
    }
    
    public boolean contains(float x, float y){
        Victory tl = getTopLeft();
        Victory br = getBotRight();
        return x >= tl.x() && x <= br.x() && y >= tl.y() && y <= br.y();
    }
    public boolean contains(Victory v){
        return this.contains(v.x(), v.y());
    }
    public boolean contains(Point point){
        return this.contains(point.x, point.y);
    }
    public boolean contains(float x, float y, float w, float h){
        return this.getBounds().contains((int)x, (int)y, (int)w, (int)h);
    }
    public boolean contains(Rectangle neo){
        return this.contains(neo.x, neo.y, neo.width, neo.height);
    }
    public boolean contains(Boundary neo){
        Victory tl = getTopLeft();
        Victory br = getBotRight();
        Victory oTL = neo.getTopLeft();
        Victory oBR = neo.getBotRight();
        return oTL.x() >= tl.x() && oBR.x() <= br.x()
            && oTL.y() >= tl.y() && oBR.y() <= br.y();
    }
    
    public boolean intersects(Boundary other){
        Victory tl1 = getTopLeft();
        Victory br1 = getBotRight();
        Victory tl2 = other.getTopLeft();
        Victory br2 = other.getBotRight();
        // Check for no overlap, then invert
        return !(br1.x() < tl2.x() || tl1.x() > br2.x()
              || br1.y() < tl2.y() || tl1.y() > br2.y());
    }
    public boolean intersects(Rectangle neo){
        return this.getBounds().intersects(neo);
    }
    
    public Victory range(){return this.range;}
    public Victory center(){return this.center;}
    
    public Victory getTopLeft(){return this.center.sub(this.range);}
    public Victory getBotRight(){return this.center.add(this.range);}
    
    public Dimension getSize(){return this.getBotRight().sub(this.getTopLeft()).toDimension();}
    public Rectangle getBounds(){
        final var cor = this.getTopLeft();
        final var size = this.getBotRight().sub(cor);
        return new Rectangle(cor.toPoint(), size.toDimension());
    }
    
    public void moveBy(Victory delta){
        this.center = this.center.add(delta);
    }
    public void moveBy(float deltaX, float deltaY){
        this.moveBy(new Victory(deltaX, deltaY));
    }
    public void moveTo(Victory center){
        this.center = center;
    }
    public void moveTo(float cx, float cy){
        this.center = new Victory(cx, cy);
    }
    public void setRange(Victory range){
        this.range = range;
    }
    public void setRange(float rx, float ry){
        this.range = new Victory(rx, ry);
    }
    public void appendRange(float deltaX, float deltaY){
        this.range = this.range.add(deltaX, deltaY);
    }
    public void appendRange(Victory delta){
        this.range = this.range.add(delta);
    }
    // like preventing a vector from overflowing the bounds.
    public Victory clamp(Victory v) {
        // get boundary corners once
        final Victory tl = this.getTopLeft();
        final Victory br = this.getBotRight();
        // clamp components individually
        float cx = Math.max(tl.x(), Math.min(v.x(), br.x()));
        float cy = Math.max(tl.y(), Math.min(v.y(), br.y()));
        return new Victory(cx, cy);
    }
    // nice scaler, for painting objects
    public Rectangle scale(float scaler){
        if(Math.abs(scaler - 1F) < 1e-3)return this.getBounds(); // not changing anything.
        Victory cnt = this.center.scale(scaler),
                rng = this.range.scale(scaler);
        return new Boundary(cnt, rng).getBounds();
    }
    
    @Override public String toString(){
        return String.format(("%s{center-%s, range-%s}"),
                this.getClass().getSimpleName(), this.center.toString(), this.range.toString());
    }
}