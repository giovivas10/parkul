/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dao;

import Interface.InterfaceParteVehiculo;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import pojo.ParteVehiculo;

/**
 *
 * @author Giovanni
 */
public class DaoParteVehiculo implements InterfaceParteVehiculo{

    @Override
    public boolean register(ParteVehiculo parteVehiculo, Session session) throws Exception {
        session.save(parteVehiculo);
        
        return true;
    }

    @Override
    public List<ParteVehiculo> getAll(Session session) throws Exception {
        String hql = "from ParteVehiculo where id!=-1";
        Query query = session.createQuery(hql);
        
        List<ParteVehiculo> listaParteVehiculo = query.list();
        
        return listaParteVehiculo;
        
    }
    
    @Override
    public List<ParteVehiculo> cargarSelect(Session session) throws Exception {
        String hql = "from ParteVehiculo";
        Query query = session.createQuery(hql);
        
        List<ParteVehiculo> listaParteVehiculo = query.list();
        
        return listaParteVehiculo;
        
    }

    @Override
    public ParteVehiculo getById(Session session, Integer id) throws Exception {
        return (ParteVehiculo) session.get(ParteVehiculo.class, id);
    }

    @Override
    public boolean update(ParteVehiculo parteVehiculo, Session session) throws Exception {
        
        session.update(parteVehiculo);
        return true;
    }

    @Override
    public boolean delete(ParteVehiculo parteVehiculo, Session session) throws Exception {
        session.delete(parteVehiculo);
        return true;
    }
    
}
