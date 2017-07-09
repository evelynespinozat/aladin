package com.certicom.ittsa.managedBeans;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.context.RequestContext;

import com.certicom.ittsa.domain.Agencia;
import com.certicom.ittsa.domain.CategoriaServicio;
import com.certicom.ittsa.domain.ClaseServicio;
import com.certicom.ittsa.domain.Log;
import com.certicom.ittsa.domain.Turno;
import com.certicom.ittsa.services.AgenciaService;
import com.certicom.ittsa.services.CategoriaServicioService;
import com.certicom.ittsa.services.ClaseServicioServices;
import com.certicom.ittsa.services.MenuServices;
import com.pe.certicom.ittsa.commons.Constante;
import com.pe.certicom.ittsa.commons.FacesUtils;
import com.pe.certicom.ittsa.commons.GenericBeans;

@ManagedBean(name="categoriaServicioMB")
@ViewScoped
public class CategoriaServicioMB extends GenericBeans implements Serializable{
	private String titulo;
	private CategoriaServicio categoriaServicio;
	private List<CategoriaServicio> listaCategoriaServicio;
	private List<CategoriaServicio> listaFiltroCategoriaServicio;
	private Boolean editar;
	
	//services
	private CategoriaServicioService categoriaServicioService;
	private MenuServices menuServices;
	private ClaseServicioServices claseServicioServices;
	
	//datos Log
    private Log log;
	private LogMB logmb;
	
	public CategoriaServicioMB(){;}
	
	@PostConstruct
	public void incia()
	{
		this.editar = Boolean.FALSE;
	
		this.categoriaServicioService = new CategoriaServicioService();
		this.menuServices=new MenuServices();
		this.categoriaServicio = new CategoriaServicio();
		this.claseServicioServices = new ClaseServicioServices();
		
		 log = (Log)getSpringBean(Constante.SESSION_LOG);
		logmb = new LogMB();
		//listando
		try {
			this.listaCategoriaServicio = categoriaServicioService.findAll();
			int codMenu = menuServices.opcionMenuByPretty("pretty:categoriaservicio");
			log.setCod_menu(codMenu);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void cancelar(){
		this.categoriaServicio = new CategoriaServicio();
	}
	
	public void nuevoCaServicio(){
		this.categoriaServicio = new CategoriaServicio();
		this.editar = Boolean.FALSE;
		this.titulo = "Registrar nueva categoría de servicio";
	}
	
	public void editarCaServicio(CategoriaServicio cas){
		this.categoriaServicio = cas;
		this.editar = Boolean.TRUE;
		this.titulo = "Modificar categoría de servicio";
	}

	public void guardarCategoriaServicio()
	{
		
		 Boolean valido=Boolean.TRUE;
		RequestContext context = RequestContext.getCurrentInstance();  
	   	 context.addCallbackParam("esValido", valido );
		try {
			categoriaServicio.setDescripcion(categoriaServicio.getDescripcion().toUpperCase().trim());
			if(this.editar)
			{
				System.out.println("entro ene el editar ");
				this.categoriaServicio.setIdCatServicio(this.categoriaServicio.getIdCatServicio());
				this.categoriaServicioService.actualizarCategoriaServicio(categoriaServicio);
				 log.setAccion("UPDATE");
	             log.setDescripcion("Se actualiza la categoría Servicio: " + categoriaServicio.getDescripcion());
	             logmb.insertarLog(log);
				FacesUtils.showFacesMessage("Categoría servicio actualizada correctamente.",Constante.INFORMACION);
				 context.update("sms");
			}else
			{
				this.categoriaServicio.setEstado(Boolean.TRUE);
				this.categoriaServicioService.crearCategoriaServicio(categoriaServicio);
				 log.setAccion("INSERT");
	             log.setDescripcion("Se inserta la categoría servicio: " + categoriaServicio.getDescripcion());
	             logmb.insertarLog(log);
				FacesUtils.showFacesMessage("Categoría servicio registrada correctamente.",Constante.INFORMACION);
				context.update("sms");
			
			}
			listaCategoriaServicio = this.categoriaServicioService.findAll();
			

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		this.categoriaServicio = new CategoriaServicio();
		this.editar =  Boolean.FALSE;
		
	}
	
	public void cambiarEstado(CategoriaServicio categoriaServ){
		
		   if(categoriaServ.isEstado()){
			   //System.out.println("es igual a uno se pone a cero");
			   categoriaServ.setEstado(Boolean.FALSE);
			   //sistem.setInd_activo(new Integer(0));
		   }else{
			   //System.out.println("es igual a cero");
			   categoriaServ.setEstado(Boolean.TRUE);
			   //sistem.setInd_activo(new Integer(1));
		   }
		   
		   try {
			   this.categoriaServicioService.actualizarCategoriaServicio(categoriaServ);
			   log.setAccion("CHANGE ESTADO");
	           log.setDescripcion("Se cambio el estado en categoría servicio: " + categoriaServicio.getDescripcion());
	           logmb.insertarLog(log);
			   FacesUtils.showFacesMessage("Estado de categoría de servicio actualizado correctamente.",Constante.INFORMACION);
		   } catch (Exception e) {
			   System.out.println("Error:"+e.getMessage());
			   e.printStackTrace();
		   }   
	}
	
	public void eliminarCategoriaServicio(){
		try {
			
			RequestContext context = RequestContext.getCurrentInstance();
        	
			int cant = 0;
			cant = this.claseServicioServices.cantClasexCServicio(this.categoriaServicio.getIdCatServicio());
			
			if(cant == 0){
				this.categoriaServicioService.eliminarCategoriaServicio(categoriaServicio.getIdCatServicio());
				listaCategoriaServicio.remove(categoriaServicio);
				FacesUtils.showFacesMessage("Categoría servicio eliminada correctamente.",Constante.INFORMACION);
				
				log.setAccion("DELETE");
		        log.setDescripcion("Se eliminó la categoría servicio: " + categoriaServicio.getDescripcion());
		        logmb.insertarLog(log);
	
			} else {
				context.execute("dlgOmiso.show()");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.categoriaServicio =  new CategoriaServicio();
		
	}

	//**set an get 
	
	public MenuServices getMenuServices() {
		return menuServices;
	}

	public CategoriaServicio getCategoriaServicio() {
		return categoriaServicio;
	}

	public void setCategoriaServicio(CategoriaServicio categoriaServicio) {
		this.categoriaServicio = categoriaServicio;
	}

	public List<CategoriaServicio> getListaCategoriaServicio() {
		return listaCategoriaServicio;
	}

	public void setListaCategoriaServicio(
			List<CategoriaServicio> listaCategoriaServicio) {
		this.listaCategoriaServicio = listaCategoriaServicio;
	}

	public List<CategoriaServicio> getListaFiltroCategoriaServicio() {
		return listaFiltroCategoriaServicio;
	}

	public void setListaFiltroCategoriaServicio(
			List<CategoriaServicio> listaFiltroCategoriaServicio) {
		this.listaFiltroCategoriaServicio = listaFiltroCategoriaServicio;
	}

	public CategoriaServicioService getCategoriaServicioService() {
		return categoriaServicioService;
	}

	public void setCategoriaServicioService(
			CategoriaServicioService categoriaServicioService) {
		this.categoriaServicioService = categoriaServicioService;
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

	public ClaseServicioServices getClaseServicioServices() {
		return claseServicioServices;
	}

	public void setClaseServicioServices(ClaseServicioServices claseServicioServices) {
		this.claseServicioServices = claseServicioServices;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}


	
	
}

