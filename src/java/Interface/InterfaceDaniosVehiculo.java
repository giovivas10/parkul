/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interface;

import java.util.List;
import org.hibernate.Session;
import pojo.DaniosVehiculo;

/**
 *
 * @author Giovanni
 */
public interface InterfaceDaniosVehiculo {
    public boolean register(Session session,DaniosVehiculo daniosVehiculo) throws Exception;
    public DaniosVehiculo getById(Session session,int Id) throws Exception;
    public List<DaniosVehiculo> getAllByIdEvaluacion(Session session,int Id) throws Exception;
}
