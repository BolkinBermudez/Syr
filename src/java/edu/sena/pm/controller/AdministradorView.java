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
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import static java.util.Collections.list;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import org.primefaces.PrimeFaces;

/**
 *
 * @author Bolkin B
 */
@Named(value = "administradorView")
@ViewScoped
public class AdministradorView implements Serializable {

    @Inject
    private UsuarioSession usuarioSession;

    @EJB
    UsuarioFacadeLocal usuarioFacadeLocal;
    private ArrayList<Usuario> listaUsuarios = new ArrayList<>();
    private Usuario usReg = new Usuario();

    @EJB
    MonedaFacadeLocal monedaFacadeLocal;
    private ArrayList<Moneda> listaMonedas = new ArrayList<>();
    private Moneda objmoneda = new Moneda();
    
    private int idMoneda;

    
    @PostConstruct
    public void leerListaUsuarios() {

        listaUsuarios.addAll(usuarioFacadeLocal.findAll());
        usReg = new Usuario();

        listaMonedas.addAll(monedaFacadeLocal.findAll());
        objmoneda = new Moneda();

    }

    public AdministradorView() {
    }

    public void crearUsuario() {
        String mensajeSw = "";
        try {

            usuarioFacadeLocal.create(usReg);
            listaUsuarios.add(usReg);
            mensajeSw = "swal('Usuario registrado' , ' con exito ', 'success')";
        } catch (Exception e) {
            mensajeSw = "swal('El usuario' , ' Ya se encuentra registrado  ', 'error')";
        }
        usReg = new Usuario();
        PrimeFaces.current().executeScript(mensajeSw);
    }

    public void removerUsuario(Usuario usuRem) {
        String mensajeSw = "";
        try {
            usuarioFacadeLocal.remove(usuRem);
            listaUsuarios.remove(usuRem);
            mensajeSw = "swal('Usuario removido' , ' con exito ', 'success')";
        } catch (Exception e) {
            mensajeSw = "swal('Problemas removiendo' , ' al usuario  ', 'error')";
        }
        PrimeFaces.current().executeScript(mensajeSw);
    }

    public void cargaUsuarioEditar(Usuario usuEditar) {
        this.usReg = usuEditar;
    }

    public void editarUsuario() {
        String mensajeSw = "";
        try {
            usuarioFacadeLocal.edit(usReg);
            usuarioFacadeLocal.actualizarMoneda(idMoneda, usReg.getIdUsuario());
            listaUsuarios.clear();
            listaUsuarios.addAll(usuarioFacadeLocal.findAll());          
            
            mensajeSw = "swal('Usuario modificado' , ' con exito ', 'success')";
        } catch (Exception e) {
            mensajeSw = "swal('Problemas modificando' , ' al usuario  ', 'error')";
        }
        
        PrimeFaces.current().executeScript(mensajeSw);
    }

    public void leerListaMonedas(Moneda listaM) {

        this.objmoneda = listaM;
    }

    public void correoMasivo() {
        try {
            for (Usuario lUsuario : listaUsuarios) {
                Email.sendBienvenido(lUsuario.getCorreo(), lUsuario.getNombre() + " " + lUsuario.getApellidoPaterno() + " "
                        + " " + lUsuario.getApellidoMaterno(), lUsuario.getCorreo(), lUsuario.getPassword());

            }
        } catch (Exception e) {

        }

    }

    public void descargaCertificado(String idUsuario) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext context = facesContext.getExternalContext();
        HttpServletRequest request = (HttpServletRequest) context.getRequest();

        HttpServletResponse response = (HttpServletResponse) context.getResponse();
        response.setContentType("application/pdf");

        try {
            Map parametro = new HashMap();
            parametro.put("idUsuario", idUsuario);
            parametro.put("RutaImagen", context.getRealPath("/images/senaFondo.jpg"));
            Connection conec = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/dashio", "root", "root");
            System.out.println("Catalogo : " + conec.getCatalog());

            File jasper = new File(context.getRealPath("/WEB-INF/classes/edu/webapp1966781a/reportes/certificado.jasper"));

            JasperPrint jp = JasperFillManager.fillReport(jasper.getPath(), parametro, conec);

            HttpServletResponse hsr = (HttpServletResponse) context.getResponse();
            hsr.addHeader("Content-disposition", "attachment; filename=Certificado.pdf");
            OutputStream os = hsr.getOutputStream();
            JasperExportManager.exportReportToPdfStream(jp, os);
            os.flush();
            os.close();
            facesContext.responseComplete();

        } catch (JRException e) {
            System.out.println("edu.webapp1966781a.controlador.AdministradorView.descargaReporte() " + e.getMessage());
        } catch (IOException i) {
            System.out.println("edu.webapp1966781a.controlador.AdministradorView.descargaReporte() " + i.getMessage());
        } catch (SQLException q) {
            System.out.println("edu.webapp1966781a.controlador.AdministradorView.descargaReporte() " + q.getMessage());
        }

    }

    public void descargaReporte(String nombreReporte) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext context = facesContext.getExternalContext();
        HttpServletRequest request = (HttpServletRequest) context.getRequest();

        HttpServletResponse response = (HttpServletResponse) context.getResponse();
        response.setContentType("application/pdf");

        try {
            Map parametro = new HashMap();
            parametro.put("Ficha", "1966781-A");
            parametro.put("UsuarioReporte", usuarioSession.getUsuLogin().getNombre() + " " + usuarioSession.getUsuLogin().getApellidoPaterno());
            parametro.put("RutaImagen", context.getRealPath("/images/sena.png"));
            Connection conec = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/dashio", "root", "root");
            System.out.println("Catalogo : " + conec.getCatalog());

            File jasper = new File(context.getRealPath("/WEB-INF/classes/edu/webapp1966781a/reportes/" + nombreReporte + ".jasper"));

            JasperPrint jp = JasperFillManager.fillReport(jasper.getPath(), parametro, conec);

            HttpServletResponse hsr = (HttpServletResponse) context.getResponse();
            hsr.addHeader("Content-disposition", "attachment; filename=Lista Usuarios.pdf");
            OutputStream os = hsr.getOutputStream();
            JasperExportManager.exportReportToPdfStream(jp, os);
            os.flush();
            os.close();
            facesContext.responseComplete();

        } catch (JRException e) {
            System.out.println("edu.webapp1966781a.controlador.AdministradorView.descargaReporte() " + e.getMessage());
        } catch (IOException i) {
            System.out.println("edu.webapp1966781a.controlador.AdministradorView.descargaReporte() " + i.getMessage());
        } catch (SQLException q) {
            System.out.println("edu.webapp1966781a.controlador.AdministradorView.descargaReporte() " + q.getMessage());
        }

    }

    public ArrayList<Usuario> getListaUsuarios() {
        return listaUsuarios;
    }

    public void setListaUsuarios(ArrayList<Usuario> listaUsuarios) {
        this.listaUsuarios = listaUsuarios;
    }

    public Usuario getUsReg() {
        return usReg;
    }

    public void setUsReg(Usuario usReg) {
        this.usReg = usReg;
    }

    public ArrayList<Moneda> getListaMonedas() {
        return listaMonedas;
    }

    public void setListaMonedas(ArrayList<Moneda> listaMonedas) {
        this.listaMonedas = listaMonedas;
    }

    public UsuarioSession getUsuarioSession() {
        return usuarioSession;
    }

    public void setUsuarioSession(UsuarioSession usuarioSession) {
        this.usuarioSession = usuarioSession;
    }

    public Moneda getObjmoneda() {
        return objmoneda;
    }

    public void setObjmoneda(Moneda objmoneda) {
        this.objmoneda = objmoneda;
    }

    public int getIdMoneda() {
        return idMoneda;
    }

    public void setIdMoneda(int idMoneda) {
        this.idMoneda = idMoneda;
    }
    
    
}
