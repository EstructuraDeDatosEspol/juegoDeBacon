/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package espol.edu.ec.tda;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author SSAM
 */
public class JuegoBacon {
    private static List<Actor> listaActores;
    private static List<Pelicula> listaPeliculas;
    private static Map<Integer, String> mapaActores;
    private static Map<Integer, String> mapaPeliculas;
    private static Map<Integer, List<Integer>> mapaPeliculaActor;
    private static List<String> listaNombres;
    private static Graph<Integer> grafo;

    private JuegoBacon() {
        //constructor privado
    }
    
    public List<Actor> leerArchivosActoresL(){
        listaActores = new LinkedList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("src\\espol\\edu\\ec\\recursos\\actores.txt"))){
            String linea = "";
            while((linea = br.readLine()) != null){
                String[] arreglo = linea.split("[|]");
                listaActores.add(new Actor(Integer.valueOf(arreglo[0]), arreglo[1]));
            }
        }catch(Exception e){
            e.getStackTrace();
        }        
        return listaActores;
    }
    
    public List<Pelicula> leerArchivosPeliculasL(){
        listaPeliculas = new LinkedList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("src\\espol\\edu\\ec\\recursos\\peliculas.txt"))){
            String linea = "";
            while((linea = br.readLine()) != null){
                String[] arreglo = linea.split("[|]");
                listaPeliculas.add(new Pelicula(Integer.valueOf(arreglo[0]), arreglo[1]));
            }
        }catch(Exception e){
            e.getStackTrace();
        }        
        return listaPeliculas;
    }
    
    public void relacionarL(){
        leerArchivosPeliculasL();
        leerArchivosActoresL();
        
        try (BufferedReader br = new BufferedReader(new FileReader("src\\espol\\edu\\ec\\recursos\\pelicula-actores.txt"))){
            String linea = "";
            while((linea = br.readLine()) != null){
                String[] arreglo = linea.split("[|]");
                for(Pelicula p: listaPeliculas){
                    if(p.getId().equals(Integer.valueOf(arreglo[0]))){
                        for(Actor a: listaActores){
                            if(a.getId().equals(Integer.valueOf(arreglo[1]))){
                                a.getPeliculas().add(p);
                                p.getActores().add(a);
                            }                                
                        }
                    }                        
                }
            }
        }catch(Exception e){
            e.getStackTrace();
        }        
    }
    
    
    public static Map<Integer,String> leerArchivosActoresM(){
        mapaActores = new HashMap<>();
        listaNombres = new LinkedList<>();
        
        try (BufferedReader br = new BufferedReader(new FileReader("src\\espol\\edu\\ec\\recursos\\actores-test.txt"))){
            String linea = "";
            while((linea = br.readLine()) != null){
                String[] arreglo = linea.split("\\|");
                mapaActores.put(Integer.valueOf(arreglo[0]), arreglo[1]);
                listaNombres.add(arreglo[1]);
            }
        }catch(Exception e){
            e.getStackTrace();
        }        
        return mapaActores;
    }
    
    public static Map<Integer, String> leerArchivosPeliculasM(){
        mapaPeliculas = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader("src\\espol\\edu\\ec\\recursos\\peliculas-test.txt"))){
            String linea = "";
            while((linea = br.readLine()) != null){
                String[] arreglo = linea.split("\\|");
                mapaPeliculas.put(Integer.valueOf(arreglo[0]), arreglo[1]);
                
            }
        }catch(Exception e){
            e.getStackTrace();
        }        
        return mapaPeliculas;
    }
    
    public static void relacionarM(){
        leerArchivosPeliculasM();
        leerArchivosActoresM();
        mapaPeliculaActor = new HashMap<>();
        
        try (BufferedReader br = new BufferedReader(new FileReader("src\\espol\\edu\\ec\\recursos\\pelicula-actores-test.txt"))){
            String linea = "";
            while((linea = br.readLine()) != null){
                String[] arreglo = linea.split("\\|");
                if(mapaPeliculaActor.containsKey(Integer.valueOf(arreglo[0]))){
                    mapaPeliculaActor.get(Integer.valueOf(arreglo[0])).add(Integer.valueOf(arreglo[1]));
                }else{
                    List<Integer> l = new LinkedList<>();
                    l.add(Integer.valueOf(arreglo[1]));
                    mapaPeliculaActor.put(Integer.valueOf(arreglo[0]), l);
                }                
            }
        }catch(Exception e){
            e.getStackTrace();
        }        
    }
    
    public static void crearGrafo(){
        relacionarM();
        grafo = new Graph<>(false);
        
        for(Integer i: mapaActores.keySet()){
            grafo.addVertex(i);
        }
        
        for(Map.Entry<Integer, List<Integer>> e: mapaPeliculaActor.entrySet()){
            for(Integer origen: e.getValue()){
                for(Integer destino: e.getValue()){
                    if(!origen.equals(destino)){
                        grafo.addEdge(origen, destino, e.getKey());
                    }
                }
            }
        }
        
        
        System.out.println(grafo);
    }
}