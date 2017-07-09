package com.certicom.ittsa.managedBeans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.context.RequestContext;

import com.certicom.ittsa.domain.Agencia;
import com.certicom.ittsa.domain.Log;
import com.certicom.ittsa.domain.Promocion;
import com.certicom.ittsa.domain.Servicio;
import com.certicom.ittsa.domain.TipoPromocion;
import com.certicom.ittsa.domain.UsuarioAutorizante;
import com.certicom.ittsa.services.AgenciaService;
import com.certicom.ittsa.services.MenuServices;
import com.certicom.ittsa.services.PromocionServices;
import com.certicom.ittsa.services.TipoPromocionService;
import com.certicom.ittsa.services.UsuarioAutorizanteServices;
import com.pe.certicom.ittsa.commons.Constante;
import com.pe.certicom.ittsa.commons.FacesUtils;
import com.pe.certicom.ittsa.commons.GenericBeans;

@ManagedBean(name = "promocionMB")
@ViewScoped
public class PromocionMB extends GenericBeans implements Serializable {
	private String titulo;
	private Promocion promocion;
	private List<Promocion> listaPromocionfiltro;
	private List<Promocion> listaPromocion;
	private List<TipoPromocion> listaTipoPromocion;
	private List<UsuarioAutorizante> listaUsuarioAutorizante;
	private List<Agencia> listaAgencia;
	private Boolean editar;
	private Boolean mostrarPanelC1; 
	private Boolean mostrarPanelC2;
	private Boolean mostrarPanelAgeOfi;
	
	private PromocionServices promocionServices;
	private TipoPromocionService tipoPromocionService;
	private AgenciaService agenciaService;
	private UsuarioAutorizanteServices usuarioAutorizanteServices;
	private MenuServices menuServices;
	
	private Servicio servicioIda;
	private Servicio servicioVuelta;
	private List<Servicio> listaServicio;
	
	//datos Log
    private Log log;
	private LogMB logmb;
	
	public PromocionMB() {
		;
	}

	@PostConstruct
	public void inicia() {
		this.promocion = new Promocion();
		this.editar = Boolean.FALSE;
		this.titulo = "Modificar promoción";
		this.listaTipoPromocion= new ArrayList<>(); 
		this.promocionServices = new PromocionServices();
		this.menuServices = new MenuServices();
		this.tipoPromocionService = new TipoPromocionService();
		this.agenciaService = new AgenciaService();
		this.usuarioAutorizanteServices = new UsuarioAutorizanteServices();
		this.mostrarPanelC1 = false;
		this.mostrarPanelC2 = false;
		this.mostrarPanelAgeOfi = true;
		
		this.listaAgencia = new ArrayList<>();
		this.listaTipoPromocion = new ArrayList<>();
		this.listaUsuarioAutorizante = new ArrayList<>();
		
		 log = (Log)getSpringBean(Constante.SESSION_LOG);
		 logmb = new LogMB();
		
		try {
			this.listaUsuarioAutorizante = this.usuarioAutorizanteServices.listaUsuarioAutorizanteActivos();
			this.listaTipoPromocion = this.tipoPromocionService.findAll();
			this.listaAgencia = this.agenciaService.listaAgenciasActivas();
			int codMenu = menuServices.opcionMenuByPretty("pretty:promocion");
			log.setCod_menu(codMenu);
			this.listaPromocion = this.promocionServices.findAll();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void mostrarPanel(){
		if(this.promocion.getIdTipoPromocion() == 1 || this.promocion.getIdTipoPromocion() == 3){
			this.mostrarPanelC1 = true;
			this.mostrarPanelC2 = false;
		}else if(this.promocion.getIdTipoPromocion() == 2){
			this.mostrarPanelC1 = false;
			this.mostrarPanelC2 = true;
		}else{
			this.mostrarPanelC1 = false;
			this.mostrarPanelC2 = false;
		}
	}
	
	public void mostrarPanelAgeOfi()
	{
		if(this.promocion.getIsTodos() == 1)
		{
			this.mostrarPanelAgeOfi = true;
		}else if(this.promocion.getIsTodos() == 2){
			this.mostrarPanelAgeOfi = false;
		}else{
			this.mostrarPanelAgeOfi = false;
		}
	}
	
	public void nuevoPromocion() {
		this.promocion = new Promocion();
		this.editar = Boolean.FALSE;
		this.promocion.setIsTodos(1);
		this.mostrarPanelAgeOfi = true;
		this.titulo = "Registrar nueva promoción";
	}
	
	public void editarPromocion(Promocion p) {
		this.promocion = p;
		this.editar = Boolean.TRUE;
		this.titulo = "Modificar promoción";
		
		if(this.promocion.getIsTodos() == 1){
			this.mostrarPanelAgeOfi = true;
		}else if(this.promocion.getIsTodos() == 2){
			this.mostrarPanelAgeOfi = false;
		}
		
		
		if(this.promocion.getIdTipoPromocion() == 2){
			this.mostrarPanelC1 = false;
			this.mostrarPanelC2 = true;
		}else{
			this.mostrarPanelC1 = true;
			this.mostrarPanelC2 = false;
		}
	}

	public void cambiarEstado(Promocion promocion) {

		if (promocion.isEstado()) {
			promocion.setEstado(Boolean.FALSE);
		} else {
			promocion.setEstado(Boolean.TRUE);
		}

		try {
			this.promocionServices.actualizarPromocion(promocion);
			log.setAccion("CHANGE ESTADO");
	        log.setDescripcion("Se cambio el estado en Promocion: " + promocion.getDescripcion());
	        logmb.insertarLog(log);
			FacesUtils.showFacesMessage("Estado de promoción actualizado correctamente.",
					Constante.INFORMACION);
		} catch (Exception e) {
			System.out.println("Error:" + e.getMessage());
			e.printStackTrace();
		}

	}

	public void guardarPromocion() {
		Boolean valido = Boolean.TRUE;
		RequestContext context = RequestContext.getCurrentInstance();
		context.addCallbackParam("esValido", valido);
		this.promocion.setDescripcion(this.promocion.getDescripcion().toUpperCase());
		try {
			if (this.editar) { // editando

				promocionServices.actualizarPromocion(promocion);
				log.setAccion("UPDATE");
	            log.setDescripcion("Se actualiza la promocion: " + promocion.getDescripcion());
	            logmb.insertarLog(log);
	            
	            FacesUtils.showFacesMessage("Promoción actualizada correctamente.",Constante.INFORMACION);
	            context.update("sms");
			} else {// guardando un nuevo registro
				this.promocion.setEstado(Boolean.TRUE);
				this.promocion.setfRegistro(new Date());
				this.promocionServices.registrarPromocion(this.promocion);
				this.listaPromocion = promocionServices.findAll();
				 log.setAccion("INSERT");
	             log.setDescripcion("Se registro la promocion: " + promocion.getDescripcion());
	             logmb.insertarLog(log);
				FacesUtils.showFacesMessage("Promoción registrada correctamente.",Constante.INFORMACION);
				 context.update("sms");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		this.editar = Boolean.FALSE;
		this.promocion = new Promocion();

	}

	public void eliminarPromocion() {

		try {
			this.promocionServices.eliminarPromocion(promocion.getIdPromocion());
			this.listaPromocion.remove(promocion);
			FacesUtils.showFacesMessage("Promoción eliminada correctamente.",Constante.INFORMACION);
			log.setAccion("DELETE");
	        log.setDescripcion("Se elimina la promocion: " + promocion.getDescripcion());
	        logmb.insertarLog(log);
		} catch (Exception e) {
			FacesUtils.showFacesMessage("Promoción no se puede eliminar, consulte con el administrador.",Constante.ERROR);
			e.printStackTrace();
		}
		this.promocion = new Promocion();

	}

	public Promocion getPromocion() {
		return promocion;
	}

	public void setPromocion(Promocion promocion) {
		this.promocion = promocion;
	}

	public List<Promocion> getListaPromocionfiltro() {
		return listaPromocionfiltro;
	}

	public void setListaPromocionfiltro(List<Promocion> listaPromocionfiltro) {
		this.listaPromocionfiltro = listaPromocionfiltro;
	}

	public List<Promocion> getListaPromocion() {
		return listaPromocion;
	}

	public void setListaPromocion(List<Promocion> listaPromocion) {
		this.listaPromocion = listaPromocion;
	}

	public Boolean getEditar() {
		return editar;
	}

	public void setEditar(Boolean editar) {
		this.editar = editar;
	}

	public PromocionServices getPromocionServices() {
		return promocionServices;
	}

	public void setPromocionServices(PromocionServices promocionServices) {
		this.promocionServices = promocionServices;
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

	public List<TipoPromocion> getListaTipoPromocion() {
		return listaTipoPromocion;
	}

	public void setListaTipoPromocion(List<TipoPromocion> listaTipoPromocion) {
		this.listaTipoPromocion = listaTipoPromocion;
	}

	public TipoPromocionService getTipoPromocionService() {
		return tipoPromocionService;
	}

	public void setTipoPromocionService(TipoPromocionService tipoPromocionService) {
		this.tipoPromocionService = tipoPromocionService;
	}

	public List<Agencia> getListaAgencia() {
		return listaAgencia;
	}

	public void setListaAgencia(List<Agencia> listaAgencia) {
		this.listaAgencia = listaAgencia;
	}

	public AgenciaService getAgenciaService() {
		return agenciaService;
	}

	public void setAgenciaService(AgenciaService agenciaService) {
		this.agenciaService = agenciaService;
	}

	public List<UsuarioAutorizante> getListaUsuarioAutorizante() {
		return listaUsuarioAutorizante;
	}

	public void setListaUsuarioAutorizante(
			List<UsuarioAutorizante> listaUsuarioAutorizante) {
		this.listaUsuarioAutorizante = listaUsuarioAutorizante;
	}

	public UsuarioAutorizanteServices getUsuarioAutorizanteServices() {
		return usuarioAutorizanteServices;
	}

	public void setUsuarioAutorizanteServices(
			UsuarioAutorizanteServices usuarioAutorizanteServices) {
		this.usuarioAutorizanteServices = usuarioAutorizanteServices;
	}

	public Boolean getMostrarPanelC1() {
		return mostrarPanelC1;
	}

	public void setMostrarPanelC1(Boolean mostrarPanelC1) {
		this.mostrarPanelC1 = mostrarPanelC1;
	}

	public Boolean getMostrarPanelC2() {
		return mostrarPanelC2;
	}

	public void setMostrarPanelC2(Boolean mostrarPanelC2) {
		this.mostrarPanelC2 = mostrarPanelC2;
	}

	public Boolean getMostrarPanelAgeOfi() {
		return mostrarPanelAgeOfi;
	}

	public void setMostrarPanelAgeOfi(Boolean mostrarPanelAgeOfi) {
		this.mostrarPanelAgeOfi = mostrarPanelAgeOfi;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	
	
	
	// set and get 
}
