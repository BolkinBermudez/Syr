/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.sena.pm.facade;

import edu.sena.pm.entity.Moneda;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Bolkin B
 */
@Local
public interface MonedaFacadeLocal {

    void create(Moneda moneda);

    void edit(Moneda moneda);

    void remove(Moneda moneda);

    Moneda find(Object id);

    List<Moneda> findAll();

    List<Moneda> findRange(int[] range);

    int count();
    
}
