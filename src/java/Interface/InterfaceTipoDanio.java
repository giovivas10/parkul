/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interface;

import java.util.List;
import org.hibernate.Session;
import pojo.ParametroEvaluacion;

/**
 *
 * @author Giovanni
 */
public interface InterfaceTipoDanio {
    public List<ParametroEvaluacion> getAll(Session session) throws Exception;
    public List<ParametroEvaluacion> cargarSelect(Session session) throws Exception;
    public ParametroEvaluacion getById(Session session, Integer id) throws Exception;
    public boolean register(ParametroEvaluacion parametroEvaluacion, Session session) throws Exception;
    public boolean update(ParametroEvaluacion parametroEvaluacion,Session session) throws Exception;
    public boolean delete(ParametroEvaluacion parametroEvaluacion,Session session) throws Exception;
}
