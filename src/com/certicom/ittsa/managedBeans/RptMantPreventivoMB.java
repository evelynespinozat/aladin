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
import com.certicom.ittsa.services.MenuServices;
import com.certicom.ittsa.services.OficinaService;
import com.pe.certicom.ittsa.commons.Constante;
import com.pe.certicom.ittsa.commons.FacesUtils;
import com.pe.certicom.ittsa.commons.GenericBeans;

@ManagedBean(name="rptMantPreventivoMB")
@ViewScoped
public class RptMantPreventivoMB extends GenericBeans implements Serializable{

	private Integer codigoAutoparte;
	private String tipoKilometraje;
	private Date fechaFiltro;
	private Boolean editar;
	private List<Autoparte> listaAutopartes;
	private List<FlotaAutoparte> listaAutopartexCambio;
	
	//services
	private MenuServices menuServices;
	private FlotaAutoparteService flotaAutoparteService;
	private AutoparteService autoparteService;
	
	//datos Log
    private Log log;
	private LogMB logmb;
	
	public RptMantPreventivoMB(){;}
	
	@PostConstruct
	public void incia()
	{
		this.editar = Boolean.FALSE;
		this.fechaFiltro = new Date();
		this.flotaAutoparteService = new FlotaAutoparteService();
		this.autoparteService = new AutoparteService();
		this.menuServices=new MenuServices();
		
		 log = (Log)getSpringBean(Constante.SESSION_LOG);
		logmb = new LogMB();
		//listando
		try {
			int codMenu = menuServices.opcionMenuByPretty("pretty:reportManPreventivo");
			this.listaAutopartes = this.autoparteService.listaAutopartesActivas();
			log.setCod_menu(codMenu);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void filtrarAutopartes(){
		
		try {
			
			FlotaAutoparte floAutoFilter = new FlotaAutoparte();
			if(this.tipoKilometraje.equals("O")){
				
				floAutoFilter.setIdAutoparte(this.codigoAutoparte);
				floAutoFilter.setFecKmIncremento(this.fechaFiltro);
				floAutoFilter.setTipoKilometraje(this.tipoKilometraje);
				this.listaAutopartexCambio = new ArrayList<>();
					this.listaAutopartexCambio = this.flotaAutoparteService.autopartesxCambio(floAutoFilter);
			}else{
				floAutoFilter.setIdAutoparte(this.codigoAutoparte);
				floAutoFilter.setFecKmIncremento(this.fechaFiltro);
				floAutoFilter.setTipoKilometraje(this.tipoKilometraje);
				this.listaAutopartexCambio = new ArrayList<>();
					this.listaAutopartexCambio = this.flotaAutoparteService.autopartesxCambioAprox(floAutoFilter);
				
			}
			
			floAutoFilter = null;
			
			this.codigoAutoparte = 0;
			this.fechaFiltro = new Date(); 
			this.tipoKilometraje = "";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	//**set an get 
	
	
	public MenuServices getMenuServices() {
		return menuServices;
	}

	public List<FlotaAutoparte> getListaAutopartexCambio() {
		return listaAutopartexCambio;
	}

	public void setListaAutopartexCambio(List<FlotaAutoparte> listaAutopartexCambio) {
		this.listaAutopartexCambio = listaAutopartexCambio;
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

	public Integer getCodigoAutoparte() {
		return codigoAutoparte;
	}

	public void setCodigoAutoparte(Integer codigoAutoparte) {
		this.codigoAutoparte = codigoAutoparte;
	}

	public void setMenuServices(MenuServices menuServices) {
		this.menuServices = menuServices;
	}

	public Log getLog() {
		return log;
	}

	public List<Autoparte> getListaAutopartes() {
		return listaAutopartes;
	}

	public void setListaAutopartes(List<Autoparte> listaAutopartes) {
		this.listaAutopartes = listaAutopartes;
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

