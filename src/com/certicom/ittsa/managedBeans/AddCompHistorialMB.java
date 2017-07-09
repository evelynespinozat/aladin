package com.certicom.ittsa.managedBeans;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;

import org.primefaces.context.RequestContext;

import com.certicom.ittsa.domain.CompHistorial;
import com.certicom.ittsa.domain.Componente;
import com.certicom.ittsa.domain.Log;
import com.certicom.ittsa.domain.PuntoVenta;
import com.certicom.ittsa.services.CompHistorialService;
import com.certicom.ittsa.services.MenuServices;
import com.pe.certicom.ittsa.commons.Constante;
import com.pe.certicom.ittsa.commons.FacesUtils;
import com.pe.certicom.ittsa.commons.GenericBeans;

@ManagedBean(name="addCompHistorialMB")
@ViewScoped
public class AddCompHistorialMB extends GenericBeans {

	//principal
	private CompHistorial compHistorial;
	private List<CompHistorial> listaComponenteHis;
	
	//constante
	private Componente componente;
	private PuntoVenta puntoVenta;
	
	private CompHistorialService compHistorialService;
	private MenuServices menuServices;
	
	//datos Log
    private Log log;
	private LogMB logmb;
	private Boolean editar;
	
	private RequestContext context;
	
	
	@PostConstruct
	public void inicia(){
		
		Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
		this.componente =(Componente) flash.get("componente");
		this.puntoVenta =(PuntoVenta) flash.get("puntoVenta");

		this.compHistorial = new CompHistorial();
		this.compHistorialService= new CompHistorialService();
		this.menuServices = new MenuServices();
		this.context = RequestContext.getCurrentInstance();
		
		log = (Log)getSpringBean(Constante.SESSION_LOG);
		logmb = new LogMB();
		
		try {
			listaComponenteHis = compHistorialService.listaHistorialxComPV(this.componente.getIdcomponente());
			int codMenu = menuServices.opcionMenuByPretty("pretty:puntoventa");
			log.setCod_menu(codMenu);
		} catch (Exception e) {
			System.out.println("Error :"+ e.getMessage());
			e.printStackTrace();
		}
		

	}
	
	
	public void nuevoCompHis(){
		this.compHistorial = new CompHistorial();
		this.editar = Boolean.FALSE;
	}
	
	public void editarCompHis(CompHistorial ch){
		this.compHistorial = ch;
		this.editar = Boolean.TRUE;
	}
	
	public void guardarCompHistorial()
	{
		
		try {
			if(this.editar)
			{
				this.compHistorialService.actualizarCompHistorial(compHistorial);
				 log.setAccion("UPDATE");
	             log.setDescripcion("Se actualiza el historial del componente: " + compHistorial.getDescripcion() + "-" + componente.getNombre());
	             logmb.insertarLog(log);
				FacesUtils.showFacesMessage("Historial del componente actualizado correctamente.",Constante.INFORMACION);
				context.update("msgGeneral");
			}else
			{
				this.compHistorial.setIdcomponente(this.componente.getIdcomponente());
				this.compHistorialService.crearCompHistorial(compHistorial);
				 log.setAccion("INSERT");
	             log.setDescripcion("Se inserta el historial del Componente: " + 
	            		 	compHistorial.getDescripcion() + "-" + componente.getNombre());
	             logmb.insertarLog(log);
				FacesUtils.showFacesMessage("Historial del componente registrado correctamente.",Constante.INFORMACION);
				context.update("msgGeneral");
			}
			listaComponenteHis = this.compHistorialService.listaHistorialxComPV(this.componente.getIdcomponente());
			

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		this.compHistorial = new CompHistorial();
		this.editar =  Boolean.FALSE;
		
	}
	
	
	public void eliminarCompHistorial()
	{
		try {
			this.compHistorialService.eliminarCompHistorial(this.compHistorial.getIdHistComp());
			listaComponenteHis.remove(compHistorial);
			FacesUtils.showFacesMessage("Historial del componente eliminado correctamente.",Constante.INFORMACION);
			
			log.setAccion("DELETE");
	        log.setDescripcion("Se inserta el historial del Componente: " + 
	            		 	compHistorial.getDescripcion() + "-" + componente.getNombre());
	        logmb.insertarLog(log);

		} catch (Exception e) {
			e.printStackTrace();
		}
		this.compHistorial = new CompHistorial();
		
	}
	
	public String gotoComponentes(String caso){
		String outcome = null;
		try {
    		Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
    		flash.put("puntoVenta", this.puntoVenta);
    		//return "/faces/control_acceso/asignarPermisos?faces-redirect=true";
    		if (caso.equals("1")) {
    			outcome="pretty:agregaComponente";
			} else {
				outcome="agregaComponente";
			}
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


	public CompHistorial getCompHistorial() {
		return compHistorial;
	}


	public void setCompHistorial(CompHistorial compHistorial) {
		this.compHistorial = compHistorial;
	}


	public List<CompHistorial> getListaComponenteHis() {
		return listaComponenteHis;
	}


	public void setListaComponenteHis(List<CompHistorial> listaComponenteHis) {
		this.listaComponenteHis = listaComponenteHis;
	}


	public CompHistorialService getCompHistorialService() {
		return compHistorialService;
	}


	public void setCompHistorialService(CompHistorialService compHistorialService) {
		this.compHistorialService = compHistorialService;
	}


	public PuntoVenta getPuntoVenta() {
		return puntoVenta;
	}


	public void setPuntoVenta(PuntoVenta puntoVenta) {
		this.puntoVenta = puntoVenta;
	}
	
	
	
	/*#######################---getters y setters----------########################*/
	
	

	
	
}
