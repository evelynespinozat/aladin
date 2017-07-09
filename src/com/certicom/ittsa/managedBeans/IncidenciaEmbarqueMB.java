package com.certicom.ittsa.managedBeans;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.context.RequestContext;

import com.certicom.ittsa.domain.Embarque;
import com.certicom.ittsa.domain.Log;
import com.certicom.ittsa.services.EmbarqueService;
import com.certicom.ittsa.services.MenuServices;
import com.pe.certicom.ittsa.commons.Constante;
import com.pe.certicom.ittsa.commons.FacesUtils;
import com.pe.certicom.ittsa.commons.GenericBeans;

@ManagedBean(name="incidenciaEmbarqueMB")
@ViewScoped
public class IncidenciaEmbarqueMB extends GenericBeans implements Serializable{
	private String titulo;
	private Embarque embarque;
	private List<Embarque> listembarque;
	private List<Embarque> listembarquefilter;
	private EmbarqueService embarqueService;
	/* ---------------------------*/
	private Boolean editar;
	private MenuServices menuServices;
	
	//datos Log
    private Log log;
	private LogMB logmb;
	
	public IncidenciaEmbarqueMB(){;}
	
	@PostConstruct
	public void incia()
	{
		this.editar = Boolean.FALSE;
	
		this.embarqueService = new EmbarqueService();
		this.menuServices=new MenuServices();
		this.embarque = new Embarque();
		
		 log = (Log)getSpringBean(Constante.SESSION_LOG);
		logmb = new LogMB();
		//listando
		try {
			this.listembarque = embarqueService.findAll();
			int codMenu = menuServices.opcionMenuByPretty("pretty:empresas");
			log.setCod_menu(codMenu);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void cancelar(){
		this.embarque = new Embarque();
	}

	public void guardarIncidencia()
	{
		Boolean valido=Boolean.TRUE;
		RequestContext context = RequestContext.getCurrentInstance();  
   	    context.addCallbackParam("esValido", valido );
   	    
		try {
			if(this.editar)
			{
				this.embarqueService.actualizarEmbarque(embarque);
				 log.setAccion("UPDATE");
	             log.setDescripcion("Se actualiza el embarque: " + embarque.getDescripcion());
	             logmb.insertarLog(log);
				FacesUtils.showFacesMessage("Incidencia actualizada correctamente.",Constante.INFORMACION);
				context.update("sms");
			}else
			{
				this.embarqueService.crearEmbarque(embarque);
				 log.setAccion("INSERT");
	             log.setDescripcion("Se inserta el embarque: " + embarque.getDescripcion());
	             logmb.insertarLog(log);
				 FacesUtils.showFacesMessage("Embarque registrado correctamente.",Constante.INFORMACION);
				 context.update("sms");
			}
			listembarque = this.embarqueService.findAll();
			

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		this.embarque = new Embarque();
		this.editar =  Boolean.FALSE;
		
	}
	
	
	public void eliminarIncidencia()
	{
		try {
			RequestContext context = RequestContext.getCurrentInstance();
	
				this.embarqueService.eliminarEmbarque(embarque.getEmbarqueId());
				FacesUtils.showFacesMessage("Embarque eliminado correctamente.",Constante.INFORMACION);
				
				log.setAccion("DELETE");
				log.setDescripcion("Se elimina la Embarque: " + embarque.getDescripcion());
				logmb.insertarLog(log);
				
				listembarque = this.embarqueService.findAll();
				
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.embarque =  new Embarque();
		
	}
	
	public void editarIncidencia(Embarque ag)
	{
		this.editar = Boolean.TRUE;
		this.embarque = ag;
		this.titulo = "Modificar incidencia";
	}
	
	public void nuevaIncidencia()
	{
		this.editar = Boolean.FALSE;
		this.embarque = new Embarque();
		this.titulo = "Registrar nueva incidencia";
	}
	
	

	public Embarque getEmbarque() {
		return embarque;
	}

	public void setEmbarque(Embarque embarque) {
		this.embarque = embarque;
	}

	public List<Embarque> getListembarque() {
		return listembarque;
	}

	public void setListembarque(List<Embarque> listembarque) {
		this.listembarque = listembarque;
	}

	public List<Embarque> getListembarquefilter() {
		return listembarquefilter;
	}

	public void setListembarquefilter(List<Embarque> listembarquefilter) {
		this.listembarquefilter = listembarquefilter;
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

