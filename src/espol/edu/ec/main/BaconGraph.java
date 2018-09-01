/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package espol.edu.ec.main;

import espol.edu.ec.tda.JuegoBacon;
import espol.edu.ec.tda.Graph;
import java.util.List;
import javafx.geometry.Pos;
import javafx.scene.control.ScrollPane;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeLineJoin;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 *
 *
 */
public class BaconGraph extends StackPane{
    
    private final Graph<Integer> grafo;
    private final int idBacon = 1;
    private final VBox root;
    private final ScrollPane scroll;
    
    public BaconGraph() {
        grafo = JuegoBacon.crearGrafo();
        root = new VBox();
        root.setAlignment(Pos.CENTER); 
        scroll = new ScrollPane();
        scroll.setContent(root); 
        super.getChildren().add(scroll); 
        super.setAlignment(Pos.CENTER); 
    }
    
    public void dijkstra(int id) {
        root.getChildren().clear();
        List<Integer> camino = grafo.caminoMinimo(id, idBacon);
        for(int col=0; col<camino.size(); col++) {
            String actor = JuegoBacon.getActor(camino.get(col));
            String pelicula = "";
            if(col < camino.size() - 1) {
                int idPelicula = grafo.getPesoArco(camino.get(col), camino.get(col+1));
                pelicula = JuegoBacon.getPelicula(idPelicula);
            }
            if(!actor.equalsIgnoreCase("Kevin Bacon"))
                root.getChildren().addAll(drawRectangle(actor, Color.BLUE), drawArrow("Actuó en"), 
                        drawRectangle(pelicula, Color.RED), drawArrow("Con"));
            else
                root.getChildren().add(drawRectangle(actor, Color.BLUE));
            
        }
    }
    
    
    private StackPane drawRectangle(String nombre, Color color) {
        StackPane sp = new StackPane();
        Text txt = new Text(nombre);
        txt.setFont(Font.font(20)); 
        Rectangle r = new Rectangle(txt.getBoundsInLocal().getWidth() + 15, txt.getBoundsInLocal().getHeight() + 15);
        r.setFill(color);
        r.setStroke(Color.WHITE);
        r.setStrokeWidth(5); 
        r.setStrokeLineJoin(StrokeLineJoin.ROUND);
        DropShadow shadow = new DropShadow();
        r.setEffect(shadow); 
        sp.setAlignment(Pos.CENTER);
        sp.getChildren().addAll(r, txt); 
        return sp;
    } 
    
    private StackPane drawArrow(String tipo) {
        StackPane sp = new StackPane();
        ImageView iv = new ImageView(new Image(
                BaconGraph.class.getResourceAsStream("/espol/edu/ec/recursos/flecha.png"), 75, 75, true, true));
        iv.setRotate(90); 
        Text txt = new Text(tipo);
        txt.setFont(Font.font(24));
        txt.setFill(Color.DARKCYAN); 
        sp.setAlignment(Pos.CENTER);
        sp.getChildren().addAll(iv, txt); 
        return sp;
    }
}
