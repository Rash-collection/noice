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
public interface Exhaustible {
    // the condition to remove an entity (based on some countdown)
    boolean exhausted(int updatesCounter);
}