package com.certicom.ittsa.managedBeans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.faces.model.SelectItem;

import org.primefaces.context.RequestContext;

import com.certicom.ittsa.domain.ClaseServicio;
import com.certicom.ittsa.domain.Flota;
import com.certicom.ittsa.domain.Log;
import com.certicom.ittsa.domain.TipoCombustible;
import com.certicom.ittsa.domain.TipoFlota;
import com.certicom.ittsa.services.ClaseServicioServices;
import com.certicom.ittsa.services.FlotaService;
import com.certicom.ittsa.services.MenuServices;
import com.certicom.ittsa.services.TipoCombustibleService;
import com.certicom.ittsa.services.TipoFlotaService;
import com.pe.certicom.ittsa.commons.Constante;
import com.pe.certicom.ittsa.commons.FacesUtils;
import com.pe.certicom.ittsa.commons.GenericBeans;

@ManagedBean(name="flotaMB")
@ViewScoped
public class FlotaMB extends GenericBeans implements Serializable{
	private String titulo;
	private Flota flota;
	private List<Flota> listaFlotas;
	private List<Flota> listaFiltroFlotas;
	private List<ClaseServicio> listaClaseServicio;
	private List<TipoCombustible> listaTipoCombustible;
	private List<TipoFlota> listaTipoFlota;
	private Boolean editar;
	private SelectItem[] listaFiltroTipoFlota;  
	private Integer editKmAproximado = 0;
	private Integer editRecorrido = 0;
	
	//services
	private FlotaService flotaSevice;
	private ClaseServicioServices claseServicioServices;
	private TipoFlotaService tipoFlotaService;
	private TipoCombustibleService tipoCombustibleService;
	private MenuServices menuServices;
	
	//datos Log
    private Log log;
	private LogMB logmb;
	
	public FlotaMB(){;}
	
	@PostConstruct
	public void incia()
	{
		this.editar = Boolean.FALSE;
		
		this.listaClaseServicio = new ArrayList<>();
	    this.listaTipoCombustible = new ArrayList<>();
	    
		this.flotaSevice = new FlotaService();
		this.claseServicioServices = new ClaseServicioServices();
		this.tipoFlotaService = new TipoFlotaService();
		this.tipoCombustibleService = new TipoCombustibleService();
		this.menuServices=new MenuServices();
		this.flota = new Flota();
		
		 log = (Log)getSpringBean(Constante.SESSION_LOG);
		logmb = new LogMB();
		
		//listando
		try {
			this.listaFlotas = flotaSevice.findAll();
			this.listaClaseServicio = claseServicioServices.listaCServiciosActivos();
			this.listaTipoFlota = tipoFlotaService.listaTipoFlotasActivas();
			this.listaTipoCombustible = tipoCombustibleService.listaTipoCombustibleActivas();
			int codMenu = menuServices.opcionMenuByPretty("pretty:flotaVehicular");
			log.setCod_menu(codMenu);
			
			listaFiltroTipoFlota = filterTipoFlota();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public String asociarSistemas(Flota flo){
		String outcome = null;
		try {
    		Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
    		flash.put("flota", flo);
    		outcome="pretty:flotaAutomotor";
		} catch (Exception e) {	
			e.printStackTrace();
		}
		return outcome;
	}
	
	public String mantenimiento(Flota flo){
		String outcome = null;
		try {
    		Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
    		flash.put("flota", flo);
    		outcome="pretty:mantenimientoFlota";
		} catch (Exception e) {	
			e.printStackTrace();
		}
		return outcome;
	}
	
	public String kilometraje(Flota flo){
		String outcome = null;
		try {
    		Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
    		flash.put("flota", flo);
    		outcome="pretty:kilometrajeFlota";
		} catch (Exception e) {	
			e.printStackTrace();
		}
		return outcome;
	}
	
	public String combustible(Flota flo){
		String outcome = null;
		try {
    		Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
    		flash.put("flota", flo);
    		outcome="pretty:consumocombustiblebus";
		} catch (Exception e) {	
			e.printStackTrace();
		}
		return outcome;
	}
	
	public String consolidado(Flota flo){
		String outcome = null;
		try {
    		Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
    		flash.put("flota", flo);
    		outcome="pretty:consolidadokmbusfecha";
		} catch (Exception e) {	
			e.printStackTrace();
		}
		return outcome;
	}
	
	public String verHistorial(Flota flo){
		String outcome = null;
		try {
    		Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
    		flash.put("flota", flo);
    		outcome="pretty:verFlotaHistorial";
		} catch (Exception e) {	
			e.printStackTrace();
		}
		return outcome;
	}
	
	public void cancelar(){
		this.flota = new Flota();
	}
	
	public SelectItem[] filterTipoFlota(){
		int cant = listaTipoFlota.size();
		    SelectItem[] items ;
		    
			items = new SelectItem[cant+1];
			items[0]= new SelectItem("","--Seleccionar--");
			for(int i =0 ; i<cant;i++){
				TipoFlota tf = listaTipoFlota.get(i);
				items[i+1]= new SelectItem(tf.getDescripcion());
			}
			
			
			return items;
	}

	public void guardarFlota(){
		Boolean valido=Boolean.TRUE;
		RequestContext context = RequestContext.getCurrentInstance();  
   	    context.addCallbackParam("esValido", valido );
   	    
		try {
			this.flota.setPlaca(this.flota.getPlaca().toUpperCase().trim());
			if(this.editar)
			{
				
				this.flota.setIdBus(this.flota.getIdBus());
				this.flotaSevice.actualizarFlota(flota);
				 log.setAccion("UPDATE");
	             log.setDescripcion("Se actualiza la flota: " + flota.getPlaca());
	             logmb.insertarLog(log);
				FacesUtils.showFacesMessage("Flota actualizada correctamente.",Constante.INFORMACION);
				context.update("sms");
			}else
			{
				this.flota.setDisponibilidad(1);
				this.flota.setEstado(Boolean.TRUE);
				this.flotaSevice.crearFlota(flota);
				 log.setAccion("INSERT");
	             log.setDescripcion("Se inserta la flota: " + flota.getPlaca());
	             logmb.insertarLog(log);
				 FacesUtils.showFacesMessage("Flota registrada correctamente.",Constante.INFORMACION);
				 context.update("sms");
			}
			listaFlotas = this.flotaSevice.findAll();
			

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		this.flota = new Flota();
		this.editar =  Boolean.FALSE;
		
	}
	
	public void cambiarEstado(Flota flo){
		
		   if(flo.isEstado()){
			   //System.out.println("es igual a uno se pone a cero");
			   flo.setEstado(Boolean.FALSE);
			   //sistem.setInd_activo(new Integer(0));
		   }else{
			   //System.out.println("es igual a cero");
			   flo.setEstado(Boolean.TRUE);
			   //sistem.setInd_activo(new Integer(1));
		   }
		   
		   try {
			   this.flotaSevice.actualizarFlota(flo);
			   log.setAccion("CHANGE ESTADO");
	           log.setDescripcion("Se cambio el estado en Flota: " + flota.getPlaca());
	           logmb.insertarLog(log);
			   FacesUtils.showFacesMessage("Estado de flota actualizado correctamente.",Constante.INFORMACION);
		   } catch (Exception e) {
			   System.out.println("Error:"+e.getMessage());
			   e.printStackTrace();
		   }   
	}
	
	public void eliminarFlota()
	{
		try {
//			RequestContext context = RequestContext.getCurrentInstance();
//			
//			int cantFlotas = 0;
//			cantFlotas = this.oficinaService.cantOfixFlota(flota.getIdflota());
//			if(cantFlotas == 0){
				this.flotaSevice.eliminarFlota(flota.getIdBus());
				listaFlotas.remove(flota);
				FacesUtils.showFacesMessage("Flota eliminada correctamente.",Constante.INFORMACION);
				
				log.setAccion("DELETE");
				log.setDescripcion("Se elimina la Flota: " + flota.getPlaca());
				logmb.insertarLog(log);
				
				listaFlotas = this.flotaSevice.findAll();
//			} else {
//				context.execute("dlgOmiso.show()");
//			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.flota =  new Flota();
		
	}
	
	public void editarFlota(Flota ag)
	{
		this.editar = Boolean.TRUE;
		this.flota = ag;
		this.titulo = "Modificar flota";
	}
	
	public void nuevaFlota()
	{
		this.editar = Boolean.FALSE;
		this.flota = new Flota();
		this.titulo = "Registrar nueva flota";
	}
	
	public void actualizarKmAproximados(){
		
		try {
			RequestContext context = RequestContext.getCurrentInstance();  
			
			this.flotaSevice.actualizarKmAproximado(this.flota.getIdBus(), this.editKmAproximado);
			this.editKmAproximado = 0;
			listaFlotas = this.flotaSevice.findAll();
			
			log.setAccion("Update");
			log.setDescripcion("Se Actualizo el Km Aproximado de Flota: " + flota.getPlaca());
			logmb.insertarLog(log);
			
			FacesUtils.showFacesMessage("Kms aproximados de flota actualizados correctamente.",Constante.INFORMACION);
			context.execute("dlgEditKm.hide()");
			context.update("sms");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
public void actualizarRecorrido(){
		
		try {
			RequestContext context = RequestContext.getCurrentInstance();  
			
			this.flotaSevice.actualizarRecorrido(this.flota.getIdBus(), this.editRecorrido);
			this.editRecorrido = 0;
			listaFlotas = this.flotaSevice.findAll();
			
			log.setAccion("Update");
			log.setDescripcion("Se Actualizo el Recorrido de Flota: " + flota.getPlaca());
			logmb.insertarLog(log);
			
			FacesUtils.showFacesMessage("Recorrido de flota actualizado correctamente.",Constante.INFORMACION);
			context.execute("dlgEditRecorrido.hide()");
			context.update("sms");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//**set an get 
	
	public MenuServices getMenuServices() {
		return menuServices;
	}

	public List<TipoFlota> getListaTipoFlota() {
		return listaTipoFlota;
	}

	public void setListaTipoFlota(List<TipoFlota> listaTipoFlota) {
		this.listaTipoFlota = listaTipoFlota;
	}

	public TipoFlotaService getTipoFlotaService() {
		return tipoFlotaService;
	}

	public void setTipoFlotaService(TipoFlotaService tipoFlotaService) {
		this.tipoFlotaService = tipoFlotaService;
	}

	public List<ClaseServicio> getListaClaseServicio() {
		return listaClaseServicio;
	}

	public void setListaClaseServicio(List<ClaseServicio> listaClaseServicio) {
		this.listaClaseServicio = listaClaseServicio;
	}

	public FlotaService getFlotaSevice() {
		return flotaSevice;
	}

	public void setFlotaSevice(FlotaService flotaSevice) {
		this.flotaSevice = flotaSevice;
	}

	public ClaseServicioServices getClaseServicioServices() {
		return claseServicioServices;
	}

	public void setClaseServicioServices(ClaseServicioServices claseServicioServices) {
		this.claseServicioServices = claseServicioServices;
	}

	public void setMenuServices(MenuServices menuServices) {
		this.menuServices = menuServices;
	}

	public List<Flota> getListaFiltroFlotas() {
		return listaFiltroFlotas;
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

	public void setListaFiltroFlotas(List<Flota> listaFiltroFlotas) {
		this.listaFiltroFlotas = listaFiltroFlotas;
	}

	public Flota getFlota() {
		return flota;
	}

	public void setFlota(Flota flota) {
		this.flota = flota;
	}

	public List<Flota> getListaFlotas() {
		return listaFlotas;
	}

	public void setListaFlotas(List<Flota> listaFlotas) {
		this.listaFlotas = listaFlotas;
	}

	public Boolean getEditar() {
		return editar;
	}

	public void setEditar(Boolean editar) {
		this.editar = editar;
	}

	public List<TipoCombustible> getListaTipoCombustible() {
		return listaTipoCombustible;
	}

	public void setListaTipoCombustible(List<TipoCombustible> listaTipoCombustible) {
		this.listaTipoCombustible = listaTipoCombustible;
	}

	public TipoCombustibleService getTipoCombustibleService() {
		return tipoCombustibleService;
	}

	public void setTipoCombustibleService(
			TipoCombustibleService tipoCombustibleService) {
		this.tipoCombustibleService = tipoCombustibleService;
	}

	public SelectItem[] getListaFiltroTipoFlota() {
		return listaFiltroTipoFlota;
	}

	public void setListaFiltroTipoFlota(SelectItem[] listaFiltroTipoFlota) {
		this.listaFiltroTipoFlota = listaFiltroTipoFlota;
	}

	public Integer getEditKmAproximado() {
		return editKmAproximado;
	}

	public void setEditKmAproximado(Integer editKmAproximado) {
		this.editKmAproximado = editKmAproximado;
	}

	public Integer getEditRecorrido() {
		return editRecorrido;
	}

	public void setEditRecorrido(Integer editRecorrido) {
		this.editRecorrido = editRecorrido;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	
}

