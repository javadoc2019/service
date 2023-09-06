/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXML2.java to edit this template
 */
package esame;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Iterator;
import java.util.ResourceBundle;
import java.util.Scanner;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 *
 * @author Paolo
 */
public class FXMLDocumentController implements Initializable {
    
    private HubQueue buffer = new HubQueue(3);
    private SensorService s1 = new SensorService(buffer,1);
    private SensorService s2 = new SensorService(buffer,2);
    private HubService h = new HubService(buffer);
    
    @FXML private Label label;
    @FXML private Button sensor1_button; @FXML private Button sensor2_button; @FXML private Button hub_button; @FXML private Button export_button; @FXML private Button read_button;
    @FXML private ProgressIndicator sensor1_progress; @FXML private ProgressIndicator sensor2_progress;
    @FXML private TableView table_view;  
    @FXML private TableColumn c1; @FXML private TableColumn c2; @FXML private TableColumn c3; @FXML private TableColumn c4; @FXML private TableColumn c5;
    
    FileChooser file_chooser = new FileChooser();
    @FXML
    private void ReadEvent(ActionEvent event) {
        File file = file_chooser.showOpenDialog(new Stage());
     
        try{
            Scanner reader = new Scanner(file);
            System.out.println(reader.nextLine());
        }catch(FileNotFoundException e){
            e.printStackTrace();
        }   
    }
    
    @FXML 
    private void ExportEvent(ActionEvent event){
        File file = file_chooser.showSaveDialog(new Stage());
        String str = "";
        Iterator i = table_view.getItems().iterator();
        while(i.hasNext()){
            SensorMeasure s = (SensorMeasure)i.next();
            str = str + s.toString() + ';'+ '\n';
        }
        
        try{
            FileOutputStream stream = new FileOutputStream(file);
            byte[] b = str.getBytes();
            stream.write(b);
            stream.close();
        }catch(FileNotFoundException e){
            e.printStackTrace();
        }catch(IOException e2){
            e2.printStackTrace();
        }      
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        c1.setCellValueFactory(new PropertyValueFactory("SensorID"));
        c2.setCellValueFactory(new PropertyValueFactory("Time"));
        c3.setCellValueFactory(new PropertyValueFactory("Value"));
        c4.setCellValueFactory(new PropertyValueFactory("Unit"));
        c5.setCellValueFactory(new PropertyValueFactory("Description"));
        
        file_chooser.setTitle("Save File");
        file_chooser.setInitialDirectory(new File("C:\\Users\\Paolo\\Documents\\NetBeansProjects\\ESAME"));
        file_chooser.getExtensionFilters().addAll(
                new ExtensionFilter("Text Files","*.txt")
        );
        //h.setPeriod(Duration.seconds(1));
        
        sensor1_button.pressedProperty().addListener(new ChangeListener<Boolean>(){
            @Override
            public void changed(ObservableValue<? extends Boolean> ov, Boolean t, Boolean t1) {
                if(t1){
                    if(s1.isRunning()){
                        s1.cancel();
                        sensor1_button.setText("Start");
                    }else{
                        s1.reset();
                        s1.start();
                        sensor1_button.setText("Stop");
                    }
                }
            }       
        });
        
        sensor2_button.pressedProperty().addListener(new ChangeListener<Boolean>(){
            @Override
            public void changed(ObservableValue<? extends Boolean> ov, Boolean t, Boolean t1) {
                if(t1){
                    if(s2.isRunning()){
                        s2.cancel();
                        sensor2_button.setText("Start");
                    }else{
                        s2.reset();
                        s2.start();
                        sensor2_button.setText("Stop");
                    }
                }
            }       
        });
        
        hub_button.pressedProperty().addListener(new ChangeListener<Boolean>(){
            @Override
            public void changed(ObservableValue<? extends Boolean> ov, Boolean t, Boolean t1) {
                if(t1){
                    if(h.isRunning()){
                        h.cancel();
                        hub_button.setText("Start");
                    }else{
                        h.reset();
                        h.start();
                        hub_button.setText("Stop");
                    }
                }               
            }       
        });
        
        h.valueProperty().addListener(new ChangeListener<SensorMeasure>(){
            @Override
            public void changed(ObservableValue<? extends SensorMeasure> ov, SensorMeasure t, SensorMeasure t1) {
               ObservableList<SensorMeasure> list = FXCollections.observableArrayList();
               if(t1 != null){
               list.add(t1);
               list.addAll(table_view.getItems());
               table_view.setItems(list);
               }
            }       
        });
        /*
        h.setOnSucceeded(new EventHandler<WorkerStateEvent>(){
            @Override
            public void handle(WorkerStateEvent t) {
               SensorMeasure m= (SensorMeasure) t.getSource().getValue();
               ObservableList<SensorMeasure> list = FXCollections.observableArrayList();
               list.add(m);
               list.addAll(table_view.getItems());
               table_view.setItems(list);
            }
        });
        */
        
       
        sensor1_progress.visibleProperty().bind(s1.runningProperty());
        sensor1_progress.progressProperty().bind(s1.progressProperty());
        sensor2_progress.visibleProperty().bind(s2.runningProperty());
        sensor2_progress.progressProperty().bind(s2.progressProperty());
        export_button.disableProperty().bind(h.runningProperty());
    }    
    
}
