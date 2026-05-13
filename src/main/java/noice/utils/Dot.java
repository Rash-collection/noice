/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package noice.utils;

/**
 *
 * @author rash4
 */
public record Dot(float dot) implements Comparable<Dot>{
    public Dot{
    }
    public Dot(Dot neo){
        this(neo.dot);
    }
    public Dot add(Dot neo){
        return new Dot(this.dot + neo.dot);
    }
    public Dot add(float dud){
        return new Dot(this.dot + dud);
    }
    public Dot sub(Dot neo){
        return new Dot(this.dot - neo.dot);
    }
    public Dot sub(float dud){
        return new Dot(this.dot - dud);
    }
    public Dot mul(Dot neo){
        return new Dot(this.dot * neo.dot);
    }
    public Dot mul(float dud){
        return new Dot(this.dot * dud);
    }
    public Dot div(Dot neo){//let it throw XD
        return new Dot(this.dot / neo.dot);
    }
    public Dot div(float dud){// let it throw XD
        return new Dot(this.dot / dud);
    }
    public Dot negated(){
        return new Dot(this.negate());
    }
    public float negate(){
        return this.dot * -1;
    }
    
    public int toInt(){return Math.round(this.dot);}
    public double toDouble(){return (double)this.dot;}
    
    @Override public int compareTo(Dot o) {
        return Float.compare(this.dot, o.dot);
    }
    @Override public boolean equals(Object o){
        if(this == o) return true;
        if(!(o instanceof Dot other))return false;
        return Float.compare(this.dot, other.dot) == 0;
    }
    @Override public int hashCode(){
        return Float.hashCode(this.dot);
    }
    @Override public String toString(){return "Dot(" + this.dot + ")";}
}