/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dao;

import Interface.InterfaceTipoUsuario;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import pojo.TipoUsuario;

/**
 *
 * @author Giovanni
 */
public class DaoTipoUsuario implements InterfaceTipoUsuario{

    @Override
    public List<TipoUsuario> getAll(Session session) throws Exception {
        String hql = "from TipoUsuario";
        Query query = session.createQuery(hql);
        
        List<TipoUsuario> listaUsuario = (List<TipoUsuario>) query.list();
        
        return listaUsuario;
    }
    
    @Override
    public TipoUsuario getById(Session session, Integer id) throws Exception {
        return (TipoUsuario) session.load(TipoUsuario.class, id);
    }
    
}
