/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dao;

import Interface.InterfaceTipoVehiculo;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import pojo.TipoVehiculo;

/**
 *
 * @author Giovanni
 */
public class DaoTipoVehiculo implements InterfaceTipoVehiculo{

    @Override
    public List<TipoVehiculo> getAll(Session session) throws Exception {
        //String hql = "from TipoVehiculo where id!=-1";
        String hql = "from TipoVehiculo";
        Query query = session.createQuery(hql);
        
        List<TipoVehiculo> listaVehiculos = (List<TipoVehiculo>) query.list();
        
        return listaVehiculos;
    }
    
    @Override
    public TipoVehiculo getById(Session session, Integer id) throws Exception {
        return (TipoVehiculo) session.get(TipoVehiculo.class, id);
    }

    @Override
    public boolean register(TipoVehiculo tipoVehiculo, Session session) throws Exception {
        session.save(tipoVehiculo);
        
        return true;
    }

    @Override
    public boolean update(TipoVehiculo tipoVehiculo, Session session) throws Exception {
        session.update(tipoVehiculo);
        
        return true;
    }

    @Override
    public boolean delete(TipoVehiculo tipoVehiculo, Session session) throws Exception {
        session.delete(tipoVehiculo);
        
        return true;
    }
    
}
