/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package espol.edu.ec.tda;

import java.util.Objects;

/**
 *
 * @author SSAM
 */
public class Edge <E>{
    private int peso;
    Vertex<E> origen;
    Vertex<E> destino;
    
    public Edge(Vertex<E> origen, Vertex<E> destino, int peso){
        this.origen = origen;
        this.destino = destino;
        this.peso = peso;        
    }

    public int getPeso() {
        return peso;
    }

    public void setPeso(int peso) {
        this.peso = peso;
    }

    public Vertex<E> getOrigen() {
        return origen;
    }

    public void setOrigen(Vertex<E> origen) {
        this.origen = origen;
    }

    public Vertex<E> getDestino() {
        return destino;
    }

    public void setDestino(Vertex<E> destino) {
        this.destino = destino;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof Edge))return false;

        Edge<E> nu = (Edge)obj;
        return this.origen.equals(nu.origen) && this.destino.equals(nu.destino);
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 31 * hash + Objects.hashCode(this.origen);
        hash = 31 * hash + Objects.hashCode(this.destino);
        return hash;
    }
    
    
}