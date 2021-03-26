/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.sena.pm.facade;

import edu.sena.pm.entity.Producto;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Bolkin B
 */
@Stateless
public class ProductoFacade extends AbstractFacade<Producto> implements ProductoFacadeLocal {

    @PersistenceContext(unitName = "SubastasyRematesPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ProductoFacade() {
        super(Producto.class);
    }

    @Override
    public int contarPorCategoria(int fk_categoria) {
        try {
            Query qt = em.createNativeQuery("SELECT count(*) FROM producto WHERE idCategoria = ?");
            qt.setParameter(1, fk_categoria);
            return ((Number) qt.getSingleResult()).intValue();
        } catch (Exception e) {
            System.out.println("edu.sena.pm.facade.ProductoFacade.contarPorCategoria() " + e.getMessage());
            return 0;
        }
    }

    @Override
    public List<Producto> listaPorCategoria(int fk_categoria) {
        try {
            Query qt = em.createQuery("SELECT p FROM Producto p WHERE p.idCategoria.idCategoria = :fk_categoria");
            qt.setParameter("fk_categoria", fk_categoria);
            return qt.getResultList();
        } catch (Exception e) {
            System.out.println("edu.sena.pm.facade.ProductoFacade.listaPorCategoria() " + e.getMessage());
            return new ArrayList<>();
        }
    }
}
