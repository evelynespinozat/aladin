package com.certicom.ittsa.managedBeans;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRParameter;

import org.primefaces.context.RequestContext;

import com.certicom.ittsa.domain.Agencia;
import com.certicom.ittsa.domain.Automotor;
import com.certicom.ittsa.domain.Flota;
import com.certicom.ittsa.domain.HistorialMantenimiento;
import com.certicom.ittsa.domain.Log;
import com.certicom.ittsa.domain.Mecanico;
import com.certicom.ittsa.services.AgenciaService;
import com.certicom.ittsa.services.HistorialMantenimientoService;
import com.certicom.ittsa.services.MenuServices;
import com.certicom.ittsa.services.OficinaService;
import com.pe.certicom.ittsa.commons.Constante;
import com.pe.certicom.ittsa.commons.ExportarArchivo;
import com.pe.certicom.ittsa.commons.FacesUtils;
import com.pe.certicom.ittsa.commons.GenericBeans;

@ManagedBean(name="verFlotaHistorialMB")
@ViewScoped
public class VerFlotaHistorialMB extends GenericBeans implements Serializable{

	private Flota flota;
	private List<HistorialMantenimiento> listaHistorialMantenimiento;
	private List<HistorialMantenimiento> listaFiltroHistorialMantenimiento;
	private HistorialMantenimientoService historialMantenimientoService;
	
	//services
	private MenuServices menuServices;
	
	//datos Log
    private Log log;
	private LogMB logmb;
	
	public VerFlotaHistorialMB(){;}
	
	@PostConstruct
	public void incia()
	{
		Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
		this.flota = (Flota) flash.get("flota");
		this.listaHistorialMantenimiento = new ArrayList<>();
		
		this.menuServices=new MenuServices();
		this.historialMantenimientoService = new HistorialMantenimientoService();
		
		 log = (Log)getSpringBean(Constante.SESSION_LOG);
		logmb = new LogMB();
		//listando
		try {
			this.listaHistorialMantenimiento = this.historialMantenimientoService.obtenerHistorialFlota(this.flota);
			int codMenu = menuServices.opcionMenuByPretty("pretty:flotaHistorial");
			log.setCod_menu(codMenu);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void consultarHistorial(){
		try {
			System.out.println("fini " + this.flota.getFecInicio());
			System.out.println("fini " + this.flota.getFecFin());
			this.listaHistorialMantenimiento = this.historialMantenimientoService.obtenerHistorialFlota(this.flota);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void imprimirPDF(){
		try {
			ServletContext servletContext = (ServletContext) (FacesContext.getCurrentInstance().getExternalContext().getContext());
			
			SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");		
			String desde = "",hasta = "";
				if(this.flota.getFecInicio()!=null) desde =formato.format(this.flota.getFecInicio());
				if(this.flota.getFecFin()!=null)  hasta = formato.format(this.flota.getFecFin());
			
			String p_logo = ExportarArchivo.getPath("/resources/img/logoittsa.jpg");
			System.out.println("logo ruta:"+ p_logo);
			
			Map<String, Object> input =new  HashMap<String,Object>();
			input.put("P_LOGO", p_logo);
			input.put("P_FEC_DESDE", desde);
			input.put("P_FEC_HASTA", hasta);
			input.put("P_NROBUS", this.flota.getNumero());
			input.put("P_PLACA", this.flota.getPlaca());
			input.put("P_MARCA", this.flota.getMarca());
			input.put("P_MODELO", this.flota.getModelo());
			input.put("P_CLASE", this.flota.getDescClase());
			input.put("P_KILOMETRAJE", this.flota.getRecorrido());
			input.put(JRParameter.REPORT_LOCALE, new Locale("es"));
			//input.put(JRParameter.IS_IGNORE_PAGINATION, Boolean.TRUE); // no parte la pagina todo lo mete en un A4
			
			String path = ExportarArchivo.getPath("/resources/reports/jxrml/rptHistMantFlota.jasper");
			HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
			
			
			byte[] bytes;
				bytes = ExportarArchivo.exportPdf(path, input, this.listaHistorialMantenimiento);
			ExportarArchivo.executePdf(bytes, "HistorialFlotaMant.pdf");
			FacesContext.getCurrentInstance().responseComplete();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	

	//**set an get 
	
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

	public Flota getFlota() {
		return flota;
	}

	public void setFlota(Flota flota) {
		this.flota = flota;
	}

	public List<HistorialMantenimiento> getListaHistorialMantenimiento() {
		return listaHistorialMantenimiento;
	}

	public void setListaHistorialMantenimiento(
			List<HistorialMantenimiento> listaHistorialMantenimiento) {
		this.listaHistorialMantenimiento = listaHistorialMantenimiento;
	}

	public List<HistorialMantenimiento> getListaFiltroHistorialMantenimiento() {
		return listaFiltroHistorialMantenimiento;
	}

	public void setListaFiltroHistorialMantenimiento(
			List<HistorialMantenimiento> listaFiltroHistorialMantenimiento) {
		this.listaFiltroHistorialMantenimiento = listaFiltroHistorialMantenimiento;
	}
	
}

