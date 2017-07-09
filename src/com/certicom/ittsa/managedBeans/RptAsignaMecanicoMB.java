package com.certicom.ittsa.managedBeans;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import com.certicom.ittsa.domain.Autoparte;
import com.certicom.ittsa.domain.Flota;
import com.certicom.ittsa.domain.FlotaAutoparte;
import com.certicom.ittsa.domain.HistorialMantenimiento;
import com.certicom.ittsa.domain.Log;
import com.certicom.ittsa.domain.Mecanico;
import com.certicom.ittsa.services.AgenciaService;
import com.certicom.ittsa.services.AutoparteService;
import com.certicom.ittsa.services.FlotaAutomotorService;
import com.certicom.ittsa.services.FlotaAutoparteService;
import com.certicom.ittsa.services.HistorialMantenimientoService;
import com.certicom.ittsa.services.MecanicoService;
import com.certicom.ittsa.services.MenuServices;
import com.certicom.ittsa.services.OficinaService;
import com.pe.certicom.ittsa.commons.Constante;
import com.pe.certicom.ittsa.commons.ExportarArchivo;
import com.pe.certicom.ittsa.commons.FacesUtils;
import com.pe.certicom.ittsa.commons.GenericBeans;

@ManagedBean(name = "rptAsignaMecanicoMB")
@ViewScoped
public class RptAsignaMecanicoMB extends GenericBeans implements Serializable {

	private List<Mecanico> listaMecanicos;
	private HistorialMantenimiento historialMantenimientoFilter;
	private List<HistorialMantenimiento> listaHistorialMecanico;

	// services
	private HistorialMantenimientoService historialMantenimientoService;
	private MecanicoService mecanicoService;
	private MenuServices menuServices;

	// datos Log
	private Log log;
	private LogMB logmb;

	public RptAsignaMecanicoMB() {
		;
	}

	@PostConstruct
	public void incia() {
		this.listaMecanicos = new ArrayList<>();
		this.historialMantenimientoFilter = new HistorialMantenimiento();
		
		this.historialMantenimientoService = new HistorialMantenimientoService();
		this.mecanicoService = new MecanicoService();
		this.menuServices = new MenuServices();

		log = (Log) getSpringBean(Constante.SESSION_LOG);
		logmb = new LogMB();
		// listando
		try {
			this.listaMecanicos = this.mecanicoService.listaMecanicosActivas();
			int codMenu = menuServices
					.opcionMenuByPretty("pretty:reporteAsigMecanico");
			log.setCod_menu(codMenu);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void buscarMecanico(){
		
		try {
			this.listaHistorialMecanico = new ArrayList<>();
			System.out.println("ffin " + this.historialMantenimientoFilter.getFecFin());
			this.listaHistorialMecanico = this.historialMantenimientoService.obtenerMecanicoAsignado(this.historialMantenimientoFilter);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void imprimirPDF(){
		try {
			ServletContext servletContext = (ServletContext) (FacesContext.getCurrentInstance().getExternalContext().getContext());
			
			SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");		
			String desde = formato.format(this.historialMantenimientoFilter.getFecInicio());
			String hasta = formato.format(this.historialMantenimientoFilter.getFecFin());
			
			Mecanico m = this.mecanicoService.findById(this.historialMantenimientoFilter.getIdMecanico());
//			Integer total = this.listaRptEncomienda.size();
			
			String p_logo = ExportarArchivo.getPath("/resources/img/logoittsa.jpg");
			System.out.println("logo ruta:"+ p_logo);
			
			Map<String, Object> input =new  HashMap<String,Object>();
			input.put("P_LOGO", p_logo);
			input.put("P_MECANICO", m.getNombre()+" "+m.getApePaterno() + " " + m.getApeMaterno());
			input.put("P_FEC_DESDE", desde);
			input.put("P_FEC_HASTA", hasta);
			input.put(JRParameter.REPORT_LOCALE, new Locale("es"));
			//input.put(JRParameter.IS_IGNORE_PAGINATION, Boolean.TRUE); // no parte la pagina todo lo mete en un A4
			
			String path = ExportarArchivo.getPath("/resources/reports/jxrml/rptAsignacionMecanico.jasper");
			HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
			
			
			byte[] bytes;
				bytes = ExportarArchivo.exportPdf(path, input, this.listaHistorialMecanico);
			ExportarArchivo.executePdf(bytes, "AsignacionMecanico.pdf");
			FacesContext.getCurrentInstance().responseComplete();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}

	// **set an get

	public MenuServices getMenuServices() {
		return menuServices;
	}

	public List<HistorialMantenimiento> getListaHistorialMecanico() {
		return listaHistorialMecanico;
	}

	public void setListaHistorialMecanico(
			List<HistorialMantenimiento> listaHistorialMecanico) {
		this.listaHistorialMecanico = listaHistorialMecanico;
	}

	public List<Mecanico> getListaMecanicos() {
		return listaMecanicos;
	}

	public void setListaMecanicos(List<Mecanico> listaMecanicos) {
		this.listaMecanicos = listaMecanicos;
	}

	public HistorialMantenimiento getHistorialMantenimientoFilter() {
		return historialMantenimientoFilter;
	}

	public void setHistorialMantenimientoFilter(
			HistorialMantenimiento historialMantenimientoFilter) {
		this.historialMantenimientoFilter = historialMantenimientoFilter;
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

}
