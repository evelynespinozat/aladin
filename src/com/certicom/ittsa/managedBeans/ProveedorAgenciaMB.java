package com.certicom.ittsa.managedBeans;

import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;

import org.primefaces.context.RequestContext;

import com.certicom.ittsa.domain.Agencia;
import com.certicom.ittsa.domain.AgenciaProducto;
import com.certicom.ittsa.domain.Proveedor;
import com.certicom.ittsa.domain.ProveedorAgencia;
import com.certicom.ittsa.domain.Almacen;
import com.certicom.ittsa.domain.Kardex;
import com.certicom.ittsa.domain.Log;
import com.certicom.ittsa.domain.Oficina;
import com.certicom.ittsa.domain.Producto;
import com.certicom.ittsa.domain.ProveedorAgencia;
import com.certicom.ittsa.services.ProveedorAgenciaService;
import com.certicom.ittsa.services.AgenciaService;
import com.certicom.ittsa.services.AlmacenService;
import com.certicom.ittsa.services.KardexService;
import com.certicom.ittsa.services.MenuServices;
import com.certicom.ittsa.services.OficinaService;
import com.pe.certicom.ittsa.commons.Constante;
import com.pe.certicom.ittsa.commons.FacesUtils;
import com.pe.certicom.ittsa.commons.GenericBeans;

@ManagedBean(name="proveedorAgenciaMB")
@ViewScoped
public class ProveedorAgenciaMB extends GenericBeans {
	
	//bean principal
	private ProveedorAgencia proveedorAgencia;
	
	private AgenciaService agenciaService;
	private OficinaService oficinaService;
	private AlmacenService almacenService;
	private ProveedorAgenciaService proveedorAgenciaService;
	private KardexService kardexService;
	
	private MenuServices menuServices;
	
	
	private List<Agencia> listaAgencias;
	private List<Oficina> listaOficinas;
	private List<ProveedorAgencia> listAgProveedor;
	
	
	private Proveedor proveedor;

	//datos Log
    private Log log;
	private LogMB logmb;
	
	@PostConstruct
	public void inicia(){

		this.proveedorAgencia = new ProveedorAgencia();

		Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
		this.proveedor =(Proveedor) flash.get("proveedor");
		
		
		this.agenciaService = new AgenciaService();
		this.oficinaService = new OficinaService();
		this.proveedorAgenciaService = new ProveedorAgenciaService();
		this.menuServices = new MenuServices();
		
		log = (Log)getSpringBean(Constante.SESSION_LOG);
		logmb = new LogMB();
		
		try {
			int codMenu = menuServices.opcionMenuByPretty("pretty:productos");
			log.setCod_menu(codMenu);
			this.listaAgencias = this.agenciaService.listaAgenciasActivas();
			this.listAgProveedor = this.proveedorAgenciaService.listOfixProveedor(this.proveedor.getIdProveedor());
			
		} catch (Exception e) {
			System.out.println("Error :"+ e.getMessage());
			e.printStackTrace();
		}
		

	}
	
	
	public void addProveedorToOficina() {
		Boolean valido=Boolean.TRUE;
		RequestContext context = RequestContext.getCurrentInstance();  
   	    context.addCallbackParam("esValido", valido );
   	    
		try {
			this.proveedorAgencia.setIdProveedor(this.proveedor.getIdProveedor());
			int cant =0 ;
			cant = this.proveedorAgenciaService.getCantProvAgencia(this.proveedorAgencia);
			if(cant == 0){
				this.proveedorAgencia.setIdProveedor(this.proveedor.getIdProveedor());
				this.proveedorAgenciaService.crearProveedorAgencia(this.proveedorAgencia);
				
				log.setAccion("INSERT");
				log.setDescripcion("Se asigno el Proveedor "+ this.proveedor.getRazonSocial() + " a la oficina "
									+ this.proveedorAgencia.getIdOficina());
				logmb.insertarLog(log);
				
				FacesUtils.showFacesMessage("Proveedor en oficina registrado correctamente.",Constante.INFORMACION);
				this.listAgProveedor = this.proveedorAgenciaService.listOfixProveedor(this.proveedor.getIdProveedor());
			} else {
				FacesUtils.showFacesMessage("El proveedor se encuentra ya asignado a esta oficina.",Constante.ERROR);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.proveedorAgencia = new ProveedorAgencia();
	}
	
	
	public void getOficinasxAgencia(){
		try {
			this.listaOficinas = this.oficinaService.getOficinasxAgencia(this.proveedorAgencia.getIdagencia());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}	
	
	
	
	public void deleteOficinaFromProveedor(){
		try {
			// falta la validacion
			this.proveedorAgenciaService.deleteOficinaFromProveedor(this.proveedorAgencia.getId());
			
			log.setAccion("INSERT");
			log.setDescripcion("Se eliminó el Proveedor "+ this.proveedor.getRazonSocial() + " de la oficina "
								+ this.proveedorAgencia.getDesOficina());
			logmb.insertarLog(log);
			
			
			FacesUtils.showFacesMessage("Proveedor de la oficina eliminado correctamente.",Constante.INFORMACION);
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.proveedorAgencia = new ProveedorAgencia();
		this.listAgProveedor = this.proveedorAgenciaService.listOfixProveedor(this.proveedor.getIdProveedor());
		
	}


	public ProveedorAgencia getProveedorAgencia() {
		return proveedorAgencia;
	}


	public AgenciaService getAgenciaService() {
		return agenciaService;
	}


	public OficinaService getOficinaService() {
		return oficinaService;
	}


	public AlmacenService getAlmacenService() {
		return almacenService;
	}


	public ProveedorAgenciaService getProveedorAgenciaService() {
		return proveedorAgenciaService;
	}


	public KardexService getKardexService() {
		return kardexService;
	}


	public MenuServices getMenuServices() {
		return menuServices;
	}


	public List<Agencia> getListaAgencias() {
		return listaAgencias;
	}


	public List<Oficina> getListaOficinas() {
		return listaOficinas;
	}


	public List<ProveedorAgencia> getListAgProveedor() {
		return listAgProveedor;
	}


	public Proveedor getProveedor() {
		return proveedor;
	}


	public Log getLog() {
		return log;
	}


	public LogMB getLogmb() {
		return logmb;
	}


	public void setProveedorAgencia(ProveedorAgencia proveedorAgencia) {
		this.proveedorAgencia = proveedorAgencia;
	}


	public void setAgenciaService(AgenciaService agenciaService) {
		this.agenciaService = agenciaService;
	}


	public void setOficinaService(OficinaService oficinaService) {
		this.oficinaService = oficinaService;
	}


	public void setAlmacenService(AlmacenService almacenService) {
		this.almacenService = almacenService;
	}


	public void setProveedorAgenciaService(
			ProveedorAgenciaService proveedorAgenciaService) {
		this.proveedorAgenciaService = proveedorAgenciaService;
	}


	public void setKardexService(KardexService kardexService) {
		this.kardexService = kardexService;
	}


	public void setMenuServices(MenuServices menuServices) {
		this.menuServices = menuServices;
	}


	public void setListaAgencias(List<Agencia> listaAgencias) {
		this.listaAgencias = listaAgencias;
	}


	public void setListaOficinas(List<Oficina> listaOficinas) {
		this.listaOficinas = listaOficinas;
	}


	public void setListAgProveedor(List<ProveedorAgencia> listAgProveedor) {
		this.listAgProveedor = listAgProveedor;
	}


	public void setProveedor(Proveedor proveedor) {
		this.proveedor = proveedor;
	}


	public void setLog(Log log) {
		this.log = log;
	}


	public void setLogmb(LogMB logmb) {
		this.logmb = logmb;
	}



	
	
	
	/*#######################---getters y setters----------########################*/
	
	

	
	
}
