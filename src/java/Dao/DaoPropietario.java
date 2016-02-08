/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dao;

import Interface.InterfacePropietario;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import pojo.Propietario;

/**
 *
 * @author Giovanni
 */
public class DaoPropietario implements InterfacePropietario{

    @Override
    public boolean register(Propietario propietario, Session session) throws Exception {
        session.save(propietario);
        
        return true;
    }

    @Override
    public List<Propietario> getAll(Session session) throws Exception {
        String hql = "from Propietario";
        Query query = session.createQuery(hql);
        
        List<Propietario> listaPropietario = (List<Propietario>) query.list();
        
        return listaPropietario;
    }
    
    @Override
    public Propietario getByIdPropietario(Session session,int id) throws Exception {
        return (Propietario) session.get(Propietario.class, id);
    }

    @Override
    public boolean update(Propietario propietario, Session session) throws Exception {
        session.update(propietario);
        
        return true;
    }

    @Override
    public boolean delete(Propietario propietario, Session session) throws Exception {
        session.delete(propietario);
        
        return true;
    }

    @Override
    public Propietario getByPlaca(Session session, String Placa) throws Exception {
        String hql = "from Propietario where placa=:Placa";
        Query query = session.createQuery(hql);
        query.setParameter("Placa", Placa);
        
        Propietario listaPropietario = (Propietario) query.uniqueResult();
        
        return listaPropietario;
    }
    
}
