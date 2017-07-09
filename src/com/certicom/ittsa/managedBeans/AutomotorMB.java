package com.certicom.ittsa.managedBeans;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;

import org.primefaces.context.RequestContext;

import com.certicom.ittsa.domain.Automotor;
import com.certicom.ittsa.domain.Log;
import com.certicom.ittsa.domain.Oficina;
import com.certicom.ittsa.services.AutomotorService;
import com.certicom.ittsa.services.AutoparteService;
import com.certicom.ittsa.services.MenuServices;
import com.certicom.ittsa.services.OficinaService;
import com.pe.certicom.ittsa.commons.Constante;
import com.pe.certicom.ittsa.commons.FacesUtils;
import com.pe.certicom.ittsa.commons.GenericBeans;

@ManagedBean(name="automotorMB")
@ViewScoped
public class AutomotorMB extends GenericBeans implements Serializable{
	private String titulo;
	private Automotor automotor;
	private List<Automotor> listaAutomotors;
	private List<Automotor> listaFiltroAutomotors;
	private Boolean editar;
	
	//services
	private AutomotorService automotorSevice;
	private AutoparteService autoparteService;
	private MenuServices menuServices;
	
	//datos Log
    private Log log;
	private LogMB logmb;
	
	public AutomotorMB(){;}
	
	@PostConstruct
	public void incia()
	{
		this.editar = Boolean.FALSE;
		this.autoparteService = new AutoparteService();
		this.automotorSevice = new AutomotorService();
		this.menuServices=new MenuServices();
		this.automotor = new Automotor();
		
		 log = (Log)getSpringBean(Constante.SESSION_LOG);
		logmb = new LogMB();
		//listando
		try {
			this.listaAutomotors = automotorSevice.findAll();
			int codMenu = menuServices.opcionMenuByPretty("pretty:sisAutomotor");
			log.setCod_menu(codMenu);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void cancelar(){
		this.automotor = new Automotor();
	}
	
	public String asociarPartes(Automotor auto){
		String outcome = null;
		try {
    		Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
    		flash.put("automotor", auto);
    		outcome="pretty:autopartesAutomotor";
		} catch (Exception e) {	
			e.printStackTrace();
		}
		return outcome;
	}

	public void guardarAutomotor()
	{
		Boolean valido=Boolean.TRUE;
		RequestContext context = RequestContext.getCurrentInstance();  
   	    context.addCallbackParam("esValido", valido );
   	    
		try {
			automotor.setDescripcion(automotor.getDescripcion().toUpperCase().trim());
			//automotor.setGrupo(automotor.getGrupo().toUpperCase().trim());
			if(this.editar)
			{
				System.out.println("entro ene el editar ");
				this.automotor.setIdAutomotor(this.automotor.getIdAutomotor());
				this.automotorSevice.actualizarAutomotor(automotor);
				 log.setAccion("UPDATE");
	             log.setDescripcion("Se actualiza la automotor: " + automotor.getDescripcion());
	             logmb.insertarLog(log);
				FacesUtils.showFacesMessage("Automotor actualizado correctamente.",Constante.INFORMACION);
				context.update("sms");
			}else
			{
				this.automotorSevice.crearAutomotor(automotor);
				 log.setAccion("INSERT");
	             log.setDescripcion("Se inserta la automotor: " + automotor.getDescripcion());
	             logmb.insertarLog(log);
				 FacesUtils.showFacesMessage("Automotor registrado correctamente.",Constante.INFORMACION);
				 context.update("sms");
			}
			listaAutomotors = this.automotorSevice.findAll();
			

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		this.automotor = new Automotor();
		this.editar =  Boolean.FALSE;
		
	}
	
	public void cambiarEstado(Automotor auto){
		
		   if(auto.isEstado()){
			   //System.out.println("es igual a uno se pone a cero");
			   auto.setEstado(Boolean.FALSE);
			   //sistem.setInd_activo(new Integer(0));
		   }else{
			   //System.out.println("es igual a cero");
			   auto.setEstado(Boolean.TRUE);
			   //sistem.setInd_activo(new Integer(1));
		   }
		   
		   try {
			   this.automotorSevice.actualizarAutomotor(auto);
			   log.setAccion("CHANGE ESTADO");
	           log.setDescripcion("Se cambio el estado en Automotor: " + automotor.getDescripcion());
	           logmb.insertarLog(log);
			   FacesUtils.showFacesMessage("Estado de automotor actualizado correctamente.",Constante.INFORMACION);
		   } catch (Exception e) {
			   System.out.println("Error:"+e.getMessage());
			   e.printStackTrace();
		   }   
	}
	
	public void eliminarAutomotor()
	{
		try {
			RequestContext context = RequestContext.getCurrentInstance();
			
			int cantAutomotors = this.autoparteService.countxAutomotor(automotor.getIdAutomotor());
			
			if(cantAutomotors == 0){
				this.automotorSevice.eliminarAutomotor(automotor.getIdAutomotor());
				listaAutomotors.remove(automotor);
				FacesUtils.showFacesMessage("Automotor eliminado correctamente.",Constante.INFORMACION);
				
				log.setAccion("DELETE");
				log.setDescripcion("Se elimina la Automotor: " + automotor.getDescripcion());
				logmb.insertarLog(log);
				
			} else {
				context.execute("dlgOmiso.show()");
			}
			
			
			

			
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.automotor =  new Automotor();
		
	}
	
	public void editarAutomotor(Automotor ag)
	{
		this.editar = Boolean.TRUE;
		this.automotor = ag;
		this.titulo = "Modificar automotor";
	}
	
	public void nuevaAutomotor()
	{
		this.editar = Boolean.FALSE;
		this.automotor = new Automotor();
		this.titulo = "Registrar nuevo automotor";
	}

	//**set an get 
	
	public MenuServices getMenuServices() {
		return menuServices;
	}

	public void setMenuServices(MenuServices menuServices) {
		this.menuServices = menuServices;
	}

	public List<Automotor> getListaFiltroAutomotors() {
		return listaFiltroAutomotors;
	}

	public Log getLog() {
		return log;
	}

	public void setLog(Log log) {
		this.log = log;
	}

	public LogMB getLogmb() {
		return logmb;
	}

	public void setLogmb(LogMB logmb) {
		this.logmb = logmb;
	}

	public void setListaFiltroAutomotors(List<Automotor> listaFiltroAutomotors) {
		this.listaFiltroAutomotors = listaFiltroAutomotors;
	}

	public Automotor getAutomotor() {
		return automotor;
	}

	public void setAutomotor(Automotor automotor) {
		this.automotor = automotor;
	}

	public List<Automotor> getListaAutomotors() {
		return listaAutomotors;
	}

	public void setListaAutomotors(List<Automotor> listaAutomotors) {
		this.listaAutomotors = listaAutomotors;
	}

	public Boolean getEditar() {
		return editar;
	}

	public void setEditar(Boolean editar) {
		this.editar = editar;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	
	
}

