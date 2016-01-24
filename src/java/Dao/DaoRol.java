/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dao;

import Interface.InterfaceRol;
import org.hibernate.Session;
import pojo.Rol;

/**
 *
 * @author Giovanni
 */
public class DaoRol implements InterfaceRol{

    @Override
    public Rol getById(Session session, Integer id) throws Exception {
        return (Rol) session.load(Rol.class, id);
    }
    
}
