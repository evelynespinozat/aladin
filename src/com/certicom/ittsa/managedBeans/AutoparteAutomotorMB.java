package com.certicom.ittsa.managedBeans;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;

import org.primefaces.context.RequestContext;

import com.certicom.ittsa.domain.Autoparte;
import com.certicom.ittsa.domain.Log;
import com.certicom.ittsa.domain.Automotor;
import com.certicom.ittsa.domain.AutoparteAutomotor;
import com.certicom.ittsa.services.AutoparteService;
import com.certicom.ittsa.services.AutopartesAutomotorService;
import com.certicom.ittsa.services.MenuServices;
import com.pe.certicom.ittsa.commons.Constante;
import com.pe.certicom.ittsa.commons.FacesUtils;
import com.pe.certicom.ittsa.commons.GenericBeans;

@ManagedBean(name="autoparteAutomotorMB")
@ViewScoped
public class AutoparteAutomotorMB extends GenericBeans {
	
	//bean principal
	private Autoparte autoparte;
	private Automotor automotor;
	private AutoparteAutomotor autoparteAutomotor;
	private List<Autoparte> listAutoparte;
	private List<Autoparte> listFiltroAutoparte;
	private List<AutoparteAutomotor> listaAutoparteAutomotor;
	
	private AutoparteService autoparteService;
	private AutopartesAutomotorService autoparteAutomotorService;
	private MenuServices menuServices;
	
	//datos Log
    private Log log;
	private LogMB logmb;
	
	@PostConstruct
	public void inicia(){


		Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
		this.automotor =(Automotor) flash.get("automotor");
		
		this.autoparte = new Autoparte();
		this.autoparteAutomotor = new AutoparteAutomotor();
		this.autoparteService = new AutoparteService();
		this.menuServices = new MenuServices();
		this.autoparteAutomotorService = new AutopartesAutomotorService();
		this.listaAutoparteAutomotor = new ArrayList<>();
		//this.listFiltroAutoparte = new ArrayList<>();
		
		
		log = (Log)getSpringBean(Constante.SESSION_LOG);
		logmb = new LogMB();
		
		try {
			this.listAutoparte = this.autoparteService.listaAutopartesActivas();
			this.listaAutoparteAutomotor = this.autoparteAutomotorService.listaAutopartesxAutomotor(this.automotor.getIdAutomotor());
			int codMenu = menuServices.opcionMenuByPretty("pretty:productos");
			log.setCod_menu(codMenu);
			
		} catch (Exception e) {
			System.out.println("Error :"+ e.getMessage());
			e.printStackTrace();
		}
		

	}
	
	public void addAutoparte(Autoparte autopart){
		
		try {
			RequestContext context = RequestContext.getCurrentInstance();  
			
			//verificamos que no este ya registrado el autoparte
			int cant = this.autoparteAutomotorService.verificarAutopartesxAutomotor(this.automotor.getIdAutomotor(), autopart.getIdAutoparte());
			if(cant == 0){
				this.autoparteAutomotor.setIdAutoparte(autopart.getIdAutoparte());
				this.autoparteAutomotor.setIdAutomotor(this.automotor.getIdAutomotor());
				this.autoparteAutomotorService.crearAutopartesAutomotor(this.autoparteAutomotor);
				this.listaAutoparteAutomotor = this.autoparteAutomotorService.listaAutopartesxAutomotor(this.automotor.getIdAutomotor());
				 log.setAccion("INSERT");
	             log.setDescripcion("Se registro la Autoparte al sistema automotor: " + this.autoparte.getDescripcion());
	             logmb.insertarLog(log);
				FacesUtils.showFacesMessage("Autoparte registrada en el sistema automotor correctamente.",Constante.INFORMACION);
				context.update("msgGeneral");
			}else{
				FacesUtils.showFacesMessage("Parte ya asociada al sistema automotor.",Constante.INFORMACION);
				context.update("msgGeneral");
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void deleteAutopartexAutomotor(){
		
		try {
			RequestContext context = RequestContext.getCurrentInstance();  
			
			this.autoparteAutomotorService.eliminarAutopartesAutomotor(this.autoparteAutomotor.getIdParteAutomotor());
			log.setAccion("DELETE");
            log.setDescripcion("Se eliminó el autoparte del Sistema Automotor: " + this.autoparte.getDescripcion());
            logmb.insertarLog(log);
			FacesUtils.showFacesMessage("Autoparte eliminado del automotor correctamente.",Constante.INFORMACION);
			context.update("msgGeneral");
			this.listaAutoparteAutomotor = this.autoparteAutomotorService.listaAutopartesxAutomotor(this.automotor.getIdAutomotor());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
// set and get
	
	

	public AutoparteService getAutoparteService() {
		return autoparteService;
	}

	public List<Autoparte> getListFiltroAutoparte() {
		return listFiltroAutoparte;
	}

	public void setListFiltroAutoparte(List<Autoparte> listFiltroAutoparte) {
		this.listFiltroAutoparte = listFiltroAutoparte;
	}

	public List<AutoparteAutomotor> getListaAutoparteAutomotor() {
		return listaAutoparteAutomotor;
	}

	public void setListaAutoparteAutomotor(List<AutoparteAutomotor> listaAutoparteAutomotor) {
		this.listaAutoparteAutomotor = listaAutoparteAutomotor;
	}

	public AutoparteAutomotor getAutoparteAutomotor() {
		return autoparteAutomotor;
	}

	public void setAutoparteAutomotor(AutoparteAutomotor autoparteAutomotor) {
		this.autoparteAutomotor = autoparteAutomotor;
	}

	public Autoparte getAutoparte() {
		return autoparte;
	}

	public void setAutoparte(Autoparte autoparte) {
		this.autoparte = autoparte;
	}

	public Automotor getAutomotor() {
		return automotor;
	}

	public void setAutomotor(Automotor automotor) {
		this.automotor = automotor;
	}

	public MenuServices getMenuServices() {
		return menuServices;
	}

	public List<Autoparte> getListAutoparte() {
		return listAutoparte;
	}

	public Log getLog() {
		return log;
	}

	public LogMB getLogmb() {
		return logmb;
	}

	public void setAutoparteService(AutoparteService autoparteService) {
		this.autoparteService = autoparteService;
	}
	
	public void setMenuServices(MenuServices menuServices) {
		this.menuServices = menuServices;
	}

	public void setListAutoparte(List<Autoparte> listAutoparte) {
		this.listAutoparte = listAutoparte;
	}

	public void setLog(Log log) {
		this.log = log;
	}

	public void setLogmb(LogMB logmb) {
		this.logmb = logmb;
	}
	
}
