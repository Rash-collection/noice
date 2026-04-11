/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package noice;

import debug.Display;
import noice.interact.Channel;

/**
 *
 * @author rash4
 */
public class Noice {
    public static void main(String[] args) {
        final Display dis = Display.display(0, 0, 800, 600);
        dis.initialize();
        Channel.initalize();
        System.out.println("Hello World!");
        
    }
}
