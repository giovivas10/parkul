/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interface;

import java.util.Date;
import java.util.List;
import org.hibernate.Session;
import pojo.EvaluacionEstadoVehicular;

/**
 *
 * @author Giovanni
 */
public interface InterfaceEvaluacionEstadoVehicular {
    public boolean register(EvaluacionEstadoVehicular estadoVehicular, Session session) throws Exception;
    public EvaluacionEstadoVehicular getUltimoRegistro(Session session) throws Exception;
    public List<EvaluacionEstadoVehicular> getall(Session session, Date fecha, String usuario)throws Exception;
    public EvaluacionEstadoVehicular getById(Session session, int Id)throws Exception;
}
