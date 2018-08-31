/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package espol.edu.ec.main;

import espol.edu.ec.tda.juegoBacon;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;


/**
 *
 * @author SSAM
 */
public class Main extends Application{

    @Override
    public void start(Stage stage) {
        stage.setScene(new Scene(new JuegoPane().getRoot()));
        stage.show();
    }

    public static void main(String[] args) {
        juegoBacon j = new juegoBacon();
        
        long star = System.currentTimeMillis();
//        j.relacionarL();
//        long end = System.currentTimeMillis();
//        System.out.println("Relacion lista: "+(end-star)+ " milisegundos");
        
        
        //star = System.currentTimeMillis();
        j.relacionarM();
        long end = System.currentTimeMillis();
        System.out.println("Relacion mapa: "+(end-star)+ " milisegundos");
        
        launch();
    }
}
