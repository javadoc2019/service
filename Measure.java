/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package esame;

/**
 *
 * @author Paolo
 */
public class Measure {
    private final double Value;
    private final String Description;
    private final String Unit;

    public Measure(double Value, String Description, String Unit) {
        this.Value = Value;
        this.Description = Description;
        this.Unit = Unit;
    }

    public double getValue() {
        return Value;
    }

    public String getDescription() {
        return Description;
    }

    public String getUnit() {
        return Unit;
    }  

    @Override
    public String toString() {
        return "Value=" + Value + ", Description=" + Description + ", Unit=" + Unit;
    }
    
    
}
