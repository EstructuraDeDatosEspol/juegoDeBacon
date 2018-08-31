/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package espol.edu.ec.main;

import espol.edu.ec.tda.juegoBacon;


/**
 *
 * @author SSAM
 */
public class Main {
    public static void main(String[] args) {
        juegoBacon j = new juegoBacon();
        
        long star = System.currentTimeMillis();
        j.relacionar();
        long end = System.currentTimeMillis();
        System.out.println("Relacion lista: "+(end-star)+ " milisegundos");
        System.out.println(j.getListaActores().get(0).getPeliculas());
        
        
    }
}
