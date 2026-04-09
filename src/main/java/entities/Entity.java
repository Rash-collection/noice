/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package entities;

/**
 * Real entity class, for bounded and paint-able items/objects.
 * @author rash4
 */
public abstract class Entity<T extends Entity<T>> extends BoundedEntity<T> 
        implements Viewable{
    
}