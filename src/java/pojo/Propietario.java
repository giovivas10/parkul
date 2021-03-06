package pojo;
// Generated 4/03/2016 10:14:53 AM by Hibernate Tools 4.3.1


import java.util.HashSet;
import java.util.Set;

/**
 * Propietario generated by hbm2java
 */
public class Propietario  implements java.io.Serializable {


     private Integer id;
     private TipoUsuario tipoUsuario;
     private TipoVehiculo tipoVehiculo;
     private String documento;
     private String nombres;
     private String apellidos;
     private String telefono;
     private String email;
     private String placa;
     private String fotoPropietario;
     private String marca;
     private Integer modelo;
     private String color;
     private String targetaPropiedad;
     private Set evaluacionEstadoVehiculars = new HashSet(0);

    public Propietario() {
    }

    public Propietario(TipoUsuario tipoUsuario, TipoVehiculo tipoVehiculo, String documento, String nombres, String apellidos, String telefono, String email, String placa, String fotoPropietario, String marca, Integer modelo, String color, String targetaPropiedad, Set evaluacionEstadoVehiculars) {
       this.tipoUsuario = tipoUsuario;
       this.tipoVehiculo = tipoVehiculo;
       this.documento = documento;
       this.nombres = nombres;
       this.apellidos = apellidos;
       this.telefono = telefono;
       this.email = email;
       this.placa = placa;
       this.fotoPropietario = fotoPropietario;
       this.marca = marca;
       this.modelo = modelo;
       this.color = color;
       this.targetaPropiedad = targetaPropiedad;
       this.evaluacionEstadoVehiculars = evaluacionEstadoVehiculars;
    }
   
    public Integer getId() {
        return this.id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    public TipoUsuario getTipoUsuario() {
        return this.tipoUsuario;
    }
    
    public void setTipoUsuario(TipoUsuario tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }
    public TipoVehiculo getTipoVehiculo() {
        return this.tipoVehiculo;
    }
    
    public void setTipoVehiculo(TipoVehiculo tipoVehiculo) {
        this.tipoVehiculo = tipoVehiculo;
    }
    public String getDocumento() {
        return this.documento;
    }
    
    public void setDocumento(String documento) {
        this.documento = documento;
    }
    public String getNombres() {
        return this.nombres;
    }
    
    public void setNombres(String nombres) {
        this.nombres = nombres;
    }
    public String getApellidos() {
        return this.apellidos;
    }
    
    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }
    public String getTelefono() {
        return this.telefono;
    }
    
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
    public String getEmail() {
        return this.email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPlaca() {
        return this.placa;
    }
    
    public void setPlaca(String placa) {
        this.placa = placa;
    }
    public String getFotoPropietario() {
        return this.fotoPropietario;
    }
    
    public void setFotoPropietario(String fotoPropietario) {
        this.fotoPropietario = fotoPropietario;
    }
    public String getMarca() {
        return this.marca;
    }
    
    public void setMarca(String marca) {
        this.marca = marca;
    }
    public Integer getModelo() {
        return this.modelo;
    }
    
    public void setModelo(Integer modelo) {
        this.modelo = modelo;
    }
    public String getColor() {
        return this.color;
    }
    
    public void setColor(String color) {
        this.color = color;
    }
    public String getTargetaPropiedad() {
        return this.targetaPropiedad;
    }
    
    public void setTargetaPropiedad(String targetaPropiedad) {
        this.targetaPropiedad = targetaPropiedad;
    }
    public Set getEvaluacionEstadoVehiculars() {
        return this.evaluacionEstadoVehiculars;
    }
    
    public void setEvaluacionEstadoVehiculars(Set evaluacionEstadoVehiculars) {
        this.evaluacionEstadoVehiculars = evaluacionEstadoVehiculars;
    }




}


