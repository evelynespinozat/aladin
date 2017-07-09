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
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRParameter;

import com.certicom.ittsa.domain.Liquidacion_Venta;
import com.certicom.ittsa.domain.Log;
import com.certicom.ittsa.domain.Programacion;
import com.certicom.ittsa.services.MenuServices;
import com.certicom.ittsa.services.ProgramacionService;
import com.pe.certicom.ittsa.commons.Constante;
import com.pe.certicom.ittsa.commons.ExportarArchivo;
import com.pe.certicom.ittsa.commons.GenericBeans;

@ManagedBean(name="rptSalidaRetornoMB")
@ViewScoped
public class RptSalidaRetornoMB extends GenericBeans implements Serializable{
	
	/**Declaración de Variables**/
	private MenuServices menuServices;
	private ProgramacionService programacionService;
	
	private Programacion programacionFilter;
	private List<Programacion> listaProgramacionSalida;
	private List<Programacion> listaProgramacionRetorno;
	
	//datos Log
    private Log log;
	private LogMB logmb;
	
	@PostConstruct
	public void incia()
	{
		this.programacionFilter = new Programacion();
		this.programacionFilter.setfLiquidacion(new Date());
		this.listaProgramacionSalida = new ArrayList<>();
		this.listaProgramacionRetorno = new ArrayList<>();
		this.menuServices=new MenuServices();
		this.programacionService = new ProgramacionService();
		
		this.listaProgramacionSalida = new ArrayList<>();
		this.listaProgramacionRetorno = new ArrayList<>();
		
		 log = (Log)getSpringBean(Constante.SESSION_LOG);
		logmb = new LogMB();
		//listando
		try {
			int codMenu = menuServices.opcionMenuByPretty("pretty:agencia");
			log.setCod_menu(codMenu);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void listarSalidas()
	{
		try {
			
			this.listaProgramacionSalida.clear();
			if(this.programacionFilter.getRuta().equals("2"))//trujillo-lima
			{
				this.listaProgramacionSalida = this.programacionService.trujilloLimaSalida(this.programacionFilter.getfLiquidacion());
			}else{ //trujillo norte
				
				this.listaProgramacionSalida = this.programacionService.trujilloNorteSalida(this.programacionFilter.getfLiquidacion());
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void listarRetornos(){
		try {
			this.listaProgramacionRetorno.clear();
			
			if(this.programacionFilter.getRuta().equals("2"))//trujillo-lima
			{
				this.listaProgramacionRetorno = this.programacionService.trujilloLimaRetorno(this.programacionFilter.getfLiquidacion());
			}else{ //trujillo norte
				
				this.listaProgramacionRetorno = this.programacionService.trujilloNorteRetorno(this.programacionFilter.getfLiquidacion());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public void imprimirPDF(){
		try {
			ServletContext servletContext = (ServletContext) (FacesContext.getCurrentInstance().getExternalContext().getContext());
			
//			SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");		
//			String fecha = formato.format(new Date());
			List<Programacion> rptLista = new ArrayList<Programacion>();
			Programacion program = new Programacion();
			program.setListaSalida(this.listaProgramacionSalida);
			program.setListaRetorno(this.listaProgramacionRetorno);
			
			rptLista.add(program);
			
			String p_logo = ExportarArchivo.getPath("/resources/img/logoittsa.jpg");
			Map<String, Object> input =new  HashMap<String,Object>();
			input.put("P_LOGO", p_logo);
			input.put("P_FECHFILTRO", this.programacionFilter.getfLiquidacion());
			input.put(JRParameter.REPORT_LOCALE, new Locale("es"));
			//input.put(JRParameter.IS_IGNORE_PAGINATION, Boolean.TRUE); // no parte la pagina todo lo mete en un A4
			
			String path = ExportarArchivo.getPath("/resources/reports/jxrml/rptSalidaRetorno.jasper");
			HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
			
			
			byte[] bytes;
				bytes = ExportarArchivo.exportPdf(path, input, rptLista);
			ExportarArchivo.executePdf(bytes, "ReporteSalidaRetorno.pdf");
			FacesContext.getCurrentInstance().responseComplete();
			} catch (Exception e) {
				e.printStackTrace();
			}
	}
	
	/* -------------- SET AND GET ---------------- */

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

	public Programacion getProgramacionFilter() {
		return programacionFilter;
	}

	public void setProgramacionFilter(Programacion programacionFilter) {
		this.programacionFilter = programacionFilter;
	}

	public List<Programacion> getListaProgramacionSalida() {
		return listaProgramacionSalida;
	}

	public void setListaProgramacionSalida(
			List<Programacion> listaProgramacionSalida) {
		this.listaProgramacionSalida = listaProgramacionSalida;
	}

	public List<Programacion> getListaProgramacionRetorno() {
		return listaProgramacionRetorno;
	}

	public void setListaProgramacionRetorno(
			List<Programacion> listaProgramacionRetorno) {
		this.listaProgramacionRetorno = listaProgramacionRetorno;
	}

}