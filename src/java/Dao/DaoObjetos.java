/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dao;

import Interface.InterfaceObjeto;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import pojo.Objetos;

/**
 *
 * @author Giovanni
 */
public class DaoObjetos implements InterfaceObjeto{

    @Override
    public boolean register(Objetos objetos, Session session) throws Exception {
        session.save(objetos);
        
        return true;
    }

    @Override
    public List<Objetos> getAll(Session session) throws Exception {
        String hql = "from Objetos where id != -1";
        Query query = session.createQuery(hql);
        
        List<Objetos> listaObjetos = (List<Objetos>) query.list();
        
        return listaObjetos;
    }
    
    @Override
    public List<Objetos> cargarSelect(Session session) throws Exception {
        String hql = "from Objetos";
        Query query = session.createQuery(hql);
        
        List<Objetos> listaObjetos = (List<Objetos>) query.list();
        
        return listaObjetos;
    }

    @Override
    public Objetos getById(Session session, Integer id) throws Exception {
        return (Objetos) session.get(Objetos.class, id);
    }

    @Override
    public boolean update(Objetos objetos, Session session) throws Exception {
        session.update(objetos);
        
        return true;
    }

    @Override
    public boolean delete(Objetos objetos, Session session) throws Exception {
        session.delete(objetos);
        
        return true;
    }
    
}
