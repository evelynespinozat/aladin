package com.certicom.ittsa.managedBeans;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;

import org.primefaces.context.RequestContext;

import com.certicom.ittsa.domain.CategoriaProducto;
import com.certicom.ittsa.domain.Log;
import com.certicom.ittsa.domain.Producto;
import com.certicom.ittsa.services.CategoriaProductoService;
import com.certicom.ittsa.services.MenuServices;
import com.certicom.ittsa.services.ProductoService;
import com.pe.certicom.ittsa.commons.Constante;
import com.pe.certicom.ittsa.commons.FacesUtils;
import com.pe.certicom.ittsa.commons.GenericBeans;

@ManagedBean(name="productoMB")
@ViewScoped
public class ProductoMB extends GenericBeans implements Serializable{
	private String titulo;
	private Producto producto;
	private List<Producto> listaProductos;
	private List<Producto> listaFiltroProductos;
	private List<CategoriaProducto> listaCategoriaProducto;
	private Boolean editar;
	private Boolean bandDesgregado;
	
	//services
	private ProductoService productoSevice;
	private MenuServices menuServices;
	private CategoriaProductoService categoriaProductoService;
	
	//datos Log
    private Log log;
	private LogMB logmb;
	
	public ProductoMB(){;}
	
	@PostConstruct
	public void incia()
	{
		this.editar = Boolean.FALSE;
		this.bandDesgregado = Boolean.FALSE;
	
		this.productoSevice = new ProductoService();
		this.menuServices=new MenuServices();
		this.categoriaProductoService = new CategoriaProductoService();
		this.producto = new Producto();
		
		
		log = (Log)getSpringBean(Constante.SESSION_LOG);
		logmb = new LogMB();
		//listando
		try {
			this.listaProductos = productoSevice.findAll();
			this.listaCategoriaProducto = categoriaProductoService.listaCategoriaProductosActivas();
			int codMenu = menuServices.opcionMenuByPretty("pretty:productos");
			log.setCod_menu(codMenu);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void cancelar(){
		this.producto = new Producto();
	}

	public void guardarProducto()
	{
		Boolean valido=Boolean.TRUE;
		RequestContext context = RequestContext.getCurrentInstance();  
   	    context.addCallbackParam("esValido", valido );
   	    
//   	    double ctxpasj = 0.00;
//   	    ctxpasj = this.producto.getCostoUni()/this.producto.getCantDist();
//   	    this.producto.setCostoxPasj(ctxpasj);
   	    
   	    if(this.producto.getDesgregacion().equals("NO")){
   	    	this.producto.setCantDist(0);
			this.producto.setCostoxPasj(0);
		}
   	    
		try {
			if(this.editar)
			{
				this.productoSevice.actualizarProducto(producto);
				 log.setAccion("UPDATE");
	             log.setDescripcion("Se actualiza el producto: " + producto.getDescripcion());
	             logmb.insertarLog(log);
				FacesUtils.showFacesMessage("Producto actualizado correctamente.",Constante.INFORMACION);
				context.update("sms");
			}else
			{	
				this.producto.setEstado(Boolean.TRUE);
				this.productoSevice.crearProducto(producto);
				 log.setAccion("INSERT");
	             log.setDescripcion("Se inserta el producto: " + producto.getDescripcion());
	             logmb.insertarLog(log);
				 FacesUtils.showFacesMessage("Producto registrado correctamente.",Constante.INFORMACION);
				 context.update("sms");
			}
			listaProductos = this.productoSevice.findAll();
			

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		this.producto = new Producto();
		this.editar =  Boolean.FALSE;
		
	}
	
	public void cambiarEstado(Producto p){
		
		   if(p.isEstado()){
			   p.setEstado(Boolean.FALSE);
		   }else{
			   p.setEstado(Boolean.TRUE);
		   }
		   try {
			   this.productoSevice.actualizarProducto(p);
			   log.setAccion("CHANGE ESTADO");
	           log.setDescripcion("Se cambio el estado del  Producto: " + p.getDescripcion() +" "
	           		+ " a " + p.isEstado());
	           logmb.insertarLog(log);
			   FacesUtils.showFacesMessage("Estado de producto actualizado correctamente.",Constante.INFORMACION);
		   } catch (Exception e) {
			   System.out.println("Error:"+e.getMessage());
			   e.printStackTrace();
		   }   
	}
	
	public void eliminarProducto()
	{
		try {
			
//			int cantProductos = 0;
//			cantProductos = this.productoSevice.cantProductoAsignados(producto.getIdProducto());
//			if(cantProductos == 0){
				this.productoSevice.eliminarProducto(producto.getIdProducto());
				listaProductos.remove(producto);
				FacesUtils.showFacesMessage("Producto eliminado correctamente.",Constante.INFORMACION);
				
				log.setAccion("DELETE");
				log.setDescripcion("Se elimina la Producto: " + producto.getDescripcion());
				logmb.insertarLog(log);
				
//			} else {
//				
//			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.producto =  new Producto();
		
	}
	
	public String asociarOficina(Producto p){
		String outcome = null;
		try {
			System.out.println("produco pasado:"+p.getDescripcion());
    		Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
    		flash.put("producto", p);
    		//return "/faces/control_acceso/asignarPermisos?faces-redirect=true";
    		outcome="pretty:agenciaProducto";
		} catch (Exception e) {	
			System.out.println("Error:"+e.getMessage());
			e.printStackTrace();
		}
		return outcome;
	}
	
	public void editarProducto(Producto ag)
	{
		this.editar = Boolean.TRUE;
		this.producto = ag;
		this.titulo = "Modificar producto";
		if(this.producto.getDesgregacion().equals("SI")){
			this.bandDesgregado = Boolean.TRUE;
		} else if(this.producto.getDesgregacion().equals("NO")){
			this.bandDesgregado = Boolean.FALSE;
			
		}
	}
	
	public void nuevaProducto()
	{
		this.editar = Boolean.FALSE;
	//	this.bandDesgregado = Boolean.FALSE;
		this.producto = new Producto();
		this.producto.setCantDist(1);
		this.titulo = "Registrar nuevo producto";
	}
	
	public void calcularCostxPsj(){
		if(this.producto.getCantDist() ==0) this.producto.setCantDist(1);
		if(this.producto.getCostoUni() == 0.00) this.producto.setCostoUni(1);
		
		double ctxpasj = 0.00;
   	    ctxpasj = this.producto.getCostoUni()/this.producto.getCantDist();
   	    this.producto.setCostoxPasj(ctxpasj);
	
	}
	

	public void activarCamposDesgregado(){
		if(this.producto.getDesgregacion().equals("SI")){
			this.bandDesgregado = Boolean.TRUE;
		} else if(this.producto.getDesgregacion().equals("NO")){
			this.bandDesgregado = Boolean.FALSE;
		}
	}

	public Producto getProducto() {
		return producto;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
	}

	public List<Producto> getListaProductos() {
		return listaProductos;
	}

	public void setListaProductos(List<Producto> listaProductos) {
		this.listaProductos = listaProductos;
	}

	public List<Producto> getListaFiltroProductos() {
		return listaFiltroProductos;
	}

	public void setListaFiltroProductos(List<Producto> listaFiltroProductos) {
		this.listaFiltroProductos = listaFiltroProductos;
	}

	public Boolean getEditar() {
		return editar;
	}

	public void setEditar(Boolean editar) {
		this.editar = editar;
	}

	public ProductoService getProductoSevice() {
		return productoSevice;
	}

	public void setProductoSevice(ProductoService productoSevice) {
		this.productoSevice = productoSevice;
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

	public List<CategoriaProducto> getListaCategoriaProducto() {
		return listaCategoriaProducto;
	}

	public CategoriaProductoService getCategoriaProductoService() {
		return categoriaProductoService;
	}

	public void setListaCategoriaProducto(
			List<CategoriaProducto> listaCategoriaProducto) {
		this.listaCategoriaProducto = listaCategoriaProducto;
	}

	public void setCategoriaProductoService(
			CategoriaProductoService categoriaProductoService) {
		this.categoriaProductoService = categoriaProductoService;
	}

	public Boolean getBandDesgregado() {
		return bandDesgregado;
	}

	public void setBandDesgregado(Boolean bandDesgregado) {
		this.bandDesgregado = bandDesgregado;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}



	
	
}

