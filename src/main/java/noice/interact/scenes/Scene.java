/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package noice.interact.scenes;

import java.awt.Dimension;
import java.awt.Graphics2D;
import noice.interact.Inpux;
import noice.interact.Visu;
import noice.interact.scenes.functionals.*;

/**
 *
 * @author rash4
 */
public class Scene<T extends Scene<T>> extends SceneAdapter{
    private Scalable scale;
    private Paintable paint;
    private Resizable resize;
    private Updatable update;
    private Inputz inputs;
    public Scene(noice.interact.Visu vis){
        this.setAdapter(new SceneAdapter());
        this.inputs = new Inputz(vis);
    }
    public Scene(SceneAdapter adapter, noice.interact.Visu vis){
        this(vis);
        this.setAdapter(adapter);
    }
    
    public T self(){return (T)this;}
    
    public T initialize(){
        this.inputs.initialize();
        return this.self();
    }
    public void dispose(){
        this.inputs.dispose();
        this.inputs = null;
    }
    
    public boolean hasScaler() {return this.scale  != null;}
    public boolean hasPainter(){return this.paint  != null;}
    public boolean hasResizer(){return this.resize != null;}
    public boolean hasUpdater(){return this.update != null;}
    
    @Override public void painting(Graphics2D grr) {
        this.paint.painting(grr);
    }
    @Override public void resized(Dimension containerSize) {
        this.resize.resized(containerSize);
    }
    @Override public void scales(float scaleFactor) {
        this.scale.scales(scaleFactor);
    }
    @Override public void updating(int incremental) {
        this.update.updating(incremental);
    }
    
    public T setScaler(Scalable scaler){
        this.scale = scaler;
        return this.self();
    }
    public T setPainter(Paintable painter){
        this.paint = painter;
        return this.self();
    }
    public T setUpdater(Updatable updater){
        this.update = updater;
        return this.self();
    }
    public T setResizer(Resizable resizer){
        this.resize = resizer;
        return this.self();
    }
    public final T setAdapter(SceneAdapter adapter){
        return this
                .setScaler(adapter::scales)
                .setResizer(adapter::resized)
                .setPainter(adapter::painting)
                .setUpdater(adapter::updating);
    }
    protected class Inputz<U extends Inputz<U>> extends Inpux<U>{
        public Inputz(Visu container) {
            super(container);
        }
        @Override public U initialize() {
            return this.self();
        }
    }
}