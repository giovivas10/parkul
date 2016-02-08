/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dao;

import Interface.InterfaceDaniosVehiculo;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import pojo.DaniosVehiculo;

/**
 *
 * @author Giovanni
 */
public class DaoDaniosVehiculo implements InterfaceDaniosVehiculo{

    @Override
    public boolean register(Session session, DaniosVehiculo daniosVehiculo) throws Exception {
        session.save(daniosVehiculo);
        
        return true;
    }

    @Override
    public DaniosVehiculo getById(Session session, int Id) throws Exception {
        return (DaniosVehiculo) session.get(DaniosVehiculo.class, Id);
    }

    @Override
    public List<DaniosVehiculo> getAllByIdEvaluacion(Session session, int Id) throws Exception {
        String hql="from DaniosVehiculo where evaluacionEstadoVehicular=:Id";
        Query query = session.createQuery(hql);
        query.setInteger("Id", Id);
        
        return (List<DaniosVehiculo>) query.list();
        
    }
    
}
