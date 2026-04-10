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
public interface Consumable {
    // the condition to remove the entity (based on it's resources)
    boolean consumed();
}