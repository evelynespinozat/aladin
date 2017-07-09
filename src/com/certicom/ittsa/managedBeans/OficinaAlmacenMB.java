package com.certicom.ittsa.managedBeans;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;

import org.primefaces.context.RequestContext;

import com.certicom.ittsa.domain.Almacen;
import com.certicom.ittsa.domain.Log;
import com.certicom.ittsa.domain.Oficina;
import com.certicom.ittsa.domain.OficinaAlmacen;
import com.certicom.ittsa.services.AlmacenService;
import com.certicom.ittsa.services.MenuServices;
import com.certicom.ittsa.services.OficinaAlmacenService;
import com.pe.certicom.ittsa.commons.Constante;
import com.pe.certicom.ittsa.commons.FacesUtils;
import com.pe.certicom.ittsa.commons.GenericBeans;

@ManagedBean(name="oficinaAlmacenMB")
@ViewScoped
public class OficinaAlmacenMB extends GenericBeans {
	
	//bean principal
	private Almacen almacen;
	private Oficina oficina;
	private OficinaAlmacen oficinaAlmacen;
	private List<Almacen> listAlmacen;
	private List<OficinaAlmacen> listaOficinaAlmacen;
	
	private AlmacenService almacenService;
	private OficinaAlmacenService oficinaAlmacenService;
	private MenuServices menuServices;
	
	//datos Log
    private Log log;
	private LogMB logmb;
	
	@PostConstruct
	public void inicia(){


		Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
		this.oficina =(Oficina) flash.get("oficina");
		
		this.almacen = new Almacen();
		this.oficinaAlmacen = new OficinaAlmacen();
		this.listaOficinaAlmacen = new ArrayList<>();
		this.almacenService = new AlmacenService();
		this.oficinaAlmacenService = new OficinaAlmacenService();
		this.menuServices = new MenuServices();
		
		log = (Log)getSpringBean(Constante.SESSION_LOG);
		logmb = new LogMB();
		
		try {
			this.listAlmacen = this.almacenService.listaAlmacenNoAsignados();
			this.listaOficinaAlmacen = this.oficinaAlmacenService.listaOficinasxAlmacen(this.oficina.getIdOficina());
			int codMenu = menuServices.opcionMenuByPretty("pretty:productos");
			log.setCod_menu(codMenu);
			
		} catch (Exception e) {
			System.out.println("Error :"+ e.getMessage());
			e.printStackTrace();
		}
		

	}
	
	public void addAlmacen(){
		
		try {
			RequestContext context = RequestContext.getCurrentInstance();  
			
			//verificamos que no este ya registrado el almacen
			int cant = this.oficinaAlmacenService.verificarAlmacenxOficina(this.oficina.getIdOficina(), this.oficinaAlmacen.getIdAlmacen());
			if(cant == 0){
				this.oficinaAlmacen.setIdOficina(this.oficina.getIdOficina());
				this.oficinaAlmacenService.crearOficinaAlmacen(this.oficinaAlmacen);
				this.listaOficinaAlmacen = this.oficinaAlmacenService.listaOficinasxAlmacen(this.oficina.getIdOficina());
				 log.setAccion("INSERT");
	             log.setDescripcion("Se registro el almacen en la oficina: " + this.oficinaAlmacen.getDesAlmacen());
	             logmb.insertarLog(log);
				FacesUtils.showFacesMessage("Almacén registrado en la oficina correctamente.",Constante.INFORMACION);
				context.update("msgGeneral");
				this.almacenService.actualizarAsignacionAlmacen(Boolean.TRUE, this.oficinaAlmacen.getIdAlmacen());
				this.listAlmacen = this.almacenService.listaAlmacenNoAsignados();
			}else{
				FacesUtils.showFacesMessage("Almacén ya asociado a la oficina.",Constante.INFORMACION);
				context.update("msgGeneral");
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void deleteOficinaxAlmacen(){
		
		try {
			RequestContext context = RequestContext.getCurrentInstance();  
			
			this.oficinaAlmacenService.eliminarOficinaAlmacen(this.oficinaAlmacen.getId());
			log.setAccion("DELETE");
            log.setDescripcion("Se eliminó el almacen de la oficina: " + this.oficinaAlmacen.getDesAlmacen());
            logmb.insertarLog(log);
			FacesUtils.showFacesMessage("Almacén eliminado de la oficina correctamente.",Constante.INFORMACION);
			context.update("msgGeneral");
			this.listaOficinaAlmacen = this.oficinaAlmacenService.listaOficinasxAlmacen(this.oficina.getIdOficina());
			this.almacenService.actualizarAsignacionAlmacen(Boolean.FALSE, this.oficinaAlmacen.getIdAlmacen());
			this.listAlmacen = this.almacenService.listaAlmacenNoAsignados();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
// set and get

	public AlmacenService getAlmacenService() {
		return almacenService;
	}

	public List<OficinaAlmacen> getListaOficinaAlmacen() {
		return listaOficinaAlmacen;
	}

	public void setListaOficinaAlmacen(List<OficinaAlmacen> listaOficinaAlmacen) {
		this.listaOficinaAlmacen = listaOficinaAlmacen;
	}

	public OficinaAlmacen getOficinaAlmacen() {
		return oficinaAlmacen;
	}

	public void setOficinaAlmacen(OficinaAlmacen oficinaAlmacen) {
		this.oficinaAlmacen = oficinaAlmacen;
	}

	public Almacen getAlmacen() {
		return almacen;
	}

	public void setAlmacen(Almacen almacen) {
		this.almacen = almacen;
	}

	public Oficina getOficina() {
		return oficina;
	}

	public void setOficina(Oficina oficina) {
		this.oficina = oficina;
	}

	public MenuServices getMenuServices() {
		return menuServices;
	}

	public List<Almacen> getListAlmacen() {
		return listAlmacen;
	}

	public Log getLog() {
		return log;
	}

	public LogMB getLogmb() {
		return logmb;
	}

	public void setAlmacenService(AlmacenService almacenService) {
		this.almacenService = almacenService;
	}
	
	public void setMenuServices(MenuServices menuServices) {
		this.menuServices = menuServices;
	}

	public void setListAlmacen(List<Almacen> listAlmacen) {
		this.listAlmacen = listAlmacen;
	}

	public void setLog(Log log) {
		this.log = log;
	}

	public void setLogmb(LogMB logmb) {
		this.logmb = logmb;
	}




	
	
	
	/*#######################---getters y setters----------########################*/
	
	

	
	
}
