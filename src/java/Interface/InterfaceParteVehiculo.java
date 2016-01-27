/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interface;

import java.util.List;
import org.hibernate.Session;
import pojo.ParteVehiculo;

/**
 *
 * @author Giovanni
 */
public interface InterfaceParteVehiculo {
    public boolean register(ParteVehiculo parteVehiculo, Session session) throws Exception;
    public List<ParteVehiculo> getAll(Session session)throws Exception;
    public ParteVehiculo getById(Session session, Integer id) throws Exception;
    public boolean update(ParteVehiculo parteVehiculo,Session session) throws Exception;
    public boolean delete(ParteVehiculo parteVehiculo,Session session) throws Exception;
}
