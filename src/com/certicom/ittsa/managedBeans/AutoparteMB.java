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

import com.certicom.ittsa.domain.Almacen;
import com.certicom.ittsa.domain.Automotor;
import com.certicom.ittsa.domain.Autoparte;
import com.certicom.ittsa.domain.Log;
import com.certicom.ittsa.domain.Medida;
import com.certicom.ittsa.domain.Producto;
import com.certicom.ittsa.domain.Proveedor;
import com.certicom.ittsa.services.AlmacenService;
import com.certicom.ittsa.services.AutomotorService;
import com.certicom.ittsa.services.AutoparteService;
import com.certicom.ittsa.services.MedidaService;
import com.certicom.ittsa.services.MenuServices;
import com.certicom.ittsa.services.OficinaService;
import com.certicom.ittsa.services.ProveedorService;
import com.pe.certicom.ittsa.commons.Constante;
import com.pe.certicom.ittsa.commons.FacesUtils;
import com.pe.certicom.ittsa.commons.GenericBeans;

@ManagedBean(name="autoparteMB")
@ViewScoped
public class AutoparteMB extends GenericBeans implements Serializable{
	private String titulo;
	private Autoparte autoparte;
	private List<Autoparte> listaAutopartes;
	private List<Autoparte> listaFiltroAutopartes;
	private List<Almacen> listaAlmacen;
	private List<Proveedor> listaProveedor;
	private Boolean editar;
	private List<Automotor> listaSistemaAutomotor; 
	private List<Medida> listaMedida;
	
	//services
	private AutoparteService autoparteSevice;
	private AutomotorService automotorService;
	private MenuServices menuServices;
	private AlmacenService almacenService;
	private ProveedorService  proveedorService;
	private MedidaService medidaService;
	
	
	//datos Log
    private Log log;
	private LogMB logmb;
	
	public AutoparteMB(){;}
	
	@PostConstruct
	public void incia()
	{
		this.editar = Boolean.FALSE;
	    this.listaAlmacen = new ArrayList<>();
	    this.autoparte = new Autoparte();
	    this.listaProveedor = new ArrayList<>();
	    this.listaSistemaAutomotor = new ArrayList<>();
	    //service
	    this.automotorService = new AutomotorService();
		this.autoparteSevice = new AutoparteService();
		this.menuServices = new MenuServices();
		this.almacenService = new AlmacenService();
		this.proveedorService = new ProveedorService();
		this.medidaService = new MedidaService();
		
		
		 log = (Log)getSpringBean(Constante.SESSION_LOG);
		logmb = new LogMB();
		//listando
		try {
			this.listaSistemaAutomotor = this.automotorService.listaAutomotorsActivas();
			this.listaAutopartes = this.autoparteSevice.findAll();
			this.listaAlmacen = this.almacenService.listaAlmacensActivas();
			this.listaProveedor = this.proveedorService.listaProveedoresActivosMAN();
			this.listaMedida = this.medidaService.listaMedidasActivas();
			int codMenu = menuServices.opcionMenuByPretty("pretty:autoparte");
			log.setCod_menu(codMenu);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void cancelar(){
		this.autoparte = new Autoparte();
	}

	public void guardarAutoparte()
	{
		Boolean valido=Boolean.TRUE;
		RequestContext context = RequestContext.getCurrentInstance();  
   	    context.addCallbackParam("esValido", valido );
   	    
		try {
			autoparte.setDescripcion(autoparte.getDescripcion().toUpperCase().trim());
			if(this.editar)
			{
				
				this.autoparte.setIdAutoparte(this.autoparte.getIdAutoparte());
				this.autoparteSevice.actualizarAutoparte(autoparte);
				 log.setAccion("UPDATE");
	             log.setDescripcion("Se actualiza la autoparte: " + autoparte.getDescripcion());
	             logmb.insertarLog(log);
				FacesUtils.showFacesMessage("Autoparte actualizada correctamente.",Constante.INFORMACION);
				context.update("sms");
			}else
			{
				this.autoparte.setEstado(Boolean.TRUE);
				this.autoparteSevice.crearAutoparte(autoparte);
				 log.setAccion("INSERT");
	             log.setDescripcion("Se inserta la autoparte: " + autoparte.getDescripcion());
	             logmb.insertarLog(log);
				 FacesUtils.showFacesMessage("Autoparte registrada correctamente.",Constante.INFORMACION);
				 context.update("sms");
			}
			listaAutopartes = this.autoparteSevice.findAll();
			

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		this.autoparte = new Autoparte();
		this.editar =  Boolean.FALSE;
		
	}
	
	public void cambiarEstado(Autoparte agen){
		
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
			   this.autoparteSevice.actualizarAutoparte(agen);
			   log.setAccion("CHANGE ESTADO");
	           log.setDescripcion("Se cambio el estado en Autoparte: " + autoparte.getDescripcion());
	           logmb.insertarLog(log);
			   FacesUtils.showFacesMessage("Estado de autoparte actualizado correctamente.",Constante.INFORMACION);
		   } catch (Exception e) {
			   System.out.println("Error:"+e.getMessage());
			   e.printStackTrace();
		   }   
	}
	
	public void eliminarAutoparte()
	{
		try {
			RequestContext context = RequestContext.getCurrentInstance();
			
//			int cantAutopartes = 0;
//			cantAutopartes = this.oficinaService.cantOfixAutoparte(autoparte.getIdautoparte());
//			if(cantAutopartes == 0){
				this.autoparteSevice.eliminarAutoparte(autoparte.getIdAutoparte());
				listaAutopartes.remove(autoparte);
				FacesUtils.showFacesMessage("Autoparte eliminada correctamente.",Constante.INFORMACION);
				
				log.setAccion("DELETE");
				log.setDescripcion("Se elimina la Autoparte: " + autoparte.getDescripcion());
				logmb.insertarLog(log);
	
//			} else {
//				context.execute("dlgOmiso.show()");
//			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.autoparte =  new Autoparte();
		
	}
	
	public void editarAutoparte(Autoparte ag)
	{
		this.editar = Boolean.TRUE;
		this.autoparte = ag;
		this.titulo = "Modificar autoparte";
	}
	
	public void nuevaAutoparte()
	{
		this.editar = Boolean.FALSE;
		this.autoparte = new Autoparte();
		this.titulo = "Registrar nueva autoparte";
	}
	
	public String asociarOficina(Autoparte a){
		String outcome = null;
		try {
			System.out.println("autoparte pasado:"+a.getDescripcion());
    		Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
    		flash.put("autoparte", a);
    		//return "/faces/control_acceso/asignarPermisos?faces-redirect=true";
    		outcome="pretty:agenciaAutoparte";
		} catch (Exception e) {	
			System.out.println("Error:"+e.getMessage());
			e.printStackTrace();
		}
		return outcome;
	}

	//**set an get 
	
	public MenuServices getMenuServices() {
		return menuServices;
	}

	public List<Automotor> getListaSistemaAutomotor() {
		return listaSistemaAutomotor;
	}

	public void setListaSistemaAutomotor(List<Automotor> listaSistemaAutomotor) {
		this.listaSistemaAutomotor = listaSistemaAutomotor;
	}

	public void setMenuServices(MenuServices menuServices) {
		this.menuServices = menuServices;
	}

	public List<Autoparte> getListaFiltroAutopartes() {
		return listaFiltroAutopartes;
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

	public void setListaFiltroAutopartes(List<Autoparte> listaFiltroAutopartes) {
		this.listaFiltroAutopartes = listaFiltroAutopartes;
	}

	public Autoparte getAutoparte() {
		return autoparte;
	}

	public void setAutoparte(Autoparte autoparte) {
		this.autoparte = autoparte;
	}

	public List<Autoparte> getListaAutopartes() {
		return listaAutopartes;
	}

	public void setListaAutopartes(List<Autoparte> listaAutopartes) {
		this.listaAutopartes = listaAutopartes;
	}

	public Boolean getEditar() {
		return editar;
	}

	public void setEditar(Boolean editar) {
		this.editar = editar;
	}

	public List<Almacen> getListaAlmacen() {
		return listaAlmacen;
	}

	public void setListaAlmacen(List<Almacen> listaAlmacen) {
		this.listaAlmacen = listaAlmacen;
	}

	public List<Proveedor> getListaProveedor() {
		return listaProveedor;
	}

	public void setListaProveedor(List<Proveedor> listaProveedor) {
		this.listaProveedor = listaProveedor;
	}

	public List<Medida> getListaMedida() {
		return listaMedida;
	}

	public void setListaMedida(List<Medida> listaMedida) {
		this.listaMedida = listaMedida;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	
	
}

