package com.certicom.ittsa.managedBeans;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;

import org.primefaces.context.RequestContext;

import com.certicom.ittsa.domain.AgenciaProducto;
import com.certicom.ittsa.domain.Almacen;
import com.certicom.ittsa.domain.Log;
import com.certicom.ittsa.domain.Plantilla;
import com.certicom.ittsa.domain.PlantillaDetalle;
import com.certicom.ittsa.services.AgenciaProductoService;
import com.certicom.ittsa.services.AlmacenService;
import com.certicom.ittsa.services.MenuServices;
import com.certicom.ittsa.services.PlantillaDetalleService;
import com.pe.certicom.ittsa.commons.Constante;
import com.pe.certicom.ittsa.commons.FacesUtils;
import com.pe.certicom.ittsa.commons.GenericBeans;

@ManagedBean(name="plantillaDetalleMB")
@ViewScoped
public class PlantillaDetalleMB extends GenericBeans{
	
	// objeto que va a recibir de la pagiona principal
	public Plantilla plantilla;
	public PlantillaDetalle pldetselect;
	
	public List<Almacen> listaAlmacen;
	public List<AgenciaProducto> listaProductosxAgencia;
	public List<PlantillaDetalle> listPlantillaDetalle;
	
	
	public AlmacenService almacenService;
	public AgenciaProductoService agenciaProductoService;
	public PlantillaDetalleService plantillaDetalleService;
	public MenuServices menuServices;
	

	//datos Log
    private Log log;
	private LogMB logmb;
	
	
	
	@PostConstruct
	public void inicia(){

		Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
		this.plantilla  = (Plantilla)flash.get("plantilla");
		
		this.almacenService = new AlmacenService();
		this.agenciaProductoService = new AgenciaProductoService();
		this.plantillaDetalleService = new PlantillaDetalleService();
		this.menuServices = new MenuServices();
		
		log = (Log)getSpringBean(Constante.SESSION_LOG);
		logmb = new LogMB();
		
		try {
			int codMenu = menuServices.opcionMenuByPretty("pretty:plantillaProd");
			log.setCod_menu(codMenu);
			this.listaAlmacen = this.almacenService.listAlmacenxOficina(this.plantilla.IdOficina);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}
	
	public void getProductosxAgencia(){
		AgenciaProducto ap  = new AgenciaProducto();
		ap.setIdagencia(this.plantilla.getIdagencia());
		ap.setIdOficina(this.plantilla.getIdOficina());
		ap.setIdAlmacen(this.plantilla.getIdAlmacen());
		try {
			this.listaProductosxAgencia = this.agenciaProductoService.listarAgenProductos(ap);
			this.listPlantillaDetalle = this.plantillaDetalleService.listarPlantillaDetalle(this.plantilla.getIdPlantilla());
			System.out.println("el tamaño de la lista es "  + listaProductosxAgencia.size());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void agregarProductosPlantilla(AgenciaProducto agp){
		PlantillaDetalle pd = new PlantillaDetalle();
		pd.setIdPlantilla(this.plantilla.getIdPlantilla());
		pd.setIdAgPro(agp.getId());
		pd.setCantDefecto(agp.getCantSalidaDefecto());
		
		try {
			int cant  = 0;
			cant  = this.plantillaDetalleService.cantProductoxPlantilla(pd);
			if(cant == 0){
			this.plantillaDetalleService.registarPlantillaDetalle(pd);
			 log.setAccion("INSERT");
             log.setDescripcion("Se inserto el producto: " + agp.getDesProducto() + " a la plantilla " +this.plantilla.getIdPlantilla());
             logmb.insertarLog(log);
             FacesUtils.showFacesMessage("Producto en plantilla registrado correctamente.",Constante.INFORMACION);
             this.listPlantillaDetalle = this.plantillaDetalleService.listarPlantillaDetalle(this.plantilla.getIdPlantilla());
			} else {
				FacesUtils.showFacesMessage("Este producto ya ha sido agregado a la plantilla.",Constante.ERROR);
			}
			} catch (Exception e) {
			e.printStackTrace();	
		}
		// refrescar la lista detalle de plantilla
	}
	
	
	public void eliminarProductoPlantilla(PlantillaDetalle pd){
		try {
			this.plantillaDetalleService.eliminarPlantillaDetalle(pd.getIdPlanDet());
			 log.setAccion("DELETE");
             log.setDescripcion("Se eliminó el producto: " + pd.getDesProducto() + " de la plantilla " +this.plantilla.getIdPlantilla());
             logmb.insertarLog(log);
             FacesUtils.showFacesMessage("Producto de la plantilla eliminado correctamente.",Constante.INFORMACION);
             this.listPlantillaDetalle = this.plantillaDetalleService.listarPlantillaDetalle(this.plantilla.getIdPlantilla());
		} catch (Exception e) {
			e.printStackTrace();	
		}
	}
	
	public void editarCantidad(PlantillaDetalle pd){
		this.pldetselect = pd;
	}
	
	public void guardarCambioCantidad(){
		Boolean valido=Boolean.TRUE;
		RequestContext context = RequestContext.getCurrentInstance();  
   	    context.addCallbackParam("esValido", valido );
		try {
			this.plantillaDetalleService.actualizarCantidad(this.pldetselect.getIdPlanDet(), this.pldetselect.getCantDefecto());
			FacesUtils.showFacesMessage("Cantidad del producto en la plantilla actualizada correctamente.",Constante.INFORMACION);
			context.update("msgGeneral");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	public Plantilla getPlantilla() {
		return plantilla;
	}
	public void setPlantilla(Plantilla plantilla) {
		this.plantilla = plantilla;
	}


	public List<Almacen> getListaAlmacen() {
		return listaAlmacen;
	}


	public void setListaAlmacen(List<Almacen> listaAlmacen) {
		this.listaAlmacen = listaAlmacen;
	}

	public List<AgenciaProducto> getListaProductosxAgencia() {
		return listaProductosxAgencia;
	}

	public void setListaProductosxAgencia(
			List<AgenciaProducto> listaProductosxAgencia) {
		this.listaProductosxAgencia = listaProductosxAgencia;
	}

	public List<PlantillaDetalle> getListPlantillaDetalle() {
		return listPlantillaDetalle;
	}

	public void setListPlantillaDetalle(List<PlantillaDetalle> listPlantillaDetalle) {
		this.listPlantillaDetalle = listPlantillaDetalle;
	}

	public PlantillaDetalle getPldetselect() {
		return pldetselect;
	}

	public void setPldetselect(PlantillaDetalle pldetselect) {
		this.pldetselect = pldetselect;
	}
	
	
	
	
	
}
