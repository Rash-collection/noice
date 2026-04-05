/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package noice.interact;

import noice.interact.Channel;

/**
 *
 * @author rash4
 */
public class Loop implements Runnable{
    final static double NANOS = 1_000_000_000.0d;
    public Loop(){
        this.running = false;
    }
    public void toggle(){
        if(this.running)this.stops();
        else            this.start();
    }
    public boolean isRunning(){return this.running;}
    public synchronized void start() {
        if (this.running) return;
        this.running = true;
        this.runner = new Thread(this, "Loop");
        this.runner.start();
    }
    public synchronized void stops() {
        if (!this.running) return;
        this.running = false;
        if (Thread.currentThread() != this.runner) {
            try {
                this.runner.join();
            } catch (InterruptedException ignored) {}
        }
    }
    @Override public void run(){
        final double timePerFrame = NANOS / 60D; // TEMPO
        final double timePerUpdate = NANOS / 90D; // TEMPO
        
        long previousTime = System.nanoTime();
        
        long lastCheck = System.currentTimeMillis();
        double deltaU = 0;
        double deltaF = 0;
        
        int realFPS = 0;
        int realUPS = 0;
        long currentTime;
        try{while(this.running){
            deltaF += ((currentTime = System.nanoTime()) - previousTime) / timePerFrame;
            deltaU += (currentTime - previousTime) / timePerUpdate;
            previousTime = currentTime;
            
            // for the UPDATE
            while(deltaU >= 1){// to handle lags XD
                Channel.updating();
                realUPS++;
                deltaU--;
            }
            
            // for the REPAINT
            if(deltaF >= 1){
                Channel.repaints();
                realFPS++;
                deltaF--;
            }
            if(System.currentTimeMillis() - lastCheck >= 1000){
                lastCheck = System.currentTimeMillis();
                System.out.println("FPS: " + realFPS + " || UPS: " + realUPS);
//                config.Setting.PERSONALIZE.setRealFPS(realFPS);
//                config.Setting.PERSONALIZE.setRealUPS(realUPS);
                realFPS = realUPS = 0;
            }
            Thread.yield();
        }}catch(Exception ex){
            Loop.this.stops();
            System.out.println(">> Due to Thread-loop exception, the loop has been forcefully STOPPED.");
            System.out.println("> SOME EXCEPTION :\n\t>>" + ex.getMessage());
            ex.printStackTrace();
        }catch(Error er){
            System.out.println("> SEVER ERROR :\n\t>>" + er.getMessage());
            er.printStackTrace();
            Channel.disposeCaza(javax.swing.JFrame.EXIT_ON_CLOSE);// this handles the thread stops() first.
        }
    }
    
    private volatile boolean running;
    private Thread runner;
}