/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package noice.interact.scenes;

import java.awt.Dimension;
import java.awt.Graphics2D;
import noice.interact.Channel;
import noice.interact.Inpux;
import noice.interact.scenes.functionals.*;

/**
 *
 * @author rash4
 */
public abstract class Scene<T extends Scene<T, IN>, IN extends Inpux<IN>> extends SceneAdapter{
    private Scalable scale;
    private Paintable paint;
    private Resizable resize;
    private Updatable update;
    protected IN inputs;
    public Scene(){
        this.setAdapter(new SceneAdapter());
        this.inputs = (IN) new Inpux<IN>(){};
    }
    @SuppressWarnings("LeakingThisInConstructor")
    public Scene(String title){
        this();
        Channel.add(title, this);
    }
    public Scene(SceneAdapter adapter){
        this.setAdapter(adapter);
    }
    public Scene(IN inputs){
        this.inputs = inputs;
        this.setAdapter(new SceneAdapter());
    }
    public Scene(SceneAdapter adapter, IN inputs){
        this.setAdapter(adapter);
        this.inputs = inputs;
    }
    @SuppressWarnings("LeakingThisInConstructor")
    public Scene(String title, SceneAdapter adapter, IN inputs){
        this(adapter, inputs);
        Channel.add(title, this);
    }
    public T self(){return (T)this;}
    
    public boolean hasInputs() {return this.inputs != null;}
    public boolean hasScaler() {return this.scale  != null;}
    public boolean hasPainter(){return this.paint  != null;}
    public boolean hasResizer(){return this.resize != null;}
    public boolean hasUpdater(){return this.update != null;}
    
    protected Scalable  getScaler() {return this.scale;}
    protected Paintable getPainter(){return this.paint;}
    protected Updatable getUpdater(){return this.update;}
    protected Resizable getResizer(){return this.resize;}
    
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
    
    public T setInputs(IN inputs){
        this.inputs = inputs;
        return this.self();
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
    public final T registerInputs(){
        if(this.inputs == null)return this.self();
        this.inputs.register();
        return this.self();
    }
    public final T removeInputs(){
        if(this.inputs == null)return this.self();
        this.inputs.removeit();
        return this.self();
    }
    public abstract T initialize();
    public abstract void dispose();
}