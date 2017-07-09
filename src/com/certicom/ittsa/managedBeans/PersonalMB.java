package com.certicom.ittsa.managedBeans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.context.RequestContext;

import com.certicom.ittsa.domain.Agencia;
import com.certicom.ittsa.domain.Log;
import com.certicom.ittsa.domain.Personal;
import com.certicom.ittsa.services.AgenciaService;
import com.certicom.ittsa.services.MenuServices;
import com.certicom.ittsa.services.OficinaService;
import com.certicom.ittsa.services.PersonalService;
import com.pe.certicom.ittsa.commons.Constante;
import com.pe.certicom.ittsa.commons.FacesUtils;
import com.pe.certicom.ittsa.commons.GenericBeans;

@ManagedBean(name="personalMB")
@ViewScoped
public class PersonalMB extends GenericBeans implements Serializable{
	private String titulo;
	private Personal personal;
	private List<Personal> listaPersonal;
	private List<Personal> listaFiltroPersonas;
	private Boolean editar;
	
	//services
	private PersonalService personalService;
	private MenuServices menuServices;
	
	//datos Log
    private Log log;
	private LogMB logmb;
	
	public PersonalMB(){
	}
	
	@PostConstruct
	public void incia()
	{
		this.editar = Boolean.FALSE;
		this.personal = new Personal();
		this.listaPersonal = new ArrayList<>();
		
		this.personalService = new PersonalService();
		this.menuServices = new MenuServices();
		log = (Log)getSpringBean(Constante.SESSION_LOG);
		logmb = new LogMB();
		//listando
		try {
			this.listaPersonal = this.personalService.findAll();
			int codMenu = menuServices.opcionMenuByPretty("pretty:personal");
			log.setCod_menu(codMenu);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void registrarPersonal(){
		Boolean valido=Boolean.TRUE;
		RequestContext context = RequestContext.getCurrentInstance();  
   	    context.addCallbackParam("esValido", valido );
   	    
		try {
			if(this.editar)
			{
				this.personalService.actualizarPersonal(this.personal);
				 log.setAccion("UPDATE");
	             log.setDescripcion("Se actualizo el Personal: " + this.personal.getNomcompleto());
	             logmb.insertarLog(log);
				FacesUtils.showFacesMessage("Agencia actualizada correctamente.",Constante.INFORMACION);
				context.update("sms");
			}else
			{
				this.personal.setEstado(Boolean.TRUE);
				this.personalService.crearPersonal(this.personal);
				 log.setAccion("INSERT");
	             log.setDescripcion("Se inserta el Personal: " + this.personal.getNomcompleto());
	             logmb.insertarLog(log);
				 FacesUtils.showFacesMessage("Personal registrada correctamente.",Constante.INFORMACION);
				 context.update("sms");
			}
			listaPersonal = this.personalService.findAll();
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.personal = new Personal();
		this.editar =  Boolean.FALSE;
		this.titulo = "Registrar nuevo personal";
	}
	
	public void cambiarEstado(Personal personal){
		try {
			RequestContext context = RequestContext.getCurrentInstance();
			if(personal.isEstado() == true){
				personal.setEstado(Boolean.FALSE);	
			}else{
				personal.setEstado(Boolean.TRUE);	
			}
			FacesUtils.showFacesMessage("Estado de personal actualizado correctamente.",Constante.INFORMACION);
			context.update("sms");
			this.personalService.actualizarPersonal(personal);
		} catch (Exception e) {
		}
	}
	
	public void editarPersonal(Personal personal){
		this.editar = Boolean.TRUE;
		this.personal = personal;
		this.titulo = "Modificar personal";
	}
	
	public void eliminarPersonal(){
		try {
			RequestContext context = RequestContext.getCurrentInstance();
			System.out.println("el personal es   " + this.personal.getIdPersonal());
			this.personalService.deletePersonal(this.personal.getIdPersonal());
			FacesUtils.showFacesMessage("Personal eliminado correctamente.",Constante.INFORMACION);
			context.update("sms");
			
			log.setAccion("DELETE");
			log.setDescripcion("Se eliminó el Personal: " + this.personal.getNomcompleto());
			logmb.insertarLog(log);
			listaPersonal = this.personalService.findAll();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	//set an get
	public Personal getPersonal() {
		return personal;
	}

	public void setPersonal(Personal personal) {
		this.personal = personal;
	}

	public List<Personal> getListaFiltroPersonas() {
		return listaFiltroPersonas;
	}

	public void setListaFiltroPersonas(List<Personal> listaFiltroPersonas) {
		this.listaFiltroPersonas = listaFiltroPersonas;
	}

	public Boolean getEditar() {
		return editar;
	}

	public void setEditar(Boolean editar) {
		this.editar = editar;
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

	public List<Personal> getListaPersonal() {
		return listaPersonal;
	}

	public void setListaPersonal(List<Personal> listaPersonal) {
		this.listaPersonal = listaPersonal;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	
}

