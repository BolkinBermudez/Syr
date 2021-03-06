/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.sena.pm.facade;

import edu.sena.pm.entity.Usuario;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Bolkin B
 */
@Stateless
public class UsuarioFacade extends AbstractFacade<Usuario> implements UsuarioFacadeLocal {

    @PersistenceContext(unitName = "SubastasyRematesPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UsuarioFacade() {
        super(Usuario.class);
    }
    
    @Override
    public Usuario recuperarClave( String correoIn){
        try {
           Query qt = em.createQuery("SELECT p FROM Usuario p WHERE p.correo = :correoIn");
           qt.setParameter("correoIn", correoIn);
           return  (Usuario) qt.getSingleResult();
        } catch (Exception e) {
            return new Usuario();
        }        
    }
    
    @Override
    public Usuario loginUsuario( String correIn , String claveIn){
        try {
            
            em.getEntityManagerFactory().getCache().evictAll();
            Query q = em.createQuery("SELECT u FROM Usuario u WHERE u.correo = :correo AND u.password = :clave"  );
            q.setParameter("correo", correIn);
            q.setParameter("clave", claveIn);
            return (Usuario) q.getSingleResult();
        } catch (Exception e) {
            return new Usuario();
        }
    } 
    
}
