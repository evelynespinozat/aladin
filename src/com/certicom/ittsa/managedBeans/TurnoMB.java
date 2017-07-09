package com.certicom.ittsa.managedBeans;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.context.RequestContext;

import com.certicom.ittsa.domain.Agencia;
import com.certicom.ittsa.domain.Log;
import com.certicom.ittsa.domain.Perfil;
import com.certicom.ittsa.domain.Turno;
import com.certicom.ittsa.services.AgenciaService;
import com.certicom.ittsa.services.MenuServices;
import com.certicom.ittsa.services.OficinaTurnoService;
import com.certicom.ittsa.services.TurnoService;
import com.pe.certicom.ittsa.commons.Constante;
import com.pe.certicom.ittsa.commons.FacesUtils;
import com.pe.certicom.ittsa.commons.GenericBeans;

@ManagedBean(name="turnoMB")
@ViewScoped
public class TurnoMB extends GenericBeans implements Serializable{
	private String titulo;
	private Turno turno;
	private List<Turno> listaTurno;
	private List<Turno> listaFiltroTurno;
	private Boolean editar;
	
	//services
	private TurnoService turnoSevice;
	private MenuServices menuServices;
	private OficinaTurnoService oficinaTurnoService;
	
	//datos Log
    private Log log;
	private LogMB logmb;
	
	public TurnoMB(){;}
	
	@PostConstruct
	public void incia()
	{
		this.editar = Boolean.FALSE;
		
		this.turnoSevice = new TurnoService();
		this.menuServices=new MenuServices();
		this.oficinaTurnoService = new OficinaTurnoService();
		this.turno = new Turno();
		
		 log = (Log)getSpringBean(Constante.SESSION_LOG);
		logmb = new LogMB();

		//listando
		try {
			this.listaTurno = turnoSevice.findAll();
			int codMenu = menuServices.opcionMenuByPretty("pretty:turno");
			log.setCod_menu(codMenu);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void cancelar(){
		this.turno = new Turno();
	}
	
	public void nuevoTurno(){
		this.turno = new Turno();
		this.editar = Boolean.FALSE;
		this.titulo = "Registrar nuevo turno";
	}
	
	public void editarTurno(Turno t){
		this.turno = t;
		this.editar = Boolean.TRUE;
		this.titulo = "Modificar turno";
	}
	
	private String dateToString(Date date){
		SimpleDateFormat formato = new SimpleDateFormat("HH:mm");		
		String fecha = formato.format(date);
		return fecha;
	}
	

	public void guardarTurno()
	{
		 Boolean valido=Boolean.TRUE;
		 RequestContext context = RequestContext.getCurrentInstance();  
	   	 context.addCallbackParam("esValido", valido );
		
		try {
			turno.sethInicio(dateToString(turno.getHoraInicio()));
			turno.sethFin(dateToString(turno.getHoraFin()));
			turno.setDescripcion(turno.getDescripcion().toUpperCase().trim());
			if(this.editar)
			{
				this.turno.setIdTurno(this.turno.getIdTurno());
				this.turnoSevice.actualizarTurno(turno);
				 log.setAccion("UPDATE");
	             log.setDescripcion("Se actualiza la turno: " + turno.getDescripcion());
	             logmb.insertarLog(log);
				FacesUtils.showFacesMessage("Turno actualizado correctamente.",Constante.INFORMACION);
				 context.update("sms");
			}else
			{
				this.turno.setEstado(Boolean.TRUE);
				this.turnoSevice.crearTurno(turno);
				 log.setAccion("UPDATE");
	             log.setDescripcion("Se registro la turno: " + turno.getDescripcion());
	             logmb.insertarLog(log);
				FacesUtils.showFacesMessage("Turno registrado correctamente.",Constante.INFORMACION);
				 context.update("sms");
			}
			listaTurno = this.turnoSevice.findAll();
			

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		this.turno = new Turno();
		this.editar =  Boolean.FALSE;
		
	}
	
	public void cambiarEstado(Turno turn){
		
		   if(turn.isEstado()){
			   //System.out.println("es igual a uno se pone a cero");
			   turn.setEstado(Boolean.FALSE);
			   //sistem.setInd_activo(new Integer(0));
		   }else{
			   //System.out.println("es igual a cero");
			   turn.setEstado(Boolean.TRUE);
			   //sistem.setInd_activo(new Integer(1));
		   }
		   
		   try {
			   this.turnoSevice.actualizarTurno(turn);
			   log.setAccion("CHANGE ESTADO");
	           log.setDescripcion("Se cambio el estado en turno: " + turno.getDescripcion());
	           logmb.insertarLog(log);
			   FacesUtils.showFacesMessage("Estado de turno actualizado correctamente.",Constante.INFORMACION);
		   } catch (Exception e) {
			   System.out.println("Error:"+e.getMessage());
			   e.printStackTrace();
		   }   
	}
	
	public void eliminarTurno()
	{
	
		try {
			RequestContext context = RequestContext.getCurrentInstance();
        	
			int cant = this.oficinaTurnoService.cantTurnAgencia(turno.getIdTurno());
			if(cant>0){
				context.execute("dlgOmiso.show()");
			} else {
				this.turnoSevice.eliminarTurno(turno.getIdTurno());
				listaTurno.remove(turno);
				FacesUtils.showFacesMessage("Turno eliminado correctamente.",Constante.INFORMACION);
				
				log.setAccion("DELETE");
				log.setDescripcion("Se elimina la Turno: " + turno.getDescripcion());
				logmb.insertarLog(log);
			}
			

		} catch (Exception e) {
			e.printStackTrace();
		}
		this.turno =  new Turno();
		
	}
	
	//set and Get
	
	

	public Boolean getEditar() {
		return editar;
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

	public List<Turno> getListaFiltroTurno() {
		return listaFiltroTurno;
	}

	public void setListaFiltroTurno(List<Turno> listaFiltroTurno) {
		this.listaFiltroTurno = listaFiltroTurno;
	}

	public Turno getTurno() {
		return turno;
	}

	public void setTurno(Turno turno) {
		this.turno = turno;
	}

	public List<Turno> getListaTurno() {
		return listaTurno;
	}

	public void setListaTurno(List<Turno> listaTurno) {
		this.listaTurno = listaTurno;
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

