package pojo;
// Generated 4/03/2016 10:14:53 AM by Hibernate Tools 4.3.1


import java.util.HashSet;
import java.util.Set;

/**
 * TipoVehiculo generated by hbm2java
 */
public class TipoVehiculo  implements java.io.Serializable {


     private Integer id;
     private String vehiculo;
     private Set propietarios = new HashSet(0);

    public TipoVehiculo() {
    }

	
    public TipoVehiculo(String vehiculo) {
        this.vehiculo = vehiculo;
    }
    public TipoVehiculo(String vehiculo, Set propietarios) {
       this.vehiculo = vehiculo;
       this.propietarios = propietarios;
    }
   
    public Integer getId() {
        return this.id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    public String getVehiculo() {
        return this.vehiculo;
    }
    
    public void setVehiculo(String vehiculo) {
        this.vehiculo = vehiculo;
    }
    public Set getPropietarios() {
        return this.propietarios;
    }
    
    public void setPropietarios(Set propietarios) {
        this.propietarios = propietarios;
    }




}


