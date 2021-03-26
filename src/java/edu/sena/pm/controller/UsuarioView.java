/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.sena.pm.controller;

import edu.sena.pm.entity.Categoria;
import edu.sena.pm.entity.Producto;
import edu.sena.pm.facade.CategoriaFacadeLocal;
import edu.sena.pm.facade.ProductoFacadeLocal;
import java.io.File;
import java.io.InputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.servlet.http.Part;
import org.primefaces.PrimeFaces;
import org.primefaces.shaded.commons.io.FilenameUtils;

/**
 *
 * @author Bolkin B
 */
@Named(value = "usuarioView")
@ViewScoped
public class UsuarioView implements Serializable {

    
    @EJB
    private CategoriaFacadeLocal categoriaFacadeLocal;
    private ArrayList<Categoria> listaCategoria = new ArrayList<>();
    private Categoria objCategoria = new Categoria();

    @EJB
    private ProductoFacadeLocal productoFacadeLocal;
    private Producto objProd = new Producto();
    private ArrayList<Producto> listaProductos = new ArrayList<>();
    
    

    private int id_cat_selec = 0;
    private String nombreArchivo;
    private Part archivoImagen;
    private Part archivoExcel;

    public UsuarioView() {
    }

    @PostConstruct
    public void leerListaProductos() {
        listaCategoria.addAll(categoriaFacadeLocal.findAll());
        objCategoria = new Categoria();
        listaProductos.addAll(productoFacadeLocal.findAll());
        objProd = new Producto();

    }
    
    public void leerListaCategoria(Categoria listaC){
        this.objCategoria = listaC;
    }

    public void crearProducto() {
        String mensajeSw = "";
        try {
            Categoria obt = categoriaFacadeLocal.find(id_cat_selec);
            objProd.setIdCategoria(obt);
            objProd.setImagen(nombreArchivo);
            productoFacadeLocal.create(objProd);
            objProd = new Producto();
            mensajeSw = "swal('Producto registrado' , ' con exito ', 'success')";
        } catch (Exception e) {
            mensajeSw = "swal('Problemas ingresando ' un nuevo producto  ', 'error')";
        }

        PrimeFaces.current().executeScript(mensajeSw);
    }
    
    

//    public void subeImagen() {
//        String mensajeSw = "";
//        if (archivoImagen != null) {
//            if (archivoImagen.getSize() < 4000000) {
//                if ("image/jpeg".equals(archivoImagen.getContentType()) || "image/png".equals(archivoImagen.getContentType())) {
//                    File carpeta = new File("C:/imgSyR/Productos");
//                    if (!carpeta.exists()) {
//                        carpeta.mkdirs();
//                    }
//                    try (InputStream is = archivoImagen.getInputStream()) {
//                        SimpleDateFormat ffecha = new SimpleDateFormat("yyyyMMddHHmmssSSS");
//                        Calendar hoy = Calendar.getInstance();
//                        String renombraArchivo = ffecha.format(hoy.getTime()) + ".";
//                        renombraArchivo += FilenameUtils.getExtension(archivoImagen.getSubmittedFileName());
//                        Files.copy(is, (new File(carpeta, renombraArchivo)).toPath(), StandardCopyOption.REPLACE_EXISTING);
//                        nombreArchivo = renombraArchivo;
//                    } catch (Exception e) {
//                    }
//
//                } else {
//                    mensajeSw = "swal('El archivo' , ' no es una imagen ', 'error')";
//                }
//            } else {
//                mensajeSw = "swal('La imagen' , ' es muy grande  ', 'error')";
//            }
//        } else {
//            mensajeSw = "swal('No se subio' , ' Una imagen  ', 'error')";
//        }
//
//        PrimeFaces.current().executeScript(mensajeSw);
//    }
    
    

    public void selecionCategoria(int idCategoria) {
        objCategoria = categoriaFacadeLocal.find(idCategoria);
    }
    
    

    public Producto getObjProd() {
        return objProd;
    }

    public void setObjProd(Producto objProd) {
        this.objProd = objProd;
    }

    public ArrayList<Producto> getListaProductos() {
        return listaProductos;
    }

    public void setListaProductos(ArrayList<Producto> listaProductos) {
        this.listaProductos = listaProductos;
    }

    public Part getArchivoImagen() {
        return archivoImagen;
    }

    public void setArchivoImagen(Part archivoImagen) {
        this.archivoImagen = archivoImagen;
    }

    public Part getArchivoExcel() {
        return archivoExcel;
    }

    public void setArchivoExcel(Part archivoExcel) {
        this.archivoExcel = archivoExcel;
    }

    public String getNombreArchivo() {
        return nombreArchivo;
    }

    public void setNombreArchivo(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }

    public int getId_cat_selec() {
        return id_cat_selec;
    }

    public void setId_cat_selec(int id_cat_selec) {
        this.id_cat_selec = id_cat_selec;
    }

    public Categoria getObjCategoria() {
        return objCategoria;
    }

    public void setObjCategoria(Categoria objCategoria) {
        this.objCategoria = objCategoria;
    }

    public ArrayList<Categoria> getListaCategoria() {
        return listaCategoria;
    }

    public void setListaCategoria(ArrayList<Categoria> listaCategoria) {
        this.listaCategoria = listaCategoria;
    }

}
