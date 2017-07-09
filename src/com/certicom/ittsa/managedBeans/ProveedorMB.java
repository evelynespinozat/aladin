package com.certicom.ittsa.managedBeans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;

import org.primefaces.context.RequestContext;

import com.certicom.ittsa.domain.Producto;
import com.certicom.ittsa.domain.Proveedor;
import com.certicom.ittsa.domain.Log;
import com.certicom.ittsa.domain.Ubigeo;
import com.certicom.ittsa.services.ProveedorService;
import com.certicom.ittsa.services.MenuServices;
import com.certicom.ittsa.services.OficinaService;
import com.certicom.ittsa.services.UbigeoService;
import com.pe.certicom.ittsa.commons.Constante;
import com.pe.certicom.ittsa.commons.FacesUtils;
import com.pe.certicom.ittsa.commons.GenericBeans;

@ManagedBean(name="proveedorMB")
@ViewScoped
public class ProveedorMB extends GenericBeans implements Serializable{
	private String titulo;
	private Proveedor proveedor;
	private List<Proveedor> listaProveedores;
	private List<Proveedor> listaFiltroProveedores;
	private List<Ubigeo> listaDepartamentos;
	private List<Ubigeo> listaProvincias;
	private List<Ubigeo> listaDistritos;
	private Boolean editar;
	
	//services
	private ProveedorService proveedorSevice;
	private MenuServices menuServices;
	private UbigeoService ubigeoService;
	private String sdepartamento;
	private String sprovincia;
	
	//datos Log
    private Log log;
	private LogMB logmb;
	
	public ProveedorMB(){;}
	
	@PostConstruct
	public void incia()
	{
		this.editar = Boolean.FALSE;
	
		this.proveedorSevice = new ProveedorService();
		this.menuServices=new MenuServices();
		this.proveedor = new Proveedor();
		this.ubigeoService = new UbigeoService();
		
		 log = (Log)getSpringBean(Constante.SESSION_LOG);
		logmb = new LogMB();
		//listando
		try {
			this.listaProveedores = proveedorSevice.listarProveedorATB();
			int codMenu = menuServices.opcionMenuByPretty("pretty:proveedor");
			this.listaDepartamentos = ubigeoService.listarDepartamentos();
			log.setCod_menu(codMenu);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void listarProvincias(){
		try {
			this.listaProvincias = this.ubigeoService.listarProvincias(this.sdepartamento);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void listarDistritos(){
		try {
			this.listaDistritos  = this.ubigeoService.listardistritos(this.sdepartamento, this.sprovincia);	
			System.out.println("EL TAMAÑO ES  " + listaDistritos.size());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
	public void cancelar(){
		this.proveedor = new Proveedor();
	}

	public void guardarProveedor()
	{
		Boolean valido=Boolean.TRUE;
		RequestContext context = RequestContext.getCurrentInstance();  
   	    context.addCallbackParam("esValido", valido );
   	    
		try {
			if(this.editar)
			{
				this.proveedorSevice.actualizarProveedor(proveedor);
				 log.setAccion("UPDATE");
	             log.setDescripcion("Se actualiza el proveedor: " + proveedor.getRazonSocial());
	             logmb.insertarLog(log);
				FacesUtils.showFacesMessage("Proveedor actualizado correctamente.",Constante.INFORMACION);
				context.update("sms");
			}else
			{
				this.proveedor.setTipo("ATB");
				this.proveedor.setEstado(Boolean.TRUE);
				this.proveedorSevice.crearProveedor(proveedor);
				 log.setAccion("INSERT");
	             log.setDescripcion("Se inserta el proveedor: " + proveedor.getRazonSocial());
	             logmb.insertarLog(log);
				 FacesUtils.showFacesMessage("Proveedor registrado correctamente.",Constante.INFORMACION);
				 context.update("sms");
			}
			this.listaProveedores = proveedorSevice.listarProveedorATB();
			

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		this.proveedor = new Proveedor();
		this.editar =  Boolean.FALSE;
		
	}
	
	public void cambiarEstado(Proveedor p){
		
		   if(p.isEstado()){
			   p.setEstado(Boolean.FALSE);
		   }else{
			   p.setEstado(Boolean.TRUE);
		   }
		   
		   try {
			   this.proveedorSevice.actualizarProveedor(p);
			   log.setAccion("CHANGE ESTADO");
	           log.setDescripcion("Se cambio el estado en Proveedor: " + p.getRazonSocial() + " a " + p.isEstado() );
	           logmb.insertarLog(log);
			   FacesUtils.showFacesMessage("Estado de proveedor actualizado correctamente.",Constante.INFORMACION);
		   } catch (Exception e) {
			   System.out.println("Error:"+e.getMessage());
			   e.printStackTrace();
		   }   
	}
	
	public void eliminarProveedor()
	{
		try {
			RequestContext context = RequestContext.getCurrentInstance();
			int cant = 0;
			cant = this.proveedorSevice.cantProveedoresxProducto((proveedor.getIdProveedor()));
			if(cant == 0){
				this.proveedorSevice.eliminarProveedor(proveedor.getIdProveedor());
				listaProveedores.remove(proveedor);
				FacesUtils.showFacesMessage("Proveedor eliminado correctamente.",Constante.INFORMACION);
				
				log.setAccion("DELETE");
				log.setDescripcion("Se elimina el Proveedor: " + proveedor.getRazonSocial());
				logmb.insertarLog(log);
				
			} else {
				context.execute("dlgOmiso.show()");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.proveedor =  new Proveedor();
		
	}
	
	public void editarProveedor(Proveedor ag)
	{
		this.editar = Boolean.TRUE;
		this.proveedor = ag;
		this.titulo = "Modificar proveedor";
		//obteniendo los datos de ubigeo 
		this.listaProvincias = new ArrayList<>();
		this.listaDepartamentos = new ArrayList<>();
		this.listaDistritos = new ArrayList<>();
		
		Ubigeo ubige = new Ubigeo();
		ubige = this.ubigeoService.obtenerUbigeoById(this.proveedor.getCodUbigeo());
		
		this.listaDepartamentos = this.ubigeoService.listarDepartamentos();
		this.listaProvincias = this.ubigeoService.listarProvincias(ubige.getSdepartamento());
		this.listaDistritos = this.ubigeoService.listardistritos(ubige.getSdepartamento(), ubige.getSprovincia());
		
		this.sdepartamento = ubige.getSdepartamento();
		this.sprovincia = ubige.getSprovincia();
	}
	
	
	
	public String asociarOficina(Proveedor p){
		String outcome = null;
		try {
			System.out.println("proveedor pasado:"+p.getRazonSocial());
    		Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
    		flash.put("proveedor", p);
    		//return "/faces/control_acceso/asignarPermisos?faces-redirect=true";
    		outcome="pretty:proveedorAgencia";
		} catch (Exception e) {	
			System.out.println("Error:"+e.getMessage());
			e.printStackTrace();
		}
		return outcome;
	}
	
	public String consultarProductos(Proveedor p){
		String outcome = null;
		try {
			System.out.println("proveedor pasado:"+p.getRazonSocial());
    		Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
    		flash.put("proveedor", p);
    		//return "/faces/control_acceso/asignarPermisos?faces-redirect=true";
    		outcome="pretty:productosxProveedor";
		} catch (Exception e) {	
			System.out.println("Error:"+e.getMessage());
			e.printStackTrace();
		}
		return outcome;
	}
	
	
	public void nuevaProveedor()
	{
		this.titulo = "Registrar nuevo proveedor";
		this.editar = Boolean.FALSE;
		this.proveedor = new Proveedor();
	}

	public Proveedor getProveedor() {
		return proveedor;
	}

	public void setProveedor(Proveedor proveedor) {
		this.proveedor = proveedor;
	}

	public List<Proveedor> getListaProveedores() {
		return listaProveedores;
	}

	public void setListaProveedores(List<Proveedor> listaProveedores) {
		this.listaProveedores = listaProveedores;
	}

	public List<Proveedor> getListaFiltroProveedores() {
		return listaFiltroProveedores;
	}

	public void setListaFiltroProveedores(List<Proveedor> listaFiltroProveedores) {
		this.listaFiltroProveedores = listaFiltroProveedores;
	}

	public Boolean getEditar() {
		return editar;
	}

	public void setEditar(Boolean editar) {
		this.editar = editar;
	}

	public ProveedorService getProveedorSevice() {
		return proveedorSevice;
	}

	public void setProveedorSevice(ProveedorService proveedorSevice) {
		this.proveedorSevice = proveedorSevice;
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

	public List<Ubigeo> getListaDepartamentos() {
		return listaDepartamentos;
	}

	public void setListaDepartamentos(List<Ubigeo> listaDepartamentos) {
		this.listaDepartamentos = listaDepartamentos;
	}

	public List<Ubigeo> getListaProvincias() {
		return listaProvincias;
	}

	public void setListaProvincias(List<Ubigeo> listaProvincias) {
		this.listaProvincias = listaProvincias;
	}

	public List<Ubigeo> getListaDistritos() {
		return listaDistritos;
	}

	public void setListaDistritos(List<Ubigeo> listaDistritos) {
		this.listaDistritos = listaDistritos;
	}

	public UbigeoService getUbigeoService() {
		return ubigeoService;
	}

	public void setUbigeoService(UbigeoService ubigeoService) {
		this.ubigeoService = ubigeoService;
	}

	public String getSdepartamento() {
		return sdepartamento;
	}

	public void setSdepartamento(String sdepartamento) {
		this.sdepartamento = sdepartamento;
	}

	public String getSprovincia() {
		return sprovincia;
	}

	public void setSprovincia(String sprovincia) {
		this.sprovincia = sprovincia;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	//**set an get 
	
	
	
}

