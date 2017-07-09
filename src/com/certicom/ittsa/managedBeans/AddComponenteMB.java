package com.certicom.ittsa.managedBeans;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;

import org.primefaces.context.RequestContext;

import com.certicom.ittsa.domain.Componente;
import com.certicom.ittsa.domain.Log;
import com.certicom.ittsa.domain.PuntoVenta;
import com.certicom.ittsa.services.CompHistorialService;
import com.certicom.ittsa.services.ComponenteService;
import com.certicom.ittsa.services.MenuServices;
import com.pe.certicom.ittsa.commons.Constante;
import com.pe.certicom.ittsa.commons.FacesUtils;
import com.pe.certicom.ittsa.commons.GenericBeans;

@ManagedBean(name="addComponenteMB")
@ViewScoped
public class AddComponenteMB extends GenericBeans {

	private Componente componente;
	private List<Componente> listaComponente;
	private PuntoVenta puntoVenta;
	
	private ComponenteService componenteService;
	private CompHistorialService compHistorialService;
	private MenuServices menuServices;
	
	//datos Log
    private Log log;
	private LogMB logmb;
	private Boolean editar;
	
	
	@PostConstruct
	public void inicia(){
		
		Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
		this.puntoVenta =(PuntoVenta) flash.get("puntoVenta");
		
		this.componenteService = new ComponenteService();
		this.menuServices = new MenuServices();
		this.componente = new Componente();
		this.compHistorialService = new CompHistorialService();
		
		log = (Log)getSpringBean(Constante.SESSION_LOG);
		logmb = new LogMB();
		
		try {
			listaComponente = componenteService.listaComponentesxPV(this.puntoVenta.getIdPuntoVenta());
			int codMenu = menuServices.opcionMenuByPretty("pretty:puntoventa");
			log.setCod_menu(codMenu);
		} catch (Exception e) {
			System.out.println("Error :"+ e.getMessage());
			e.printStackTrace();
		}
		

	}
	
	
	public void nuevoComponente(){
		this.componente = new Componente();
		this.editar = Boolean.FALSE;
	}
	
	public void editarComponente(Componente com){
		this.componente = com;
		this.editar = Boolean.TRUE;
	}
	
	public void guardarComponente()
	{
		Boolean valido=Boolean.TRUE;
		RequestContext context = RequestContext.getCurrentInstance();  
   	    context.addCallbackParam("esValido", valido );
   	    
		try {
			if(this.editar)
			{
				this.componenteService.actualizarComponente(componente);
				 log.setAccion("UPDATE");
	             log.setDescripcion("Se actualiza el Componente: " + componente.getNombre());
	             logmb.insertarLog(log);
				FacesUtils.showFacesMessage("Componente actualizado correctamente.",Constante.INFORMACION);
				context.update("msgGeneral");
			}else
			{
				this.componente.setIdPuntoVenta(this.puntoVenta.getIdPuntoVenta());
				this.componente.setEstado(Boolean.TRUE);
				this.componenteService.crearComponente(componente);
				 log.setAccion("INSERT");
	             log.setDescripcion("Se inserta el compomente: " + componente.getNombre());
	             logmb.insertarLog(log);
				 FacesUtils.showFacesMessage("Componente registrado correctamente.",Constante.INFORMACION);
				 context.update("msgGeneral");
			}
			listaComponente = this.componenteService.listaComponentesxPV(this.puntoVenta.getIdPuntoVenta());
			

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		this.componente = new Componente();
		this.editar =  Boolean.FALSE;
		
	}
	
	public void cambiarEstado(Componente comp){
		
		   if(comp.isEstado()){
			   comp.setEstado(Boolean.FALSE);
		   }else{
			   comp.setEstado(Boolean.TRUE);
		   }
		   
		   try {
			   this.componenteService.actualizarComponente(comp);
			   log.setAccion("CHANGE ESTADO");
	           log.setDescripcion("Se cambio el estado en Componente: " + componente.getNombre());
	           logmb.insertarLog(log);
			   FacesUtils.showFacesMessage("Estado de componente actualizado correctamente.",Constante.INFORMACION);
		   } catch (Exception e) {
			   System.out.println("Error:"+e.getMessage());
			   e.printStackTrace();
		   }   
	}
	
	public void eliminarComponente()
	{
		try {
			RequestContext context = RequestContext.getCurrentInstance();
			
			int cant  = 0;
			cant = this.compHistorialService.cantCompxHistorial(this.componente.getIdcomponente());
			
			if(cant  == 0){
				this.componenteService.eliminarComponente(this.componente.getIdcomponente());
				listaComponente.remove(componente);
				FacesUtils.showFacesMessage("Componente eliminado correctamente.",Constante.INFORMACION);
				
				log.setAccion("DELETE");
				log.setDescripcion("Se elimina el Componente: " + puntoVenta.getNamePuntoVenta());
				logmb.insertarLog(log);
			} else {
				context.execute("dlgOmiso.show()");
			}
			

		} catch (Exception e) {
			e.printStackTrace();
		}
		this.componente =  new Componente();
		
	}
	
	public String agregarHistorial(Componente comp){
		String outcome = null;
		try {
			System.out.println("comp pasado:"+comp.getDescripcion());
    		Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
    		flash.put("componente", comp);
    		flash.put("puntoVenta", this.puntoVenta);
    		
    		//return "/faces/control_acceso/asignarPermisos?faces-redirect=true";
    		outcome="pretty:agregaCompHistorial";
		} catch (Exception e) {	
			System.out.println("Error:"+e.getMessage());
			e.printStackTrace();
		}
		return outcome;
	}
	
	


	public Componente getComponente() {
		return componente;
	}


	public void setComponente(Componente componente) {
		this.componente = componente;
	}


	public List<Componente> getListaComponente() {
		return listaComponente;
	}


	public void setListaComponente(List<Componente> listaComponente) {
		this.listaComponente = listaComponente;
	}


	public PuntoVenta getPuntoVenta() {
		return puntoVenta;
	}


	public void setPuntoVenta(PuntoVenta puntoVenta) {
		this.puntoVenta = puntoVenta;
	}


	public ComponenteService getComponenteService() {
		return componenteService;
	}


	public void setComponenteService(ComponenteService componenteService) {
		this.componenteService = componenteService;
	}


	public MenuServices getMenuServices() {
		return menuServices;
	}


	public void setMenuServices(MenuServices menuServices) {
		this.menuServices = menuServices;
	}


	public Boolean getEditar() {
		return editar;
	}


	public void setEditar(Boolean editar) {
		this.editar = editar;
	}


	public CompHistorialService getCompHistorialService() {
		return compHistorialService;
	}


	public void setCompHistorialService(CompHistorialService compHistorialService) {
		this.compHistorialService = compHistorialService;
	}
	
	
	
	
	/*#######################---getters y setters----------########################*/
	
	

	
	
}
