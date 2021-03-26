/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.sena.pm.controller;

import edu.sena.pm.entity.Cesta;
import edu.sena.pm.facade.CestaFacadeLocal;
import java.io.Serializable;
import java.util.ArrayList;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;

/**
 *
 * @author Bolkin B
 */
@Named(value = "productosCestaView")
@ViewScoped
public class ProductosCestaView implements Serializable{

    @EJB
    private CestaFacadeLocal cestaFacadeLocal;
    private ArrayList<Cesta> listaProductosCesta = new ArrayList<>();
    private Cesta objCesta = new Cesta();

    
    public ProductosCestaView() {
    }
    
    @PostConstruct
    public void postUsuario() {
        listaProductosCesta.addAll(cestaFacadeLocal.findAll());
    }
    
    public void leerListaProductosCesta(Cesta listaProducCesta){
        this.objCesta = listaProducCesta;
    }
    
    public void cargarProductoCesta(Cesta verProduc) {
        this.objCesta = verProduc;
    }
    
     //geete`s and setter`s
    public ArrayList<Cesta> getListaProductosCesta() {
        return listaProductosCesta;
    }

    public void setListaProductosCesta(ArrayList<Cesta> listaProductosCesta) {
        this.listaProductosCesta = listaProductosCesta;
    }

    public Cesta getObjCesta() {
        return objCesta;
    }

    public void setObjCesta(Cesta objCesta) {
        this.objCesta = objCesta;
    }
    
}
