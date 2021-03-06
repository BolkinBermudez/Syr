/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.sena.pm.facade;

import edu.sena.pm.entity.Moneda;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Bolkin B
 */
@Stateless
public class MonedaFacade extends AbstractFacade<Moneda> implements MonedaFacadeLocal {

    @PersistenceContext(unitName = "SubastasyRematesPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public MonedaFacade() {
        super(Moneda.class);
    }
    
}
