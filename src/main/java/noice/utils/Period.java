/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package noice.utils;

/**
 *
 * @author rash4
 */
public record Period(float start, float end) implements Comparable<Period>{
    public Period{
        if(Math.abs(start - end) < 1e-6f)
            throw new IllegalArgumentException("Period cannot be zero-length"); // or remove it???
    }
    public Period(Period neo){
        this(neo.start, neo.end);
    }
    public Period add(float start, float end){
        return new Period(this.start + start, this.end + end);
    }
    public Period add(Period neo){
        return this.add(neo.start, neo.end);
    }
    public Period sub(float start, float end){
        return new Period(this.start - start, this.end - end);
    }
    public Period sub(Period neo){
        return this.sub(neo.start, neo.end);
    }
    public Period mul(float factor){
        return new Period(this.start * factor, this.end * factor);
    }
    public Period div(float factor){
        return new Period(this.start / factor, this.end / factor);
    }
    public float period(){
        return this.end - this.start;
    }
    public float abs(){
        return Math.abs(this.period());
    }
    @Override public int hashCode(){
        return 31*Float.hashCode(this.start) + Float.hashCode(this.end);
    }
    @Override public boolean equals(Object o){
        if(this == o)return true;
        if(!(o instanceof Period other))return false;
        return Float.compare(this.start, other.start) == 0 &&
                Float.compare(this.end, other.end) == 0;
    }
    @Override public int compareTo(Period o){
        return Float.compare(this.period(), o.period()); // based on length
    }
    @Override public String toString(){
        return "Period(start=" + this.start + ", end=" + this.end + "):length[" + this.period() + "]";
    }
}