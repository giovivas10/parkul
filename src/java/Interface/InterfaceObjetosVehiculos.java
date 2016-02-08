/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interface;

import java.util.List;
import org.hibernate.Session;
import pojo.ObjetosVehiculos;

/**
 *
 * @author Giovanni
 */
public interface InterfaceObjetosVehiculos {
    public boolean register(Session session, ObjetosVehiculos objetosVehiculos) throws Exception;
    public List<ObjetosVehiculos> getAllByIdEvaluacion(Session session, int Id) throws Exception;
}
