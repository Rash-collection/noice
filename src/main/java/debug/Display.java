/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package debug;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.geom.Path2D;
import java.awt.image.BufferedImage;
import noice.entities.Animatable;
import noice.entities.Entity;
import noice.interact.Channel;
import noice.interact.InputsAdapt;
import noice.interact.scenes.SceneManager;
import noice.utils.Boundary;
import noice.utils.Victory;

/**
 *
 * @author rash4
 */
public class Display extends SceneManager<Display, InputsAdapt>{
    private static volatile Display display;
    private final static int MOV = 0x10;
    private final static String DISPLAY = "Display";
    
    
//    private BufferedImage background;
    private Display(int x, int y, int w, int h){
        super(x, y, w, h);
//        this.background = background();
        super.setInputs(new Inputs()).setDefAdapter();
    }
    @Override protected final String onInitialize(){
        this.griddy(99, 99);
        this.setDefAdapter();
        final var ups = super.getUpdater();
        this.setUpdater(inc->{
            Display.this.updateInputs();
            ups.updating(inc);
        });
        return DISPLAY;
    }
    public Display add(Entity<?> enty){
        if(enty == null)return this.self();
        synchronized(this.getPetties()){
            this.getPetties().add(enty);
        }
        return this.self();
    }
    private void updateInputs(){
        
    }
    private class Inputs extends InputsAdapt{
        private int longPress = 0;
        private float move(){
            final float scl = 1F/Display.this.getVSCL();
            this.longPress++;
            if(this.longPress <= 12){
                return MOV * scl;
            }else if(this.longPress <= 24){
                return (MOV * 1.5F) * scl;
            }else if(this.longPress <= 36){
                return (MOV * 2.2F) * scl;
            }else return (MOV * 2.8F) * scl;
        }
        @Override public void keyReleased(KeyEvent e){
            switch(e.getKeyCode()){
                case KeyEvent.VK_UP, KeyEvent.VK_DOWN,
                        KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT->
                    this.longPress = 1;
                default->{}
            }
        }
        @Override public void keyPressed(KeyEvent e){
            switch(e.getKeyCode()){
                case KeyEvent.VK_ESCAPE->Channel.disposeCaza(3);
                case KeyEvent.VK_UP     ->Display.this.getView().moveScreen(0, -move());
                case KeyEvent.VK_DOWN   ->Display.this.getView().moveScreen(0, +move());
                case KeyEvent.VK_RIGHT  ->Display.this.getView().moveScreen(+move(), 0);
                case KeyEvent.VK_LEFT   ->Display.this.getView().moveScreen(-move(), 0);
                case KeyEvent.VK_ADD        ->Channel.addScale(+0.125F);
                case KeyEvent.VK_SUBTRACT   ->Channel.addScale(-0.125F);
            }
        }
    }
    private static BufferedImage background(){
        final int sidX = 16, sidY = 10, ety = 80, h = ety * sidY, w = ety * sidX;
        return graphicker.MageCons.printOn(w, h, grr->{
            // clock-wise from zero
            final int top = sidX, rht = top + sidY, bot = rht + sidX, lft = bot + sidY;
            grr.setStroke(new BasicStroke(4));
            Victory prv = new Victory(0, 0);
            final var jgdy = new Path2D.Float();
            jgdy.moveTo(prv.x(), prv.y());
            for(int i = 1; i <= lft; i++){
                final Victory next;
                if(i <= top){
                    next = prv.add(+ety, (i%2 == 0)?-ety:+ety);
                }else if(i <= rht){
                    next = prv.add((i%2 == 0)?+ety:-ety, +ety);
                }else if(i <= bot){
                    next = prv.add(-ety, (i%2 == 0)?+ety:-ety);
                }else{
                    next = prv.add((i%2 == 0)?-ety:+ety, -ety);
                }
                jgdy.lineTo(next.x(), next.y());
                prv = next;
            }jgdy.closePath();
            grr.setColor(Color.ORANGE);
            grr.fill(jgdy);
            grr.setColor(Color.GRAY);
            grr.draw(jgdy);
        });
    }
    private void griddy(int cols, int rows){
        final var img = background();
        final int len = cols * rows;
        while(LocoTity.counter < len){
            final int x = (LocoTity.counter%cols),
                      y = (LocoTity.counter/cols),
                      xx = x*LocoTity.WID,
                      yy = y*LocoTity.HIT;
            final String nn = String.format(("[%02d:%02d]"), y+1, x+1);
            final var item = new LocoTity(xx, yy);
            item.setImage(graphicker.MageCons.printOn(LocoTity.WID, LocoTity.HIT, grr->{
                grr.drawImage(img, 0, 0, LocoTity.WID, LocoTity.HIT, null);
                grr.setFont(LocoTity.BAH);
                grr.drawString(nn, 8, 130);
            }));
            this.add(item);
        }
    }
    class LocoTity extends Entity<LocoTity> implements Animatable{
        private final static Font BAH = new Font(Font.MONOSPACED, Font.BOLD, 16);
        private static int counter = 0;
        private final static int WID = 0x100, HIT = 0xA0;
        Boundary bounds;
        BufferedImage sprite;
        LocoTity(int x, int y, int w, int h){
            final int hlfW = w/2, hlfH = h/2;
            this.bounds = new Boundary(x-hlfW, y-hlfH, hlfW, hlfH);
        }
        LocoTity(int x, int y, int w, int h, BufferedImage img){
            this(x, y, w, h);
            this.sprite = img;
        }
        LocoTity(int x, int y){
            this(x, y, WID, HIT);
            counter++;
        }
        @Override public Boundary getBounds() {
            return this.bounds;
        }
//        @Override public void paint(Graphics2D grr, Rectangle adjusted) {
//            grr.drawImage(this.sprite, adjusted.x, adjusted.y,
//                    adjusted.width, adjusted.height, null);
//        }
        @Override public void updating(int incremental) {
        }
        void setImage(BufferedImage img){
            this.sprite = img;
        }

        @Override public BufferedImage currentImage() {
            return this.sprite;
        }
    }
    public static void deleteDisplay(){
        if(display != null){
            display.dispose();
            display = null;
        }
        // just making sure.
        Channel.remove(DISPLAY);
    }
    public static Display display(int x, int y, int w, int h){
        if(display == null)
            display = new Display(x, y, w, h);
        return display;
    }
}