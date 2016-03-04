/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BeanView;

import Dao.DaoDaniosVehiculo;
import Dao.DaoEvaluacionEstadoVehicular;
import Dao.DaoObjetos;
import Dao.DaoObjetosVehiculos;
import Dao.DaoParteVehiculo;
import Dao.DaoPropietario;
import Dao.DaoTipoDanio;
import HibernateUtil.HibernateUtil;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.FacesException;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.imageio.stream.FileImageOutputStream;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.primefaces.context.RequestContext;
import org.primefaces.event.CaptureEvent;
import pojo.DaniosVehiculo;
import pojo.EvaluacionEstadoVehicular;
import pojo.Objetos;
import pojo.ObjetosVehiculos;
import pojo.ParametroEvaluacion;
import pojo.ParteVehiculo;
import pojo.Propietario;
import pojo.TipoVehiculo;

/**
 *
 * @author Giovanni
 */
@ManagedBean
@ViewScoped
public class MbVEvaluacion {

    /**
     * Creates a new instance of MbVEvaluacion
     */
    public EvaluacionEstadoVehicular evaluacionEstadoVehicular;
    public ObjetosVehiculos objetosVehiculos;
    public DaniosVehiculo daniosVehiculo;
    public Propietario propietario;
    public TipoVehiculo tipoVehiculo;
    public ParametroEvaluacion parametroEvaluacion;
    public ParteVehiculo parteVehiculo;
    public Objetos objetos;

    private List<EvaluacionEstadoVehicular> listaEvaluacionEstadoVehicular;
    private List<EvaluacionEstadoVehicular> listaEvaluacionEstadoVehicularFiltrada;

    private List<DaniosVehiculo> listaDaniosVehiculos;
    private List<ObjetosVehiculos> listaObjetosVehiculos;

    private String foto;
    private String rutaFoto;

    private String fechaM;

    private Session session;
    private Transaction transaction;

    private Boolean flag;
    private Boolean bandera1;
    private Boolean bandera2;
    private Boolean habilitarCampos;

    public MbVEvaluacion() {
        this.evaluacionEstadoVehicular = new EvaluacionEstadoVehicular();
        this.objetosVehiculos = new ObjetosVehiculos();
        this.daniosVehiculo = new DaniosVehiculo();
        this.propietario = new Propietario();
        this.tipoVehiculo = new TipoVehiculo();
        this.parametroEvaluacion = new ParametroEvaluacion();
        this.parteVehiculo = new ParteVehiculo();
        this.objetos = new Objetos();

        java.util.Date fecha = new Date();

        Date fechaActual = new Date();
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        this.fechaM = formato.format(fechaActual);
        this.evaluacionEstadoVehicular.setFecha(fecha);

        this.evaluacionEstadoVehicular.setPoseeDanios(false);
        this.evaluacionEstadoVehicular.setPoseeObjetos(false);

        this.listaDaniosVehiculos = new ArrayList<>();
        this.listaObjetosVehiculos = new ArrayList<>();

        this.flag = false;
        this.bandera1 = false;
        this.bandera2 = false;
        this.habilitarCampos = true;
        
        session();

    }
    ////////////////////////////////////////////////////////////////////////////
    public final void session(){
        HttpSession httpSession = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        if (httpSession.getAttribute("rol") != null) {
            String rol = httpSession.getAttribute("rol").toString();
            switch (rol) {
                case "1":
                    RequestContext.getCurrentInstance().execute("mostrarMenu('liParametros,liUsuario,liPropietarios,liAuxiliar,liInformes')");
                    break;
                case "2":
                    RequestContext.getCurrentInstance().execute("mostrarMenu('liAuxiliar')");
                    break;
                case "3":
                    RequestContext.getCurrentInstance().execute("mostrarMenu('liPropietarios')");
                    break;
            }
        }
    }

    public void validarPlaca() {
        this.session = null;
        this.transaction = null;
        Pattern pattern;
        Matcher matcher = null;
        Propietario propietarioTemp;

        try {
            DaoPropietario daoPropietario = new DaoPropietario();

            this.session = HibernateUtil.getSessionFactory().openSession();
            this.transaction = session.beginTransaction();
            
            if (this.tipoVehiculo.getId() == -1) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Debe selecionar un tipo de vehículo"));
                return;
            }

            //asignamos la expresion
            if (this.tipoVehiculo.getId() == 1) {//MOTO
                pattern = Pattern.compile("^([A-Z|a-z]{3}\\d{2}[A-Z|a-z]{1})$");
                matcher = pattern.matcher(this.propietario.getPlaca());
            } else if (this.tipoVehiculo.getId() == 2) {//CARRO
                pattern = Pattern.compile("^([A-Z|a-z]{3}\\d{3})$");
                matcher = pattern.matcher(this.propietario.getPlaca());
            }

            if (matcher != null) {
                if (matcher.matches() == false) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "El formato de placa para ese tipo de vehiculo no es correcto"));
                    return;
                }
            }

            propietarioTemp = daoPropietario.getByPlaca(this.session, this.propietario.getPlaca());

            if (propietarioTemp != null) {
                this.propietario = propietarioTemp;
                //this.tipoVehiculo.setVehiculo(this.propietario.getTipoVehiculo().getVehiculo());
                RequestContext.getCurrentInstance().update("frmRegistrarEvaluacion");
                this.flag = true;
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Error:", "No se encontraron datos relacionados a esta placa"));
                //this.propietario = new Propietario();
                //this.tipoVehiculo = new TipoVehiculo();
                
                RequestContext.getCurrentInstance().update("frmRegistrarEvaluacion");
                RequestContext.getCurrentInstance().execute("PF('deleteConfirmation').show()");
                this.flag = false;
            }
            this.transaction.commit();

        } catch (Exception ex) {
            if (this.transaction != null) {
                this.transaction.rollback();
            }

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error fatal", ex.getMessage() + " Contacte al administrador"));
        } finally {
            if (this.transaction != null) {
                this.session.close();
            }
        }

    }
    
    public void habilidarCamposRegistro(){
        this.habilitarCampos = false;        
    }
    
    public void camposNoAplica(){
        this.propietario.setDocumento("N/A");
        this.propietario.setNombres("N/A");
        this.propietario.setApellidos("N/A");
        this.propietario.setTelefono("N/A");
        this.propietario.setTipoVehiculo(this.tipoVehiculo);
    }

    public void agregarListaDanios() {
        this.session = null;
        this.transaction = null;

        try {
            this.session = HibernateUtil.getSessionFactory().openSession();
            this.transaction = this.session.beginTransaction();

            if (this.parametroEvaluacion.getId() != -1 && this.parteVehiculo.getId() != -1) {

                DaoTipoDanio daoTipoDanio = new DaoTipoDanio();
                DaoParteVehiculo daoParteVehiculo = new DaoParteVehiculo();

                this.daniosVehiculo.setParametroEvaluacion(daoTipoDanio.getById(this.session, this.parametroEvaluacion.getId()));
                this.daniosVehiculo.setParteVehiculo(daoParteVehiculo.getById(this.session, this.parteVehiculo.getId()));

                java.util.Date fecha = new Date();
                this.listaDaniosVehiculos.add(new DaniosVehiculo(null, this.daniosVehiculo.getParametroEvaluacion(), this.daniosVehiculo.getParteVehiculo(), fecha, this.rutaFoto));

                this.transaction.commit();

                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Correcto", "Daño agregado"));
                
                this.foto = new String();
                this.rutaFoto = new String();
                
                RequestContext.getCurrentInstance().update("frmRegistrarEvaluacion:modalDialog2");
                RequestContext.getCurrentInstance().update("frmRegistrarEvaluacion:divListaDaños");
                RequestContext.getCurrentInstance().update("frmRegistrarEvaluacion:divDaños");
                RequestContext.getCurrentInstance().update("frmRegistrarEvaluacion:mensajeGeneral");

                this.parametroEvaluacion.setId(-1);
                this.parteVehiculo.setId(-1);
                this.foto = new String();
                this.rutaFoto = new String();
                this.bandera1 = false;
                session();

            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Debe elegir un tipo de daño y una parte del vehiculo"));
                RequestContext.getCurrentInstance().update("frmRegistrarEvaluacion:divListaDaños");
                RequestContext.getCurrentInstance().update("frmRegistrarEvaluacion:divDaños");
                RequestContext.getCurrentInstance().update("frmRegistrarEvaluacion:mensajeGeneral");
                this.foto = new String();
                this.rutaFoto = new String();
                
                RequestContext.getCurrentInstance().update("frmRegistrarEvaluacion:modalDialog2");
                session();
            }
        } catch (Exception ex) {
            if (this.transaction != null) {
                transaction.rollback();
            }

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error", ex.getMessage()));
        } finally {
            if (this.session != null) {
                this.session.close();
            }
        }
    }

    public void retirarListaDanios(ParametroEvaluacion pEvaluacion, ParteVehiculo pVehiculo) {
        try {
            int i = 0;

            for (DaniosVehiculo item : this.listaDaniosVehiculos) {
                if (item.getParametroEvaluacion().equals(pEvaluacion) && item.getParteVehiculo().equals(pVehiculo)) {
                    this.listaDaniosVehiculos.remove(i);

                    break;
                }

                i++;
            }

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Correcto", "Daño retirado de la lista"));

            RequestContext.getCurrentInstance().update("frmRegistrarEvaluacion:divListaDaños");
                RequestContext.getCurrentInstance().update("frmRegistrarEvaluacion:divDaños");
                RequestContext.getCurrentInstance().update("frmRegistrarEvaluacion:mensajeGeneral");
            session();

        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error", ex.getMessage()));
        }
    }

    public void agregarListaObjetos() {
        this.session = null;
        this.transaction = null;

        try {
            this.session = HibernateUtil.getSessionFactory().openSession();
            this.transaction = this.session.beginTransaction();

            if (this.objetos.getId() != -1) {

                DaoObjetos daoObjetos = new DaoObjetos();

                this.objetosVehiculos.setObjetos(daoObjetos.getById(this.session, this.objetos.getId()));

                java.util.Date fecha = new Date();
                this.listaObjetosVehiculos.add(new ObjetosVehiculos(null, this.objetosVehiculos.getObjetos(), this.objetosVehiculos.getDescripcion(), this.rutaFoto, fecha));

                this.transaction.commit();

                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Correcto", "Objeto agregado"));
                
                this.foto = new String();
                this.rutaFoto = new String();
                
                RequestContext.getCurrentInstance().update("frmRegistrarEvaluacion:modalDialog");
                RequestContext.getCurrentInstance().update("frmRegistrarEvaluacion:divListaObjetos");
                RequestContext.getCurrentInstance().update("frmRegistrarEvaluacion:divObejtos");
                RequestContext.getCurrentInstance().update("frmRegistrarEvaluacion:mensajeGeneral");
                session();

                this.objetos.setId(-1);
                this.objetosVehiculos.setDescripcion("");
                this.foto = new String();
                this.rutaFoto = new String();
                this.bandera2 = false;

            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Debe elegir un tipo de daño y una parte del vehiculo"));
                
                this.foto = new String();
                this.rutaFoto = new String();
                
                RequestContext.getCurrentInstance().update("frmRegistrarEvaluacion:modalDialog");
                RequestContext.getCurrentInstance().update("frmRegistrarEvaluacion:divListaObjetos");
                RequestContext.getCurrentInstance().update("frmRegistrarEvaluacion:divObejtos");
                RequestContext.getCurrentInstance().update("frmRegistrarEvaluacion:mensajeGeneral");
                session();
            }
        } catch (Exception ex) {
            if (this.transaction != null) {
                transaction.rollback();
            }

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error", ex.getMessage()));
        } finally {
            if (this.session != null) {
                this.session.close();
            }
        }
    }

    public void retirarListaObjetos(Objetos objetos) {
        try {
            int i = 0;

            for (ObjetosVehiculos item : this.listaObjetosVehiculos) {
                if (item.getObjetos().equals(objetos)) {
                    this.listaObjetosVehiculos.remove(i);

                    break;
                }

                i++;
            }

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Correcto", "Objeto retirado de la lista"));

            RequestContext.getCurrentInstance().update("frmRegistrarEvaluacion:divListaObjetos");
            RequestContext.getCurrentInstance().update("frmRegistrarEvaluacion:divObejtos");
            RequestContext.getCurrentInstance().update("frmRegistrarEvaluacion:mensajeGeneral");
            session();

        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error", ex.getMessage()));
        }
    }

    public void registrar() {
        this.session = null;
        this.transaction = null;

        try {
            this.session = HibernateUtil.getSessionFactory().openSession();
            this.transaction = this.session.beginTransaction();

            if (this.propietario.getPlaca() != null /*&& this.flag == true*/) {
                
                if(this.flag == false){
                    DaoPropietario daoPropietario = new DaoPropietario();
                    this.propietario.setTipoVehiculo(this.tipoVehiculo);
                    daoPropietario.register(this.propietario, this.session);
                }

                DaoPropietario daoPropietario = new DaoPropietario();
                DaoEvaluacionEstadoVehicular daoEvaluacionEstadoVehicular = new DaoEvaluacionEstadoVehicular();
                DaoDaniosVehiculo daoDaniosVehiculo = new DaoDaniosVehiculo();
                DaoObjetosVehiculos daoObjetosVehiculos = new DaoObjetosVehiculos();
                java.util.Date fecha = new Date();

                this.propietario = daoPropietario.getByPlaca(this.session, this.propietario.getPlaca());
                this.evaluacionEstadoVehicular.setPropietario(this.propietario);
                this.evaluacionEstadoVehicular.setFecha(fecha);
                this.evaluacionEstadoVehicular.setHora(fecha);
                this.evaluacionEstadoVehicular.setPlaca(this.propietario.getPlaca());

                HttpSession httpSession = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
                this.evaluacionEstadoVehicular.setUsuario(httpSession.getAttribute("usuario").toString());

                daoEvaluacionEstadoVehicular.register(this.evaluacionEstadoVehicular, this.session);
                this.evaluacionEstadoVehicular = daoEvaluacionEstadoVehicular.getUltimoRegistro(this.session);

                for (DaniosVehiculo item : this.listaDaniosVehiculos) {
                    item.setEvaluacionEstadoVehicular(this.evaluacionEstadoVehicular);
                    daoDaniosVehiculo.register(this.session, item);
                }

                for (ObjetosVehiculos item : this.listaObjetosVehiculos) {
                    item.setEvaluacionEstadoVehicular(this.evaluacionEstadoVehicular);
                    daoObjetosVehiculos.register(this.session, item);
                }

                this.transaction.commit();

                this.evaluacionEstadoVehicular = new EvaluacionEstadoVehicular();
                this.objetosVehiculos = new ObjetosVehiculos();
                this.daniosVehiculo = new DaniosVehiculo();
                this.propietario = new Propietario();
                this.tipoVehiculo = new TipoVehiculo();
                this.parametroEvaluacion = new ParametroEvaluacion();
                this.parteVehiculo = new ParteVehiculo();
                this.objetos = new Objetos();
                this.evaluacionEstadoVehicular.setPoseeDanios(false);
                this.evaluacionEstadoVehicular.setPoseeObjetos(false);
                this.listaDaniosVehiculos = new ArrayList<>();
                this.listaObjetosVehiculos = new ArrayList<>();
                this.habilitarCampos = true;

                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Correcto", "Evaluacion realizada correctamente"));
                RequestContext.getCurrentInstance().update("frmRegistrarEvaluacion:mensajeGeneral");
                RequestContext.getCurrentInstance().update("frmRegistrarEvaluacion");
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Debe validar la placa del vehiculo"));
                RequestContext.getCurrentInstance().update("frmRegistrarEvaluacion:mensajeGeneral");
                RequestContext.getCurrentInstance().update("frmRegistrarEvaluacion");
            }

        } catch (Exception ex) {
            if (this.transaction != null) {
                transaction.rollback();
            }

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error", ex.getMessage()));
        } finally {
            if (this.session != null) {
                this.session.close();
            }
        }

    }

    public List<EvaluacionEstadoVehicular> getall() {

        this.session = null;
        this.transaction = null;

        try {
            DaoEvaluacionEstadoVehicular daoEvaluacionEstadoVehicular = new DaoEvaluacionEstadoVehicular();

            this.session = HibernateUtil.getSessionFactory().openSession();
            this.transaction = session.beginTransaction();

            HttpSession httpSession = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
            java.util.Date fecha = new Date();

            this.listaEvaluacionEstadoVehicular = daoEvaluacionEstadoVehicular.getall(this.session, fecha, httpSession.getAttribute("usuario").toString());
            this.transaction.commit();

            return listaEvaluacionEstadoVehicular;

        } catch (Exception ex) {
            if (this.transaction != null) {
                this.transaction.rollback();
            }

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error fatal", ex.getMessage() + " Contacte al administrador"));

            return null;
        } finally {
            if (this.transaction != null) {
                this.session.close();
            }
        }
    }

    public void mosrarDetalle(int Id) {
        this.session = null;
        this.transaction = null;

        try {

            this.session = HibernateUtil.getSessionFactory().openSession();
            this.transaction = session.beginTransaction();

            DaoPropietario daoPropietario = new DaoPropietario();
            DaoEvaluacionEstadoVehicular daoEvaluacionEstadoVehicular = new DaoEvaluacionEstadoVehicular();
            DaoDaniosVehiculo daoDaniosVehiculo = new DaoDaniosVehiculo();
            DaoObjetosVehiculos daoObjetosVehiculos = new DaoObjetosVehiculos();

            this.evaluacionEstadoVehicular = daoEvaluacionEstadoVehicular.getById(this.session, Id);
            this.propietario = daoPropietario.getByIdPropietario(this.session, this.evaluacionEstadoVehicular.getPropietario().getId());
            this.tipoVehiculo.setVehiculo(this.propietario.getTipoVehiculo().getVehiculo());

            this.listaDaniosVehiculos = daoDaniosVehiculo.getAllByIdEvaluacion(this.session, Id);
            this.listaObjetosVehiculos = daoObjetosVehiculos.getAllByIdEvaluacion(this.session, Id);

            //RequestContext.getCurrentInstance().update("frmDetalle:panelDetalle");
            RequestContext.getCurrentInstance().update("frmDetalle");
           // RequestContext.getCurrentInstance().update("frmDetalle:panelListaDanios");
           // RequestContext.getCurrentInstance().update("frmDetalle:panelListaObjetos");
            RequestContext.getCurrentInstance().execute("PF('dialogoDetalle').show()");

            this.transaction.commit();

        } catch (Exception ex) {
            if (this.transaction != null) {
                this.transaction.rollback();
            }

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error fatal", ex.getMessage() + " Contacte al administrador"));
        } finally {
            if (this.transaction != null) {
                this.session.close();
            }
        }
    }

    public String retornaNombreDanio(int id) throws Exception {

        this.session = null;
        this.transaction = null;
        try {
            this.session = HibernateUtil.getSessionFactory().openSession();
            this.transaction = session.beginTransaction();
            DaoTipoDanio daoTipoDanio = new DaoTipoDanio();
            this.parametroEvaluacion = daoTipoDanio.getById(this.session, id);
            this.transaction.commit();
            return parametroEvaluacion.getNombre();
        } catch (Exception ex) {
            if (this.transaction != null) {
                this.transaction.rollback();
            }
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error fatal", ex.getMessage() + " Contacte al administrador"));
            return null;
        } finally {
            if (this.transaction != null) {
                this.session.close();
            }
        }
    }

    public String retornaNombreParteVehiculo(int id) throws Exception {

        this.session = null;
        this.transaction = null;
        try {
            this.session = HibernateUtil.getSessionFactory().openSession();
            this.transaction = session.beginTransaction();
            DaoParteVehiculo daoParteVehiculo = new DaoParteVehiculo();
            this.parteVehiculo = daoParteVehiculo.getById(this.session, id);
            this.transaction.commit();
            return parteVehiculo.getNombre();
        } catch (Exception ex) {
            if (this.transaction != null) {
                this.transaction.rollback();
            }
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error fatal", ex.getMessage() + " Contacte al administrador"));
            return null;
        } finally {
            if (this.transaction != null) {
                this.session.close();
            }
        }
    }

    public String retornaNombreObejto(int id) throws Exception {

        this.session = null;
        this.transaction = null;
        try {
            this.session = HibernateUtil.getSessionFactory().openSession();
            this.transaction = session.beginTransaction();
            DaoObjetos daoObjetos = new DaoObjetos();
            this.objetos = daoObjetos.getById(this.session, id);
            this.transaction.commit();
            return objetos.getNombre();
        } catch (Exception ex) {
            if (this.transaction != null) {
                this.transaction.rollback();
            }
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error fatal", ex.getMessage() + " Contacte al administrador"));
            return null;
        } finally {
            if (this.transaction != null) {
                this.session.close();
            }
        }
    }

    private String getRandomImageName() {
        int i = (int) (Math.random() * 1000000000);

        return String.valueOf(i);
    }

    public void oncaptureDanios(CaptureEvent captureEvent) {
        this.foto = getRandomImageName();
        byte[] data = captureEvent.getData();

        ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        String newFileName = servletContext.getRealPath("") + File.separator + "resources" + File.separator + "images" + File.separator + "evaluar" + File.separator + "danios" + File.separator + foto + ".png";

        this.rutaFoto = this.foto + ".png";
        this.bandera1 = true;

        FileImageOutputStream imageOutput;
        try {
            imageOutput = new FileImageOutputStream(new File(newFileName));
            imageOutput.write(data, 0, data.length);
            imageOutput.close();
        } catch (IOException e) {
            throw new FacesException("Error in writing captured image.", e);
        }
    }

    public void oncaptureObjetos(CaptureEvent captureEvent) {
        this.foto = getRandomImageName();
        byte[] data = captureEvent.getData();

        ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        String newFileName = servletContext.getRealPath("") + File.separator + "resources" + File.separator + "images" + File.separator + "evaluar" + File.separator + "objetos" + File.separator + foto + ".png";

        this.rutaFoto = this.foto + ".png";
        this.bandera2 = true;

        FileImageOutputStream imageOutput;
        try {
            imageOutput = new FileImageOutputStream(new File(newFileName));
            imageOutput.write(data, 0, data.length);
            imageOutput.close();
        } catch (IOException e) {
            throw new FacesException("Error in writing captured image.", e);
        }
    }
    ////////////////////////////////////////////////////////////////////////////

    public EvaluacionEstadoVehicular getEvaluacionEstadoVehicular() {
        return evaluacionEstadoVehicular;
    }

    public void setEvaluacionEstadoVehicular(EvaluacionEstadoVehicular evaluacionEstadoVehicular) {
        this.evaluacionEstadoVehicular = evaluacionEstadoVehicular;
    }

    public ObjetosVehiculos getObjetosVehiculos() {
        return objetosVehiculos;
    }

    public void setObjetosVehiculos(ObjetosVehiculos objetosVehiculos) {
        this.objetosVehiculos = objetosVehiculos;
    }

    public DaniosVehiculo getDaniosVehiculo() {
        return daniosVehiculo;
    }

    public void setDaniosVehiculo(DaniosVehiculo daniosVehiculo) {
        this.daniosVehiculo = daniosVehiculo;
    }

    public Propietario getPropietario() {
        return propietario;
    }

    public void setPropietario(Propietario propietario) {
        this.propietario = propietario;
    }

    public TipoVehiculo getTipoVehiculo() {
        return tipoVehiculo;
    }

    public void setTipoVehiculo(TipoVehiculo tipoVehiculo) {
        this.tipoVehiculo = tipoVehiculo;
    }

    public List<EvaluacionEstadoVehicular> getListaEvaluacionEstadoVehicular() {
        return listaEvaluacionEstadoVehicular;
    }

    public void setListaEvaluacionEstadoVehicular(List<EvaluacionEstadoVehicular> listaEvaluacionEstadoVehicular) {
        this.listaEvaluacionEstadoVehicular = listaEvaluacionEstadoVehicular;
    }

    public List<EvaluacionEstadoVehicular> getListaEvaluacionEstadoVehicularFiltrada() {
        return listaEvaluacionEstadoVehicularFiltrada;
    }

    public void setListaEvaluacionEstadoVehicularFiltrada(List<EvaluacionEstadoVehicular> listaEvaluacionEstadoVehicularFiltrada) {
        this.listaEvaluacionEstadoVehicularFiltrada = listaEvaluacionEstadoVehicularFiltrada;
    }

    public List<DaniosVehiculo> getListaDaniosVehiculos() {
        return listaDaniosVehiculos;
    }

    public void setListaDaniosVehiculos(List<DaniosVehiculo> listaDaniosVehiculos) {
        this.listaDaniosVehiculos = listaDaniosVehiculos;
    }

    public ParametroEvaluacion getParametroEvaluacion() {
        return parametroEvaluacion;
    }

    public void setParametroEvaluacion(ParametroEvaluacion parametroEvaluacion) {
        this.parametroEvaluacion = parametroEvaluacion;
    }

    public ParteVehiculo getParteVehiculo() {
        return parteVehiculo;
    }

    public void setParteVehiculo(ParteVehiculo parteVehiculo) {
        this.parteVehiculo = parteVehiculo;
    }

    public Objetos getObjetos() {
        return objetos;
    }

    public void setObjetos(Objetos objetos) {
        this.objetos = objetos;
    }

    public List<ObjetosVehiculos> getListaObjetosVehiculos() {
        return listaObjetosVehiculos;
    }

    public void setListaObjetosVehiculos(List<ObjetosVehiculos> listaObjetosVehiculos) {
        this.listaObjetosVehiculos = listaObjetosVehiculos;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public Boolean getBandera1() {
        return bandera1;
    }

    public void setBandera1(Boolean bandera1) {
        this.bandera1 = bandera1;
    }

    public Boolean getBandera2() {
        return bandera2;
    }

    public void setBandera2(Boolean bandera2) {
        this.bandera2 = bandera2;
    }

    public String getFechaM() {
        return fechaM;
    }

    public void setFechaM(String fechaM) {
        this.fechaM = fechaM;
    }

    public Boolean getHabilitarCampos() {
        return habilitarCampos;
    }

    public void setHabilitarCampos(Boolean habilitarCampos) {
        this.habilitarCampos = habilitarCampos;
    }
    
    

}
