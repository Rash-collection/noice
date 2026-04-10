/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package noice.interact.scenes;

import noice.entities.Entity;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;
import noice.entities.Consumable;
import noice.entities.Exhaustible;
import noice.interact.Channel;
import noice.interact.InputsAdapt;
import noice.interact.Inpux;
import noice.utils.Victory;

/**
 *
 * @author rash4
 */
public class SceneManager<T extends SceneManager<T, IN>, IN extends Inpux<IN>> 
        extends Scene<T, IN> {
    private static int scenes_count = 0;
    protected final Viewer view;
    private final List<Entity<?>> petties = new ArrayList<>();
    public SceneManager(){
        // not necessarily needed @@! ..I mean the whole constructor.
        super();
        this.view = new Viewer(new Rectangle());
    }
    public SceneManager(int x, int y, int w, int h, SceneAdapter adapt){
        this.setAdapter(adapt);
        this.inputs = ((IN)new InputsAdapt()).self();
        this.view = new Viewer(x, y, w, h);
    }
    public SceneManager(Rectangle scrnBnds, IN inputs){
        this.setDefAdapter();
        this.view = new Viewer(scrnBnds);
        super.inputs = inputs;
    }
    public SceneManager(Rectangle scrnBounds, IN inputs, SceneAdapter adapt){
        this.setAdapter(adapt);// scene functionals
        this.inputs = inputs;  // scene inputs
        this.view = new Viewer(scrnBounds); // scene view
    }
    public Viewer getView(){return this.view;}
    public List<Entity<?>> getPetties(){
        synchronized(this.petties){
            return this.petties;
        }
    }
    protected void moveView(float deltaX, float deltaY){
        synchronized(this.view){
            this.view.realView.moveBy(deltaX, deltaY);
        }
    }
    protected void moveView(Victory delta){
        synchronized(this.view){
            this.view.realView.moveBy(delta);
        }
    }
    protected T setDefPainter(){
        return super.setPainter(grr->{
            // synchronized inside this method.
            this.view.painting(grr, this.petties);
        });
    }
    protected T setDefUpdater(){
        return super.setUpdater(crr->{
            synchronized(this.petties){
                for(var elt : this.petties)elt.updating(crr);
                // the remove check is totally separated from the heavy update-method
                this.petties.removeIf(ent->{
                    return switch(ent){
                        case Consumable cons->cons.consumed();
                        case Exhaustible exhu->exhu.exhausted(crr);
                        default ->false;
                    };
                });
            }
        });
    }
    protected T setDefScaler(){
        return super.setScaler(frr->{
            this.view.scales(frr);
        });
    }
    protected T setDefResizer(){
        return super.setResizer(siz->{
            this.view.resized(siz);
        });
    }
    protected final T setDefAdapter(){
        return this.setDefPainter().setDefResizer().setDefScaler().setDefUpdater();
    }
    // return the name to get registered in the channel-map.
    protected String onInitialize(){return String.format(("Scene(%02d)"), ++scenes_count);}
    @Override public synchronized T initialize() {
        final String name = this.onInitialize();
        Channel.add(name, this.self());
        return this.self();
    }
    @Override public synchronized void dispose() {
        Channel.remove(this.self());
        super.setPainter(null).setUpdater(null).setResizer(null).setScaler(null);
        super.inputs.removeit(); // makes sure it's unregistered.
        super.inputs = null;
        this.petties.clear();
    }
}