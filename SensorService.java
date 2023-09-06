/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package esame;

import java.time.LocalDate;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

/**
 *
 * @author Paolo
 */
public class SensorService extends Service {
    private HubQueue buffer;
    private int sensorID;

    public SensorService(HubQueue buffer, int sensorID) {
        this.buffer = buffer;
        this.sensorID = sensorID;
    }

    @Override
    protected Task<Void> createTask() {
        return new Task<Void>(){
            @Override
            protected Void call() throws Exception {
                for(int i=0; i<=10000;i++){
                    if(this.isCancelled()){
                        break;
                    }
               
                    double value = Math.round(Math.random()*100);
                    SensorMeasure s = new SensorMeasure(LocalDate.now(), sensorID, value, "PM10", "µg/m");
                    buffer.add(s);      
                    Thread.sleep((long)Math.random()*5000);
                }
                return null;
            }
            
        };
    }
}
