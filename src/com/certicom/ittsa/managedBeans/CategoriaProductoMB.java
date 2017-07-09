package com.certicom.ittsa.managedBeans;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.context.RequestContext;

import com.certicom.ittsa.domain.CategoriaProducto;
import com.certicom.ittsa.domain.Log;
import com.certicom.ittsa.services.CategoriaProductoService;
import com.certicom.ittsa.services.MenuServices;
import com.pe.certicom.ittsa.commons.Constante;
import com.pe.certicom.ittsa.commons.FacesUtils;
import com.pe.certicom.ittsa.commons.GenericBeans;

@ManagedBean(name="categoriaProductoMB")
@ViewScoped
public class CategoriaProductoMB extends GenericBeans implements Serializable{
	private String titulo;
	private CategoriaProducto categoriaProducto;
	private List<CategoriaProducto> listaCategoriaProductos;
	private List<CategoriaProducto> listaFiltroCategoriaProductos;
	private Boolean editar;
	
	//services
	private CategoriaProductoService categoriaProductoService;
	private MenuServices menuServices;
	
	//datos Log
    private Log log;
	private LogMB logmb;
	
	public CategoriaProductoMB(){;}
	
	@PostConstruct
	public void incia()
	{
		this.editar = Boolean.FALSE;
	
		this.categoriaProductoService = new CategoriaProductoService();
		this.menuServices=new MenuServices();
		this.categoriaProducto = new CategoriaProducto();
		
		 log = (Log)getSpringBean(Constante.SESSION_LOG);
		logmb = new LogMB();
		//listando
		try {
			this.listaCategoriaProductos = categoriaProductoService.findAll();
			int codMenu = menuServices.opcionMenuByPretty("pretty:catProducto");
			log.setCod_menu(codMenu);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void cancelar(){
		this.categoriaProducto = new CategoriaProducto();
	}

	public void guardarCategoriaProducto()
	{
		Boolean valido=Boolean.TRUE;
		RequestContext context = RequestContext.getCurrentInstance();  
   	    context.addCallbackParam("esValido", valido );
   	    
		try {
			if(this.editar)
			{
				this.categoriaProductoService.actualizarCategoriaProducto(categoriaProducto);
				 log.setAccion("UPDATE");
	             log.setDescripcion("Se actualizo la categoría  " + categoriaProducto.getDescripcion());
	             logmb.insertarLog(log);
				FacesUtils.showFacesMessage("Categoría actualizada correctamente.",Constante.INFORMACION);
				context.update("sms");
			}else
			{	
				this.categoriaProducto.setEstado(Boolean.TRUE);
				this.categoriaProductoService.crearCategoriaProducto(categoriaProducto);
				 log.setAccion("INSERT");
	             log.setDescripcion("Se inserto la categoría : " + categoriaProducto.getDescripcion());
	             logmb.insertarLog(log);
				 FacesUtils.showFacesMessage("Categoría registrada correctamente.",Constante.INFORMACION);
				 context.update("sms");
			}
			listaCategoriaProductos = this.categoriaProductoService.findAll();
			

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		this.categoriaProducto = new CategoriaProducto();
		this.editar =  Boolean.FALSE;
		
	}
	
	public void cambiarEstado(CategoriaProducto cp){
		
		   if(cp.isEstado()){
			   cp.setEstado(Boolean.FALSE);
		   }else{
			   cp.setEstado(Boolean.TRUE);
		   }
		   
		   try {
			   this.categoriaProductoService.actualizarCategoriaProducto(cp);
			   log.setAccion("CHANGE ESTADO");
	           log.setDescripcion("Se cambio el estado de la categoría: " + cp.getDescripcion() + " a " + cp.isEstado());
	           logmb.insertarLog(log);
			   FacesUtils.showFacesMessage("Estado de categoría de producto actualizado correctamente.",Constante.INFORMACION);
		   } catch (Exception e) {
			   System.out.println("Error:"+e.getMessage());
			   e.printStackTrace();
		   }   
	}
	
	public void eliminarCategoriaProducto()
	{
		try {
			RequestContext context = RequestContext.getCurrentInstance();
			int cant = 0;
			cant = this.categoriaProductoService.cantCategoriaProducto(categoriaProducto.getIdCatProducto());
			if(cant == 0){
				this.categoriaProductoService.eliminarCategoriaProducto(categoriaProducto.getIdCatProducto());
				listaCategoriaProductos.remove(categoriaProducto);
				FacesUtils.showFacesMessage("Categoría de producto eliminada correctamente.",Constante.INFORMACION);
				
				log.setAccion("DELETE");
				log.setDescripcion("Se elimina la Categoría de producto: " + categoriaProducto.getDescripcion());
				logmb.insertarLog(log);
				
			} else {
				context.execute("dlgOmiso.show()");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.categoriaProducto =  new CategoriaProducto();
		
	}
	
	public void editarCategoriaProducto(CategoriaProducto ag)
	{
		this.editar = Boolean.TRUE;
		this.categoriaProducto = ag;
		this.titulo = "Modificar categoría producto";
	}
	
	public void nuevaCategoriaProducto()
	{
		this.editar = Boolean.FALSE;
		this.categoriaProducto = new CategoriaProducto();
		this.titulo = "Registrar nueva categoría producto";
	}

	public CategoriaProducto getCategoriaProducto() {
		return categoriaProducto;
	}

	public void setCategoriaProducto(CategoriaProducto categoriaProducto) {
		this.categoriaProducto = categoriaProducto;
	}

	public List<CategoriaProducto> getListaCategoriaProductos() {
		return listaCategoriaProductos;
	}

	public void setListaCategoriaProductos(
			List<CategoriaProducto> listaCategoriaProductos) {
		this.listaCategoriaProductos = listaCategoriaProductos;
	}

	public List<CategoriaProducto> getListaFiltroCategoriaProductos() {
		return listaFiltroCategoriaProductos;
	}

	public void setListaFiltroCategoriaProductos(
			List<CategoriaProducto> listaFiltroCategoriaProductos) {
		this.listaFiltroCategoriaProductos = listaFiltroCategoriaProductos;
	}

	public Boolean getEditar() {
		return editar;
	}

	public void setEditar(Boolean editar) {
		this.editar = editar;
	}

	public CategoriaProductoService getCategoriaProductoService() {
		return categoriaProductoService;
	}

	public void setCategoriaProductoService(
			CategoriaProductoService categoriaProductoService) {
		this.categoriaProductoService = categoriaProductoService;
	}

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

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	//**set an get 
	

	
	
}

