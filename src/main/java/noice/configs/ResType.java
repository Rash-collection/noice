/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package noice.configs;

/**
 *
 * @author rash4
 */
public enum ResType {
    Texts("/texts/"),
    Image("/media/images/"),
    Audio("/media/audios/"),
    Video("/media/videos/"),
    ;
    private ResType(String track){
        this.TRACK = track;
    }
    final String TRACK;
    public String rootTrack(){return this.TRACK;}
}