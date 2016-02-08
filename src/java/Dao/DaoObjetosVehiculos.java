/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dao;

import Interface.InterfaceObjetosVehiculos;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import pojo.ObjetosVehiculos;

/**
 *
 * @author Giovanni
 */
public class DaoObjetosVehiculos implements InterfaceObjetosVehiculos{

    @Override
    public boolean register(Session session, ObjetosVehiculos objetosVehiculos) throws Exception {
        session.save(objetosVehiculos);
        
        return true;
    }
    
    @Override
    public List<ObjetosVehiculos> getAllByIdEvaluacion(Session session, int Id) throws Exception {
        String hql="from ObjetosVehiculos where evaluacionEstadoVehicular=:Id";
        Query query = session.createQuery(hql);
        query.setInteger("Id", Id);
        
        return (List<ObjetosVehiculos>) query.list();
        
    }
    
}
