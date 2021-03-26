/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.sena.pm.controller;

import edu.sena.pm.entity.Moneda;
import edu.sena.pm.entity.Usuario;
import edu.sena.pm.facade.MonedaFacadeLocal;
import edu.sena.pm.facade.UsuarioFacadeLocal;
import edu.sena.pm.utilidades.Email;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import org.jboss.weld.context.RequestContext;
import org.primefaces.PrimeFaces;

/**
 *
 * @author Bolkin B
 */
@Named(value = "usuarioRequest")
@RequestScoped
public class UsuarioRequest implements Serializable{

    @EJB
    private UsuarioFacadeLocal usuarioFacadeLocal;
    private Usuario objUsuNew = new Usuario();
    
     
    @EJB
    private MonedaFacadeLocal monedaFacadeLocal;
    private Moneda objMon= new Moneda();
    private ArrayList<Moneda> listaMonedas = new ArrayList<>();
    
    private int id_mon_select = 0;
    private String correoRecuperar = "";
    
    public UsuarioRequest() {
    }
    
    
    @PostConstruct
    public void leerListaUsuarios() {
        listaMonedas.addAll(monedaFacadeLocal.findAll());
        objMon = new Moneda();

    }
    
    public void cargaMonedas() {
        try {
            objMon = monedaFacadeLocal.find(1);
            listaMonedas.addAll(monedaFacadeLocal.findAll());
        } catch (Exception e) {
            System.out.println("edu.sena.pm.controller.ProductosView.cargaMonedas() " + e.getMessage());
        }
    }
    public void crearUsuario() {
        String mensajeSw = "";
        try {
            Moneda obt = monedaFacadeLocal.find(id_mon_select);
            objUsuNew.setIdMoneda(obt);
            //objUsuNew.setImagenRuta(nombreArchivo);
            usuarioFacadeLocal.create(objUsuNew);
            objUsuNew = new Usuario();
            mensajeSw = "swal('Usuario registrado' , ' con exito ', 'success')";
        } catch (Exception e) {
            mensajeSw = "swal('Problemas ingresando ' un nuevo producto  ', 'error')";
        }
        
        
        PrimeFaces.current().executeScript(mensajeSw);
    }
    
    public List<Moneda> listarPorMoneda(){
        listaMonedas =  (ArrayList<Moneda>) monedaFacadeLocal.findAll();
        return listaMonedas;
    }
    
    public void leerListaMonedas(Moneda listaM) {

        this.objMon = listaM;
    }
    
    public void recuperarClave(){
        Usuario usuRecuperar = new Usuario();
        String mensajeSw = "";
        try {
             usuRecuperar = usuarioFacadeLocal.recuperarClave(correoRecuperar);
             if(usuRecuperar.getNombre() == null){
                  mensajeSw = "swal('El correo' , ' No se encuentra registrado  ', 'error')";
             }else{
                 
                    Email.sendClaves(usuRecuperar.getCorreo(), 
                                      usuRecuperar.getNombre() +" " + usuRecuperar.getApellidoPaterno()+" " + usuRecuperar.getApellidoMaterno(),
                                      usuRecuperar.getCorreo(),
                                      usuRecuperar.getPassword());
                    
                  mensajeSw = "swal('La clave es enviada' , ' A su correo electronico  ', 'success')";
             }
        } catch (Exception e) {
            mensajeSw = "swal('El correo' , ' No se encuentra registrado  ', 'error')";
        }
             PrimeFaces.current().executeScript(mensajeSw);
    }
    
    public Usuario getObjUsu() {
        return objUsuNew;
    }

    public void setObjUsu(Usuario objUsu) {
        this.objUsuNew = objUsu;
    }

    public Moneda getObjMon() {
        return objMon;
    }

    public void setObjMon(Moneda objMon) {
        this.objMon = objMon;
    }

    public ArrayList<Moneda> getListaMonedas() {
        return listaMonedas;
    }

    public void setListaMonedas(ArrayList<Moneda> listaMonedas) {
        this.listaMonedas = listaMonedas;
    }

    public int getId_mon_select() {
        return id_mon_select;
    }

    public void setId_mon_select(int id_mon_select) {
        this.id_mon_select = id_mon_select;
    }

    public Usuario getObjUsuNew() {
        return objUsuNew;
    }

    public void setObjUsuNew(Usuario objUsuNew) {
        this.objUsuNew = objUsuNew;
    }

    public String getCorreoRecuperar() {
        return correoRecuperar;
    }

    public void setCorreoRecuperar(String correoRecuperar) {
        this.correoRecuperar = correoRecuperar;
    }
    
}
