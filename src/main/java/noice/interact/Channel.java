/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package noice.interact;

import java.util.HashMap;
import noice.interact.scenes.Scene;

/**
 *
 * @author rash4
 */
public class Channel {
    /**the number that safely divide on these numbers (1, 2, 3, ..., 16).*/
    final static int CEIL = 720720;
    static volatile int incremental = 0;
    
    static volatile float scaleFactor = 1.0F;
    
    private final static ChainChan CHAN = new ChainChan();
    /**The only CAZA-nova object, frame and panel set as contentPane.*/
    final static Visu CAZA = new Visu(BasicStatics.APP_NAME, 760, 600);
    
    final static Loop LOOP = new Loop();
    
    final static java.util.Map<String, Scene<?,?>> SCENES = new HashMap<>();
    
    private static Scene<?,?> currentScene;
    
    public static ChainChan add(String name, Scene<?,?> scene){
        if(name == null || name.isBlank())System.out.println("invalid name (key).");
        final boolean firstScene = SCENES.isEmpty();
        if(scene == null)
            System.out.println("scene is null can't be added to the map 'SCENES'");
        else synchronized(SCENES){
            SCENES.putIfAbsent(name, scene);
            if(firstScene){
                currentScene = scene.registerInputs();
                titleSwitch(name);
            }
        }
        return CHAN;
    }
    public static ChainChan remove(Scene<?,?> scene){
        if(scene == null) return CHAN;
        if(SCENES.size() < 2 || !SCENES.containsValue(scene))return CHAN;
        final boolean running = LOOP.isRunning();
        synchronized(SCENES){
            StringBuilder title = new StringBuilder();
            SCENES.entrySet().removeIf(el->{
                final var elem = el.getValue();
                final boolean remove = elem == scene;
                if(remove) title.append(el.getKey());
                return remove;
            });
            if(currentScene == scene){
                if(running)LOOP.stops();
                currentScene = SCENES.values().iterator().next().registerInputs();
                titleSwitch(title.toString());
                if(running)LOOP.start();
            }
        }return CHAN;
    }
    public static void titleSwitch(String title){
        CAZA.frame.setTitle(BasicStatics.APP_NAME + "-" + title);
    }
    public static void switchScene(String name){
        if(name == null || name.isBlank()){
            System.out.println("invalid name (" + name + ").");
            return;
        }
        final var neo = SCENES.get(name);
        if(neo == null){
            System.out.println("scene '" + name + "' is not available.");
            return;
        }
        // dispose (unregister inputs)
        currentScene.removeInputs();
        // register new scene's inputs
        currentScene = neo.registerInputs();
        titleSwitch(name);
    }
    public static void disposeCaza(int operatiOnClose){
        CAZA.dispose(operatiOnClose);
    }
    public static void resizing(java.awt.Dimension size){
        currentScene.resized(size);
    }
    public static void painting(java.awt.Graphics2D grr){
        currentScene.painting(grr);
    }
    public static void scales(){
        // pass the scalling factor
        currentScene.scales(scaleFactor);
    }
    public static void updating(){
        if((++incremental) > CEIL)incremental = 0;
        // whatever next..pass the incremental variable
        currentScene.updating(incremental);
    }
    public static void repaints(){CAZA.panel.repaint();}
    public static void initalize(){
        CAZA.setPaints(Channel::painting);
        CAZA.setResizing(Channel::resizing);
        CAZA.initialize();
        LOOP.start();
    }
    private static class ChainChan{
        public ChainChan add(String name, Scene<?,?> scene){
            return Channel.add(name, scene);
        }
        public ChainChan remove(Scene<?,?> scene){
            return Channel.remove(scene);
        }
    }
}