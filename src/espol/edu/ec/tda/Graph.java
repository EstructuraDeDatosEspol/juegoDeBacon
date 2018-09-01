/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package espol.edu.ec.tda;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.TreeSet;

/**
 *
 * @author SSAM
 * @param <E>
 */
public class Graph <E>{
    private final List<Vertex<E>> vertices;
    private final boolean dirigido;

    public Graph(boolean dirigido) {
        this.dirigido = dirigido;
        vertices = new LinkedList<>();
    }
    
    public boolean contains(E data){
        for(Vertex<E> i: vertices){
            if(i.getData().equals(data))return true;
        }        
        return false;
    }
    
    public boolean addVertex(E data){
        if(data == null || contains(data))return false;
        return vertices.add(new Vertex(data));
    }
    
    public Vertex<E> searchVertex(E data){
        for(Vertex<E> v: vertices){
            if(v.getData().equals(data))return v;            
        }
        return null;
    }
    
    public boolean addEdge(E origen, E destino, int peso){
        Vertex<E> vo = searchVertex(origen);
        Vertex<E> vd = searchVertex(destino);
        if(vo == null || vd == null)return false;
        
        Edge<E> a = new Edge<>(vo,vd,peso);
        if(vo.getArcos().contains(a))return false;
        
        vo.getArcos().add(a);
        if(!dirigido){
            Edge<E> b = new Edge<>(vd,vo,peso);
            vd.getArcos().add(b);
        }
        return true;
    }
    
    /*remove Edge, remove Vertex, InDegree, OutDegree, invertir*/
    public boolean removeEdge(E origen, E destino){
        Vertex<E> vo = searchVertex(origen);
        Vertex<E> vd = searchVertex(destino);
        
        if(vo != null && vd != null){
            Edge<E> e = new Edge<>(vo, vd, 0);
            vo.getArcos().remove(e);
            if(!dirigido){
                Edge<E> en = new Edge<>(vd, vo, 0);
                vd.getArcos().remove(en);
            }
            return true;
        }
        return false;
    }
    
    public boolean removeVertex(E data){
        Vertex<E> v = searchVertex(data);
        if(v != null){
            for(Vertex<E> is: vertices){
                Edge<E> es = new Edge<>(is,v,0);
                is.getArcos().remove(es);
            }
            return vertices.remove(v);
        }
        return false;
    }
    
    public int inDegree(E data){
        Vertex<E> v = searchVertex(data);
        int contador = 0;
        if(v!=null){            
            for(Vertex<E> is: vertices){
                Edge<E> e = new Edge<>(is,v,0);
                if(is.getArcos().contains(e))
                    contador++;
            }
        }
        return contador;
    }
    
    public int outDegree(E data){
        Vertex<E> v = searchVertex(data);
        if(v != null) return v.getArcos().size();
        return 0;
    }
    
    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        for(Vertex<E> v: vertices){
            sb.append(v.toString());
            sb.append(" ");
        }
        sb.append("}");
        sb.append("\n");
        
        sb.append("{");
        for(Vertex<E> v: vertices){
            for(Edge e: v.getArcos()){
                sb.append("(");
                sb.append(e.getOrigen().toString());
                sb.append(",");
                sb.append(e.getDestino().toString());
                sb.append(")");
                sb.append(" ");
            }
        }
        sb.append("}");
        return sb.toString();
    }
    
    //invertir
    public Graph invertir(){
        Graph<E> gr = new Graph<>(dirigido);
        
        for(Vertex<E> v: vertices){
            gr.addVertex(v.getData());
        }
        
        for(Vertex<E> v: vertices){
            for(Edge<E> e: v.getArcos()){
                gr.addEdge(e.getDestino().getData(), e.getOrigen().getData(), e.getPeso());
            }
        }
        return gr;
    }
    
    public boolean isEmpty(){
        return vertices.isEmpty();
    }
    
    private void vaciar(){
        for(Vertex<E> vertice: vertices){
            vertice.setVisitado(false);
            vertice.setDistancia(Integer.MAX_VALUE);
            vertice.setAntecesor(null);
        }
    }
    
    public List<E> bfs(E data){
        vaciar();
        List<E> lista = new LinkedList<>();
        Vertex<E> v = searchVertex(data);
        if(v == null || this.isEmpty()){
            return lista;
        }            
        
        Queue<Vertex<E>> cola = new LinkedList<>();
        v.setVisitado(true);
        cola.offer(v);        
        while(!cola.isEmpty()){
            Vertex<E> vi = cola.poll();
            lista.add(vi.getData());
            for(Edge<E> e: vi.getArcos()){
                if(!e.getDestino().isVisitado()){
                    e.getDestino().setVisitado(true);
                    cola.offer(e.getDestino());
                }   
            }
        }
        return lista;
    }
    
    public List<E> dfs(E data){
        List<E> lista = new LinkedList<>();
        Vertex<E> v = searchVertex(data);
        if(v == null || this.isEmpty()){
            return lista;
        }
        
        Deque<Vertex<E>> pila = new LinkedList<>();
        v.setVisitado(true);
        pila.push(v);
        while(!pila.isEmpty()){
            Vertex<E> vi = pila.pop();
            lista.add(vi.getData());
            for(Edge<E> e: vi.getArcos()){
                if(!e.getDestino().isVisitado()){
                    e.getDestino().setVisitado(true);
                    pila.push(e.getDestino());
                }
            }
        }
        return lista;
    }
    
    public boolean esConexo(){
        boolean res = false;
        if(isEmpty())return res;

        List<E> n = bfs(vertices.get(0).getData());
        res =  n.size() == vertices.size();
        
        if(dirigido && res){
            List<E> n2 = invertir().bfs(vertices.get(0).getData());
            res = vertices.size() == n2.size();
        }
        
        vaciar();
        return res;
    }
    
    private E noMarcado(){
        for(Vertex<E> v: vertices){
            if(!v.isVisitado())return v.getData();
        }
        return null;
    }
    
    private List<E> intersepto(List<E> o, List<E> i){
        List<E> lista = new LinkedList<>();
        
        Set<E> set = new TreeSet<>(o);
        Set<E> set1 = new TreeSet<>(i);
        set.retainAll(set1);
        lista.addAll(set);
        return lista;
    }
    
    public List<List<E>> componentesConexas(){
        List<List<E>> listaL = new LinkedList<>();
        
        if(isEmpty()) return listaL;
        
        if(esConexo()){
            listaL.add(bfs(vertices.get(0).getData()));
            return listaL;
        }
        
        int contador = 0;
        E d = vertices.get(0).getData();
        List<E> nuevoS = new LinkedList<>();
        
        if(!dirigido){            
            while(contador != vertices.size()){
                List<E> n = bfs(d);
                contador += n.size();
                listaL.add(n);
                nuevoS.addAll(n);
                d = noMarcado();
            }
            vaciar();
        }else{
            List<Vertex<E>> l = new LinkedList<>(vertices);
            
            while(contador != vertices.size()){
                List<E> n1 = bfs(d);
                List<E> n2 = invertir().bfs(d);
                
                List<E> n = intersepto(n1,n2);
                contador += n.size();
                listaL.add(n);
                vaciar();
                nuevoS.addAll(n);
                
                l = verticeSin(nuevoS, l);
                if(!l.isEmpty())d = l.get(0).getData();
            }
        }
        return listaL;
    }
    
    private List<Vertex<E>> verticeSin(List<E> datos, List<Vertex<E>> vertices){
        List<Vertex<E>> lista = new LinkedList<>();
        for(Vertex<E> v: vertices){
            if(!datos.contains(v.getData())){
                lista.add(v);
            }
        }
        return lista;
    }
    
    public boolean existeCiclo(){
        if(isEmpty())return false;
        List<E> lista = new LinkedList<>();
        vaciar();
        
        for(Vertex<E> v: vertices){
            if(!lista.contains(v.getData())){
                Deque<Vertex<E>> pila = new LinkedList<>();
                v.setVisitado(true);
                pila.push(v);
                if(profundidadB(lista, pila))return true;
            }
            vaciar();
        }
        
        return false;
    }
    
    private boolean profundidadB(List<E> lista, Deque<Vertex<E>> pila){
        while(!pila.isEmpty()){
            Vertex<E> vi = pila.pop();
            lista.add(vi.getData());

            for(Edge<E> e: vi.getArcos()){
                if(!e.getDestino().isVisitado()){
                    e.getDestino().setVisitado(true);
                    pila.push(e.getDestino());
                }else{
                    vaciar();
                    return true;
                }
            }
        }
        return false;
    }
    
    private void dijkstra(E origen){
        vaciar();
        Vertex<E> ori = searchVertex(origen);
        ori.setAntecesor(ori);
        ori.setDistancia(0);
        
        PriorityQueue<Vertex<E>> cola = new PriorityQueue<>((Vertex<E> v1, Vertex<E> v2) -> v1.getDistancia() - v2.getDistancia() );
        cola.offer(ori);
        
        while(!cola.isEmpty()){
            Vertex<E> espacio = cola.poll();
            if(!espacio.isVisitado()){
                espacio.setVisitado(true);
                for(Edge<E> edge: espacio.getArcos()){
                    if(edge.getDestino().getDistancia() > espacio.getDistancia()+ edge.getPeso()){
                        edge.getDestino().setDistancia(espacio.getDistancia()+ edge.getPeso());
                        edge.getDestino().setAntecesor(espacio);
                        cola.offer(edge.getDestino());
                    }
                }
            }
        }
    }
    
    public List<E> caminoMinimo(E origen, E destino){
        List<E> lista = new ArrayList<>();
        dijkstra(origen);
        lista.add(origen);
        Deque<E> pila = new LinkedList<>();
        pila.push(destino);
        
        Vertex<E> buscado = searchVertex(destino);
        while(!buscado.getAntecesor().getData().equals(origen)){
            pila.push(buscado.getAntecesor().getData());
            buscado = buscado.getAntecesor();
        }        
        while(!pila.isEmpty()){
            lista.add(pila.pop());
        }
        
        return lista;
    }
    
    public int distanciaMinima(E origen, E destino){
        dijkstra(origen);
        return searchVertex(destino).getDistancia();
    }
    
    public Graph<E> prim(){
        if(dirigido)return null;
        vaciar();
        Graph<E> g = new Graph<>(false);
        for(Vertex<E> v: vertices){
            g.addVertex(v.getData());
        }
        
        PriorityQueue<Edge<E>> cola = new PriorityQueue<>((Edge<E> e1, Edge<E> e2) -> e1.getPeso()- e2.getPeso());
        
        vertices.get(0).setVisitado(true);
        int contador = 1;
        
        for(Edge<E> e: vertices.get(0).getArcos()){
            cola.offer(e);
        }
        
        while(contador != g.vertices.size()){
            Edge<E> edge = cola.poll();
            Vertex<E> v = edge.getDestino();
            
            if(!v.isVisitado()){
                v.setVisitado(true);
                g.addEdge(edge.getOrigen().getData(), v.getData(), edge.getPeso());
                contador++;
                for(Edge<E> e: v.getArcos()){
                        cola.offer(e);
                }
            }
        }
        return g;
    }
    
    public Graph<E> kruskal(){
        if(dirigido)return null;
        vaciar();
        Graph<E> g = new Graph<>(false);
        PriorityQueue<Edge<E>> cola = new PriorityQueue<>((Edge<E> e1, Edge<E> e2) -> e1.getPeso()- e2.getPeso());
        
        for(Vertex<E> v: vertices){
            g.addVertex(v.getData());
            for(Edge<E> e: v.getArcos()){
                cola.offer(e);
            }
        }
        int contador = 0;
              
        while((g.vertices.size()-1) != contador ){
            Edge<E> e = cola.poll();
            
            List<List<E>> componentesConexas = g.componentesConexas();
            boolean validacion = true;
            for(List<E> lista: componentesConexas){
                if(lista.contains(e.getDestino().getData()) && lista.contains(e.getOrigen().getData()))
                    validacion = false;                
            }
            
            if(validacion){
                g.addEdge(e.getOrigen().getData(), e.getDestino().getData(), e.getPeso());
                contador++;
            }
        }
        return g;
    }
    
    public int getPesoArco(E inicio, E fin) {
        Vertex<E> vi = searchVertex(inicio);
        Vertex<E> vf = searchVertex(fin);
        for(Edge<E> e: vi.getArcos()) {
            if(e.origen.equals(vi) && e.destino.equals(vf))
                return e.getPeso();
        }
        return -1;
    }
    
    public Graph<E> unirGrafo(Graph<E> g){
        Graph<E> union = new Graph<>(g.dirigido);
        PriorityQueue<Edge<E>> cola = new PriorityQueue<>((Edge<E> e1, Edge<E> e2)-> e1.getPeso()-e2.getPeso());
        
        for(Vertex<E> v: g.vertices){
            cola.addAll(v.getArcos());
            union.addVertex(v.getData());
        }
        
        for(Vertex<E> v: this.vertices){
            union.addVertex(v.getData());
            cola.addAll(v.getArcos());
        }
        
        while(cola.isEmpty()){
            Edge<E> e = cola.poll();
            union.addEdge(e.getOrigen().getData(), e.getDestino().getData(), e.getPeso());
        }        
        return union;
    }
}