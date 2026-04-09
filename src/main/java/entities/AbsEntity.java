/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package entities;

/**
 * <p>
 * The root of all entities, it has two main-direct subclasses (Bounded and Entity),
 * if another subclass directly extends this,
 * mainly it's for invoking events not related to bounds nor painting.
 * </p>
 * @author rash4
 */
public abstract class AbsEntity<T extends AbsEntity<T>> {
    public T self(){return (T)this;}
}