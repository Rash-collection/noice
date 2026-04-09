/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package noice.entities;

/**
 *
 * @author rash4
 */
@FunctionalInterface
public interface Viewable {
    void paint(java.awt.Graphics2D grr, java.awt.Rectangle adjusted);
}