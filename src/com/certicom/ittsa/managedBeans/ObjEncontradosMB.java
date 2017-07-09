package com.certicom.ittsa.managedBeans;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRParameter;

import org.apache.ibatis.exceptions.PersistenceException;
import org.primefaces.context.RequestContext;
import org.springframework.dao.DataAccessException;

import com.certicom.ittsa.domain.Log;
import com.certicom.ittsa.domain.ObjEncontrados;
import com.certicom.ittsa.domain.Programacion;
import com.certicom.ittsa.domain.Usuario;
import com.certicom.ittsa.services.MenuServices;
import com.certicom.ittsa.services.ObjEncontradosService;
import com.pe.certicom.ittsa.commons.Constante;
import com.pe.certicom.ittsa.commons.ExportarArchivo;
import com.pe.certicom.ittsa.commons.FacesUtils;
import com.pe.certicom.ittsa.commons.GenericBeans;

@ManagedBean(name="objEncontradosMB")
@ViewScoped
public class ObjEncontradosMB extends GenericBeans {

	private ObjEncontrados objEncontrados;
	private List<ObjEncontrados> listaObjEncontrados;
	private List<ObjEncontrados> listaObjEncontradosFilter;
	private Programacion programacion;
	
	private ObjEncontradosService objEncontradosService;
	private MenuServices menuServices;
	
	@ManagedProperty(value="#{loginMB.usuario}")
	private Usuario usuarioLogin;
	
	//datos Log
    private Log log;
	private LogMB logmb;
	private Boolean editar;
	
	
	@PostConstruct
	public void inicia(){
		
		Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
		this.programacion =(Programacion) flash.get("programacion");
		
		this.objEncontradosService = new ObjEncontradosService();
		this.menuServices = new MenuServices();
		this.objEncontrados = new ObjEncontrados();
		
		log = (Log)getSpringBean(Constante.SESSION_LOG);
		logmb = new LogMB();
		
		try {
			listaObjEncontrados = this.objEncontradosService.findByProgramacion(this.programacion.getIdProgramacion());
			int codMenu = menuServices.opcionMenuByPretty("pretty:registroLlegadaBus");
			log.setCod_menu(codMenu);
		} catch (Exception e) {
			System.out.println("Error :"+ e.getMessage());
			e.printStackTrace();
		}
		

	}
	
	
	public void nuevoObjEncontrados(){
		this.objEncontrados = new ObjEncontrados();
		this.editar = Boolean.FALSE;
	}
	
	public void editarObjEncontrados(ObjEncontrados com){
		this.objEncontrados = com;
		this.editar = Boolean.TRUE;
	}
	
	public void guardarObjEncontrados()
	{
		Boolean valido=Boolean.TRUE;
		RequestContext context = RequestContext.getCurrentInstance();  
   	    context.addCallbackParam("esValido", valido );
   	    
		try {
			if(this.editar)
			{
				this.objEncontrados.setEstado("DEVUELTO");
				this.objEncontrados.setFechaEntrega(new Date());
				this.objEncontrados.setUsuarioEntrega(getUsuarioLogin().getIdusuario()+"");
				this.objEncontradosService.actualizarObjEncontrados(objEncontrados);
				 log.setAccion("UPDATE");
	             log.setDescripcion("Se actualiza el objeto encontrado: " + objEncontrados.getIdObjeto() +"-" + objEncontrados.getDescripcion());
	             logmb.insertarLog(log);
				FacesUtils.showFacesMessage("Objeto actualizado correctamente.",Constante.INFORMACION);
				context.update("msgGeneral");
			}else
			{
				this.objEncontrados.setIdProgramacion(this.programacion.getIdProgramacion());
				this.objEncontrados.setfCreacion(new Date());
				this.objEncontrados.setEstado("NO DEVUELTO");
				System.out.println(objEncontrados.getDescripcion());
				this.objEncontradosService.crearObjEncontrados(objEncontrados);
				 log.setAccion("INSERT");
	             log.setDescripcion("Se inserta el objeto : " + objEncontrados.getDescripcion()  + " a la programacion " + this.programacion.getIdProgramacion());
	             logmb.insertarLog(log);
				 FacesUtils.showFacesMessage("Objeto registrado correctamente.",Constante.INFORMACION);
				 context.update("msgGeneral");
			}
			listaObjEncontrados = this.objEncontradosService.findByProgramacion(this.programacion.getIdProgramacion());
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		this.objEncontrados = new ObjEncontrados();
		this.editar =  Boolean.FALSE;
		
	}
	
	public void eliminarObjEncontrados()
	{
		try {
				this.objEncontradosService.eliminarObjEncontrados(this.objEncontrados.getIdObjeto());
				listaObjEncontrados.remove(objEncontrados);
				FacesUtils.showFacesMessage("Objeto eliminado correctamente.",Constante.INFORMACION);
				
				log.setAccion("DELETE");
				log.setDescripcion("Se elimina el Objeto : " + objEncontrados.getDescripcion() +" de la programacion " + this.programacion.getIdProgramacion());
				logmb.insertarLog(log);

		} catch (Exception e) {
			e.printStackTrace();
		}
		this.objEncontrados =  new ObjEncontrados();
		
	}
	

	/** SET AND GET **/
	public ObjEncontrados getObjEncontrados() {
		return objEncontrados;
	}


	public List<ObjEncontrados> getListaObjEncontrados() {
		return listaObjEncontrados;
	}


	public List<ObjEncontrados> getListaObjEncontradosFilter() {
		return listaObjEncontradosFilter;
	}


	public Programacion getProgramacion() {
		return programacion;
	}


	public Boolean getEditar() {
		return editar;
	}


	public void setObjEncontrados(ObjEncontrados objEncontrados) {
		this.objEncontrados = objEncontrados;
	}


	public void setListaObjEncontrados(List<ObjEncontrados> listaObjEncontrados) {
		this.listaObjEncontrados = listaObjEncontrados;
	}


	public void setListaObjEncontradosFilter(
			List<ObjEncontrados> listaObjEncontradosFilter) {
		this.listaObjEncontradosFilter = listaObjEncontradosFilter;
	}


	public void setProgramacion(Programacion programacion) {
		this.programacion = programacion;
	}


	public void setEditar(Boolean editar) {
		this.editar = editar;
	}


	public Usuario getUsuarioLogin() {
		return usuarioLogin;
	}


	public void setUsuarioLogin(Usuario usuarioLogin) {
		this.usuarioLogin = usuarioLogin;
	}
	
	
	
	/*#######################---getters y setters----------########################*/
	
	

	
	
}
