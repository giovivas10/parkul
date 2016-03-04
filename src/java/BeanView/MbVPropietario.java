/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BeanView;

import Dao.DaoPropietario;
import Dao.DaoTipoUsuario;
import Dao.DaoTipoVehiculo;
import HibernateUtil.HibernateUtil;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Calendar;
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
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
import pojo.Propietario;
import pojo.TipoUsuario;
import pojo.TipoVehiculo;

/**
 *
 * @author Giovanni
 */
@ManagedBean
@ViewScoped
public class MbVPropietario {

    /**
     * Creates a new instance of MbVPropietario
     */
    private Session session;
    private Transaction transaction;

    private Propietario propietario;
    private TipoUsuario tipoUsuario;
    private TipoVehiculo tipoVehiculo;

    private List<Propietario> listaPropietario;
    private List<Propietario> listaPropietarioFiltrado;

    private String foto;
    private String rutaFoto;
    private UploadedFile tarjetaPropiedad;
    private String TP;

    public MbVPropietario() {
        this.propietario = new Propietario();
        this.tipoUsuario = new TipoUsuario();
        this.tipoVehiculo = new TipoVehiculo();

        Calendar fecha = Calendar.getInstance();
        this.propietario.setModelo(fecha.getWeekYear());

        this.tarjetaPropiedad = null;

        this.TP = null;

        session();
    }

    ///////////////////////////////////////////////////////////////////////////
    //FUNCIONES
    public final void session() {
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

    public void register() throws Exception {

        this.session = null;
        this.transaction = null;
        Pattern pattern;
        Matcher matcher = null;

        try {
            this.session = HibernateUtil.getSessionFactory().openSession();
            this.transaction = session.beginTransaction();
            DaoPropietario daoPropietario = new DaoPropietario();

            if (daoPropietario.getByPlaca(this.session, this.propietario.getPlaca()) != null) {

                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Esta placa ya se encuentra registrada en el sistema"));
                return;
            }

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

            DaoTipoUsuario daoTipoUsuario = new DaoTipoUsuario();
            DaoTipoVehiculo daoTipoVehiculo = new DaoTipoVehiculo();
            ///////////////////////////////////////////////////////////
            //Asignacion del Id del rol al pojo del rol
            this.propietario.setTipoUsuario(daoTipoUsuario.getById(this.session, this.tipoUsuario.getId()));
            this.propietario.setTipoVehiculo(daoTipoVehiculo.getById(this.session, this.tipoVehiculo.getId()));
            this.propietario.setFotoPropietario(this.rutaFoto);
            this.propietario.setTargetaPropiedad(this.TP);
            //////////////////////////////////////////////////////////

            daoPropietario.register(this.propietario, this.session);

            this.transaction.commit();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Correcto", "Se ha registrado el propietario correctamente"));

            this.propietario = new Propietario();
            this.tipoUsuario.setId(-1);
            this.tipoVehiculo.setId(-1);
            Calendar fecha = Calendar.getInstance();
            this.propietario.setModelo(fecha.getWeekYear());
            this.rutaFoto = new String();
            this.foto = new String();
            this.TP = new String();
            RequestContext.getCurrentInstance().update("frmRegistrarUsuario");

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

    public List<Propietario> getall() {

        this.session = null;
        this.transaction = null;

        try {
            DaoPropietario daoPropietario = new DaoPropietario();

            this.session = HibernateUtil.getSessionFactory().openSession();
            this.transaction = session.beginTransaction();

            this.listaPropietario = daoPropietario.getAll(this.session);
            this.transaction.commit();

            return listaPropietario;

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

    public void cargaUsuarioEditar(int Id) {
        this.session = null;
        this.transaction = null;

        try {

            DaoPropietario daoPropietario = new DaoPropietario();
            this.session = HibernateUtil.getSessionFactory().openSession();
            this.transaction = session.beginTransaction();

            this.propietario = daoPropietario.getByIdPropietario(this.session, Id);
            if (this.propietario.getTipoUsuario() != null) {
                this.tipoUsuario.setId(this.propietario.getTipoUsuario().getId());
            }
            if (this.propietario.getTipoVehiculo() != null) {
                this.tipoVehiculo.setId(this.propietario.getTipoVehiculo().getId());
            }

            RequestContext.getCurrentInstance().update("frmEditarPropietario:panelActualizarPropietario");
            RequestContext.getCurrentInstance().execute("PF('dialogoEditarPropietario').show()");

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

    public void update() throws Exception {
        this.session = null;
        this.transaction = null;

        try {

            DaoPropietario daoPropietario = new DaoPropietario();
            DaoTipoUsuario daoTipoUsuario = new DaoTipoUsuario();
            DaoTipoVehiculo daoTipoVehiculo = new DaoTipoVehiculo();

            this.session = HibernateUtil.getSessionFactory().openSession();
            this.transaction = session.beginTransaction();

            ///////////////////////////////////////////////////////////
            //Asignacion del Id del rol al pojo del rol
            if (this.tipoUsuario.getId() != null) {
                this.propietario.setTipoUsuario(daoTipoUsuario.getById(this.session, this.tipoUsuario.getId()));
            }
            if (this.tipoVehiculo.getId() != null) {
                this.propietario.setTipoVehiculo(daoTipoVehiculo.getById(this.session, this.tipoVehiculo.getId()));
            }
            this.propietario.setFotoPropietario(this.rutaFoto);
            //////////////////////////////////////////////////////////

            daoPropietario.update(this.propietario, this.session);

            this.transaction.commit();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Correcto", "Se ha actualizado el propietario correctamente"));

            this.propietario = new Propietario();
            this.tipoUsuario.setId(-1);
            this.tipoVehiculo.setId(-1);
            Calendar fecha = Calendar.getInstance();
            this.propietario.setModelo(fecha.getWeekYear());
            this.rutaFoto = new String();
            RequestContext.getCurrentInstance().execute("PF('dialogoEditarPropietario').hide()");

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

    public void delete(int Id) throws Exception {

        this.session = null;
        this.transaction = null;

        try {

            DaoPropietario daoPropietario = new DaoPropietario();

            this.session = HibernateUtil.getSessionFactory().openSession();
            this.transaction = session.beginTransaction();

            this.propietario = daoPropietario.getByIdPropietario(this.session, Id);
            daoPropietario.delete(this.propietario, this.session);

            this.transaction.commit();

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Correcto", "Informacion Eliminada"));

            RequestContext.getCurrentInstance().update("frmListaPropietario");
            this.propietario = new Propietario();
            this.tipoUsuario.setId(-1);
            this.tipoVehiculo.setId(-1);
            Calendar fecha = Calendar.getInstance();
            this.propietario.setModelo(fecha.getWeekYear());
            this.rutaFoto = new String();
            this.foto = new String();

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

    private String getRandomImageName() {
        int i = (int) (Math.random() * 1000000000);

        return String.valueOf(i);
    }

    public void oncapture(CaptureEvent captureEvent) {
        this.foto = getRandomImageName();
        byte[] data = captureEvent.getData();

        ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        String newFileName = servletContext.getRealPath("") + File.separator + "resources" + File.separator + "images" + File.separator + "propietario" + File.separator + foto + ".png";

        this.rutaFoto = this.foto + ".png";

        FileImageOutputStream imageOutput;
        try {
            imageOutput = new FileImageOutputStream(new File(newFileName));
            imageOutput.write(data, 0, data.length);
            imageOutput.close();
        } catch (IOException e) {
            throw new FacesException("Error in writing captured image.", e);
        }
    }

    public void subirTarjetaPropiedad() throws IOException {
        InputStream inputStream = null;
        OutputStream outputStream = null;
        String ruta;

        try {
            if (this.tarjetaPropiedad == null) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", "Debe seleccionar un archivo"));
                return;
            }

            if (this.tarjetaPropiedad.getSize() <= 0) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", "Ud. debe seleccionar un archivo de imagen \".png\""));
                return;
            }

            if (this.tarjetaPropiedad.getSize() > 2097152) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", "El archivo no puede ser más de 2mb"));
                return;
            }

            ruta = getRandomImageName() + tarjetaPropiedad.getFileName();

            ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
            String carpeta = servletContext.getRealPath("") + File.separator + "resources" + File.separator + "images" + File.separator + "TP" + File.separator + ruta;

            //String carpetaAvatar=(String)servletContext.getRealPath("/avatar");
            outputStream = new FileOutputStream(new File(carpeta));
            inputStream = this.tarjetaPropiedad.getInputstream();
            this.TP = ruta;

            int read = 0;
            byte[] bytes = new byte[1024];

            while ((read = inputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, read);
            }

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Correcto:", "Tarjeta subida exitosamente"));
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error fatal:", "Por favor contacte con su administrador " + ex.getMessage()));
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }

            if (outputStream != null) {
                outputStream.close();
            }
        }

        session();
    }
    //////////////////////////////////////////////////////////////////////////

    /////////////////////////////////////////////////////////////////////////
    //GETTERS AND SETTERS
    public Propietario getPropietario() {
        return propietario;
    }

    public void setPropietario(Propietario propietario) {
        this.propietario = propietario;
    }

    public TipoUsuario getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(TipoUsuario tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    public TipoVehiculo getTipoVehiculo() {
        return tipoVehiculo;
    }

    public void setTipoVehiculo(TipoVehiculo tipoVehiculo) {
        this.tipoVehiculo = tipoVehiculo;
    }

    public List<Propietario> getListaPropietario() {
        return listaPropietario;
    }

    public void setListaPropietario(List<Propietario> listaPropietario) {
        this.listaPropietario = listaPropietario;
    }

    public List<Propietario> getListaPropietarioFiltrado() {
        return listaPropietarioFiltrado;
    }

    public void setListaPropietarioFiltrado(List<Propietario> listaPropietarioFiltrado) {
        this.listaPropietarioFiltrado = listaPropietarioFiltrado;
    }

    //////////////////////////////////////////////////////////////////////////
    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public UploadedFile getTarjetaPropiedad() {
        return tarjetaPropiedad;
    }

    public void setTarjetaPropiedad(UploadedFile tarjetaPropiedad) {
        this.tarjetaPropiedad = tarjetaPropiedad;
    }

}
