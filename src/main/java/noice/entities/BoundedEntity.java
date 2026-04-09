/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package noice.entities;

/**
 * usually extended for event's kinda entity.
 * @author rash4
 */
public abstract class BoundedEntity<T extends BoundedEntity<T>> extends AbsEntity<T>
        implements Boundable{
    
}