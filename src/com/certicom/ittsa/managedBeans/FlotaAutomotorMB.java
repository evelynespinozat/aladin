package com.certicom.ittsa.managedBeans;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;

import org.primefaces.context.RequestContext;

import com.certicom.ittsa.domain.Autoparte;
import com.certicom.ittsa.domain.AutoparteAutomotor;
import com.certicom.ittsa.domain.Flota;
import com.certicom.ittsa.domain.FlotaAutoparte;
import com.certicom.ittsa.domain.Log;
import com.certicom.ittsa.domain.Automotor;
import com.certicom.ittsa.domain.FlotaAutomotor;
import com.certicom.ittsa.services.AutomotorService;
import com.certicom.ittsa.services.AutoparteService;
import com.certicom.ittsa.services.AutopartesAutomotorService;
import com.certicom.ittsa.services.FlotaAutomotorService;
import com.certicom.ittsa.services.FlotaAutoparteService;
import com.certicom.ittsa.services.MenuServices;
import com.pe.certicom.ittsa.commons.Constante;
import com.pe.certicom.ittsa.commons.FacesUtils;
import com.pe.certicom.ittsa.commons.GenericBeans;

@ManagedBean(name="flotaAutomotorMB")
@ViewScoped
public class FlotaAutomotorMB extends GenericBeans {
	
	//bean principal
	private Flota flota;
	private FlotaAutomotor flotaAutomotor;
	private List<FlotaAutomotor> listaFlotaAutomotor;
	private List<FlotaAutomotor> listaFlotaAutomotorFilter;
	private List<Automotor> listaAutomotor;
	private List<Automotor> listaFilterAutomotor;
	
	private FlotaAutomotorService flotaAutomotorService;
	private MenuServices menuServices;
	private AutomotorService automotorService;
	private FlotaAutoparteService flotaAutoparteService;
	private AutopartesAutomotorService autopartesAutomotorService;
	
	//datos Log
    private Log log;
	private LogMB logmb;
	
	@PostConstruct
	public void inicia(){

		Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
		this.flota =(Flota) flash.get("flota");
		
		this.flotaAutomotor = new FlotaAutomotor();
		this.listaFlotaAutomotor = new ArrayList<>();
		this.listaAutomotor = new ArrayList<>();
		
		//SERVICES
		this.menuServices = new MenuServices();
		this.flotaAutomotorService = new FlotaAutomotorService();
		this.automotorService = new AutomotorService();
		this.flotaAutoparteService = new FlotaAutoparteService();
		this.autopartesAutomotorService = new AutopartesAutomotorService();
		
		this.log = (Log)getSpringBean(Constante.SESSION_LOG);
		this.logmb = new LogMB();
		
		try {
			this.listaAutomotor = this.automotorService.listaAutomotorsActivas();
			this.listaFlotaAutomotor = this.flotaAutomotorService.listaAutomotorxFlota(this.flota.getIdBus());
			int codMenu = menuServices.opcionMenuByPretty("pretty:flotaVehicular");
			this.log.setCod_menu(codMenu);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void addSistemaAutomotor(Automotor automoto){
		
		try {
			RequestContext context = RequestContext.getCurrentInstance();  
			int cant = this.flotaAutomotorService.verificarSistema(this.flota.getIdBus(), automoto.getIdAutomotor());
			if(cant == 0){
				this.flotaAutomotor.setIdAutomotor(automoto.getIdAutomotor());
				this.flotaAutomotor.setIdBus(this.flota.getIdBus());
				this.flotaAutomotorService.crearFlotaAutomotor(this.flotaAutomotor);
				/**se asocia las partes del sistema al bus**/
				FlotaAutoparte floAutopart = new FlotaAutoparte();
				floAutopart.setIdBus(this.flota.getIdBus());
				floAutopart.setIdAutomotor(automoto.getIdAutomotor());
				floAutopart.setFecCambio(new Date());
				floAutopart.setKmActual(0);
				List<AutoparteAutomotor> lista = this.autopartesAutomotorService.listaAutopartesxAutomotor(automoto.getIdAutomotor());
				if(lista.size() > 0){
					for(int i=0; i<lista.size() ; i++){
                        floAutopart.setIdAutoparte(lista.get(i).getIdAutoparte());
						this.flotaAutoparteService.crearFlotaAutoparte(floAutopart);
					}	
				}
				/**fin**/
				this.listaFlotaAutomotor = this.flotaAutomotorService.listaAutomotorxFlota(this.flota.getIdBus());
				log.setAccion("INSERT");
	             log.setDescripcion("Se registro el sistema automotor el sistema a la flota: " + automoto.getDescripcion());
	             logmb.insertarLog(log);
				FacesUtils.showFacesMessage("Sistema automotor registrado en flota correctamente.",Constante.INFORMACION);
				context.update("msgGeneral");
				
			}else{
				FacesUtils.showFacesMessage("El sistema automotor ya se encuentra agregado a la flota.",Constante.INFORMACION);
				context.update("msgGeneral");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void deleteAutomotorxFlota(){
		
		try {
			RequestContext context = RequestContext.getCurrentInstance();  
			
			this.flotaAutomotorService.eliminarFlotaAutomotor(this.flotaAutomotor.getIdFlotaAutomotor());
			log.setAccion("DELETE");
            log.setDescripcion("Se eliminó el Sistema Automotor del Bus: " + this.flotaAutomotor.getDesAutomotor());
            logmb.insertarLog(log);
			FacesUtils.showFacesMessage("Sistema automotor eliminado del bus correctamente.",Constante.INFORMACION);
			context.update("msgGeneral");
			this.listaFlotaAutomotor = this.flotaAutomotorService.listaAutomotorxFlota(this.flota.getIdBus());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
// set and get

	
	public MenuServices getMenuServices() {
		return menuServices;
	}

	public List<Automotor> getListaFilterAutomotor() {
		return listaFilterAutomotor;
	}

	public void setListaFilterAutomotor(List<Automotor> listaFilterAutomotor) {
		this.listaFilterAutomotor = listaFilterAutomotor;
	}

	public FlotaAutomotorService getFlotaAutomotorService() {
		return flotaAutomotorService;
	}

	public void setFlotaAutomotorService(FlotaAutomotorService flotaAutomotorService) {
		this.flotaAutomotorService = flotaAutomotorService;
	}

	public AutomotorService getAutomotorService() {
		return automotorService;
	}

	public void setAutomotorService(AutomotorService automotorService) {
		this.automotorService = automotorService;
	}

	public List<Automotor> getListaAutomotor() {
		return listaAutomotor;
	}

	public void setListaAutomotor(List<Automotor> listaAutomotor) {
		this.listaAutomotor = listaAutomotor;
	}

	public Flota getFlota() {
		return flota;
	}

	public void setFlota(Flota flota) {
		this.flota = flota;
	}

	public FlotaAutomotor getFlotaAutomotor() {
		return flotaAutomotor;
	}

	public void setFlotaAutomotor(FlotaAutomotor flotaAutomotor) {
		this.flotaAutomotor = flotaAutomotor;
	}

	public List<FlotaAutomotor> getListaFlotaAutomotor() {
		return listaFlotaAutomotor;
	}

	public void setListaFlotaAutomotor(List<FlotaAutomotor> listaFlotaAutomotor) {
		this.listaFlotaAutomotor = listaFlotaAutomotor;
	}

	public Log getLog() {
		return log;
	}

	public LogMB getLogmb() {
		return logmb;
	}
	
	public void setMenuServices(MenuServices menuServices) {
		this.menuServices = menuServices;
	}

	public void setLog(Log log) {
		this.log = log;
	}

	public void setLogmb(LogMB logmb) {
		this.logmb = logmb;
	}

	public List<FlotaAutomotor> getListaFlotaAutomotorFilter() {
		return listaFlotaAutomotorFilter;
	}

	public void setListaFlotaAutomotorFilter(
			List<FlotaAutomotor> listaFlotaAutomotorFilter) {
		this.listaFlotaAutomotorFilter = listaFlotaAutomotorFilter;
	}
	
	
}
