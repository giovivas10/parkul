/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interface;

import org.hibernate.Session;
import pojo.Rol;

/**
 *
 * @author Giovanni
 */
public interface InterfaceRol {
    public Rol getById(Session session, Integer id) throws Exception;
}
