/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interface;

import java.util.List;
import org.hibernate.Session;
import pojo.TipoVehiculo;

/**
 *
 * @author Giovanni
 */
public interface InterfaceTipoVehiculo {
    public List<TipoVehiculo> getAll(Session session) throws Exception;
    public TipoVehiculo getById(Session session, Integer id) throws Exception;
}