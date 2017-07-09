package com.certicom.ittsa.managedBeans;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;

import org.primefaces.context.RequestContext;

import com.certicom.ittsa.domain.Autoparte;
import com.certicom.ittsa.domain.AutoparteAutomotor;
import com.certicom.ittsa.domain.Detalle_Liquidacion;
import com.certicom.ittsa.domain.Flota;
import com.certicom.ittsa.domain.FlotaAutoparte;
import com.certicom.ittsa.domain.Liquidacion;
import com.certicom.ittsa.domain.Log;
import com.certicom.ittsa.domain.Automotor;
import com.certicom.ittsa.domain.FlotaAutomotor;
import com.certicom.ittsa.domain.Programacion;
import com.certicom.ittsa.domain.Usuario;
import com.certicom.ittsa.services.AutomotorService;
import com.certicom.ittsa.services.AutoparteService;
import com.certicom.ittsa.services.AutopartesAutomotorService;
import com.certicom.ittsa.services.Detalle_LiquidacionServices;
import com.certicom.ittsa.services.FlotaAutomotorService;
import com.certicom.ittsa.services.FlotaAutoparteService;
import com.certicom.ittsa.services.LiquidacionServices;
import com.certicom.ittsa.services.MenuServices;
import com.pe.certicom.ittsa.commons.Constante;
import com.pe.certicom.ittsa.commons.FacesUtils;
import com.pe.certicom.ittsa.commons.GenericBeans;

@ManagedBean(name="liquidacionMB")
@ViewScoped
public class LiquidacionMB extends GenericBeans {
	
	//bean principal
	private Programacion programacion;
	private Detalle_Liquidacion detalle_liquidacion;
	//private Liquidacion liquidacion;
	
	private List<Detalle_Liquidacion> listaDetalleLiq;
	
	private MenuServices menuServices;
	private Detalle_LiquidacionServices detalle_liquidacionServices;
	private LiquidacionServices liquidacionServices;
	
	//datos Log
    private Log log;
	private LogMB logmb;
	private Boolean editar;
	
	@PostConstruct
	public void inicia(){

		Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
		this.programacion=(Programacion) flash.get("programacion");

		//SERVICES
		this.menuServices = new MenuServices();
		this.detalle_liquidacionServices = new Detalle_LiquidacionServices();
		this.liquidacionServices = new LiquidacionServices();
		
		log = (Log)getSpringBean(Constante.SESSION_LOG);
		logmb = new LogMB();
		
		try {
			System.out.println("IdProgramacion: "+ programacion.getIdProgramacion());
			Liquidacion l = liquidacionServices.findByIdProgramacion(programacion.getIdProgramacion());
			if(l!=null){
				listaDetalleLiq = detalle_liquidacionServices.findByIdLiquidacion(l.getIdLiquidacion());
			}else listaDetalleLiq = new ArrayList<>();
			 
			int codMenu = menuServices.opcionMenuByPretty("pretty:flotaVehicular");
			log.setCod_menu(codMenu);
			
		} catch (Exception e) {
			System.out.println("Error :"+ e.getMessage());
			e.printStackTrace();
		}
		

	}
	
	public void nuevoDetalle(){
		this.editar = Boolean.FALSE;
		this.detalle_liquidacion = new Detalle_Liquidacion();
	}
	
	public void editarDetalle(Detalle_Liquidacion det){
		this.editar = Boolean.TRUE;
		this.detalle_liquidacion = det;
	}
	
	public void guardarDetalleLiq(){
		Liquidacion l;
		try {
			l = liquidacionServices.findByIdProgramacion(programacion.getIdProgramacion());
			
			if(editar){
				detalle_liquidacion.setIdLiquidacion(l.getIdLiquidacion());
				detalle_liquidacionServices.actualizarDetalleLiq(this.detalle_liquidacion);
				listaDetalleLiq = detalle_liquidacionServices.findByIdLiquidacion(l.getIdLiquidacion());
				FacesUtils.showFacesMessage("Detalle de liquidación actualizado correctamente.",Constante.INFORMACION);
			}else{
				if(l==null){
					Liquidacion liq = new Liquidacion();
					liq.setIdProgramacion(this.programacion.getIdProgramacion());
					liquidacionServices.crearLiquidacion(liq);
					liq = liquidacionServices.findByIdProgramacion(programacion.getIdProgramacion());
					detalle_liquidacion.setIdLiquidacion(liq.getIdLiquidacion());
					detalle_liquidacionServices.crearDetalleLiq(this.detalle_liquidacion);
					listaDetalleLiq = detalle_liquidacionServices.findByIdLiquidacion(liq.getIdLiquidacion());
				}else{
					detalle_liquidacion.setIdLiquidacion(l.getIdLiquidacion());
					detalle_liquidacionServices.crearDetalleLiq(this.detalle_liquidacion);
					listaDetalleLiq = detalle_liquidacionServices.findByIdLiquidacion(l.getIdLiquidacion());
				}	
				
				FacesUtils.showFacesMessage("Detalle registrado correctamente.",Constante.INFORMACION);
			}
			
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	public void eliminarDetalle(){
		try {
			Liquidacion l = liquidacionServices.findByIdProgramacion(programacion.getIdProgramacion());
			detalle_liquidacionServices.eliminarDetalleLiq(detalle_liquidacion.getIdDetalleLiq());
			listaDetalleLiq = detalle_liquidacionServices.findByIdLiquidacion(l.getIdLiquidacion());
			FacesUtils.showFacesMessage("Detalle eliminado correctamente.",Constante.INFORMACION);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	
	public void cambiarEstado(Detalle_Liquidacion det){
		
		if(det.isEstado()){
			det.setEstado(Boolean.FALSE);
		}else{
			det.setEstado(Boolean.TRUE);
		}
		
		try {
			this.detalle_liquidacionServices.actualizarEstado(det);
		} catch (Exception e) {
			e.printStackTrace();
		}
		 
	 }
	
	
// set and get

	
	public MenuServices getMenuServices() {
		return menuServices;
	}

	public Log getLog() {
		return log;
	}

	public LogMB getLogmb() {
		return logmb;
	}
	
	public void setMenuServices(MenuServices menuServices) {
		this.menuServices = menuServices;
	}

	public void setLog(Log log) {
		this.log = log;
	}

	public void setLogmb(LogMB logmb) {
		this.logmb = logmb;
	}

	public Programacion getProgramacion() {
		return programacion;
	}

	public void setProgramacion(Programacion programacion) {
		this.programacion = programacion;
	}

	public Detalle_Liquidacion getDetalle_liquidacion() {
		return detalle_liquidacion;
	}

	public void setDetalle_liquidacion(Detalle_Liquidacion detalle_liquidacion) {
		this.detalle_liquidacion = detalle_liquidacion;
	}

	public List<Detalle_Liquidacion> getListaDetalleLiq() {
		return listaDetalleLiq;
	}

	public void setListaDetalleLiq(List<Detalle_Liquidacion> listaDetalleLiq) {
		this.listaDetalleLiq = listaDetalleLiq;
	}

	public Boolean getEditar() {
		return editar;
	}

	public void setEditar(Boolean editar) {
		this.editar = editar;
	}
	
	
}
