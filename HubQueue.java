/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package esame;

import java.util.LinkedList;


/**
 *
 * @author Paolo
 */
public class HubQueue {
    private  LinkedList<SensorMeasure> buffer;
    private final int hsize;

    public HubQueue( int hsize) {
        this.buffer = new LinkedList<>();
        this.hsize = hsize; 
    }
    
    public synchronized void add(SensorMeasure m){
        if(buffer.size()>=hsize){
            try{
                this.wait();
            }catch(InterruptedException e){
                e.printStackTrace();
            }
        }       
        buffer.addLast(m);      
        this.notifyAll();
    }
    
    public synchronized SensorMeasure remove(){
        if(buffer.isEmpty()){
            try{
                this.wait();
            }catch(InterruptedException e){
                e.printStackTrace();
            }
        }
        SensorMeasure m = buffer.remove(0);    
        this.notifyAll();
        return m;    
    }
}
