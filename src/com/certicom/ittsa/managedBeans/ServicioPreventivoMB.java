package com.certicom.ittsa.managedBeans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;

import org.primefaces.context.RequestContext;

import com.certicom.ittsa.domain.Agencia;
import com.certicom.ittsa.domain.Autoparte;
import com.certicom.ittsa.domain.Flota;
import com.certicom.ittsa.domain.FlotaAutoparte;
import com.certicom.ittsa.domain.Log;
import com.certicom.ittsa.services.AgenciaService;
import com.certicom.ittsa.services.AutoparteService;
import com.certicom.ittsa.services.FlotaAutomotorService;
import com.certicom.ittsa.services.FlotaAutoparteService;
import com.certicom.ittsa.services.FlotaService;
import com.certicom.ittsa.services.MenuServices;
import com.certicom.ittsa.services.OficinaService;
import com.pe.certicom.ittsa.commons.Constante;
import com.pe.certicom.ittsa.commons.FacesUtils;
import com.pe.certicom.ittsa.commons.GenericBeans;

@ManagedBean(name="servicioPreventivoMB")
@ViewScoped
public class ServicioPreventivoMB extends GenericBeans implements Serializable{

	private String tipoKilometraje;
	private Date fechaFiltro;
	private Boolean editar;
	private List<Flota> listaBusesCambio;
	
	//services
	private MenuServices menuServices;
	private FlotaService flotaService;
	private AutoparteService autoparteService;
	
	//datos Log
    private Log log;
	private LogMB logmb;
	
	public ServicioPreventivoMB(){;}
	
	@PostConstruct
	public void incia()
	{
		this.editar = Boolean.FALSE;
		this.fechaFiltro = new Date();
		this.flotaService = new FlotaService();
		this.autoparteService = new AutoparteService();
		this.menuServices=new MenuServices();
		
		 log = (Log)getSpringBean(Constante.SESSION_LOG);
		logmb = new LogMB();
		//listando
		try {
			int codMenu = menuServices.opcionMenuByPretty("pretty:reportManPreventivo");
			log.setCod_menu(codMenu);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void filtrarAutopartes(){
		
		try {
			
			FlotaAutoparte floAutoFilter = new FlotaAutoparte();
			floAutoFilter.setTipoKilometraje(this.tipoKilometraje);
			this.listaBusesCambio = new ArrayList<>();
			if(this.tipoKilometraje.equals("O")){
				floAutoFilter.setFecKmIncremento(this.fechaFiltro);
					this.listaBusesCambio = this.flotaService.busesxCambio(floAutoFilter);
			}else{
				floAutoFilter.setFecKmIncrementoAprox(this.fechaFiltro);
					this.listaBusesCambio = this.flotaService.busesxCambioAprox(floAutoFilter);
				
			}
			
			floAutoFilter = null;
			
//			this.fechaFiltro = new Date(); 
//			this.tipoKilometraje = "";
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	public String asignarMecanico(Flota flota){
		String outcome = null;
		try {
    		Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
    		flash.put("flota", flota);
    		flash.put("operation", this.tipoKilometraje);
    		//flash.put("fecha", this.fechaFiltro);
    		outcome="pretty:mantenimientoAutoparte";
		} catch (Exception e) {	
			e.printStackTrace();
		}
		return outcome;
	}
	
	//**set an get 
	
	public MenuServices getMenuServices() {
		return menuServices;
	}

	public List<Flota> getListaBusesCambio() {
		return listaBusesCambio;
	}

	public void setListaBusesCambio(List<Flota> listaBusesCambio) {
		this.listaBusesCambio = listaBusesCambio;
	}

	public String getTipoKilometraje() {
		return tipoKilometraje;
	}

	public void setTipoKilometraje(String tipoKilometraje) {
		this.tipoKilometraje = tipoKilometraje;
	}

	public Date getFechaFiltro() {
		return fechaFiltro;
	}

	public void setFechaFiltro(Date fechaFiltro) {
		this.fechaFiltro = fechaFiltro;
	}

	public AutoparteService getAutoparteService() {
		return autoparteService;
	}

	public void setAutoparteService(AutoparteService autoparteService) {
		this.autoparteService = autoparteService;
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
	
	
}

