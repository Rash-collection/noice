/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package noice.interact;

import noice.BasicStatics;

/**
 *
 * @author rash4
 */
public class Channel {
    /**the number that safely divide on these numbers (1, 2, 3, ..., 16).*/
    final static int CEIL = 720720;
    static volatile int incremental = 0;
    
    static volatile float scaleFactor = 1.0F;
    
    /**The only CAZA-nova object, frame and panel set as contentPane.*/
    final static Visu CAZA = new Visu(BasicStatics.APP_NAME, 760, 600);
    
    public static void disposeCaza(int operatiOnClose){
        CAZA.dispose(operatiOnClose);
    }
    public static void resizing(java.awt.Dimension size){
        
    }
    public static void painting(java.awt.Graphics2D grr){
        
    }
    public static void scales(){
        // pass the scalling factor
        
    }
    public static void updating(){
        if((++incremental) > CEIL)incremental = 0;
        // whatever next..pass the incremental variable
        
    }
    public static void repaints(){CAZA.panel.repaint();}
    public static void initalize(){
        CAZA.setPaints(Channel::painting);
        CAZA.setResizing(Channel::resizing);
        CAZA.initialize();
    }
}