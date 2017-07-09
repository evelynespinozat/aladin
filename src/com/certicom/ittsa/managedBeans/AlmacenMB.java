package com.certicom.ittsa.managedBeans;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.context.RequestContext;

import com.certicom.ittsa.domain.Almacen;
import com.certicom.ittsa.domain.Log;
import com.certicom.ittsa.services.AlmacenService;
import com.certicom.ittsa.services.MenuServices;
import com.certicom.ittsa.services.OficinaService;
import com.pe.certicom.ittsa.commons.Constante;
import com.pe.certicom.ittsa.commons.FacesUtils;
import com.pe.certicom.ittsa.commons.GenericBeans;

@ManagedBean(name="almacenMB")
@ViewScoped
public class AlmacenMB extends GenericBeans implements Serializable{
	private String titulo;
	private Almacen almacen;
	private List<Almacen> listaAlmacenes;
	private List<Almacen> listaFiltroAlmacenes;
	private Boolean editar;
	
	
	//services
	private AlmacenService almacenSevice;
	private MenuServices menuServices;
	
	//datos Log
    private Log log;
	private LogMB logmb;
	
	private String descalmacenanterior;
	
	public AlmacenMB(){;}
	
	@PostConstruct
	public void incia()
	{
		this.editar = Boolean.FALSE;
	
		this.almacenSevice = new AlmacenService();
		this.menuServices=new MenuServices();
		this.almacen = new Almacen();
		
		 log = (Log)getSpringBean(Constante.SESSION_LOG);
		logmb = new LogMB();
		//listando
		try {
			this.listaAlmacenes = almacenSevice.findAll();
			int codMenu = menuServices.opcionMenuByPretty("pretty:almacen");
			log.setCod_menu(codMenu);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void cancelar(){
		this.almacen = new Almacen();
	}

	public void guardarAlmacen()
	{
		Boolean valido=Boolean.TRUE;
		RequestContext context = RequestContext.getCurrentInstance();  
   	    context.addCallbackParam("esValido", valido );
   	    
   	   this.almacen.setDescripcion(almacen.getDescripcion().toUpperCase());
   	    
		try {
			int cant = 0;
			if(this.editar){
				boolean paso = true;
				if(this.descalmacenanterior.equals(this.almacen.getDescripcion())){
					 paso = true;		
				} else{
					cant = this.almacenSevice.cantAlmacen(this.almacen.getDescripcion());
					if(cant ==0){
						paso = true;
					} else{
						paso = false;
					}
				}
				 
				if(paso){
				 this.almacenSevice.actualizarAlmacen(almacen);
				 log.setAccion("UPDATE");
	             log.setDescripcion("Se actualiza el almacen: " + almacen.getDescripcion());
	             logmb.insertarLog(log);
				 FacesUtils.showFacesMessage("Almacén actualizado correctamente.",Constante.INFORMACION);
				 context.update("sms");
				} else{
					FacesUtils.showFacesMessage("Almacén se encuentra ya registrado.",Constante.ERROR);
					context.addCallbackParam("esValido", Boolean.FALSE );
				}
				 
			} else {
				System.out.println(this.almacen.getDescripcion());
				cant = this.almacenSevice.cantAlmacen(this.almacen.getDescripcion());
				if(cant == 0){
					this.almacen.setEstado(Boolean.TRUE);
					this.almacenSevice.crearAlmacen(almacen);
					log.setAccion("INSERT");
					log.setDescripcion("Se inserta la almacen: " + almacen.getDescripcion());
					logmb.insertarLog(log);
					FacesUtils.showFacesMessage("Almacén registrado correctamente.",Constante.INFORMACION);
					context.update("sms");
				
				
				} else{
					FacesUtils.showFacesMessage("Almacén se encuentra ya registrado.",Constante.ERROR);
					context.addCallbackParam("esValido", Boolean.FALSE );
				}
			}
			listaAlmacenes = this.almacenSevice.findAll();
			

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		this.almacen = new Almacen();
		this.editar =  Boolean.FALSE;
		
	}
	
	public void cambiarEstado(Almacen agen){
		
		   if(agen.isEstado()){
			   agen.setEstado(Boolean.FALSE);
		   }else{
			   agen.setEstado(Boolean.TRUE);
		   }
		   
		   try {
			   this.almacenSevice.actualizarAlmacen(agen);
			   log.setAccion("CHANGE ESTADO");
	           log.setDescripcion("Se cambio el estado en almacén: " + almacen.getDescripcion());
	           logmb.insertarLog(log);
			   FacesUtils.showFacesMessage("Estado de almacén actualizado correctamente.",Constante.INFORMACION);
		   } catch (Exception e) {
			   System.out.println("Error:"+e.getMessage());
			   e.printStackTrace();
		   }   
	}
	
	public void eliminarAlmacen()
	{
		try {
			RequestContext context = RequestContext.getCurrentInstance();
			int cantAlmacens = 0;
	//		cantAlmacens = this.oficinaService.cantOfixAlmacen(almacen.getIdalmacen());
			if(cantAlmacens == 0)
			{
				if(!almacen.isAsignado())
				{
					this.almacenSevice.eliminarAlmacen(almacen.getIdAlmacen());
					listaAlmacenes.remove(almacen);
					FacesUtils.showFacesMessage("Almacén eliminado correctamente.",Constante.INFORMACION);
					
					log.setAccion("DELETE");
					log.setDescripcion("Se eliminó el almacén: " + almacen.getDescripcion());
					logmb.insertarLog(log);
				}else{
					FacesUtils.showFacesMessage("Almacén no se puede eliminar porque ya esta asignado.",Constante.ERROR);
				}
				
				
				
			} else {
				context.execute("dlgOmiso.show()");
			}
			
			
			

			
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.almacen =  new Almacen();
		
	}
	
	public void editarAlmacen(Almacen ag)
	{
		this.editar = Boolean.TRUE;
		this.almacen = ag;
		this.descalmacenanterior = "";
		this.descalmacenanterior = this.almacen.getDescripcion();
		this.titulo = "Modificar almacén";
	}
	
	public void nuevaAlmacen()
	{
		this.editar = Boolean.FALSE;
		this.almacen = new Almacen();
		this.titulo = "Registrar nuevo almacén";
	}

	public Almacen getAlmacen() {
		return almacen;
	}

	public List<Almacen> getListaAlmacenes() {
		return listaAlmacenes;
	}

	public List<Almacen> getListaFiltroAlmacenes() {
		return listaFiltroAlmacenes;
	}

	public Boolean getEditar() {
		return editar;
	}

	public AlmacenService getAlmacenSevice() {
		return almacenSevice;
	}

	public MenuServices getMenuServices() {
		return menuServices;
	}

	public Log getLog() {
		return log;
	}

	public LogMB getLogmb() {
		return logmb;
	}

	public void setAlmacen(Almacen almacen) {
		this.almacen = almacen;
	}

	public void setListaAlmacenes(List<Almacen> listaAlmacenes) {
		this.listaAlmacenes = listaAlmacenes;
	}

	public void setListaFiltroAlmacenes(List<Almacen> listaFiltroAlmacenes) {
		this.listaFiltroAlmacenes = listaFiltroAlmacenes;
	}

	public void setEditar(Boolean editar) {
		this.editar = editar;
	}

	public void setAlmacenSevice(AlmacenService almacenSevice) {
		this.almacenSevice = almacenSevice;
	}

	public void setMenuServices(MenuServices menuServices) {
		this.menuServices = menuServices;
	}

	public void setLog(Log log) {
		this.log = log;
	}

	public void setLogmb(LogMB logmb) {
		this.logmb = logmb;
	}

	public String getDescalmacenanterior() {
		return descalmacenanterior;
	}

	public void setDescalmacenanterior(String descalmacenanterior) {
		this.descalmacenanterior = descalmacenanterior;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	//**set an get 
	

	
	
}

