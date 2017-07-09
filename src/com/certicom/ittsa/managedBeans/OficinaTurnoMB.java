package com.certicom.ittsa.managedBeans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.context.RequestContext;

import com.certicom.ittsa.domain.Agencia;
import com.certicom.ittsa.domain.Log;
import com.certicom.ittsa.domain.Oficina;
import com.certicom.ittsa.domain.OficinaTurno;
import com.certicom.ittsa.domain.PuntoVenta;
import com.certicom.ittsa.domain.Turno;
import com.certicom.ittsa.services.AgenciaService;
import com.certicom.ittsa.services.MenuServices;
import com.certicom.ittsa.services.OficinaService;
import com.certicom.ittsa.services.OficinaTurnoService;
import com.certicom.ittsa.services.TurnoService;
import com.pe.certicom.ittsa.commons.Constante;
import com.pe.certicom.ittsa.commons.FacesUtils;
import com.pe.certicom.ittsa.commons.GenericBeans;

@ManagedBean(name="oficinaTurnoMB")
@ViewScoped
public class OficinaTurnoMB extends GenericBeans implements Serializable{
	private String titulo;
	private OficinaTurno oficinaTurno;
	private List<OficinaTurno> listaOficinaTurno;
	private List<Agencia> listaAgencia;
	private List<OficinaTurno> listaFiltroOficinaTurno;
	private List<Oficina> listaOficinas;
	private List<Turno> listaTurno;

	
	private Boolean editar;
	
	//services
	private OficinaTurnoService oficinaTurnoService;
	private AgenciaService agenciaService;
	private OficinaService oficinaService;
	private TurnoService turnoService;
	private MenuServices menuServices;
	
	//datos Log
    private Log log;
	private LogMB logmb;
	
	public OficinaTurnoMB(){
	}
	
	@PostConstruct
	public void incia()
	{
		this.editar = Boolean.FALSE;
	
		//services
		this.oficinaTurnoService = new OficinaTurnoService();
		this.agenciaService = new AgenciaService();
		this.oficinaService = new OficinaService();
		this.menuServices = new MenuServices();
		this.turnoService = new TurnoService();

		this.oficinaTurno = new OficinaTurno();
		
		log = (Log)getSpringBean(Constante.SESSION_LOG);
		logmb = new LogMB();
		//listando
		try {
			this.listaOficinaTurno = oficinaTurnoService.listaOficinaTurno();
			this.listaAgencia = agenciaService.listaAgenciasActivas();
			this.listaTurno = turnoService.listarTurnoActivos();
			int codMenu = menuServices.opcionMenuByPretty("pretty:turnoagencia");
			log.setCod_menu(codMenu);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void cancelar(){
		this.oficinaTurno = new OficinaTurno();
	}
	
	public void nuevoOficTurno(){
		this.oficinaTurno = new OficinaTurno();
		this.editar = Boolean.FALSE;
		this.listaOficinas = new ArrayList<Oficina>();
		this.titulo = "Registrar nuevo turno de oficina";
	}
	
	public void editarOficTurno(OficinaTurno oficinaTurno){
		this.oficinaTurno = oficinaTurno;
		this.editar = Boolean.TRUE;
		this.titulo = "Modificar turno de oficina";
		try {
			this.listaOficinas = this.oficinaService.getOficinasxAgencia(this.oficinaTurno.getIdAgencia());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void getOficinasxAgencia(){
		try {
			this.listaOficinas = oficinaService.getOficinasxAgencia(this.oficinaTurno.getIdAgencia());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

	public void guardarOficinaTurno()
	{
		 Boolean valido=Boolean.TRUE;
		 RequestContext context = RequestContext.getCurrentInstance();  
	   	 context.addCallbackParam("esValido", valido );
		
		try {
			if(this.editar)
			{
				this.oficinaTurno.setIdAgenciaTurno(this.oficinaTurno.getIdAgenciaTurno());
				this.oficinaTurnoService.actualizarOficinaTurno(oficinaTurno);
				 log.setAccion("UPDATE");
	             log.setDescripcion("Se actualiza la Oficina Turno: " + oficinaTurno.getIdAgenciaTurno());
	             logmb.insertarLog(log);
				FacesUtils.showFacesMessage("Oficina turno actualizados correctamente.",Constante.INFORMACION);
				 context.update("sms");
			}else
			{
				this.oficinaTurno.setEstado(Boolean.TRUE);
				this.oficinaTurnoService.crearOficinaTurno(oficinaTurno);
				 log.setAccion("INSERT");
	             log.setDescripcion("Se inserta la agencia: " + oficinaTurno.getIdAgenciaTurno());
	             logmb.insertarLog(log);
				FacesUtils.showFacesMessage("Oficina turno registrados correctamente.",Constante.INFORMACION);
				 context.update("sms");
			}
			listaOficinaTurno = this.oficinaTurnoService.listaOficinaTurno();

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		this.oficinaTurno = new OficinaTurno();
		this.editar =  Boolean.FALSE;
		
	}
	
	public void cambiarEstado(OficinaTurno oficinaTur){
		
		   if(oficinaTur.isEstado()){
			   //System.out.println("es igual a uno se pone a cero");
			   oficinaTur.setEstado(Boolean.FALSE);
			   //sistem.setInd_activo(new Integer(0));
		   }else{
			   //System.out.println("es igual a cero");
			   oficinaTur.setEstado(Boolean.TRUE);
			   //sistem.setInd_activo(new Integer(1));
		   }
		   
		   try {
			   this.oficinaTurnoService.actualizarOficinaTurno(oficinaTur);
			   log.setAccion("CHANGE ESTADO");
	           log.setDescripcion("Se cambio el estado en Oficina Turno: " + oficinaTurno.getDesAgencia());
	           logmb.insertarLog(log);
			   FacesUtils.showFacesMessage("Estado de oficina por turno actualizado correctamente.",Constante.INFORMACION);
		   } catch (Exception e) {
			   System.out.println("Error:"+e.getMessage());
			   e.printStackTrace();
		   }   
	}
	
	public void eliminarOficnaTurno()
	{
		try {
			this.oficinaTurnoService.eliminarOficinaTurno(oficinaTurno.getIdAgenciaTurno());
			listaOficinaTurno.remove(oficinaTurno);
			FacesUtils.showFacesMessage("Oficina turno eliminada correctamente.",Constante.INFORMACION);
			
			log.setAccion("DELETE");
	        log.setDescripcion("Se eliminó la Oficina Turno: " + oficinaTurno.getIdAgenciaTurno());
	        logmb.insertarLog(log);

			
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.oficinaTurno =  new OficinaTurno();
		
	}

	//**set an get 
	
	public MenuServices getMenuServices() {
		return menuServices;
	}

	public List<Turno> getListaTurno() {
		return listaTurno;
	}

	public void setListaTurno(List<Turno> listaTurno) {
		this.listaTurno = listaTurno;
	}

	public OficinaService getOficinaService() {
		return oficinaService;
	}

	public void setOficinaService(OficinaService oficinaService) {
		this.oficinaService = oficinaService;
	}

	public TurnoService getTurnoService() {
		return turnoService;
	}

	public void setTurnoService(TurnoService turnoService) {
		this.turnoService = turnoService;
	}

	public List<Oficina> getListaOficinas() {
		return listaOficinas;
	}

	public void setListaOficinas(List<Oficina> listaOficinas) {
		this.listaOficinas = listaOficinas;
	}

	public AgenciaService getAgenciaService() {
		return agenciaService;
	}

	public void setAgenciaService(AgenciaService agenciaService) {
		this.agenciaService = agenciaService;
	}

	public List<Agencia> getListaAgencia() {
		return listaAgencia;
	}

	public void setListaAgencia(List<Agencia> listaAgencia) {
		this.listaAgencia = listaAgencia;
	}

	public OficinaTurno getOficinaTurno() {
		return oficinaTurno;
	}

	public void setOficinaTurno(OficinaTurno oficinaTurno) {
		this.oficinaTurno = oficinaTurno;
	}

	public List<OficinaTurno> getListaOficinaTurno() {
		return listaOficinaTurno;
	}

	public void setListaOficinaTurno(List<OficinaTurno> listaOficinaTurno) {
		this.listaOficinaTurno = listaOficinaTurno;
	}

	public List<OficinaTurno> getListaFiltroOficinaTurno() {
		return listaFiltroOficinaTurno;
	}

	public void setListaFiltroOficinaTurno(
			List<OficinaTurno> listaFiltroOficinaTurno) {
		this.listaFiltroOficinaTurno = listaFiltroOficinaTurno;
	}

	public OficinaTurnoService getOficinaTurnoService() {
		return oficinaTurnoService;
	}

	public void setOficinaTurnoService(OficinaTurnoService oficinaTurnoService) {
		this.oficinaTurnoService = oficinaTurnoService;
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

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	
	
}

