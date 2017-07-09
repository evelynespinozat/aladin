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
import com.certicom.ittsa.domain.ConsumoCombustible;
import com.certicom.ittsa.domain.Flota;
import com.certicom.ittsa.domain.Log;
import com.certicom.ittsa.services.AgenciaService;
import com.certicom.ittsa.services.ConsumoCombustibleService;
import com.certicom.ittsa.services.MenuServices;
import com.certicom.ittsa.services.OficinaService;
import com.pe.certicom.ittsa.commons.Constante;
import com.pe.certicom.ittsa.commons.FacesUtils;
import com.pe.certicom.ittsa.commons.GenericBeans;

@ManagedBean(name="consumoCombustibleMB")
@ViewScoped
public class ConsumoCombustibleMB extends GenericBeans implements Serializable{

	private Flota flota;
	private ConsumoCombustible consumoCombustible;
	private List<ConsumoCombustible> listaProgramcnKilom;
	private List<ConsumoCombustible> listaFiltroProgramcnKilom;
	private Boolean editar;
	private Integer _idOrigen;
	private List<Agencia> listaAgOrigen;
	
	//services
	private MenuServices menuServices;
	private ConsumoCombustibleService consumoCombustibleService;
	private AgenciaService agenciaService;
	
	//datos Log
    private Log log;
	private LogMB logmb;
	
	public ConsumoCombustibleMB(){;}
	
	@PostConstruct
	public void incia()
	{
		this.listaAgOrigen = new ArrayList<>();
		this.editar = Boolean.FALSE;
		Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
		this.flota =(Flota) flash.get("flota");
		
		this.menuServices=new MenuServices();
		this.consumoCombustible = new ConsumoCombustible();
		this.listaProgramcnKilom = new ArrayList<>();
		
		this.consumoCombustibleService = new ConsumoCombustibleService();
		this.agenciaService = new AgenciaService();
		
		 log = (Log)getSpringBean(Constante.SESSION_LOG);
		logmb = new LogMB();
		//listando
		try {
			this.listaAgOrigen = this.agenciaService.listaAgenciasActivas();
			this.listaProgramcnKilom = consumoCombustibleService.listarProgramacionescnKilometro(this.flota.getIdBus());
			int codMenu = menuServices.opcionMenuByPretty("pretty:consumocombustible");
			log.setCod_menu(codMenu);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void editarCombustible(ConsumoCombustible consumo){
		this.consumoCombustible = consumo;
		this.consumoCombustible.setActivar(false);
		
	}
	
	public void guardarConsumoCombustible(){
		try {
			//this.consumoCombustible = consumo;
			Boolean valido=Boolean.TRUE;
			RequestContext context = RequestContext.getCurrentInstance();  
	   	    context.addCallbackParam("esValido", valido );
			
			this.consumoCombustible.setKmGalon(Redondear(this.consumoCombustible.getKmRecorridos()/this.consumoCombustible.getConsumo()));
			this.consumoCombustible.setCostoTotal(Redondear(this.consumoCombustible.getConsumo()*this.consumoCombustible.getPrecioComb()));
			this.consumoCombustible.setCostoKm(Redondear(this.consumoCombustible.getCostoTotal()/this.consumoCombustible.getKmRecorridos()));
			this.consumoCombustible.setFecRegCombus(new Date());
			this.consumoCombustible.setActivar(Boolean.TRUE);
			this.consumoCombustible.setIdOrigenComb(this._idOrigen);
			this.consumoCombustibleService.actualizarConsumoCombustible(this.consumoCombustible);

			FacesUtils.showFacesMessage("Combustible registrado correctamente.",Constante.INFORMACION);
			context.update("sms");
		} catch (Exception e) {
		   e.printStackTrace();
		}
	}
	
	public double Redondear(double numero){
	      return Math.rint(numero*1000)/1000;
	}
	
	public void setearCombustible(ConsumoCombustible consumo){
		this.consumoCombustible = consumo;
	}

	//**set an get 
	
	public MenuServices getMenuServices() {
		return menuServices;
	}

	public Flota getFlota() {
		return flota;
	}

	public void setFlota(Flota flota) {
		this.flota = flota;
	}

	public Integer get_idOrigen() {
		return _idOrigen;
	}

	public void set_idOrigen(Integer _idOrigen) {
		this._idOrigen = _idOrigen;
	}

	public List<Agencia> getListaAgOrigen() {
		return listaAgOrigen;
	}

	public void setListaAgOrigen(List<Agencia> listaAgOrigen) {
		this.listaAgOrigen = listaAgOrigen;
	}

	public ConsumoCombustible getConsumoCombustible() {
		return consumoCombustible;
	}

	public void setConsumoCombustible(ConsumoCombustible consumoCombustible) {
		this.consumoCombustible = consumoCombustible;
	}

	public List<ConsumoCombustible> getListaProgramcnKilom() {
		return listaProgramcnKilom;
	}

	public void setListaProgramcnKilom(List<ConsumoCombustible> listaProgramcnKilom) {
		this.listaProgramcnKilom = listaProgramcnKilom;
	}
	
	public List<ConsumoCombustible> getListaFiltroProgramcnKilom() {
		return listaFiltroProgramcnKilom;
	}

	public void setListaFiltroProgramcnKilom(
			List<ConsumoCombustible> listaFiltroProgramcnKilom) {
		this.listaFiltroProgramcnKilom = listaFiltroProgramcnKilom;
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

