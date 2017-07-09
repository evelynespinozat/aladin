package com.certicom.ittsa.managedBeans;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.context.RequestContext;

import com.certicom.ittsa.domain.Asiento;
import com.certicom.ittsa.domain.ClaseServicio;
import com.certicom.ittsa.domain.Log;
import com.certicom.ittsa.services.AsientoServices;
import com.certicom.ittsa.services.ClaseServicioServices;
import com.certicom.ittsa.services.MenuServices;
import com.pe.certicom.ittsa.commons.Constante;
import com.pe.certicom.ittsa.commons.FacesUtils;
import com.pe.certicom.ittsa.commons.GenericBeans;

@ManagedBean(name = "asientoMB")
@ViewScoped
public class AsientoMB extends GenericBeans implements Serializable {

	private Asiento asiento;
	private List<Asiento> listaAsientofiltro;
	private List<Asiento> listaAsiento;
	private List<ClaseServicio> listaClaseServicio;
	private Boolean editar;

	private AsientoServices asientoServices;
	private MenuServices menuServices;
	private ClaseServicioServices claseServicioServices;
	
	//datos Log
    private Log log;
	private LogMB logmb;
	
	public AsientoMB() {
		;
	}

	@PostConstruct
	public void inicia() {
		this.asiento = new Asiento();
		this.editar = Boolean.FALSE;
		this.asientoServices = new AsientoServices();
		this.menuServices=new MenuServices();
		this.claseServicioServices = new ClaseServicioServices();
		
		 log = (Log)getSpringBean(Constante.SESSION_LOG);
		 logmb = new LogMB();
		
		try {
			int codMenu = menuServices.opcionMenuByPretty("pretty:asiento");
			log.setCod_menu(codMenu);
			this.listaAsiento = this.asientoServices.findAll();
			this.listaClaseServicio = this.claseServicioServices.listaCServiciosActivos();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void newInsert() {
		this.asiento = new Asiento();
	}

	public void cambiarEstado(Asiento asiento) {

		if (asiento.getEstado()) {
			asiento.setEstado(Boolean.FALSE);
		} else {
			asiento.setEstado(Boolean.TRUE);
		}

		try {
			this.asientoServices.actualizarAsientos(asiento);
			log.setAccion("CHANGE ESTADO");
	        log.setDescripcion("Se cambio el estado en Asiento: " + asiento.getDescClase());
	        logmb.insertarLog(log);
			FacesUtils.showFacesMessage("Estado de asiento actualizado correctamente.",Constante.INFORMACION);
		} catch (Exception e) {
			System.out.println("Error:" + e.getMessage());
			e.printStackTrace();
		}

	}

	public void guardarAsiento() {
		Boolean valido = Boolean.TRUE;
		RequestContext context = RequestContext.getCurrentInstance();
		context.addCallbackParam("esValido", valido);
		try {
			if (this.editar) { // editando

				asientoServices.actualizarAsientos(asiento);
				log.setAccion("UPDATE");
	            log.setDescripcion("Se actualiza el Asiento: " + asiento.getNumero());
	            logmb.insertarLog(log);
	            
	            FacesUtils.showFacesMessage("Asiento actualizado correctamente.", Constante.INFORMACION);
	            context.update("sms");
			} else {// guardando un nuevo registro
				this.asiento.setEstado(Boolean.TRUE);

				
				int asPiso1 = this.asiento.getN_canPrPiso();
				for (int i = 1; i <= asPiso1; i++) {
					this.asiento.setNumero(i+"");
					this.asiento.setPiso(1);
					this.asientoServices.registrarAsientos(this.asiento);
				}
			
				int asPiso2 = this.asiento.getN_canSePiso();
				int totalPisos = asPiso1 + asPiso2;
				for (int i = (asPiso1+1); i <= totalPisos; i++) {
					this.asiento.setNumero(i+"");
					this.asiento.setPiso(2);
					this.asientoServices.registrarAsientos(this.asiento);
				}
				
				
				this.listaAsiento = asientoServices.findAll();
				 log.setAccion("INSERT");
	             log.setDescripcion("Se registro el asiento: " + asiento.getNumero());
	             logmb.insertarLog(log);
				FacesUtils.showFacesMessage("Asiento registrado correctamente.",Constante.INFORMACION);
				context.update("sms");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		this.editar = Boolean.FALSE;
		this.asiento = new Asiento();
		
		

	}

	public void eliminarAsiento() {
		try {
			this.asientoServices.eliminarAsientosxClase(asiento.getIdclase());
			this.listaAsiento.remove(asiento);
			FacesUtils.showFacesMessage("Asientos eliminados correctamente.",Constante.INFORMACION);
			log.setAccion("DELETE");
	        log.setDescripcion("Se elimina el Asiento de las clase : " + asiento.getDescClase());
	        logmb.insertarLog(log);
		} catch (Exception e) {
			FacesUtils.showFacesMessage("Asientos no se puede eliminar, consulte con el administrador.",Constante.ERROR);
			e.printStackTrace();
		}
		this.asiento = new Asiento();

	}

	public Asiento getAsiento() {
		return asiento;
	}

	public void setAsiento(Asiento asiento) {
		this.asiento = asiento;
	}

	public List<Asiento> getListaAsientofiltro() {
		return listaAsientofiltro;
	}

	public void setListaAsientofiltro(List<Asiento> listaAsientofiltro) {
		this.listaAsientofiltro = listaAsientofiltro;
	}

	public List<Asiento> getListaAsiento() {
		return listaAsiento;
	}

	public void setListaAsiento(List<Asiento> listaAsiento) {
		this.listaAsiento = listaAsiento;
	}

	public Boolean getEditar() {
		return editar;
	}

	public void setEditar(Boolean editar) {
		this.editar = editar;
	}

	public AsientoServices getAsientoServices() {
		return asientoServices;
	}

	public void setAsientoServices(AsientoServices asientoServices) {
		this.asientoServices = asientoServices;
	}

	public MenuServices getMenuServices() {
		return menuServices;
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

	public List<ClaseServicio> getListaClaseServicio() {
		return listaClaseServicio;
	}

	public void setListaClaseServicio(List<ClaseServicio> listaClaseServicio) {
		this.listaClaseServicio = listaClaseServicio;
	}
	
	
	// set and get 


}
