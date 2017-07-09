package com.certicom.ittsa.managedBeans;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.context.RequestContext;

import com.certicom.ittsa.domain.Agencia;
import com.certicom.ittsa.domain.Log;
import com.certicom.ittsa.domain.PuntoVenta;
import com.certicom.ittsa.services.AgenciaService;
import com.certicom.ittsa.services.MenuServices;
import com.certicom.ittsa.services.OficinaService;
import com.certicom.ittsa.services.PuntoVentaService;
import com.pe.certicom.ittsa.commons.Constante;
import com.pe.certicom.ittsa.commons.FacesUtils;
import com.pe.certicom.ittsa.commons.GenericBeans;

@ManagedBean(name="seriesMB")
@ViewScoped
public class SeriesMB extends GenericBeans implements Serializable{

	private PuntoVenta pv;
	private List<PuntoVenta> listaPV;
	private List<PuntoVenta> listaFiltroPV;
	private Boolean editar;
	
	//services
	private PuntoVentaService pvSevice;
	private OficinaService oficinaService;
	private MenuServices menuServices;
	
	//datos Log
    private Log log;
	private LogMB logmb;
	
	public SeriesMB(){;}
	
	@PostConstruct
	public void incia()
	{
		this.editar = Boolean.FALSE;
	
		this.pvSevice = new PuntoVentaService();
		this.menuServices=new MenuServices();
		this.oficinaService = new OficinaService();
		this.pv = new PuntoVenta();
		
		 log = (Log)getSpringBean(Constante.SESSION_LOG);
		logmb = new LogMB();
		//listando
		try {
			this.listaPV = pvSevice.listaPuntoVenta();
			int codMenu = menuServices.opcionMenuByPretty("pretty:agencia");
			log.setCod_menu(codMenu);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void cancelar(){
		this.pv = new PuntoVenta();
	}

	public void guardarSerie()
	{
		Boolean valido=Boolean.TRUE;
		RequestContext context = RequestContext.getCurrentInstance();  
   	    context.addCallbackParam("esValido", valido );
   	    
		try {
			//agencia.setDescripcion(agencia.getDescripcion().toUpperCase().trim());
			//agencia.setGrupo(agencia.getGrupo().toUpperCase().trim());
			
			//this.agencia.setIdagencia(this.agencia.getIdagencia());
			this.pvSevice.actualizarSeriePuntoVenta(pv);
			
			FacesUtils.showFacesMessage("Agencia actualizada correctamente.",Constante.INFORMACION);
			context.update("sms");
			
			listaPV = this.pvSevice.listaPuntoVenta();
			

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		this.pv = new PuntoVenta();
		
	}
	
	/*
	public void cambiarEstado(Agencia agen){
		
		   if(agen.isEstado()){
			   //System.out.println("es igual a uno se pone a cero");
			   agen.setEstado(Boolean.FALSE);
			   //sistem.setInd_activo(new Integer(0));
		   }else{
			   //System.out.println("es igual a cero");
			   agen.setEstado(Boolean.TRUE);
			   //sistem.setInd_activo(new Integer(1));
		   }
		   
		   try {
			   this.agenciaSevice.actualizarAgencia(agen);
			   log.setAccion("CHANGE ESTADO");
	           log.setDescripcion("Se cambio el estado en Agencia: " + agencia.getDescripcion());
	           logmb.insertarLog(log);
			   FacesUtils.showFacesMessage("Estado de agencia modificada correctamente.",Constante.INFORMACION);
		   } catch (Exception e) {
			   System.out.println("Error:"+e.getMessage());
			   e.printStackTrace();
		   }   
	}
	*/
	/*
	public void eliminarAgencia()
	{
		try {
			RequestContext context = RequestContext.getCurrentInstance();
			
			int cantAgencias = 0;
			cantAgencias = this.oficinaService.cantOfixAgencia(agencia.getIdagencia());
			if(cantAgencias == 0){
				this.agenciaSevice.eliminarAgencia(agencia.getIdagencia());
				listaAgencias.remove(agencia);
				FacesUtils.showFacesMessage("Agencia eliminada correctamente.",Constante.INFORMACION);
				
				log.setAccion("DELETE");
				log.setDescripcion("Se elimina la Agencia: " + agencia.getDescripcion());
				logmb.insertarLog(log);
				
			} else {
				context.execute("dlgOmiso.show()");
			}
			
			
			

			
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.agencia =  new Agencia();
		
	}
	*/
	public void editarSerie(PuntoVenta pv)
	{
		this.editar = Boolean.TRUE;
		this.pv = pv;
	}
	
	
	//**set an get 
	
	
	
	
	
	public MenuServices getMenuServices() {
		return menuServices;
	}

	public PuntoVenta getPv() {
		return pv;
	}

	public void setPv(PuntoVenta pv) {
		this.pv = pv;
	}

	public List<PuntoVenta> getListaPV() {
		return listaPV;
	}

	public void setListaPV(List<PuntoVenta> listaPV) {
		this.listaPV = listaPV;
	}

	public List<PuntoVenta> getListaFiltroPV() {
		return listaFiltroPV;
	}

	public void setListaFiltroPV(List<PuntoVenta> listaFiltroPV) {
		this.listaFiltroPV = listaFiltroPV;
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
	
	
}

