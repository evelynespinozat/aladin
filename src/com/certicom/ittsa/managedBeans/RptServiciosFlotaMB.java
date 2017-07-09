package com.certicom.ittsa.managedBeans;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRParameter;

import com.certicom.ittsa.domain.Agencia;
import com.certicom.ittsa.domain.Flota;
import com.certicom.ittsa.domain.Log;
import com.certicom.ittsa.domain.Piloto;
import com.certicom.ittsa.domain.Programacion;
import com.certicom.ittsa.services.AgenciaService;
import com.certicom.ittsa.services.FlotaService;
import com.certicom.ittsa.services.MenuServices;
import com.certicom.ittsa.services.PilotoService;
import com.certicom.ittsa.services.ProgramacionService;
import com.pe.certicom.ittsa.commons.ExportarArchivo;
import com.pe.certicom.ittsa.commons.Constante;
import com.pe.certicom.ittsa.commons.GenericBeans;
import com.pe.certicom.ittsa.commons.Utils;

@ManagedBean(name="rptServiciosFlotaMB")
@ViewScoped
public class RptServiciosFlotaMB extends GenericBeans implements Serializable{

	private List<Flota> listaBuses;
	private List<Programacion> listaProgramacion;
	private List<Programacion> listaProgramacionDetalle;
	private List<Programacion> listaProgramacionFilter;
	private Programacion programacion;
	private Date fecInicio;
	private Date fecFin;
	private boolean bandImprimir;
	private HashMap mapProgramacion;
	
	private MenuServices menuServices;
	private FlotaService flotaServices;
	private ProgramacionService programacionServices;
	private AgenciaService agenciaServices;
	private PilotoService pilotoServices;
	//datos Log
    private Log log;
	private LogMB logmb;
	
	private Utils utils;
	
	public RptServiciosFlotaMB(){;}
	
	@PostConstruct
	public void incia()
	{
		this.menuServices = new MenuServices();
		this.flotaServices = new FlotaService();
		this.programacionServices = new ProgramacionService();
		this.agenciaServices = new AgenciaService();
		this.pilotoServices = new PilotoService();
		log = (Log)getSpringBean(Constante.SESSION_LOG);
		logmb = new LogMB();
		//listando
		try {
			this.listaBuses= flotaServices.findAll();
			int codMenu = menuServices.opcionMenuByPretty("pretty:frecuencia");
			log.setCod_menu(codMenu);
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.bandImprimir = Boolean.FALSE;
		this.utils = new Utils();
		this.mapProgramacion = new HashMap<>();
	}
	
	public void buscarServicios(){
		Date fFin = utils.sumaDias(getFecFin(),1);
		
		try {
			this.listaProgramacion = programacionServices.findProgServCumplidosFlota(getFecInicio(), fFin); 
			for(Programacion p: listaProgramacion){
				mapProgramacion.put(p.getIdBus(), p);
			}
			this.bandImprimir = Boolean.TRUE;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Iterator it = mapProgramacion.keySet().iterator();
		while (it.hasNext()) {
			Integer clave = (Integer) it.next();
			Programacion valor = (Programacion) mapProgramacion.get(clave);
			List<Programacion> list1;
			try {
				list1 = programacionServices.findProgServCumplidosxBus(getFecInicio(), fFin, valor.getIdBus());
				for(Programacion p: listaProgramacion){
					if(p.getIdBus()==valor.getIdBus()){
						Flota f = flotaServices.findById(p.getIdBus());
						p.setNumeroBus(f.getNumero().toString()); 
						p.setCantidadSalidasBus(list1.size()); 
					}
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
	}

	public void imprimirPDF() throws Exception {
		//depurador.info("imprimirCompromisoPdf ==>");
		try {

			ServletContext servletContext = (ServletContext) (FacesContext.getCurrentInstance().getExternalContext().getContext());

			
			SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");		
			String fechaInicio_ConFormato = formato.format(getFecInicio());
			String fechaFin_ConFormato = formato.format(getFecFin());
			
			Integer total_program = listaProgramacion.size();
			
			String p_logo = ExportarArchivo.getPath("/resources/img/logoittsa.jpg");
			System.out.println("logo ruta:"+ p_logo);
			
			Map<String, Object> input = new HashMap<String, Object>();
			input.put("P_RANGO_FECHAS", fechaInicio_ConFormato +" - "+ fechaFin_ConFormato);
			input.put("P_LOGO", p_logo);
			input.put("P_TOTAL_PROGRAM",total_program.toString());
			input.put(JRParameter.REPORT_LOCALE, new Locale("es"));
			input.put(JRParameter.IS_IGNORE_PAGINATION, Boolean.TRUE);

			String path = ExportarArchivo.getPath("/resources/reports/jxrml/rptProgCumplidasxFlota.jasper");
			HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();

			byte[] bytes = ExportarArchivo.exportPdf(path, input, listaProgramacion);

			ExportarArchivo.executePdf(bytes, "reporteProgramacionesFlota.pdf");
			FacesContext.getCurrentInstance().responseComplete();
		} catch (Exception e) {

			//depurador.info("ERROR ID==>" + e);
			e.printStackTrace();
		}
	}

	public void verDetalle(Programacion prog){
		this.programacion = prog;
		Date fFin = utils.sumaDias(getFecFin(),1);
		try {
			listaProgramacionDetalle = programacionServices.findProgServCumplidosxBus(getFecInicio(), fFin, prog.getIdBus());
			for(Programacion p: listaProgramacionDetalle){
				Agencia orig = agenciaServices.findById(p.getOrigen());
				p.setNombOrigen(orig.getDescripcion()); 
				Agencia dest = agenciaServices.findById(p.getDestino());
				p.setNombDestino(dest.getDescripcion());
				Flota f = flotaServices.findById(p.getIdBus());
				Piloto pil = pilotoServices.findById(f.getPiloto());
				Piloto cop = pilotoServices.findById(f.getCopiloto());
				p.setNombPiloto(pil.getApellidoPaterno()+" "+pil.getApellidoMaterno()+" "+pil.getNombres());
				p.setNombCopiloto(cop.getApellidoPaterno()+" "+cop.getApellidoMaterno()+" "+cop.getNombres());
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
	}

	
	public void imprimirPDF_Detalle() throws Exception {
		//depurador.info("imprimirCompromisoPdf ==>");
		try {

			ServletContext servletContext = (ServletContext) (FacesContext.getCurrentInstance().getExternalContext().getContext());

			
			SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");		
			String fechaInicio_ConFormato = formato.format(getFecInicio());
			String fechaFin_ConFormato = formato.format(getFecFin());
			
			Flota f = flotaServices.findById(programacion.getIdBus());
			String nroBus = f.getNumero().toString();
			
			Integer total_program = listaProgramacionDetalle.size();
			
			String p_logo = ExportarArchivo.getPath("/resources/img/logoittsa.jpg");
			System.out.println("logo ruta:"+ p_logo);
			
			Map<String, Object> input = new HashMap<String, Object>();
			input.put("P_RANGO_FECHAS", fechaInicio_ConFormato +" - "+ fechaFin_ConFormato);
			input.put("P_LOGO", p_logo);
			input.put("P_NRO_BUS",nroBus);
			input.put("P_TOTAL_PROGRAM",total_program.toString());
			input.put(JRParameter.REPORT_LOCALE, new Locale("es"));
			input.put(JRParameter.IS_IGNORE_PAGINATION, Boolean.TRUE);

			String path = ExportarArchivo.getPath("/resources/reports/jxrml/rptProgCumplidasxBus.jasper");
			HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();

			byte[] bytes = ExportarArchivo.exportPdf(path, input, listaProgramacionDetalle);

			ExportarArchivo.executePdf(bytes, "reporteProgramacionesFlota.pdf");
			FacesContext.getCurrentInstance().responseComplete();
		} catch (Exception e) {

			//depurador.info("ERROR ID==>" + e);
			e.printStackTrace();
		}
	}
	

	//**set an get 
	
	
	public Date getFecInicio() {
		return fecInicio;
	}

	public void setFecInicio(Date fecInicio) {
		this.fecInicio = fecInicio;
	}

	public Date getFecFin() {
		return fecFin;
	}

	public void setFecFin(Date fecFin) {
		this.fecFin = fecFin;
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

	public List<Flota> getListaBuses() {
		return listaBuses;
	}

	public void setListaBuses(List<Flota> listaBuses) {
		this.listaBuses = listaBuses;
	}

	public List<Programacion> getListaProgramacion() {
		return listaProgramacion;
	}

	public void setListaProgramacion(List<Programacion> listaProgramacion) {
		this.listaProgramacion = listaProgramacion;
	}

	public List<Programacion> getListaProgramacionFilter() {
		return listaProgramacionFilter;
	}

	public void setListaProgramacionFilter(
			List<Programacion> listaProgramacionFilter) {
		this.listaProgramacionFilter = listaProgramacionFilter;
	}

	public boolean isBandImprimir() {
		return bandImprimir;
	}

	public void setBandImprimir(boolean bandImprimir) {
		this.bandImprimir = bandImprimir;
	}

	public HashMap getMapProgramacion() {
		return mapProgramacion;
	}

	public void setMapProgramacion(HashMap mapProgramacion) {
		this.mapProgramacion = mapProgramacion;
	}

	public Programacion getProgramacion() {
		return programacion;
	}

	public void setProgramacion(Programacion programacion) {
		this.programacion = programacion;
	}

	public List<Programacion> getListaProgramacionDetalle() {
		return listaProgramacionDetalle;
	}

	public void setListaProgramacionDetalle(
			List<Programacion> listaProgramacionDetalle) {
		this.listaProgramacionDetalle = listaProgramacionDetalle;
	}
	
	
}

