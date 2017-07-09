package com.certicom.ittsa.managedBeans;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.context.RequestContext;

import com.certicom.ittsa.domain.Concepto;
import com.certicom.ittsa.domain.Log;
import com.certicom.ittsa.services.ConceptoServices;
import com.certicom.ittsa.services.MenuServices;
import com.pe.certicom.ittsa.commons.Constante;
import com.pe.certicom.ittsa.commons.FacesUtils;
import com.pe.certicom.ittsa.commons.GenericBeans;

@ManagedBean(name = "conceptoMB")
@ViewScoped
public class ConceptoMB extends GenericBeans implements Serializable {

	private Concepto concepto;
	private List<Concepto> listaConceptofiltro;
	private List<Concepto> listaConcepto;
	private Boolean editar;

	private ConceptoServices conceptoServices;
	private MenuServices menuServices;
	
	//datos Log
    private Log log;
	private LogMB logmb;
	
	public ConceptoMB() {
		;
	}

	@PostConstruct
	public void inicia() {
		this.concepto = new Concepto();
		this.editar = Boolean.FALSE;
		this.conceptoServices = new ConceptoServices();
		this.menuServices=new MenuServices();
		
		 log = (Log)getSpringBean(Constante.SESSION_LOG);
		 logmb = new LogMB();
		
		try {
			int codMenu = menuServices.opcionMenuByPretty("pretty:agencia");
			log.setCod_menu(codMenu);
			this.listaConcepto = this.conceptoServices.findAll();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void nuevoConcepto() {
		this.concepto = new Concepto();
		this.editar = Boolean.FALSE;
	}
	
	public void editarConcepto(Concepto con) {
		this.concepto = con;
		this.editar = Boolean.TRUE;
	}

	public void cambiarEstado(Concepto concepto) {

		if (concepto.isEstado()) {
			concepto.setEstado(Boolean.FALSE);
		} else {
			concepto.setEstado(Boolean.TRUE);
		}

		try {
			this.conceptoServices.actualizarConcepto(concepto);
			 log.setAccion("CHANGE ESTADO");
	         log.setDescripcion("Se cambio el estado en Concepto: " + concepto.getDescripcion());
	         logmb.insertarLog(log);
			FacesUtils.showFacesMessage("Estado de concepto actualizado correctamente.",Constante.INFORMACION);
		} catch (Exception e) {
			System.out.println("Error:" + e.getMessage());
			e.printStackTrace();
		}

	}

	public void guardarConcepto() {
		Boolean valido = Boolean.TRUE;
		RequestContext context = RequestContext.getCurrentInstance();
		context.addCallbackParam("esValido", valido);
		try {
			if (this.editar) { // editando

				conceptoServices.actualizarConcepto(concepto);
				log.setAccion("UPDATE");
	            log.setDescripcion("Se actualiza el concepto: " + concepto.getDescripcion());
	            logmb.insertarLog(log);
	            
	            FacesUtils.showFacesMessage("Concepto actualizado correctamente.",Constante.INFORMACION);
	            context.update("sms");
			} else {// guardando un nuevo registro
				this.concepto.setEstado(Boolean.TRUE);
				this.concepto.setFRegistro(new Date());
				this.conceptoServices.registrarConcepto(this.concepto);
				this.listaConcepto = conceptoServices.findAll();
				 log.setAccion("INSERT");
	             log.setDescripcion("Se registro el concepto: " + concepto.getDescripcion());
	             logmb.insertarLog(log);
				FacesUtils.showFacesMessage("Concepto registrado correctamente.",Constante.INFORMACION);
				context.update("sms");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		this.editar = Boolean.FALSE;
		this.concepto = new Concepto();
		
		

	}

	public void eliminarConcepto() {

		try {
			this.conceptoServices.eliminarConcepto(concepto.getIdConcepto());
			this.listaConcepto.remove(concepto);
			FacesUtils.showFacesMessage("Concepto eliminado correctamente.",Constante.INFORMACION);
			log.setAccion("DELETE");
	        log.setDescripcion("Se elimina el concepto: " + concepto.getDescripcion());
	        logmb.insertarLog(log);
		} catch (Exception e) {
			FacesUtils.showFacesMessage("El concepto no se puede eliminar, consulte con el administrador.",Constante.ERROR);
			e.printStackTrace();
		}
		this.concepto = new Concepto();

	}
	
	// set and get 

	public Concepto getConcepto() {
		return concepto;
	}

	public void setConcepto(Concepto concepto) {
		this.concepto = concepto;
	}

	public List<Concepto> getListaConceptofiltro() {
		return listaConceptofiltro;
	}

	public void setListaConceptofiltro(List<Concepto> listaConceptofiltro) {
		this.listaConceptofiltro = listaConceptofiltro;
	}

	public List<Concepto> getListaConcepto() {
		return listaConcepto;
	}

	public void setListaConcepto(List<Concepto> listaConcepto) {
		this.listaConcepto = listaConcepto;
	}

	public Boolean getEditar() {
		return editar;
	}

	public void setEditar(Boolean editar) {
		this.editar = editar;
	}

	public ConceptoServices getConceptoServices() {
		return conceptoServices;
	}

	public void setConceptoServices(ConceptoServices conceptoServices) {
		this.conceptoServices = conceptoServices;
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
	

	
	

}
