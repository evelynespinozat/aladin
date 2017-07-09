package com.certicom.ittsa.managedBeans;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.context.RequestContext;

import com.certicom.ittsa.domain.Agencia;
import com.certicom.ittsa.domain.Frecuencia;
import com.certicom.ittsa.domain.Log;
import com.certicom.ittsa.domain.Turno;
import com.certicom.ittsa.services.AgenciaService;
import com.certicom.ittsa.services.FrecuenciaService;
import com.certicom.ittsa.services.MenuServices;
import com.pe.certicom.ittsa.commons.Constante;
import com.pe.certicom.ittsa.commons.FacesUtils;
import com.pe.certicom.ittsa.commons.GenericBeans;

@ManagedBean(name="frecuenciaMB")
@ViewScoped
public class FrecuenciaMB extends GenericBeans implements Serializable{
	private String titulo;
	private Frecuencia frecuencia;
	private List<Frecuencia> listaFrecuencia;
	private List<Frecuencia> listaFiltroFrecuencia;
	private Boolean editar;
	
	//services
	private FrecuenciaService frecuenciaService;
	private MenuServices menuServices;
	
	//datos Log
    private Log log;
	private LogMB logmb;
	
	public FrecuenciaMB(){;}
	
	@PostConstruct
	public void incia()
	{
		this.editar = Boolean.FALSE;
	
		this.frecuenciaService = new FrecuenciaService();
		this.menuServices=new MenuServices();
		this.frecuencia = new Frecuencia();
		
		 log = (Log)getSpringBean(Constante.SESSION_LOG);
		logmb = new LogMB();
		//listando
		try {
			this.listaFrecuencia = frecuenciaService.findAll();
			int codMenu = menuServices.opcionMenuByPretty("pretty:frecuencia");
			log.setCod_menu(codMenu);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void cancelar(){
		this.frecuencia = new Frecuencia();
	}
	
	public void nuevaFrecuencia(){
		this.frecuencia = new Frecuencia();
		this.editar = Boolean.FALSE;
		this.titulo = "Registrar nueva frecuencia";
	}

	public void editarFrecuencia(Frecuencia fre){
		this.frecuencia = fre;
		this.editar = Boolean.TRUE;
		this.titulo = "Modificar frecuencia";
	}

	public void guardarFrecuencia()
	{
		 Boolean valido=Boolean.TRUE;
		RequestContext context = RequestContext.getCurrentInstance();  
   	 context.addCallbackParam("esValido", valido );
		try {
			frecuencia.setDescripcion(frecuencia.getDescripcion().toUpperCase().trim());
			if(this.editar)
			{
				this.frecuencia.setIdFrecuencia(this.frecuencia.getIdFrecuencia());
				this.frecuenciaService.actualizarFrecuencia(frecuencia);
				 log.setAccion("UPDATE");
	             log.setDescripcion("Se actualiza la Frecuencia: " + frecuencia.getDescripcion());
	             logmb.insertarLog(log);
				FacesUtils.showFacesMessage("Frecuencia actualizada correctamente.",Constante.INFORMACION);
				 context.update("sms");
			}else
			{
				this.frecuencia.setEstado(Boolean.TRUE);
				this.frecuenciaService.crearFrecuencia(frecuencia);
				 log.setAccion("INSERT");
	             log.setDescripcion("Se inserta la Frecuencia: " + frecuencia.getDescripcion());
	             logmb.insertarLog(log);
				FacesUtils.showFacesMessage("Frecuencia registrada correctamente.",Constante.INFORMACION);
				context.update("sms");
			}
			listaFrecuencia = this.frecuenciaService.findAll();
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		this.frecuencia = new Frecuencia();
		this.editar =  Boolean.FALSE;
		
	}
	
	public void cambiarEstado(Frecuencia frecuen){
		
		   if(frecuen.isEstado()){
			   //System.out.println("es igual a uno se pone a cero");
			   frecuen.setEstado(Boolean.FALSE);
			   //sistem.setInd_activo(new Integer(0));
		   }else{
			   //System.out.println("es igual a cero");
			   frecuen.setEstado(Boolean.TRUE);
			   //sistem.setInd_activo(new Integer(1));
		   }
		   
		   try {
			   this.frecuenciaService.actualizarFrecuencia(frecuen);
			   log.setAccion("CHANGE ESTADO");
	           log.setDescripcion("Se cambio el estado en frecuencia: " + frecuencia.getDescripcion());
	           logmb.insertarLog(log);
			   FacesUtils.showFacesMessage("Estado de frecuencia actualizado correctamente.",Constante.INFORMACION);
		   } catch (Exception e) {
			   System.out.println("Error:"+e.getMessage());
			   e.printStackTrace();
		   }   
	}
	
	public void eliminarFrecuencia()
	{
		try {
			RequestContext context = RequestContext.getCurrentInstance();
        	
			int cant = this.frecuenciaService.cantServicios(this.frecuencia.getIdFrecuencia());
			if(cant==0){
			    this.frecuenciaService.eliminarFrecuencia(frecuencia.getIdFrecuencia());
				listaFrecuencia.remove(frecuencia);
				FacesUtils.showFacesMessage("Frecuencia eliminada correctamente.",Constante.INFORMACION);
				
				log.setAccion("DELETE");
				log.setDescripcion("Se elimina la Frecuencia: " + frecuencia.getDescripcion());
				logmb.insertarLog(log);
			} else {
				context.execute("dlgOmiso.show()");
			}
			

			
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.frecuencia =  new Frecuencia();
		
	}

	//**set an get 
	
	
	
	public MenuServices getMenuServices() {
		return menuServices;
	}

	public Frecuencia getFrecuencia() {
		return frecuencia;
	}

	public void setFrecuencia(Frecuencia frecuencia) {
		this.frecuencia = frecuencia;
	}

	public List<Frecuencia> getListaFrecuencia() {
		return listaFrecuencia;
	}

	public void setListaFrecuencia(List<Frecuencia> listaFrecuencia) {
		this.listaFrecuencia = listaFrecuencia;
	}

	public List<Frecuencia> getListaFiltroFrecuencia() {
		return listaFiltroFrecuencia;
	}

	public void setListaFiltroFrecuencia(List<Frecuencia> listaFiltroFrecuencia) {
		this.listaFiltroFrecuencia = listaFiltroFrecuencia;
	}

	public FrecuenciaService getFrecuenciaService() {
		return frecuenciaService;
	}

	public void setFrecuenciaService(FrecuenciaService frecuenciaService) {
		this.frecuenciaService = frecuenciaService;
	}

	public void setMenuServices(MenuServices menuServices) {
		this.menuServices = menuServices;
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

