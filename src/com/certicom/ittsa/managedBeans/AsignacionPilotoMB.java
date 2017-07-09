package com.certicom.ittsa.managedBeans;

import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.context.RequestContext;

import pe.gob.mtc.wshr.Conductor;
import pe.gob.mtc.wshr.Errores;
import pe.gob.mtc.wshr.ResultConductor;
import pe.gob.mtc.wshr.Seguridad;
import pe.gob.mtc.wshr.WSServiciosHR;
import pe.gob.mtc.wshr.WSServiciosHRLocator;
import pe.gob.mtc.wshr.WSServiciosHRSoap;
import pe.gob.mtc.wshr.WSServiciosHRSoapStub;

import com.certicom.ittsa.domain.Agencia;
import com.certicom.ittsa.domain.ClaseServicio;
import com.certicom.ittsa.domain.Destino;
import com.certicom.ittsa.domain.Excepcion;
import com.certicom.ittsa.domain.Flota;
import com.certicom.ittsa.domain.Historial_AsigPCT;
import com.certicom.ittsa.domain.Log;
import com.certicom.ittsa.domain.Piloto;
import com.certicom.ittsa.domain.Programacion;
import com.certicom.ittsa.domain.Servicio;
import com.certicom.ittsa.domain.Tripulacion;
import com.certicom.ittsa.services.AgenciaService;
import com.certicom.ittsa.services.ClaseServicioServices;
import com.certicom.ittsa.services.DestinoServices;
import com.certicom.ittsa.services.ExcepcionService;
import com.certicom.ittsa.services.FlotaService;
import com.certicom.ittsa.services.Historial_AsigPCTServices;
import com.certicom.ittsa.services.MenuServices;
import com.certicom.ittsa.services.ParametroServices;
import com.certicom.ittsa.services.PilotoService;
import com.certicom.ittsa.services.ProgramacionService;
import com.certicom.ittsa.services.ServicioService;
import com.certicom.ittsa.services.TripulacionServices;
import com.pe.certicom.ittsa.commons.Constante;
import com.pe.certicom.ittsa.commons.FacesUtils;
import com.pe.certicom.ittsa.commons.GenericBeans;

@ManagedBean(name = "asignacionPilotoMB")
@ViewScoped
public class AsignacionPilotoMB extends GenericBeans implements Serializable {

	private Flota bus;
	//private Flota busAnt;
	private String mensajeError =""; 

	private List<Flota> listaBuses;
	private List<Flota> listaBusesFilter;
	private List<Piloto> listaPilotos;
	private List<Piloto> listaCoPilotos;
	private List<Historial_AsigPCT> listaHistorial;


	private Date fechaProgramacion;
	private Integer idOrigen;
	private Integer idDestino;
	private Flota busSelected;
	private boolean editarBus;
	private boolean editarPiloto;
	private boolean editarCopiloto;
	
	private Date fechaInicio;
	private Date fechaFin;

	
	private Integer idPiloto;
	private Integer idCopiloto;
	
	// services
	private MenuServices menuServices;
	private FlotaService busServices;
	private PilotoService pilotoServices;
	private Historial_AsigPCTServices historial_asigPCTServices;
	private ParametroServices parametroServices;


	// datos Log
	private Log log;
	private LogMB logmb;

	public AsignacionPilotoMB() {
	}

	@PostConstruct
	public void inicia() {

		this.menuServices = new MenuServices();
		this.busServices = new FlotaService();
		this.pilotoServices = new PilotoService();	
		this.historial_asigPCTServices = new Historial_AsigPCTServices();
		this.parametroServices = new ParametroServices();


		log = (Log) getSpringBean(Constante.SESSION_LOG);
		logmb = new LogMB();
		// listando
		try {
			obtenerListaBuses();
			int codMenu = menuServices.opcionMenuByPretty("pretty:progFlota");
			log.setCod_menu(codMenu);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		this.fechaInicio = new Date();
		this.fechaFin = new Date();
	}
	
	public void obtenerListaBuses(){
		try {
			this.listaBuses = busServices.findAll2();
			for(Flota bus : listaBuses){
				if(bus.getPiloto()!=null){
					Piloto p = pilotoServices.findById(bus.getPiloto());
					if(p!=null){
						bus.setNombPiloto(p.getApellidoPaterno() + " "+p.getApellidoMaterno() +" " + p.getNombres());
					}
					
				}
				if(bus.getCopiloto()!=null){
					Piloto c = pilotoServices.findById(bus.getCopiloto());
					if(c!=null){
						bus.setNombCopiloto(c.getApellidoPaterno() + " "+c.getApellidoMaterno() +" " + c.getNombres());
						System.out.println(bus.getNombCopiloto());
					}
					
				}
								
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	public void cambiarEstado(Flota flo){
		
		   if(flo.isEstado()){
			   flo.setEstado(Boolean.FALSE);
		   }else{
			   flo.setEstado(Boolean.TRUE);
		   }
		   
		   try {
			   this.busServices.actualizarFlota(flo);
			   log.setAccion("CHANGE ESTADO");
	           log.setDescripcion("Se cambio el estado en Flota: " + flo.getPlaca());
	           logmb.insertarLog(log);
			   FacesUtils.showFacesMessage("Estado de flota actualizado correctamente.",Constante.INFORMACION);
		   } catch (Exception e) {
			   System.out.println("Error:"+e.getMessage());
			   e.printStackTrace();
		   }   
	}
	
	public void setearFlota(Flota ag)
	{
		this.setIdPiloto(ag.getPiloto());
		this.setIdCopiloto(ag.getCopiloto()); 
		this.bus = ag;		
		try {
			listaPilotos = this.pilotoServices.pilotosDisponibles();
			listaCoPilotos = this.pilotoServices.copilotosDisponibles();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		this.listaHistorial = new ArrayList<>();
	}
	
	
	
	
	public void obtenerHistorial(){
		try {
			Date fFin = sumaDias(getFechaFin(),1);
			this.listaHistorial = historial_asigPCTServices.findHistorialByFechas(bus.getIdBus(), getFechaInicio(),fFin);
			for(Historial_AsigPCT h : listaHistorial){
				if(h.getIdPiloto()!=null){
					Piloto p = pilotoServices.findById(h.getIdPiloto());
					h.setNombPiloto(p.getApellidoPaterno()+" "+p.getApellidoMaterno()+" "+p.getNombres()); 
				}
				if(h.getIdCopiloto()!=null){
					Piloto c = pilotoServices.findById(h.getIdCopiloto());
					h.setNombCopiloto(c.getApellidoPaterno()+" "+c.getApellidoMaterno()+" "+c.getNombres());
				}
				 
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public Date sumaDias(Date fecha, int dias) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(fecha);
		cal.add(Calendar.DAY_OF_YEAR, dias);
		return cal.getTime();
	}
	
	public void guardarPiloto(Piloto p){
		
		this.bus.setPiloto(p.getIdPiloto());
		RequestContext context = RequestContext.getCurrentInstance();  
		Historial_AsigPCT historial = new Historial_AsigPCT();
		historial.setIdBus(this.bus.getIdBus());
		historial.setFecha(new Date());
		historial.setEstado("0"); 
		try {
			if(this.bus.getPiloto()!=null && (getIdPiloto()!= bus.getPiloto())){
				boolean _paseParametro = false;
				String valor = this.parametroServices.findParametro_byNombre("VALIDACION_WEB_SERVICE");
				if(valor.equals("1")){
					//validacion MTC 
					WSServiciosHR w = new WSServiciosHRLocator();
					WSServiciosHRSoap soap = new WSServiciosHRSoapStub(new URL(w.getWSServiciosHRSoapAddress()),w);
					Seguridad seguridad = new Seguridad();
					seguridad.setUsuario(Constante.WEBSERVICE_USUARIO);
					seguridad.setClave(Constante.WEBSERVICE_CLAVE);
					seguridad.setPartida(Constante.WEBSERVICE_PARTIDA);
					seguridad.setRuc(Constante.WEBSERVICE_RUC);
					Conductor oConductor = new Conductor();
					oConductor.setSeguridad(seguridad);
					oConductor.setTpoDocumento("L.E.");
					oConductor.setNroDocumento(p.getdNI());
					ResultConductor rConductor = soap.getConductor(oConductor);
					if(rConductor.is_return()){
						_paseParametro = true;
					}else{
						_paseParametro = false;
						this.mensajeError = "";
						for (int i = 0; i < rConductor.getErrores().length; i++) {
							Errores err = rConductor.getErrores()[i];
							FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, err.getCode() +" - "+ err.getInfo(),""));
						}
						//FacesUtils.showFacesMessage(this.mensajeError,Constante.ERROR);
					}
				}else{
					_paseParametro = true;
				}
				
				//
				if(_paseParametro){
					this.busServices.actualizarPiloto(bus.getPiloto(), bus.getIdBus()); //actualiza la tabla t_flota con e id del piloto
					historial.setIdPiloto(bus.getPiloto()); 
					//actualizamos la discponibilidad del piloto 
					this.pilotoServices.actualizarAsignacion(p.getIdPiloto(),true); //pone el flag asignado de t_piloto a 1
					this.historial_asigPCTServices.actualizarEstadoHistorial_Piloto(historial);
					historial.setEstado("1"); 
					this.historial_asigPCTServices.crearHistorial_Piloto(historial);
					obtenerListaBuses();
					this.pilotoServices.actualizarAsignacion(idPiloto,false);
					FacesUtils.showFacesMessage("Piloto registrado en programación correctamente.",Constante.INFORMACION);
					context.update("sms");
					context.execute("dlgAsignarPiloto.hide();");
				}
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	
	
public void guardarCopiloto(Piloto p){
		
		this.bus.setCopiloto(p.getIdPiloto());
		
		RequestContext context = RequestContext.getCurrentInstance();  
		Historial_AsigPCT historial = new Historial_AsigPCT();
		historial.setIdBus(bus.getIdBus());
		historial.setFecha(new Date());
		historial.setEstado("0"); 
		try {
			
			if(bus.getCopiloto()!=null && (getIdCopiloto()!= bus.getCopiloto())){
				boolean _paseParametro = false;
				String valor = this.parametroServices.findParametro_byNombre("VALIDACION_WEB_SERVICE");
				if(valor.equals("1")){
				//validacion MTC 
				WSServiciosHR w = new WSServiciosHRLocator();
				WSServiciosHRSoap soap = new WSServiciosHRSoapStub(new URL(w.getWSServiciosHRSoapAddress()),w);
				Seguridad seguridad = new Seguridad();
				seguridad.setUsuario(Constante.WEBSERVICE_USUARIO);
				seguridad.setClave(Constante.WEBSERVICE_CLAVE);
				seguridad.setPartida(Constante.WEBSERVICE_PARTIDA);
				seguridad.setRuc(Constante.WEBSERVICE_RUC);
				Conductor oConductor = new Conductor();
				oConductor.setSeguridad(seguridad);
				oConductor.setTpoDocumento("L.E.");
				oConductor.setNroDocumento(p.getdNI());
				ResultConductor rConductor = soap.getConductor(oConductor);
				if(rConductor.is_return()){
					_paseParametro = true;
				}else{
					_paseParametro = false;
					this.mensajeError = "";
					for (int i = 0; i < rConductor.getErrores().length; i++) {
						Errores err = rConductor.getErrores()[i];
						this.mensajeError = this.mensajeError +  err.getCode() +" - "+ err.getInfo() +". \n";
					}
					FacesUtils.showFacesMessage(this.mensajeError,Constante.ERROR);
				}
				}else{
					_paseParametro = true;
				}
				
				if(_paseParametro){
					busServices.actualizarCopiloto(bus.getCopiloto(), bus.getIdBus());
					historial.setIdCopiloto(bus.getCopiloto()); 
					//actualizamos la discponibilidad del piloto 
					this.pilotoServices.actualizarAsignacion(p.getIdPiloto(), true);
					historial_asigPCTServices.actualizarEstadoHistorial_Piloto(historial);
					historial.setEstado("1"); 
					historial_asigPCTServices.crearHistorial_Copiloto(historial);
					obtenerListaBuses();
					this.pilotoServices.actualizarAsignacion(idCopiloto,false);
					FacesUtils.showFacesMessage("Copiloto registrado en programación correctamente.",Constante.INFORMACION);
					context.update("sms");
					context.execute("dlgAsignarCoPiloto.hide()");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}

	/**********************Getter's and Seter's************************/

	public MenuServices getMenuServices() {
		return menuServices;
	}


	public Date getFechaProgramacion() {
		return fechaProgramacion;
	}

	public void setFechaProgramacion(Date fechaProgramacion) {
		this.fechaProgramacion = fechaProgramacion;
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

	public Flota getBus() {
		return bus;
	}

	public void setBus(Flota bus) {
		this.bus = bus;
	}

	public List<Piloto> getListaPilotos() {
		return listaPilotos;
	}

	public void setListaPilotos(List<Piloto> listaPilotos) {
		this.listaPilotos = listaPilotos;
	}

	public Integer getIdPiloto() {
		return idPiloto;
	}

	public void setIdPiloto(Integer idPiloto) {
		this.idPiloto = idPiloto;
	}

	public Integer getIdCopiloto() {
		return idCopiloto;
	}

	public void setIdCopiloto(Integer idCopiloto) {
		this.idCopiloto = idCopiloto;
	}

	public List<Flota> getListaBusesFilter() {
		return listaBusesFilter;
	}

	public void setListaBusesFilter(List<Flota> listaBusesFilter) {
		this.listaBusesFilter = listaBusesFilter;
	}

	public List<Historial_AsigPCT> getListaHistorial() {
		return listaHistorial;
	}

	public void setListaHistorial(List<Historial_AsigPCT> listaHistorial) {
		this.listaHistorial = listaHistorial;
	}

	public Date getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public Date getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}

	public String getMensajeError() {
		return mensajeError;
	}

	public void setMensajeError(String mensajeError) {
		this.mensajeError = mensajeError;
	}

	public List<Piloto> getListaCoPilotos() {
		return listaCoPilotos;
	}

	public void setListaCoPilotos(List<Piloto> listaCoPilotos) {
		this.listaCoPilotos = listaCoPilotos;
	}

}
