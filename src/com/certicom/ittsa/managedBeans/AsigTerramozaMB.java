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

@ManagedBean(name = "asigTerramozaMB")
@ViewScoped
public class AsigTerramozaMB extends GenericBeans implements Serializable {

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
	private FlotaService busServices;
	private PilotoService pilotoServices;
	private TripulacionServices tripulacionServices;
	private UsuarioServices usuarioServices;
	private Historial_AsigPCTServices historial_asigPCTServices;
	private TerramozaService terramozaService;

	// datos Log
	private Log log;
	private LogMB logmb;

	public AsigTerramozaMB() {
		;
	}

	@PostConstruct
	public void inicia() {

		this.agenciaService = new AgenciaService();
		this.destinoServices = new DestinoServices();
		this.programacionService = new ProgramacionService();
		this.menuServices = new MenuServices();
		this.busServices = new FlotaService();
		this.pilotoServices = new PilotoService();
		this.tripulacionServices = new TripulacionServices();
		this.usuarioServices = new UsuarioServices();
		this.historial_asigPCTServices = new Historial_AsigPCTServices();
		this.terramozaService = new TerramozaService();

		this.programacion = new Programacion();
		this.servicioPaint = new Servicio();

		log = (Log) getSpringBean(Constante.SESSION_LOG);
		logmb = new LogMB();
		// listando
		try {
			this.listaAgencias = agenciaService.listaAgenciasActivas();
			int codMenu = menuServices.opcionMenuByPretty("pretty:progFlota");
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
			this.listaProgramacionAsig = this.programacionService.listProgTripulacion(getFechaProgramacion(), getIdOrigen(), getIdDestino());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void buscarTerramoza(Programacion p){
		try {
			this.programacion = p;
			this.listaTerramozas = this.terramozaService.listaTerramozaActivas();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public void buscarBus(Programacion p){
		try {
			this.listaBuses = new ArrayList<>();
			this.programacion = p;
			this.listaBuses = busServices.findByIdClase(p.getIdClase());
			for(Flota f : listaBuses){
				Piloto pil = pilotoServices.findById(f.getPiloto());
				f.setNombPiloto(pil.getApellidoPaterno() +" "+pil.getApellidoMaterno()+" "+pil.getNombres());
				Piloto cop = pilotoServices.findById(f.getCopiloto());
				f.setNombCopiloto(cop.getApellidoPaterno() +" "+cop.getApellidoMaterno()+" "+cop.getNombres()); 
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void setearBus(Programacion p){
		this.programacion = p;
		if(p.getUsuarioRegistro()!=null){
			Usuario u = this.usuarioServices.getUsuario_byIdUsuario(p.getUsuarioRegistro());
			this.programacion.setNombUsuarioRegistro(u.getApellido_paterno()+" "+u.getApellido_materno()+" "+u.getNombre()); 
		}
		
		
	}
	
	public void asignarBus(Flota f){
		/*
		for(Programacion p : listaProgramacion){
			if(p.getIdProgramacion()==programacion.getIdProgramacion()){
				try {
					Piloto pil  = pilotoServices.findById(f.getPiloto());
					f.setNombPiloto(pil.getApellidoPaterno()+" "+pil.getApellidoMaterno()+" "+pil.getNombres()); 
					Piloto cop = pilotoServices.findById(f.getCopiloto());
					f.setNombCopiloto(cop.getApellidoPaterno()+" "+cop.getApellidoMaterno()+" "+cop.getNombres()); 
					p.setFlota(f);
					p.setGuardarProgFlota(Boolean.FALSE);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				  
			}
		}*/
		
		if(editarBus){
			Tripulacion t;
			try {
				t = tripulacionServices.findByIdProgramacion(programacion.getIdProgramacion());
				this.programacionService.actualizarBusProgramacionAsig(f.getIdBus(), programacion.getIdProgramacion());
				tripulacionServices.actualizarIdBus(t.getIdTripulacion(), f.getIdBus());
				for(Programacion p : listaProgramacionAsig){
					if(p.getIdProgramacion()==programacion.getIdProgramacion()){
						Piloto pil  = pilotoServices.findById(f.getPiloto());
						f.setNombPiloto(pil.getApellidoPaterno()+" "+pil.getApellidoMaterno()+" "+pil.getNombres()); 
						Piloto cop = pilotoServices.findById(f.getCopiloto());
						f.setNombCopiloto(cop.getApellidoPaterno()+" "+cop.getApellidoMaterno()+" "+cop.getNombres());
						p.setFlota(f);
					}
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		}else{
			for(Programacion p : listaProgramacion){
				if(p.getIdProgramacion()==programacion.getIdProgramacion()){
					try {
						Piloto pil  = pilotoServices.findById(f.getPiloto());
						f.setNombPiloto(pil.getApellidoPaterno()+" "+pil.getApellidoMaterno()+" "+pil.getNombres()); 
						Piloto cop = pilotoServices.findById(f.getCopiloto());
						f.setNombCopiloto(cop.getApellidoPaterno()+" "+cop.getApellidoMaterno()+" "+cop.getNombres()); 
						p.setFlota(f);
						p.setGuardarProgFlota(Boolean.FALSE);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					  
				}
			}
		}
		
	}
	
	public void buscarPiloto(Programacion p){
		try {
			this.programacion = p;
			this.listaPilotos = pilotoServices.findByEstado();
			for(int i=0; i<listaPilotos.size();i++){
				Piloto pil = listaPilotos.get(i);
				if(p.getCopiloto()!=null){
					if(p.getCopiloto().getIdPiloto()==pil.getIdPiloto()){
						listaPilotos.remove(i);
					}
				}			
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public void buscarCopiloto(Programacion p){
		try {
			this.programacion = p;
			this.listaCopilotos = pilotoServices.findByEstado();
			for(int i=0; i<listaCopilotos.size();i++){
				Piloto pil = listaCopilotos.get(i);
				if(p.getPiloto()!=null){
					if(p.getPiloto().getIdPiloto()==pil.getIdPiloto()){
						listaCopilotos.remove(i);
					}
				}			
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	public void asignarTerramoza1(Terramoza t){
		 asignarTerramoza(1, t);
		
	}
	public void asignarTerramoza2(Terramoza t){
		asignarTerramoza(2, t);
	}
	
	private void asignarTerramoza(Integer caso,Terramoza t){
		try {
			System.out.println("id prog " + this.programacion.getIdProgramacion());
		if(this.programacion.getIdTripulacion() != null && this.programacion.getIdTripulacion()!=0){
			if(caso == 1){
				this.tripulacionServices.actualizarTerramoza1(this.programacion.getIdTripulacion(), t.getIdTerramoza());
				this.programacionService.actualizarTerramoza1(this.programacion.getIdProgramacion(),t.getIdTerramoza());
			} else if(caso == 2){
				this.tripulacionServices.actualizarTerramoza2(this.programacion.getIdTripulacion(), t.getIdTerramoza());
				this.programacionService.actualizarTerramoza2(this.programacion.getIdProgramacion(),t.getIdTerramoza());
			}
			
			log.setDescripcion("Se actualizo la terramoza " + t.getNombres() + " " + t.getApellidos() + " a la tripulacion "
					+ this.programacion.getIdTripulacion());
		
		} else{
			if(caso == 1){
				this.programacionService.actualizarTerramoza1(this.programacion.getIdProgramacion(),t.getIdTerramoza());
			} else if(caso == 2){
				this.programacionService.actualizarTerramoza2(this.programacion.getIdProgramacion(),t.getIdTerramoza());
			}
			
			log.setDescripcion("Se actualizo la terramoza " + t.getNombres() + " " + t.getApellidos() + " a la programacion "
					+ this.programacion.getIdProgramacion());
		}
		
		log.setAccion("UPDATE");
		logmb.insertarLog(log);
		
		FacesUtils.showFacesMessage("Terramoza registrada en programación correctamente.",Constante.INFORMACION);
		buscarProgramacion();
		
		} catch (Exception e) {
			FacesUtils.showFacesMessage("Ocurrió un error en el sistema comuníquese con el administrador.",Constante.ERROR);
			e.printStackTrace();
		}
		
	}
	
	public void guardarProgramacionFlota(Programacion pro) {
		
		try {
			Tripulacion t = tripulacionServices.findByIdProgramacion(pro.getIdProgramacion());
			this.programacionService.actualizarBusProgramacionAsig(programacion.getFlota().getIdBus(), pro.getIdProgramacion());
			
			t=new Tripulacion();
			t.setIdProgramacion(pro.getIdProgramacion()); 
			t.setfRegistro(new Date());
			t.setTipo(1); 
			t.setEstado(0);
			t.setIdBus(programacion.getFlota().getIdBus());
			t.setIdPiloto(programacion.getFlota().getPiloto());
			t.setIdCopiloto(programacion.getFlota().getCopiloto()); 
			tripulacionServices.crearTripulacion(t); 
			
			buscarProgramacion();
			FacesUtils.showFacesMessage("Programación de bus actualizada correctamente.",Constante.INFORMACION);

		} catch (Exception e) {
			e.printStackTrace();
		}

		this.programacion = new Programacion();
	}

	
	
	
	public void eliminarProgramacionFlota(){
		
		System.out.println("Se eliminara la programacion asignada: " + programacion.getIdProgramacion());
		try {
			Tripulacion t = tripulacionServices.findByIdProgramacion(programacion.getIdProgramacion());
			tripulacionServices.eliminarTripulacion(t.getIdTripulacion());
			programacionService.actualizarBusProgramacionNoAsig(programacion.getIdProgramacion()); 
			buscarProgramacion();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
