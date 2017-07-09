package com.certicom.ittsa.managedBeans;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.context.RequestContext;

import com.certicom.ittsa.domain.TipoAsiento;
import com.certicom.ittsa.domain.Log;
import com.certicom.ittsa.services.ClaseServicioServices;
import com.certicom.ittsa.services.TipoAsientoService;
import com.certicom.ittsa.services.MenuServices;
import com.certicom.ittsa.services.OficinaService;
import com.pe.certicom.ittsa.commons.Constante;
import com.pe.certicom.ittsa.commons.FacesUtils;
import com.pe.certicom.ittsa.commons.GenericBeans;

@ManagedBean(name="tipoAsientoMB")
@ViewScoped
public class TipoAsientoMB extends GenericBeans implements Serializable{
	private String titulo;
	private TipoAsiento tipoAsiento;
	private List<TipoAsiento> listaTipoAsientos;
	private List<TipoAsiento> listaFiltroTipoAsientos;
	private Boolean editar;
	
	//services
	private TipoAsientoService tipoAsientoSevice;
	private MenuServices menuServices;
	private ClaseServicioServices claseServicioServices;
	
	//datos Log
    private Log log;
	private LogMB logmb;
	
	public TipoAsientoMB(){;}
	
	@PostConstruct
	public void incia()
	{
		this.editar = Boolean.FALSE;
	
		this.tipoAsientoSevice = new TipoAsientoService();
		this.menuServices=new MenuServices();
		this.claseServicioServices = new ClaseServicioServices();
		this.tipoAsiento = new TipoAsiento();
		
		
		 log = (Log)getSpringBean(Constante.SESSION_LOG);
		logmb = new LogMB();
		//listando
		try {
			this.listaTipoAsientos = tipoAsientoSevice.findAll();
			int codMenu = menuServices.opcionMenuByPretty("pretty:tipoAsiento");
			log.setCod_menu(codMenu);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void cancelar(){
		this.tipoAsiento = new TipoAsiento();
	}

	public void guardarTipoAsiento()
	{
		Boolean valido=Boolean.TRUE;
		RequestContext context = RequestContext.getCurrentInstance();  
   	    context.addCallbackParam("esValido", valido );
   	    
		try {
			tipoAsiento.setNombre(tipoAsiento.getNombre().toUpperCase().trim());
			if(this.editar)
			{
				System.out.println("entro ene el editar ");
				this.tipoAsiento.setIdtipoasiento(this.tipoAsiento.getIdtipoasiento());
				this.tipoAsientoSevice.actualizarTipoAsiento(tipoAsiento);
				 log.setAccion("UPDATE");
	             log.setDescripcion("Se actualiza la tipoAsiento: " + tipoAsiento.getNombre());
	             logmb.insertarLog(log);
				FacesUtils.showFacesMessage("Tipo asiento actualizado correctamente.",Constante.INFORMACION);
				context.update("sms");
			}else
			{
				this.tipoAsiento.setEstado(Boolean.TRUE);
				this.tipoAsientoSevice.crearTipoAsiento(tipoAsiento);
				 log.setAccion("INSERT");
	             log.setDescripcion("Se inserta la tipoAsiento: " + tipoAsiento.getNombre());
	             logmb.insertarLog(log);
				 FacesUtils.showFacesMessage("Tipo asiento registrado correctamente.",Constante.INFORMACION);
				 context.update("sms");
			}
			listaTipoAsientos = this.tipoAsientoSevice.findAll();
			

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		this.tipoAsiento = new TipoAsiento();
		this.editar =  Boolean.FALSE;
		
	}
	
	public void cambiarEstado(TipoAsiento agen){
		
		   if(agen.getEstado()){
			   //System.out.println("es igual a uno se pone a cero");
			   agen.setEstado(Boolean.FALSE);
			   //sistem.setInd_activo(new Integer(0));
		   }else{
			   //System.out.println("es igual a cero");
			   agen.setEstado(Boolean.TRUE);
			   //sistem.setInd_activo(new Integer(1));
		   }
		   
		   try {
			   this.tipoAsientoSevice.actualizarTipoAsiento(agen);
			   log.setAccion("CHANGE ESTADO");
	           log.setDescripcion("Se cambio el estado en TipoAsiento: " + tipoAsiento.getNombre());
	           logmb.insertarLog(log);
			   FacesUtils.showFacesMessage("Estado de tipo de asiento actualizado correctamente.",Constante.INFORMACION);
		   } catch (Exception e) {
			   System.out.println("Error:"+e.getMessage());
			   e.printStackTrace();
		   }   
	}
	
	public void eliminarTipoAsiento()
	{
		try {
			RequestContext context = RequestContext.getCurrentInstance();
			
			int cantTipoAsientos = 0;
			cantTipoAsientos = this.claseServicioServices.cantClaseServicioxTipoAsiento(tipoAsiento.getIdtipoasiento());
			if(cantTipoAsientos == 0){
				this.tipoAsientoSevice.eliminarTipoAsiento(tipoAsiento.getIdtipoasiento());
				listaTipoAsientos.remove(tipoAsiento);
				FacesUtils.showFacesMessage("Tipo asiento eliminado correctamente.",Constante.INFORMACION);
				
				log.setAccion("DELETE");
				log.setDescripcion("Se elimina la TipoAsiento: " + tipoAsiento.getNombre());
				logmb.insertarLog(log);
				
			} else {
				context.execute("dlgOmiso.show()");
			}
			
			
			

			
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.tipoAsiento =  new TipoAsiento();
		
	}
	
	public void editarTipoAsiento(TipoAsiento ag)
	{
		this.editar = Boolean.TRUE;
		this.tipoAsiento = ag;
		this.titulo = "Modificar tipo de asiento";
	}
	
	public void nuevaTipoAsiento()
	{
		this.editar = Boolean.FALSE;
		this.tipoAsiento = new TipoAsiento();
		this.titulo = "Registrar nuevo tipo de asiento";
	}

	//**set an get 
	
	public MenuServices getMenuServices() {
		return menuServices;
	}

	public void setMenuServices(MenuServices menuServices) {
		this.menuServices = menuServices;
	}

	public List<TipoAsiento> getListaFiltroTipoAsientos() {
		return listaFiltroTipoAsientos;
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

	public void setListaFiltroTipoAsientos(List<TipoAsiento> listaFiltroTipoAsientos) {
		this.listaFiltroTipoAsientos = listaFiltroTipoAsientos;
	}

	public TipoAsiento getTipoAsiento() {
		return tipoAsiento;
	}

	public void setTipoAsiento(TipoAsiento tipoAsiento) {
		this.tipoAsiento = tipoAsiento;
	}

	public List<TipoAsiento> getListaTipoAsientos() {
		return listaTipoAsientos;
	}

	public void setListaTipoAsientos(List<TipoAsiento> listaTipoAsientos) {
		this.listaTipoAsientos = listaTipoAsientos;
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

