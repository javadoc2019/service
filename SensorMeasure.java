/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package esame;


import java.time.LocalDate;


/**
 *
 * @author Paolo
 */
public class SensorMeasure extends Measure {
    private final LocalDate  Time;
    private final int SensorID;

    public SensorMeasure(LocalDate Time, int SensorID, double Value, String Description, String Unit) {
        super(Value, Description, Unit);
        this.Time = Time;
        this.SensorID = SensorID;
    }

    public LocalDate getTime() {
        return Time;
    }

    public int getSensorID() {
        return SensorID;
    }

    @Override
    public String toString() {
        return  "Time=" + Time + ", SensorID=" + SensorID + ", "+ super.toString();
    }
    
    
}
