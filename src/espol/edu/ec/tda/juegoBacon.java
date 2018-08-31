/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package espol.edu.ec.tda;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author SSAM
 */
public class juegoBacon {
    private List<Actor> listaActores;
    private List<Pelicula> listaPeliculas;

    public List<Actor> getListaActores() {
        return listaActores;
    }

    public void setListaActores(List<Actor> listaActores) {
        this.listaActores = listaActores;
    }

    public List<Pelicula> getListaPeliculas() {
        return listaPeliculas;
    }

    public void setListaPeliculas(List<Pelicula> listaPeliculas) {
        this.listaPeliculas = listaPeliculas;
    }
    
    
    
    public List<Actor> leerArchivosActores(){
        listaActores = new LinkedList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("src\\espol\\edu\\ec\\recursos\\actores.txt"))){
            String linea = "";
            while((linea = br.readLine()) != null){
                String[] arreglo = linea.split("|");
                listaActores.add(new Actor(Integer.valueOf(arreglo[0]), arreglo[1]));
            }
        }catch(Exception e){
            e.getStackTrace();
        }        
        return listaActores;
    }
    
    public List<Pelicula> leerArchivosPeliculas(){
        listaPeliculas = new LinkedList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("src\\espol\\edu\\ec\\recursos\\peliculas.txt"))){
            String linea = "";
            while((linea = br.readLine()) != null){
                String[] arreglo = linea.split("|");
                listaPeliculas.add(new Pelicula(Integer.valueOf(arreglo[0]), arreglo[1]));
            }
        }catch(Exception e){
            e.getStackTrace();
        }        
        return listaPeliculas;
    }
    
    public void relacionar(){
        leerArchivosPeliculas();
        leerArchivosActores();
        
        try (BufferedReader br = new BufferedReader(new FileReader("src\\espol\\edu\\ec\\recursos\\pelicula-actores.txt"))){
            String linea = "";
            while((linea = br.readLine()) != null){
                String[] arreglo = linea.split("|");
                for(Actor a: listaActores){
                    if(a.getId().equals(Integer.valueOf(arreglo[0]))){
                        for(Pelicula p: listaPeliculas){
                            if(p.getId().equals(Integer.valueOf(arreglo[1]))){
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
}