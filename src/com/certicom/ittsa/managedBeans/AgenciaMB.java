package com.certicom.ittsa.managedBeans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.component.datatable.DataTable;
import org.primefaces.context.RequestContext;
import org.primefaces.util.ComponentUtils;

import com.certicom.ittsa.domain.Agencia;
import com.certicom.ittsa.domain.Log;
import com.certicom.ittsa.services.AgenciaService;
import com.certicom.ittsa.services.MenuServices;
import com.certicom.ittsa.services.OficinaService;
import com.pe.certicom.ittsa.commons.Constante;
import com.pe.certicom.ittsa.commons.FacesUtils;
import com.pe.certicom.ittsa.commons.GenericBeans;

@ManagedBean(name="agenciaMB")
@ViewScoped
public class AgenciaMB extends GenericBeans implements Serializable{
	private String titulo;
	private Agencia agencia;
	private List<Agencia> listaAgencias;
	private List<Agencia> listaFiltroAgencias;
	private Boolean editar;
	
	//services
	private AgenciaService agenciaSevice;
	private OficinaService oficinaService;
	private MenuServices menuServices;
	
	//datos Log
    private Log log;
	private LogMB logmb;
	
	public AgenciaMB(){;}
	
	@PostConstruct
	public void incia()
	{
		this.editar = Boolean.FALSE;
	
		this.agenciaSevice = new AgenciaService();
		this.menuServices=new MenuServices();
		this.oficinaService = new OficinaService();
		this.agencia = new Agencia();
		
		 log = (Log)getSpringBean(Constante.SESSION_LOG);
		logmb = new LogMB();
		//listando
		try {
			this.listaAgencias = agenciaSevice.findAll();
			int codMenu = menuServices.opcionMenuByPretty("pretty:agencia");
			log.setCod_menu(codMenu);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void cancelar(){
		this.agencia = new Agencia();
	}

	public void guardarAgencia()
	{
		Boolean valido=Boolean.TRUE;
		RequestContext context = RequestContext.getCurrentInstance();  
   	    context.addCallbackParam("esValido", valido );
   	    
		try {
			
			Agencia ag=this.agenciaSevice.findByDescripcion(agencia.getDescripcion().trim());
			
			if(ag==null || ag.getIdagencia()==this.agencia.getIdagencia()){ 
			
			agencia.setDescripcion(agencia.getDescripcion().toUpperCase().trim());
			//agencia.setGrupo(agencia.getGrupo().toUpperCase().trim());
			if(this.editar)
			{
					this.agencia.setIdagencia(this.agencia.getIdagencia());
					this.agenciaSevice.actualizarAgencia(agencia);
					log.setAccion("UPDATE");
		            log.setDescripcion("Se actualiza la agencia: " + agencia.getDescripcion());
		            logmb.insertarLog(log);
					FacesUtils.showFacesMessage("Agencia actualizada correctamente.",Constante.INFORMACION);
					context.update("sms");
					context.execute("dlgNuevo.hide();");
			}else
			{
					this.agenciaSevice.crearAgencia(agencia);
					this.listaAgencias.add(agencia);
					 log.setAccion("INSERT");
		             log.setDescripcion("Se inserta la agencia: " + agencia.getDescripcion());
		             logmb.insertarLog(log);
					 FacesUtils.showFacesMessage("Agencia registrada correctamente.",Constante.INFORMACION);
					 context.update("sms");
					 context.execute("dlgNuevo.hide();");				 
			}
			}else{
				FacesUtils.showFacesMessage("Agencia se encuentra ya registrada.",Constante.ERROR);
				 context.update("msgNuevo");
				 context.execute("dlgNuevo.show();");
			}
			listaAgencias = this.agenciaSevice.findAll();
			

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
		this.agencia = new Agencia();
		this.editar =  Boolean.FALSE;
		
	}
	
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
			   FacesUtils.showFacesMessage("Estado de agencia actualizado correctamente.",Constante.INFORMACION);
		   } catch (Exception e) {
			   System.out.println("Error:"+e.getMessage());
			   e.printStackTrace();
		   }   
	}
	
	public void eliminarAgencia()
	{
		try {
			RequestContext context = RequestContext.getCurrentInstance();
			
			int cantAgencias = 0;
			cantAgencias = this.oficinaService.cantOfixAgencia(agencia.getIdagencia());
			if(cantAgencias == 0){
				this.agenciaSevice.eliminarAgencia(agencia.getIdagencia());
				
				/*
				this.listaAgencias.clear();
				this.listaAgencias = this.agenciaSevice.findAll();
				*/
				this.listaAgencias.remove(agencia);
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
	
	public void editarAgencia(Agencia ag)
	{
		this.editar = Boolean.TRUE;
		this.agencia = ag;
		this.titulo = "Modificar agencia";
	}
	
	public void nuevaAgencia()
	{
		this.editar = Boolean.FALSE;
		this.agencia = new Agencia();
		this.titulo = "Registrar nueva agencia";
	}

	//**set an get 
	
	public MenuServices getMenuServices() {
		return menuServices;
	}

	public void setMenuServices(MenuServices menuServices) {
		this.menuServices = menuServices;
	}

	public List<Agencia> getListaFiltroAgencias() {
		return listaFiltroAgencias;
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

	public void setListaFiltroAgencias(List<Agencia> listaFiltroAgencias) {
		this.listaFiltroAgencias = listaFiltroAgencias;
	}

	public Agencia getAgencia() {
		return agencia;
	}

	public void setAgencia(Agencia agencia) {
		this.agencia = agencia;
	}

	public List<Agencia> getListaAgencias() {
		return listaAgencias;
	}

	public void setListaAgencias(List<Agencia> listaAgencias) {
		this.listaAgencias = listaAgencias;
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
