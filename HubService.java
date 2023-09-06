/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package esame;


import javafx.concurrent.ScheduledService;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

/**
 *
 * @author Paolo
 */
/*
public class HubService extends ScheduledService {
    private HubQueue buffer;

    public HubService(HubQueue buffer) {
        this.buffer = buffer;
    }
    
    @Override
    protected Task createTask() {
        return new Task<SensorMeasure>(){
            @Override
            protected SensorMeasure call() throws Exception {
                return buffer.remove();
            }           
        };
    }
    
}
*/
public class HubService extends Service<SensorMeasure> {
    private HubQueue buffer;

    public HubService(HubQueue buffer) {
        this.buffer = buffer;
    }
    
    @Override
    protected Task createTask() {
        return new Task<SensorMeasure>(){
            @Override
            protected SensorMeasure call() throws Exception {
                while(true){
                    if(this.isCancelled()){
                        break;
                    }
                    SensorMeasure a = buffer.remove();
                    this.updateValue(a);
                    Thread.sleep(1000);
                 }                
                return null;
            }           
        };
    }
    
}