package com.certicom.ittsa.managedBeans;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.context.RequestContext;

import com.certicom.ittsa.domain.Log;
import com.certicom.ittsa.domain.TipoCombustible;
import com.certicom.ittsa.services.MenuServices;
import com.certicom.ittsa.services.TipoCombustibleService;
import com.pe.certicom.ittsa.commons.Constante;
import com.pe.certicom.ittsa.commons.FacesUtils;
import com.pe.certicom.ittsa.commons.GenericBeans;

@ManagedBean(name="tipoCombustibleMB")
@ViewScoped
public class TipoCombustibleMB extends GenericBeans implements Serializable{
	private String titulo;
	private TipoCombustible tipoCombustible;
	private List<TipoCombustible> listaTipoCombustible;
	private List<TipoCombustible> listaFiltroTipoCombustible;
	private Boolean editar;
	
	//services
	private TipoCombustibleService tipoCombustibleService;
	private MenuServices menuServices;
	
	//datos Log
    private Log log;
	private LogMB logmb;
	
	public TipoCombustibleMB(){;}
	
	@PostConstruct
	public void incia()
	{
		this.editar = Boolean.FALSE;
		
		this.tipoCombustible = new TipoCombustible();
		this.tipoCombustibleService = new TipoCombustibleService();
		this.menuServices=new MenuServices();
		
		
		 log = (Log)getSpringBean(Constante.SESSION_LOG);
		logmb = new LogMB();
		//listando
		try {
			this.listaTipoCombustible = tipoCombustibleService.findAll();
			int codMenu = menuServices.opcionMenuByPretty("pretty:tipoConbustible");
			log.setCod_menu(codMenu);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void cancelar(){
		this.tipoCombustible = new TipoCombustible();
	}

	public void guardarTipoCombustible(){
		Boolean valido=Boolean.TRUE;
		RequestContext context = RequestContext.getCurrentInstance();  
   	    context.addCallbackParam("esValido", valido );
   	    
		try {
			this.tipoCombustible.setDescripcion(this.tipoCombustible.getDescripcion().toUpperCase().trim());
			this.tipoCombustible.setTipo(this.tipoCombustible.getTipo().toUpperCase().trim());
			
			if(this.editar)
			{
				this.tipoCombustible.setIdTipoCombustible(this.tipoCombustible.getIdTipoCombustible());
				this.tipoCombustibleService.actualizarTipoCombustible(this.tipoCombustible);
				 log.setAccion("UPDATE");
	             log.setDescripcion("Se actualiza la Tipo Combustible: " + this.tipoCombustible.getDescripcion());
	             logmb.insertarLog(log);
				FacesUtils.showFacesMessage("Tipo combustible actualizado correctamente.",Constante.INFORMACION);
				context.update("sms");
			}else
			{
				this.tipoCombustible.setEstado(Boolean.TRUE);
				this.tipoCombustibleService.crearTipoCombustible(this.tipoCombustible);
				 log.setAccion("INSERT");
	             log.setDescripcion("Se inserta el tipo combustible: " + this.tipoCombustible.getDescripcion());
	             logmb.insertarLog(log);
				 FacesUtils.showFacesMessage("Tipo combustible registrado correctamente.",Constante.INFORMACION);
				 context.update("sms");
			}
			listaTipoCombustible = this.tipoCombustibleService.findAll();
			

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		this.tipoCombustible = new TipoCombustible();
		this.editar =  Boolean.FALSE;
		
	}
	
	public void cambiarEstado(TipoCombustible tipoCombus){
		
		   if(tipoCombus.isEstado()){
			   //System.out.println("es igual a uno se pone a cero");
			   tipoCombus.setEstado(Boolean.FALSE);
			   //sistem.setInd_activo(new Integer(0));
		   }else{
			   //System.out.println("es igual a cero");
			   tipoCombus.setEstado(Boolean.TRUE);
			   //sistem.setInd_activo(new Integer(1));
		   }
		   
		   try {
			   this.tipoCombustibleService.actualizarTipoCombustible(tipoCombus);
			   log.setAccion("CHANGE ESTADO");
	           log.setDescripcion("Se cambio el estado en tipo combustible: " + tipoCombus.getDescripcion());
	           logmb.insertarLog(log);
			   FacesUtils.showFacesMessage("Estado de tipo de combustible actualizado correctamente.",Constante.INFORMACION);
		   } catch (Exception e) {
			   System.out.println("Error:"+e.getMessage());
			   e.printStackTrace();
		   }   
	}
	
	public void eliminarTipoCombustible(){
		try {
			RequestContext context = RequestContext.getCurrentInstance();
			
			//int cantAgencias = 0;
			//cantAgencias = this.oficinaService.cantOfixAgencia(agencia.getIdagencia());
			//if(cantAgencias == 0){
				this.tipoCombustibleService.eliminarTipoCombustible(this.tipoCombustible.getIdTipoCombustible());
				listaTipoCombustible.remove(this.tipoCombustible);
				FacesUtils.showFacesMessage("Tipo combustible eliminado correctamente.",Constante.INFORMACION);
				
				log.setAccion("DELETE");
				log.setDescripcion("Se elimina el Tipo Combustible: " + this.tipoCombustible.getDescripcion());
				logmb.insertarLog(log);
				
			//} else {
			//	context.execute("dlgOmiso.show()");
			//}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.tipoCombustible =  new TipoCombustible();
		
	}
	
	public void editarTipoCombustible(TipoCombustible tc)
	{
		this.editar = Boolean.TRUE;
		this.tipoCombustible = tc;
		this.titulo = "Modificar tipo de combustible";
	}
	
	public void nuevaAgencia()
	{
		this.editar = Boolean.FALSE;
		this.tipoCombustible = new TipoCombustible();
		this.titulo = "Registrar nuevo tipo de combustible";
	}

	//**set an get 
	
	public MenuServices getMenuServices() {
		return menuServices;
	}

	public TipoCombustible getTipoCombustible() {
		return tipoCombustible;
	}

	public void setTipoCombustible(TipoCombustible tipoCombustible) {
		this.tipoCombustible = tipoCombustible;
	}

	public List<TipoCombustible> getListaTipoCombustible() {
		return listaTipoCombustible;
	}

	public void setListaTipoCombustible(List<TipoCombustible> listaTipoCombustible) {
		this.listaTipoCombustible = listaTipoCombustible;
	}

	public List<TipoCombustible> getListaFiltroTipoCombustible() {
		return listaFiltroTipoCombustible;
	}

	public void setListaFiltroTipoCombustible(
			List<TipoCombustible> listaFiltroTipoCombustible) {
		this.listaFiltroTipoCombustible = listaFiltroTipoCombustible;
	}

	public TipoCombustibleService getTipoCombustibleService() {
		return tipoCombustibleService;
	}

	public void setTipoCombustibleService(
			TipoCombustibleService tipoCombustibleService) {
		this.tipoCombustibleService = tipoCombustibleService;
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

