package com.certicom.ittsa.managedBeans;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.context.RequestContext;

import com.certicom.ittsa.domain.TipoFlota;
import com.certicom.ittsa.domain.Log;
import com.certicom.ittsa.services.TipoFlotaService;
import com.certicom.ittsa.services.MenuServices;
import com.pe.certicom.ittsa.commons.Constante;
import com.pe.certicom.ittsa.commons.FacesUtils;
import com.pe.certicom.ittsa.commons.GenericBeans;

@ManagedBean(name="tipoFlotaMB")
@ViewScoped
public class TipoFlotaMB extends GenericBeans implements Serializable{
	private String titulo;
	private TipoFlota tipoFlota;
	private List<TipoFlota> listaTipoFlotas;
	private List<TipoFlota> listaFiltroTipoFlotas;
	private Boolean editar;
	
	//services
	private TipoFlotaService tipoFlotaSevice;
	private MenuServices menuServices;
	
	//datos Log
    private Log log;
	private LogMB logmb;
	
	public TipoFlotaMB(){;}
	
	@PostConstruct
	public void incia()
	{
		this.editar = Boolean.FALSE;
	
		this.tipoFlotaSevice = new TipoFlotaService();
		this.menuServices=new MenuServices();
		this.tipoFlota = new TipoFlota();
		
		 log = (Log)getSpringBean(Constante.SESSION_LOG);
		logmb = new LogMB();
		//listando
		try {
			this.listaTipoFlotas = tipoFlotaSevice.findAll();
			int codMenu = menuServices.opcionMenuByPretty("pretty:tipoFlota");
			log.setCod_menu(codMenu);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void cancelar(){
		this.tipoFlota = new TipoFlota();
	}

	public void guardarTipoFlota()
	{
		Boolean valido=Boolean.TRUE;
		RequestContext context = RequestContext.getCurrentInstance();  
   	    context.addCallbackParam("esValido", valido );
   	    
		try {
			if(this.editar)
			{
				this.tipoFlota.setIdTipoFlota(this.tipoFlota.getIdTipoFlota());
				this.tipoFlotaSevice.actualizarTipoFlota(tipoFlota);
				 log.setAccion("UPDATE");
	             log.setDescripcion("Se actualiza la tipoFlota: " + tipoFlota.getDescripcion());
	             logmb.insertarLog(log);
				FacesUtils.showFacesMessage("Tipo flota actualizado correctamente.",Constante.INFORMACION);
				context.update("sms");
			}else
			{
				this.tipoFlotaSevice.crearTipoFlota(tipoFlota);
				 log.setAccion("INSERT");
	             log.setDescripcion("Se inserta la tipo Flota: " + tipoFlota.getDescripcion());
	             logmb.insertarLog(log);
				 FacesUtils.showFacesMessage("Tipo flota registrado correctamente.",Constante.INFORMACION);
				 context.update("sms");
			}
			listaTipoFlotas = this.tipoFlotaSevice.findAll();
			

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		this.tipoFlota = new TipoFlota();
		this.editar =  Boolean.FALSE;
		
	}
	
	public void cambiarEstado(TipoFlota tipFlo){
		
		   if(tipFlo.isEstado()){
			   //System.out.println("es igual a uno se pone a cero");
			   tipFlo.setEstado(Boolean.FALSE);
			   //sistem.setInd_activo(new Integer(0));
		   }else{
			   //System.out.println("es igual a cero");
			   tipFlo.setEstado(Boolean.TRUE);
			   //sistem.setInd_activo(new Integer(1));
		   }
		   
		   try {
			   this.tipoFlotaSevice.actualizarTipoFlota(tipFlo);
			   log.setAccion("CHANGE ESTADO");
	           log.setDescripcion("Se cambio el estado en TipoFlota: " + tipoFlota.getDescripcion());
	           logmb.insertarLog(log);
			   FacesUtils.showFacesMessage("Estado de tipo de flota actualizado correctamente.",Constante.INFORMACION);
		   } catch (Exception e) {
			   System.out.println("Error:"+e.getMessage());
			   e.printStackTrace();
		   }   
	}
	
	public void eliminarTipoFlota()
	{
		try {
//			RequestContext context = RequestContext.getCurrentInstance();
//			
//			int cantTipoFlotas = 0;
//			cantTipoFlotas = this.oficinaService.cantOfixTipoFlota(tipoFlota.getIdtipoFlota());
//			if(cantTipoFlotas == 0){
				this.tipoFlotaSevice.eliminarTipoFlota(tipoFlota.getIdTipoFlota());
				listaTipoFlotas.remove(tipoFlota);
				FacesUtils.showFacesMessage("Tipo flota eliminado correctamente.",Constante.INFORMACION);
				
				log.setAccion("DELETE");
				log.setDescripcion("Se elimina la TipoFlota: " + tipoFlota.getDescripcion());
				logmb.insertarLog(log);
				
//			} else {
//				context.execute("dlgOmiso.show()");
//			}
//			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.tipoFlota =  new TipoFlota();
		
	}
	
	public void editarTipoFlota(TipoFlota ag)
	{
		this.editar = Boolean.TRUE;
		this.tipoFlota = ag;
		this.titulo = "Modificar tipo de flota";
	}
	
	public void nuevaTipoFlota()
	{
		this.editar = Boolean.FALSE;
		this.tipoFlota = new TipoFlota();
		this.titulo = "Registrar nuevo tipo de flota";
	}

	//**set an get 
	
	public MenuServices getMenuServices() {
		return menuServices;
	}

	public void setMenuServices(MenuServices menuServices) {
		this.menuServices = menuServices;
	}

	public List<TipoFlota> getListaFiltroTipoFlotas() {
		return listaFiltroTipoFlotas;
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

	public void setListaFiltroTipoFlotas(List<TipoFlota> listaFiltroTipoFlotas) {
		this.listaFiltroTipoFlotas = listaFiltroTipoFlotas;
	}

	public TipoFlota getTipoFlota() {
		return tipoFlota;
	}

	public void setTipoFlota(TipoFlota tipoFlota) {
		this.tipoFlota = tipoFlota;
	}

	public List<TipoFlota> getListaTipoFlotas() {
		return listaTipoFlotas;
	}

	public void setListaTipoFlotas(List<TipoFlota> listaTipoFlotas) {
		this.listaTipoFlotas = listaTipoFlotas;
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

