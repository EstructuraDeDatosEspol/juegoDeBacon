/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package espol.edu.ec.tda;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author SSAM
 */
public class Vertex <E>{
    private E data;
    private List<Edge<E>> arcos;
    private boolean visitado;
    private Vertex<E> antecesor;
    private int distancia;
    
    public Vertex(E data){
        this.data = data;
        arcos = new LinkedList<>();
        this.visitado = false;
        antecesor = null;
        distancia  = Integer.MAX_VALUE;
    }

    public E getData() {
        return data;
    }

    public void setData(E data) {
        this.data = data;
    }

    public List<Edge<E>> getArcos() {
        return arcos;
    }

    public void setArcos(List<Edge<E>> arcos) {
        this.arcos = arcos;
    }

    public boolean isVisitado() {
        return visitado;
    }

    public void setVisitado(boolean visitado) {
        this.visitado = visitado;
    }

    public Vertex<E> getAntecesor() {
        return antecesor;
    }

    public void setAntecesor(Vertex<E> antecesor) {
        this.antecesor = antecesor;
    }

    public int getDistancia() {
        return distancia;
    }

    public void setDistancia(int distancia) {
        this.distancia = distancia;
    }
    
    @Override
    public String toString(){
        return data.toString();
    }
    
    @Override
    public boolean equals(Object o){
        if (!(o instanceof Vertex))return false;
        
        Vertex<E> v = (Vertex)o;
        return data.equals(v.data);
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 47 * hash + Objects.hashCode(this.data);
        hash = 47 * hash + Objects.hashCode(this.arcos);
        hash = 47 * hash + (this.visitado ? 1 : 0);
        return hash;
    }
}