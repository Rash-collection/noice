/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package noice.utils;

import java.awt.Dimension;
import java.awt.Point;

/**
 * <p></p>
 * @author rash4
 */
public record Victory(float x, float y) implements Comparable<Victory>{
    /**The neigh <b>ZERO</b>*/
    public final static float NOIZE = 1e-6F;
    public final static Victory ZERO = new Victory(0F, 0F);
    public Victory{}
    /**copy constructor.*/
    public Victory(Victory neo){
        this(neo.x, neo.y);
    }
    
    public boolean isGreater(Victory other) { return this.compareTo(other) > 0; }
    public boolean isLlesser(Victory other) { return this.compareTo(other) < 0; }
    public Victory greater(Victory other)   { return this.compareTo(other) > 0 ? this : other; }
    public Victory lesser(Victory other)    { return this.compareTo(other) < 0 ? this : other; }
    
    public Point toPoint(){return new Point((int)this.x, (int)this.y);} 
    public Dimension toDimension(){return new Dimension((int)this.x, (int)this.y);}
    
    public Victory min(Victory other) {
        return new Victory(Math.min(this.x, other.x), Math.min(this.y, other.y));
    }
    public Victory max(Victory other) {
        return new Victory(Math.max(this.x, other.x), Math.max(this.y, other.y));
    }
    public Victory add(float x, float y){
        return new Victory(this.x + x, this.y + y);
    }
    public Victory add(Victory neo){
        return this.add(neo.x, neo.y);
    }
    public Victory sub(float x, float y){
        return new Victory(this.x - x, this.y - y);
    }
    public Victory sub(Victory neo){
        return this.sub(neo.x, neo.y);
    }
    public Victory scale(float factor){
        return new Victory(this.x * factor, this.y * factor);
    }
    public float distanceTo(float x, float y){
        float deltaX = this.x - x;
        float deltaY = this.y - y;
        final double sqrx = (double)(deltaX * deltaX);
        final double sqry = (double)(deltaY * deltaY);
        return (float)Math.sqrt(sqrx + sqry);
    }
    public float distanceTo(Victory neo){
        return this.distanceTo(neo.x, neo.y);
    }
    public Victory clamp(java.awt.Rectangle rect){
        float nx = Math.max(rect.x, Math.min(this.x, rect.x + rect.width));
        float ny = Math.max(rect.y, Math.min(this.y, rect.y + rect.height));
        return new Victory(nx, ny);
    }
    // 1️⃣ Clamp by components (explicit bounds)
    public Victory clamp(float minX, float maxX, float minY, float maxY){
        float nx = Math.max(minX, Math.min(this.x, maxX));
        float ny = Math.max(minY, Math.min(this.y, maxY));
        return new Victory(nx, ny);
    }
    // 2️⃣ Clamp using two vectors (min ↔ max)
    public Victory clamp(Victory min, Victory max){
        return clamp(min.x, max.x, min.y, max.y);
    }
    // 3️⃣ Clamp from (0,0) → max
    public Victory clamp(Victory max){
        return clamp(0f, max.x, 0f, max.y);
    }
    // 4️⃣ Clamp magnitude (length)
    public Victory clamp(float maxLength){
        if(maxLength < 0) maxLength = 0;
        float len = this.distanceTo(ZERO);
        if(len <= maxLength) return this;
        float scale = maxLength / len;
        return this.scale(scale);
    }
    public Victory normalize(){
        float length = this.distanceTo(0, 0);
        if(length <= NOIZE) return ZERO;
        return new Victory(this.x / length, this.y / length);
    }
    @Override public String toString(){
        return String.format(("%s(x[%.4f]:y[%.4f])"), this.getClass().getSimpleName(), this.x, this.y);
    }
    @Override public int compareTo(Victory neo){
        final int compa = Float.compare(this.x, neo.x);
        if(compa != 0) return compa;
        return Float.compare(this.y, neo.y);
    }
    @Override public boolean equals(Object o){
        if(this == o)return true;
        if(!(o instanceof Victory neo))return false;
        return Math.abs(this.x - neo.x) <= NOIZE && Math.abs(this.y - neo.y) <= NOIZE;
    }
    @Override public int hashCode(){
        return Float.hashCode(Math.round(x / NOIZE)) * 31 + Float.hashCode(Math.round(y / NOIZE));
    }
}