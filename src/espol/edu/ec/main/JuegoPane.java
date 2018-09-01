/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package espol.edu.ec.main;

import espol.edu.ec.tda.JuegoBacon;
import java.util.LinkedList;
import java.util.List;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.geometry.Side;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.CustomMenuItem;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;


public class JuegoPane {
    
    private final BorderPane root;
    private final VBox left;
    private final ComboBox<String> algoritmos;
    private final TextField actor;
    private ContextMenu menuSugerencias;
    private final Button empezar;
    private BaconGraph bacon;
    
    List<String> nombresActores;
    
    public JuegoPane() {
        root = new BorderPane();
        left = new VBox();
        algoritmos = new ComboBox<>();
        actor = new TextField();
        empezar = new Button("Empezar");
        leftPane();
        centerPane();
        root.setLeft(left); 
        menuSugerencias = new ContextMenu();
        actor.setContextMenu(menuSugerencias);
        evtEmpezar();
    }
    
    private void leftPane() {
        algoritmos.getItems().addAll("Dijkstra", "BSF");
        ImageView im = new ImageView(
                new Image(JuegoPane.class.getResourceAsStream("/espol/edu/ec/recursos/bacon.gif"), 
                        300, 300, true, true));
        left.setAlignment(Pos.CENTER);
        left.setSpacing(10);
        HBox metodo = crearHBox("Método", algoritmos); 
        HBox act = crearHBox("Actor", actor);
        left.getChildren().addAll(im, metodo, act, empezar);
        left.setPadding(new Insets(60, 60, 60, 60)); 
        leftDisenio();
        
        actor.textProperty().addListener((ObservableValue<? extends String> observableValue, String s, String s2) -> {
            if (actor.getText().length() == 0) {
                menuSugerencias.hide();
            } else {
                try {
                    populatePopup();
                } catch (Exception ex) {
                    System.out.println("Problema al crear sugerencias");
                }
                menuSugerencias.show(actor, Side.BOTTOM, 0, 0);
            }
        });
    }
    
    private void evtEmpezar() {
        empezar.setOnAction(e-> {
            String metodo = algoritmos.getValue();
            String act = actor.getText();
            if(metodo != null && !act.isEmpty()) {
                bacon.dijkstra(JuegoBacon.getActorId(act));  
            }else {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error!!!");
                alert.setContentText("Error al ingresar datos"); 
                alert.show();
            }
        }); 
    }
    
    private HBox crearHBox(String label, Node n) {
        Text txt = new Text(label);
        txt.setFill(Color.WHITE);
        txt.setFont(Font.font(txt.getFont().getFamily(), FontWeight.SEMI_BOLD, txt.getFont().getSize())); 
        HBox metodo = new HBox(10, txt, n);
        metodo.setAlignment(Pos.CENTER); 
        return metodo;
    }
    
    public void populatePopup() {

        List<String> suggestions = getSuggestions(actor.getText());

        LinkedList<String> itemsString = new LinkedList<>();

        for(String s: suggestions) {
            itemsString.add(s);
        }

        List<CustomMenuItem> menuItems = new LinkedList<>();

        for (String text : itemsString) {

            CustomMenuItem item = new CustomMenuItem(new Label(text), true);
            item.setOnAction((ActionEvent actionEvent) -> {
                String nombre = ((Label) item.getContent()).getText();

                actor.setText(nombre);
                menuSugerencias.hide();
            });
            menuItems.add(item);
        }
        menuSugerencias.getItems().clear();
        menuSugerencias.getItems().addAll(menuItems);
    }
    
    private List<String> getSuggestions(String subString){
        List<String> result = new LinkedList<>();
        
        for(String s: nombresActores){
            if(s.toLowerCase().startsWith(subString.toLowerCase()))
                result.add(s);
            if(result.size()>10)
                break;
        }
        
        return  result;
    }
    
    private void leftDisenio() {
        left.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
    }
    
    private void centerPane() {
        bacon = new BaconGraph();
        root.setCenter(bacon); 
    }
    
    public Pane getRoot() {
        return root;
    }

    public void setNombresActores(List<String> nombresActores) {
        this.nombresActores = nombresActores;
    }
    
    
}
