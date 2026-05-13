/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package debug;

import graphicker.Kolor;
import graphicker.MageCons;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
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
    private final static int COLS = 9;
    private final static String DISPLAY = "Display";
    private final static BufferedImage TILE_GROUND = background();
    private final static BufferedImage SELECTED_BACK = frame();
    private LocoTity gritta;
    private volatile LocoTity currentSelected = null;
    private Display(int x, int y, int w, int h){
        super(x, y, w, h);
        super.setInputs(new Inputs()).setDefAdapter();
    }
    @Override protected final String onInitialize(){
        this.griddy(300);
        this.setDefAdapter();
        final var pnt = this.getPainter();
        this.setPainter(grr->{
            pnt.painting(grr);
            if(this.currentSelected != null)
                this.currentSelected.paint(grr, null);
        });
        final var ups = this.getUpdater();
        this.setUpdater(inc->{
            Display.this.updateInputs();
            ups.updating(inc);
        });
        for(var cok : Assetz.cookies()){
            this.add(cok);
        }
        return DISPLAY;
    }
    public boolean hasSpace(){
        final var pts = this.getPetties();
        if(pts.isEmpty())return false;
        synchronized(pts){
            for(var pet : pts){
                if(pet instanceof LocoTity tit &&
                        !tit.hasSprite())return true;
            }
        }return false;
    }
    public Display add(BufferedImage img){
        if(img == null)return this.self();
        final var pts = this.getPetties();
        if(!this.hasSpace()){
            final int newMax = pts.size() + COLS;
            synchronized(pts){while(LocoTity.counter < newMax){
                final int x = (LocoTity.counter%COLS),
                          y = (LocoTity.counter/COLS),
                          xx = x*LocoTity.HIT,
                          yy = y*LocoTity.WID;
                final String nn = String.format(("%04d[%02d:%02d]"), LocoTity.counter + 1, y+1, x+1);
                final var item = new LocoTity(xx, yy);
                item.setImage(graphicker.MageCons.printOn(LocoTity.WID, LocoTity.HIT, grr->{
                    grr.drawImage(TILE_GROUND, 0, 0, LocoTity.WID, LocoTity.HIT, null);
                    grr.setFont(LocoTity.BAH);
                    grr.drawString(nn, 65, 145);
                }));
                this.add(item);
            }}
        }
        synchronized(pts){
            for(var pet : pts){
                if(pet instanceof LocoTity tit && !tit.hasSprite()){
                    tit.setImage(img);
                    return this.self();
                }
            }
        }
        return this.self();
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
    private void selectPetty(Point selector, boolean neutralize){
        if(selector == null)return;
        final Victory select = (neutralize)?
                this.view.screenToWorld(selector):
                new Victory(selector.x, selector.y);
        System.out.println("here selecting");
        int index = -1;
        synchronized(this.getPetties()){for(var et : this.getPetties()){
            index++;
            final int intx = index;
            if(et.getBounds().contains(select)){
                this.currentSelected = new LocoTity(0, 0, LocoTity.WID, LocoTity.HIT){
                    public BufferedImage baky = makeBack();
                    @Override public BufferedImage currentImage(){return ((LocoTity)et).currentImage();}
                    @Override public void paint(Graphics2D grr, Rectangle NULL){
                        grr.drawImage(this.baky, 0, 0, null);
                        grr.drawImage(this.currentImage(), 2, 2, null);
                        
                    }
                    public BufferedImage makeBack(){
                        final var bah = Display.SELECTED_BACK;
                        final int wid = bah.getWidth(), hit = bah.getHeight();
                        final var img = new BufferedImage(wid, hit, 2);
                        final var grr = img.createGraphics();
                        grr.drawImage(bah, 0, 0, null);
                        grr.setFont(LocoTity.BAH);
                        final var loco = String.format(("Item index [%03d]"), intx);
                        grr.setStroke(new BasicStroke(4));
                        grr.drawString(loco, 3, hit - 30);
                        grr.dispose();
                        return img;
                    }
                };
                System.out.println("selected " + this.currentSelected.bounds.toString());
                return;
            }
        }}this.currentSelected = null;
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
                case KeyEvent.VK_ESCAPE ->Channel.disposeCaza(3);
                case KeyEvent.VK_P      ->Channel.toggle();
                
                case KeyEvent.VK_UP     ->Display.this.getView().moveScreen(0, -move());
                case KeyEvent.VK_DOWN   ->Display.this.getView().moveScreen(0, +move());
                case KeyEvent.VK_RIGHT  ->Display.this.getView().moveScreen(+move(), 0);
                case KeyEvent.VK_LEFT   ->Display.this.getView().moveScreen(-move(), 0);
                
                case KeyEvent.VK_ADD        ->Channel.addScale(+0.125F);
                case KeyEvent.VK_SUBTRACT   ->Channel.addScale(-0.125F);
            }
        }
        // down = nigative
        // right = nigative
        // zoom-in = nigative
        // but here its best as is XD
        @Override public void mouseWheelMoved(MouseWheelEvent e){
            final float delta = (float)(e.getPreciseWheelRotation()*MOV);
            if(e.isControlDown()){
                Display.this.view.zoomAt(e.getPoint(), e.getPreciseWheelRotation()>0);
            }else if(e.isShiftDown())
                Display.this.view.moveScreen(delta, 0);
            else Display.this.view.moveScreen(0, delta);
        }
        @Override public void mouseClicked(MouseEvent e){
            if(e.getButton() == MouseEvent.BUTTON1)this.rightClicked(e);
        }
        private void rightClicked(MouseEvent e){
            Display.this.selectPetty(e.getPoint(), true);
        }
    }
    private static BufferedImage frame(){
        final int zis = 310, pad = 4;
        return MageCons.printOn(zis, zis, grr->{
            grr.setColor(Color.GRAY);
            grr.fillRect(0, 0, zis, zis);
            grr.setColor(Color.DARK_GRAY);
            grr.fillRect(pad, pad, zis - (pad*2), zis - (pad*2));
        });
    }
    private static BufferedImage background(){
        final int sidX = 10, sidY = 16, ety = 80, h = ety * sidY, w = ety * sidX;
        return MageCons.printOn(w, h, grr->{
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
    private void griddy(int items){
        final var img = TILE_GROUND;
        this.gritta = new LocoTity(-LocoTity.WID, -LocoTity.HIT, LocoTity.WID, LocoTity.HIT){
            private int dex = 0, ind = 0;
            private final BufferedImage[] sheet = shet();
            private BufferedImage[] shet(){
                final int len = 4;
                final BufferedImage[] arr = new BufferedImage[len];
                for(int i = 0; i < len; i++){
                    final Color clr = switch(i){
                        case 0->Color.GREEN;
                        case 1->Color.RED;
                        case 2->Color.BLUE;
                        case 3->new Color(Kolor.PURPLE_NEON);
                        default->null;
                    };
                    arr[i] = MageCons.printOn(LocoTity.WID, LocoTity.HIT, grr->{
                        grr.setColor(clr);
                        grr.fillRoundRect(0, 0, LocoTity.WID, LocoTity.HIT, 64, 64);
                    });
                }
                return arr;
            }
            @Override public BufferedImage currentImage(){
                return this.sheet[this.ind];
            }
            @Override public void updating(int inc){
                if((inc%Channel.PSPS.ups()) == 0){
                    if((++this.dex) > 3)this.dex = -2;
                    this.ind = Math.abs(this.dex);
                }
            }
        };this.add(this.gritta);
        while(LocoTity.counter < items){
            final int x = (LocoTity.counter%COLS),
                      y = (LocoTity.counter/COLS),
                      xx = x*LocoTity.HIT,
                      yy = y*LocoTity.WID;
            final String nn = String.format(("%04d[%02d:%02d]"), LocoTity.counter + 1, y+1, x+1);
            final var item = new LocoTity(xx, yy);
            item.setImage(graphicker.MageCons.printOn(LocoTity.WID, LocoTity.HIT, grr->{
                grr.drawImage(img, 0, 0, LocoTity.WID, LocoTity.HIT, null);
                grr.setFont(LocoTity.BAH);
                grr.drawString(nn, 65, 145);
            }));
            if(LocoTity.counter == 1)item.setImage(MageCons.printOn(256, 256, grr->{
                grr.setColor(Color.BLUE);
                grr.fillRoundRect(0, 0, 256, 256, 48, 48);
            }));
            this.add(item);
        }
    }
    
    class LocoTity extends Entity<LocoTity> implements Animatable{
        private final static Font BAH = new Font(Font.MONOSPACED, Font.BOLD, 20);
        private static int counter = 0;
        private final static int WID = 0x100, HIT = 0xA0;
        Boundary bounds;
        BufferedImage sprite;
        private boolean coupon = false;
        LocoTity(int x, int y, int w, int h){
            final int hlfW = w/2, hlfH = h/2;
            this.bounds = new Boundary(x-hlfW, y-hlfH, hlfW, hlfH);
        }
        LocoTity(int x, int y, int w, int h, BufferedImage img){
            this(x, y, w, h);
            this.sprite = img;
        }
        LocoTity(int x, int y){
            this(x, y, HIT, WID);
            counter++;
        }
        public boolean hasSprite(){return this.coupon;}
        @Override public Boundary getBounds() {
            return this.bounds;
        }
        @Override public void updating(int incremental) {
        }
        void setImage(BufferedImage img){
            if(this.coupon || img == Display.TILE_GROUND)return;
            if(img == null || this.sprite == null){
                this.sprite = img;
                return;
            }
            final int x = 35, y = 16, pad = 4, arc = 20;
            final int wid = this.sprite.getWidth() - (2*x), 
                    hit = this.sprite.getHeight() - (3*y);
            final var grr = this.sprite.createGraphics();
            grr.setColor(Color.BLACK);
            grr.fillRect(x-pad, y-pad, wid+(pad*2), hit+(pad*2));
            grr.setColor(Color.RED);
            grr.fillRoundRect(x, y, wid, hit, arc, arc);
            grr.drawImage(img, x, y, wid, hit, null);
            grr.dispose();
            this.coupon = true;
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
        Channel.remove(DISPLAY);
    }
    public static Display display(int x, int y, int w, int h){
        if(display == null)
            display = new Display(x, y, w, h);
        return display;
    }
}