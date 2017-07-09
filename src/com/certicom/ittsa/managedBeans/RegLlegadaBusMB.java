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
import com.certicom.ittsa.domain.Destino;
import com.certicom.ittsa.domain.Excepcion;
import com.certicom.ittsa.domain.Flota;
import com.certicom.ittsa.domain.Historial_AsigPCT;
import com.certicom.ittsa.domain.Log;
import com.certicom.ittsa.domain.Piloto;
import com.certicom.ittsa.domain.Programacion;
import com.certicom.ittsa.domain.Servicio;
import com.certicom.ittsa.domain.Terramoza;
import com.certicom.ittsa.domain.Tripulacion;
import com.certicom.ittsa.domain.Usuario;
import com.certicom.ittsa.mapper.Historial_AsigPCTMapper;
import com.certicom.ittsa.services.AgenciaService;
import com.certicom.ittsa.services.DestinoServices;
import com.certicom.ittsa.services.ExcepcionService;
import com.certicom.ittsa.services.FlotaService;
import com.certicom.ittsa.services.Historial_AsigPCTServices;
import com.certicom.ittsa.services.MenuServices;
import com.certicom.ittsa.services.PilotoService;
import com.certicom.ittsa.services.ProgramacionService;
import com.certicom.ittsa.services.ServicioService;
import com.certicom.ittsa.services.TerramozaService;
import com.certicom.ittsa.services.TripulacionServices;
import com.certicom.ittsa.services.UsuarioServices;
import com.pe.certicom.ittsa.commons.Constante;
import com.pe.certicom.ittsa.commons.FacesUtils;
import com.pe.certicom.ittsa.commons.GenericBeans;

@ManagedBean(name = "regLlegadaBusMB")
@ViewScoped
public class RegLlegadaBusMB extends GenericBeans implements Serializable {

	private Programacion programacion;
	private Servicio servicioPaint;
	//private 
	private List<Agencia> listaAgencias;
	private List<Destino> listaDestino;
	private List<Programacion> listaProgramacion;
	private List<Programacion> listaProgramacionFilter;
	private List<Programacion> listaProgramacionAsig;
	private List<Programacion> listaProgramacionAsigFilter;
	private List<Flota> listaBuses;
	private List<Flota> listaBusesFilter;
	private List<Piloto> listaPilotos;
	private List<Piloto> listaCopilotos;
	private List<Terramoza> listaTerramozas;
	private List<Terramoza> listaTerramozasFilter;
	

	private Date fechaProgramacion;
	private Integer idOrigen;
	private Integer idDestino;
	private Flota busSelected;
	private boolean editarBus;
	private boolean editarPiloto;
	private boolean editarCopiloto;

	// services
	private ProgramacionService programacionService;
	private AgenciaService agenciaService;
	private DestinoServices destinoServices;
	private MenuServices menuServices;

	// datos Log
	private Log log;
	private LogMB logmb;

	public RegLlegadaBusMB() {
		;
	}

	@PostConstruct
	public void inicia() {

		this.agenciaService = new AgenciaService();
		this.destinoServices = new DestinoServices();
		this.programacionService = new ProgramacionService();
		this.menuServices = new MenuServices();

		this.programacion = new Programacion();
		this.servicioPaint = new Servicio();

		log = (Log) getSpringBean(Constante.SESSION_LOG);
		logmb = new LogMB();
		// listando
		try {
			this.listaAgencias = agenciaService.listaAgenciasActivas();
			int codMenu = menuServices.opcionMenuByPretty("pretty:registroLlegadaBus");
			log.setCod_menu(codMenu);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public void listarDestinos() {
		try {
			this.listaDestino = destinoServices.obtenerDestino(getIdOrigen());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public void buscarProgramacion(){
		try {
			this.listaProgramacionAsig = this.programacionService.listProgDesembarque(getFechaProgramacion(), getIdOrigen(), getIdDestino());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void actualizarDesembarque(){
		try {
			this.programacionService.actualizarDesembarque(this.programacion.getIdProgramacion(),(new Date()));
			FacesUtils.showFacesMessage("Fecha y hora de llegada del bus actualizadas correctamente.",Constante.INFORMACION);
			this.listaProgramacionAsig = this.programacionService.listProgDesembarque(getFechaProgramacion(), getIdOrigen(), getIdDestino());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void setProg(Programacion p){
		this.programacion = p;
	}
	
	public String consultarObjetosEncontrados(Programacion p){		
		String outcome = null;
		try {
			Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
			flash.put("programacion", p);
			outcome = "pretty:objEncontrados";
		} catch (Exception e) {
			// TODO: handle exception
		}
		return outcome;
	}
	
	
	public String consultarEntregaProducto(Programacion p){		
		String outcome = null;
		try {
			Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
			flash.put("programacion", p);
			outcome = "pretty:entregaProductos";
		} catch (Exception e) {
			// TODO: handle exception
		}
		return outcome;
	}
	
	
	/**********************Getter's and Seter's************************/

	public MenuServices getMenuServices() {
		return menuServices;
	}

	public Servicio getServicioPaint() {
		return servicioPaint;
	}

	public void setServicioPaint(Servicio servicioPaint) {
		this.servicioPaint = servicioPaint;
	}

	public Date getFechaProgramacion() {
		return fechaProgramacion;
	}

	public void setFechaProgramacion(Date fechaProgramacion) {
		this.fechaProgramacion = fechaProgramacion;
	}

	public Programacion getProgramacion() {
		return programacion;
	}

	public void setProgramacion(Programacion programacion) {
		this.programacion = programacion;
	}

	public List<Destino> getListaDestino() {
		return listaDestino;
	}

	public void setListaDestino(List<Destino> listaDestino) {
		this.listaDestino = listaDestino;
	}

	public ProgramacionService getProgramacionService() {
		return programacionService;
	}

	public void setProgramacionService(ProgramacionService programacionService) {
		this.programacionService = programacionService;
	}

	public AgenciaService getAgenciaService() {
		return agenciaService;
	}

	public void setAgenciaService(AgenciaService agenciaService) {
		this.agenciaService = agenciaService;
	}

	public DestinoServices getDestinoServices() {
		return destinoServices;
	}

	public void setDestinoServices(DestinoServices destinoServices) {
		this.destinoServices = destinoServices;
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

	public List<Agencia> getListaAgencias() {
		return listaAgencias;
	}

	public void setListaAgencias(List<Agencia> listaAgencias) {
		this.listaAgencias = listaAgencias;
	}

	public Integer getIdOrigen() {
		return idOrigen;
	}

	public void setIdOrigen(Integer idOrigen) {
		this.idOrigen = idOrigen;
	}

	public Integer getIdDestino() {
		return idDestino;
	}

	public void setIdDestino(Integer idDestino) {
		this.idDestino = idDestino;
	}

	public List<Programacion> getListaProgramacion() {
		return listaProgramacion;
	}

	public void setListaProgramacion(List<Programacion> listaProgramacion) {
		this.listaProgramacion = listaProgramacion;
	}

	public List<Flota> getListaBuses() {
		return listaBuses;
	}

	public void setListaBuses(List<Flota> listaBuses) {
		this.listaBuses = listaBuses;
	}

	public Flota getBusSelected() {
		return busSelected;
	}

	public void setBusSelected(Flota busSelected) {
		this.busSelected = busSelected;
	}

	public List<Piloto> getListaPilotos() {
		return listaPilotos;
	}

	public void setListaPilotos(List<Piloto> listaPilotos) {
		this.listaPilotos = listaPilotos;
	}

	public List<Piloto> getListaCopilotos() {
		return listaCopilotos;
	}

	public void setListaCopilotos(List<Piloto> listaCopilotos) {
		this.listaCopilotos = listaCopilotos;
	}

	public List<Programacion> getListaProgramacionAsig() {
		return listaProgramacionAsig;
	}

	public void setListaProgramacionAsig(List<Programacion> listaProgramacionAsig) {
		this.listaProgramacionAsig = listaProgramacionAsig;
	}

	public boolean isEditarBus() {
		return editarBus;
	}

	public void setEditarBus(boolean editarBus) {
		this.editarBus = editarBus;
	}

	public boolean isEditarPiloto() {
		return editarPiloto;
	}

	public void setEditarPiloto(boolean editarPiloto) {
		this.editarPiloto = editarPiloto;
	}

	public boolean isEditarCopiloto() {
		return editarCopiloto;
	}

	public void setEditarCopiloto(boolean editarCopiloto) {
		this.editarCopiloto = editarCopiloto;
	}

	public List<Flota> getListaBusesFilter() {
		return listaBusesFilter;
	}

	public void setListaBusesFilter(List<Flota> listaBusesFilter) {
		this.listaBusesFilter = listaBusesFilter;
	}

	public List<Programacion> getListaProgramacionFilter() {
		return listaProgramacionFilter;
	}

	public void setListaProgramacionFilter(
			List<Programacion> listaProgramacionFilter) {
		this.listaProgramacionFilter = listaProgramacionFilter;
	}

	public List<Programacion> getListaProgramacionAsigFilter() {
		return listaProgramacionAsigFilter;
	}

	public void setListaProgramacionAsigFilter(
			List<Programacion> listaProgramacionAsigFilter) {
		this.listaProgramacionAsigFilter = listaProgramacionAsigFilter;
	}

	public List<Terramoza> getListaTerramozas() {
		return listaTerramozas;
	}

	public List<Terramoza> getListaTerramozasFilter() {
		return listaTerramozasFilter;
	}

	public void setListaTerramozas(List<Terramoza> listaTerramozas) {
		this.listaTerramozas = listaTerramozas;
	}

	public void setListaTerramozasFilter(List<Terramoza> listaTerramozasFilter) {
		this.listaTerramozasFilter = listaTerramozasFilter;
	}
	
	
	

}
