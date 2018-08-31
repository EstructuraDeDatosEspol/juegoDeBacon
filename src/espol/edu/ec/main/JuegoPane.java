/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package espol.edu.ec.main;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
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
    private final Button empezar;
    
    public JuegoPane() {
        root = new BorderPane();
        left = new VBox();
        algoritmos = new ComboBox<>();
        actor = new TextField();
        empezar = new Button("Empezar");
        leftPane();
        root.setLeft(left); 
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
    }
    
    private HBox crearHBox(String label, Node n) {
        Text txt = new Text(label);
        txt.setFill(Color.WHITE);
        txt.setFont(Font.font(txt.getFont().getFamily(), FontWeight.SEMI_BOLD, txt.getFont().getSize())); 
        HBox metodo = new HBox(10, txt, n);
        metodo.setAlignment(Pos.CENTER); 
        return metodo;
    }
    
    private void leftDisenio() {
        left.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
    }
    
    public Pane getRoot() {
        return root;
    }
}
