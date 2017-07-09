package com.certicom.ittsa.managedBeans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.certicom.ittsa.domain.Excepcion;
import com.certicom.ittsa.domain.Log;
import com.certicom.ittsa.services.ExcepcionService;
import com.certicom.ittsa.services.MenuServices;
import com.pe.certicom.ittsa.commons.Constante;
import com.pe.certicom.ittsa.commons.FacesUtils;
import com.pe.certicom.ittsa.commons.GenericBeans;

@ManagedBean(name="excepcionMB")
@ViewScoped
public class ExcepcionMB extends GenericBeans implements Serializable{
	private String titulo;
	private Excepcion excepcion;
	private List<Excepcion> listaExcepcion;
	private List<Excepcion> listaFiltroExcepcion;
	private Boolean editar;
	
	//services
	private ExcepcionService excepcionService;
	private MenuServices menuServices;
	
	//datos Log
    private Log log;
	private LogMB logmb;
	private String anio;
	private String anioGeneracion;
	
	
	public ExcepcionMB(){;}
	
	@PostConstruct
	public void incia()
	{
		this.editar = Boolean.FALSE;
	
		this.excepcionService = new ExcepcionService();
		this.menuServices=new MenuServices();
		this.excepcion = new Excepcion();
		
		this.listaExcepcion = new ArrayList<>();
		
		 Calendar calendar = Calendar.getInstance();
	     calendar.setTime(new Date());
	     this.anio = calendar.get(Calendar.YEAR)+"";
		
		
		 log = (Log)getSpringBean(Constante.SESSION_LOG);
		logmb = new LogMB();
		
	}
	
	public void cancelar(){
		this.excepcion = new Excepcion();
	}

	public void guardarExcepcion()
	{
		System.out.println("guardado Excepcion");
		System.out.println();
		
		try {
			
			//validando
			Excepcion excep = this.excepcionService.findExcepcionByFecha(this.excepcion.getFecha());
			
			if(excep == null)
			{
				excepcion.setDescripcion(excepcion.getDescripcion().toUpperCase().trim());
				if(this.editar)
				{
					this.excepcionService.actualizarExcepcion(excepcion);
					 log.setAccion("UPDATE");
		             log.setDescripcion("Se actualiza la excepcion: " + excepcion.getDescripcion());
		             logmb.insertarLog(log);
					FacesUtils.showFacesMessage("Excepción actualizada correctamente.",Constante.INFORMACION);
				}else
				{
					this.excepcion.setEstado(Boolean.TRUE);	
					this.excepcionService.crearExcepcion(excepcion);
					this.listaExcepcion.clear();
					this.listaExcepcion = this.excepcionService.findAll();
					 log.setAccion("INSERT");
		             log.setDescripcion("Se inserta la Excepcion: " + excepcion.getDescripcion());
		             logmb.insertarLog(log);
					FacesUtils.showFacesMessage("Excepcion: "+excepcion.getDescripcion()+" registrado correctamente.",Constante.INFORMACION);
				}
			}else{
				
				FacesUtils.showFacesMessage("Registro similar en la misma fecha se encuentran ya registrado en la excepción.",1);
			}
			

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		this.excepcion = new Excepcion();
		this.editar =  Boolean.FALSE;
		
	}
	
	public void cambiarEstado(Excepcion exce){
		
		   if(exce.isEstado()){
			   exce.setEstado(Boolean.FALSE);
		   }else{
			   exce.setEstado(Boolean.TRUE);
		   }
		   
		   try {
			   this.excepcionService.actualizarExcepcion(exce);
				   //this.sistemaServices.updateSistema(sistem);
			   FacesUtils.showFacesMessage("Estado de excepción: "+excepcion.getDescripcion()+" actualizado correctamente.",Constante.INFORMACION);
		   } catch (Exception e) {
			   System.out.println("Error:"+e.getMessage());
			   e.printStackTrace();
		   }   
	}
	
	public void eliminarExcepcion()
	{
		try {
			this.excepcionService.eliminarExcepcion(excepcion.getIdexcepcion());
			listaExcepcion.remove(excepcion);
			FacesUtils.showFacesMessage("Excepcion: "+excepcion.getDescripcion()+" del año "+this.anio+" ha sido eliminada correctamente.",Constante.INFORMACION);
			
			log.setAccion("DELETE");
	        log.setDescripcion("Se elimina la Excepcion: " + excepcion.getDescripcion());
	        logmb.insertarLog(log);

			
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.excepcion =  new Excepcion();
		
	}
	
	public void editarExcepcion(Excepcion ex)
	{
		this.editar = Boolean.TRUE;
		this.excepcion = ex;
		this.titulo = "Modificar excepción";
	}
	
	public void nuevaExcepcion()
	{
		this.editar = Boolean.FALSE;
		this.excepcion = new Excepcion();
		this.titulo = "Registrar nueva excepción";
	}

	public void listarExcepciones()
	{
		
		//listando
				try {
					this.listaExcepcion = excepcionService.findByAnio(this.anio);
					int codMenu = menuServices.opcionMenuByPretty("pretty:excepcion");
					log.setCod_menu(codMenu);
				} catch (Exception e) {
					e.printStackTrace();
				}
		
	}
	
	
	public void generarExcepcionesDefecto()
	{
		System.out.println("gerando al excepcion para el anio:"+this.anioGeneracion);
		Boolean resultado = this.excepcionService.crearExcepcionPorDefecto(this.anioGeneracion);
		if(resultado)
		{
			FacesUtils.showFacesMessage("Excepciones por defecto para el año:"+this.anioGeneracion+" registrado correctamente.",3);
		}else{
			FacesUtils.showFacesMessage("Registros se encuentran o no ya registrados del año anterior a "+this.anioGeneracion+" en la excepción.",1);
		}
		
		
	}
	
	//**set an get 
	
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

	public Boolean getEditar() {
		return editar;
	}

	public void setEditar(Boolean editar) {
		this.editar = editar;
	}

	public Excepcion getExcepcion() {
		return excepcion;
	}

	public void setExcepcion(Excepcion excepcion) {
		this.excepcion = excepcion;
	}

	public List<Excepcion> getListaExcepcion() {
		return listaExcepcion;
	}

	public void setListaExcepcion(List<Excepcion> listaExcepcion) {
		this.listaExcepcion = listaExcepcion;
	}

	public List<Excepcion> getListaFiltroExcepcion() {
		return listaFiltroExcepcion;
	}

	public void setListaFiltroExcepcion(List<Excepcion> listaFiltroExcepcion) {
		this.listaFiltroExcepcion = listaFiltroExcepcion;
	}

	public ExcepcionService getExcepcionService() {
		return excepcionService;
	}

	public void setExcepcionService(ExcepcionService excepcionService) {
		this.excepcionService = excepcionService;
	}

	public String getAnio() {
		return anio;
	}

	public void setAnio(String anio) {
		this.anio = anio;
	}

	public String getAnioGeneracion() {
		return anioGeneracion;
	}

	public void setAnioGeneracion(String anioGeneracion) {
		this.anioGeneracion = anioGeneracion;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	
	
}

