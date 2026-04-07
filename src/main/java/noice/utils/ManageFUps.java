/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package noice.utils;

import java.util.Objects;
import noice.interact.Loop;

/**
 *
 * @author rash4
 */
public final class ManageFUps {
    private final static double NANOS = 1_000_000_000.0d;
    
    private int perSecond = 0x783C;
    private int realResults = 0;
    private final Loop loop;
    
    public ManageFUps(Loop loop){
        this.loop = Objects.requireNonNull(loop);
    }
    
    public int fps(){return this.perSecond & 0xFF;}
    public int ups(){return (this.perSecond >> 8) & 0xFF;}
    public synchronized int realFPS(){return this.realResults & 0xFF;}
    public synchronized int realUPS(){return (this.realResults >> 8) & 0xFF;}
    
    public synchronized void update(int fps, int ups){
        this.realResults = ((ups & 0xFF) << 8) | (fps & 0xFF);
    }
    public ManageFUps setFPS(int fps){
        if(fps < 30 || fps > 0xFF)throw new IllegalArgumentException(
                "param-value must be (30 <= fps < 256)");
        final boolean runs = this.loop.isRunning();
        if(runs) this.loop.stops();
        this.perSecond &= 0xFF00;
        this.perSecond |= fps & 0xFF;
        if(runs)this.loop.start();
        return this;
    }
    public ManageFUps setUPS(int ups){
        if(ups < 30 || ups > 0xFF)throw new IllegalArgumentException(
                "param-value must be (30 <= ups < 256)");
        final boolean runs = this.loop.isRunning();
        if(runs) this.loop.stops();
        this.perSecond &= 0xFF;
        this.perSecond |= (ups << 8) & 0xFF00;
        if(runs)this.loop.start();
        return this;
    }
    public double timePerUpdate(){
        return NANOS / this.ups();
    }
    public double timePerFrame (){
        return NANOS / this.fps();
    }
    @Override public String toString(){
        return String.format(("%s(%d){FPS[%03d]||UPS[%03d]}"),
                this.getClass().getSimpleName(), this.perSecond, this.fps(), this.ups());
    }
}