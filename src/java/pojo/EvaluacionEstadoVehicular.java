package pojo;
// Generated 4/03/2016 10:14:53 AM by Hibernate Tools 4.3.1


import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * EvaluacionEstadoVehicular generated by hbm2java
 */
public class EvaluacionEstadoVehicular  implements java.io.Serializable {


     private Integer id;
     private Propietario propietario;
     private String placa;
     private Date fecha;
     private Date hora;
     private Boolean poseeDanios;
     private Boolean poseeObjetos;
     private String usuario;
     private Set objetosVehiculoses = new HashSet(0);
     private Set daniosVehiculos = new HashSet(0);

    public EvaluacionEstadoVehicular() {
    }

	
    public EvaluacionEstadoVehicular(String placa, Date fecha, Date hora, String usuario) {
        this.placa = placa;
        this.fecha = fecha;
        this.hora = hora;
        this.usuario = usuario;
    }
    public EvaluacionEstadoVehicular(Propietario propietario, String placa, Date fecha, Date hora, Boolean poseeDanios, Boolean poseeObjetos, String usuario, Set objetosVehiculoses, Set daniosVehiculos) {
       this.propietario = propietario;
       this.placa = placa;
       this.fecha = fecha;
       this.hora = hora;
       this.poseeDanios = poseeDanios;
       this.poseeObjetos = poseeObjetos;
       this.usuario = usuario;
       this.objetosVehiculoses = objetosVehiculoses;
       this.daniosVehiculos = daniosVehiculos;
    }
   
    public Integer getId() {
        return this.id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    public Propietario getPropietario() {
        return this.propietario;
    }
    
    public void setPropietario(Propietario propietario) {
        this.propietario = propietario;
    }
    public String getPlaca() {
        return this.placa;
    }
    
    public void setPlaca(String placa) {
        this.placa = placa;
    }
    public Date getFecha() {
        return this.fecha;
    }
    
    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
    public Date getHora() {
        return this.hora;
    }
    
    public void setHora(Date hora) {
        this.hora = hora;
    }
    public Boolean getPoseeDanios() {
        return this.poseeDanios;
    }
    
    public void setPoseeDanios(Boolean poseeDanios) {
        this.poseeDanios = poseeDanios;
    }
    public Boolean getPoseeObjetos() {
        return this.poseeObjetos;
    }
    
    public void setPoseeObjetos(Boolean poseeObjetos) {
        this.poseeObjetos = poseeObjetos;
    }
    public String getUsuario() {
        return this.usuario;
    }
    
    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
    public Set getObjetosVehiculoses() {
        return this.objetosVehiculoses;
    }
    
    public void setObjetosVehiculoses(Set objetosVehiculoses) {
        this.objetosVehiculoses = objetosVehiculoses;
    }
    public Set getDaniosVehiculos() {
        return this.daniosVehiculos;
    }
    
    public void setDaniosVehiculos(Set daniosVehiculos) {
        this.daniosVehiculos = daniosVehiculos;
    }




}


