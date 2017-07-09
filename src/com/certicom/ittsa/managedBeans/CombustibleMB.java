package com.certicom.ittsa.managedBeans;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.context.RequestContext;

import com.certicom.ittsa.domain.Agencia;
import com.certicom.ittsa.domain.Log;
import com.certicom.ittsa.services.AgenciaService;
import com.certicom.ittsa.services.MenuServices;
import com.certicom.ittsa.services.OficinaService;
import com.pe.certicom.ittsa.commons.Constante;
import com.pe.certicom.ittsa.commons.FacesUtils;
import com.pe.certicom.ittsa.commons.GenericBeans;

@ManagedBean(name="combustibleMB")
@ViewScoped
public class CombustibleMB extends GenericBeans implements Serializable{

	private Agencia agencia;
	private List<Agencia> listaCombustible;
	private List<Agencia> listaFiltroCombustible;
	private Boolean editar;
	
	//services
	private AgenciaService agenciaSevice;
	private MenuServices menuServices;
	
	//datos Log
    private Log log;
	private LogMB logmb;
	
	public CombustibleMB(){;}
	
	@PostConstruct
	public void incia()
	{
		this.editar = Boolean.FALSE;
	
		this.agenciaSevice = new AgenciaService();
		this.menuServices=new MenuServices();
		this.agencia = new Agencia();
		
		 log = (Log)getSpringBean(Constante.SESSION_LOG);
		logmb = new LogMB();
		//listando
		try {
			this.listaCombustible = agenciaSevice.listaAgenciasActivas();
			int codMenu = menuServices.opcionMenuByPretty("pretty:agencia");
			log.setCod_menu(codMenu);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void editarCombustible(Agencia agen){
		this.agencia = agen;
	}
	
	public void guardarCombustible(){
		
		try {
			RequestContext context = RequestContext.getCurrentInstance();  
			 log.setAccion("UPDATE");
             log.setDescripcion("Se actualiza el combustible de la agencia: " + agencia.getDescripcion());
             logmb.insertarLog(log);
			FacesUtils.showFacesMessage("Combustible actualizado correctamente.",Constante.INFORMACION);
			context.update("sms");
			this.agenciaSevice.actualizarCombustible(this.agencia);
			this.listaCombustible = agenciaSevice.listaAgenciasActivas();
			context.execute("dlgNuevo.hide()");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

	//**set an get 
	
	public MenuServices getMenuServices() {
		return menuServices;
	}

	public List<Agencia> getListaCombustible() {
		return listaCombustible;
	}

	public void setListaCombustible(List<Agencia> listaCombustible) {
		this.listaCombustible = listaCombustible;
	}

	public List<Agencia> getListaFiltroCombustible() {
		return listaFiltroCombustible;
	}

	public void setListaFiltroCombustible(List<Agencia> listaFiltroCombustible) {
		this.listaFiltroCombustible = listaFiltroCombustible;
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

	public Agencia getAgencia() {
		return agencia;
	}

	public void setAgencia(Agencia agencia) {
		this.agencia = agencia;
	}

	public Boolean getEditar() {
		return editar;
	}

	public void setEditar(Boolean editar) {
		this.editar = editar;
	}
	
	
}

