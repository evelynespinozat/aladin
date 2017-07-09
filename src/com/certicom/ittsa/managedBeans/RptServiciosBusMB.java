package com.certicom.ittsa.managedBeans;

import java.io.Serializable;
import java.text.SimpleDateFormat;
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

import com.certicom.ittsa.domain.Agencia;
import com.certicom.ittsa.domain.Flota;
import com.certicom.ittsa.domain.Log;
import com.certicom.ittsa.domain.Piloto;
import com.certicom.ittsa.domain.Programacion;
import com.certicom.ittsa.domain.Tripulacion;
import com.certicom.ittsa.services.AgenciaService;
import com.certicom.ittsa.services.FlotaService;
import com.certicom.ittsa.services.MenuServices;
import com.certicom.ittsa.services.PilotoService;
import com.certicom.ittsa.services.ProgramacionService;
import com.certicom.ittsa.services.TripulacionServices;
import com.pe.certicom.ittsa.commons.ExportarArchivo;
import com.pe.certicom.ittsa.commons.Constante;
import com.pe.certicom.ittsa.commons.GenericBeans;
import com.pe.certicom.ittsa.commons.Utiles;

@ManagedBean(name="rptServiciosBusMB")
@ViewScoped
public class RptServiciosBusMB extends GenericBeans implements Serializable{

	private List<Flota> listaBuses;
	private List<Programacion> listaProgramacion;
	private List<Programacion> listaProgramacionFilter;
	private Date fecInicio;
	private Date fecFin;
	private Integer idBus;
	private boolean bandImprimir;
	
	private MenuServices menuServices;
	private FlotaService flotaServices;
	private ProgramacionService programacionServices;
	private AgenciaService agenciaServices;
	private PilotoService pilotoServices;
	private TripulacionServices triulacionService;
	//datos Log
    private Log log;
	private LogMB logmb;
	
	public RptServiciosBusMB(){;}
	
	@PostConstruct
	public void incia()
	{
		this.menuServices = new MenuServices();
		this.flotaServices = new FlotaService();
		this.programacionServices = new ProgramacionService();
		this.agenciaServices = new AgenciaService();
		this.pilotoServices = new PilotoService();
		this.triulacionService = new TripulacionServices();
		this.fecInicio = Utiles.obtenerPrimerDiaDelMes();
		this.fecFin = Utiles.obtenerUltimoDiaDelMes();
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
	}
	
	public void buscarServicios(){
		System.out.println("F. Inicio: "+ getFecInicio());
		System.out.println("F. Fin: "+ getFecFin());
		System.out.println("Id Bus: "+ getIdBus());
			
		try {
			listaProgramacion = this.idBus.intValue() == 0 ? programacionServices.findProgServCumplidosTodos(getFecInicio(), getFecFin())  :programacionServices.findProgServCumplidosxBus(getFecInicio(), getFecFin(),getIdBus()); 
			for(Programacion p: listaProgramacion)
			{
				Agencia orig = agenciaServices.findById(p.getOrigen());
				p.setNombOrigen(orig.getDescripcion()); 
				Agencia dest = agenciaServices.findById(p.getDestino());
				p.setNombDestino(dest.getDescripcion());
				
				Tripulacion tripu = this.triulacionService.findByIdProgramacion(p.getIdProgramacion());
				Piloto pil = null;
				Piloto cop = null;
				Flota f = flotaServices.findById(p.getIdBus());
				p.setNumeroBus(f.getNumero().toString()); 
				if(tripu.getFlagTempPiloto() && tripu.getFlagTempCopiloto())
				{
					pil = pilotoServices.findById(tripu.getIdPiloto());
					cop = pilotoServices.findById(tripu.getIdCopiloto());
					
				}else if ( tripu.getFlagTempPiloto() && !tripu.getFlagTempCopiloto() ){
					
					pil = pilotoServices.findById(tripu.getIdPiloto());
					cop = pilotoServices.findById(f.getCopiloto());
					
				}else if(!tripu.getFlagTempPiloto() && tripu.getFlagTempCopiloto() ){
					
					pil = pilotoServices.findById(f.getPiloto());
					cop = pilotoServices.findById(tripu.getIdCopiloto());
					
				}else if(!tripu.getFlagTempPiloto() && !tripu.getFlagTempCopiloto() ){
					pil = pilotoServices.findById(f.getPiloto());
					cop = pilotoServices.findById(f.getCopiloto());
				}
				
				p.setNombPiloto(pil.getApellidoPaterno()+" "+pil.getApellidoMaterno()+" "+pil.getNombres());
				p.setNombCopiloto(cop.getApellidoPaterno()+" "+cop.getApellidoMaterno()+" "+cop.getNombres());
				//System.out.println("copiloto:"+p.getNombCopiloto());
				
			}
			this.bandImprimir = Boolean.TRUE;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public void imprimirPDF() throws Exception {
		//depurador.info("imprimirCompromisoPdf ==>");
		try {

			ServletContext servletContext = (ServletContext) (FacesContext.getCurrentInstance().getExternalContext().getContext());

			
			SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");		
			String fechaInicio_ConFormato = formato.format(getFecInicio());
			String fechaFin_ConFormato = formato.format(getFecFin());
			
			//Flota f = flotaServices.findById(getIdBus());
			//String nroBus = f.getNumero().toString();
			
			Integer total_program = listaProgramacion.size();
			
			String p_logo = ExportarArchivo.getPath("/resources/img/logoittsa.jpg");
			System.out.println("logo ruta:"+ p_logo);
			
			Map<String, Object> input = new HashMap<String, Object>();
			input.put("P_RANGO_FECHAS", fechaInicio_ConFormato +" - "+ fechaFin_ConFormato);
			input.put("P_LOGO", p_logo);
			//input.put("P_NRO_BUS",nroBus);
			input.put("P_TOTAL_PROGRAM",total_program.toString());
			input.put(JRParameter.REPORT_LOCALE, new Locale("es"));
			input.put(JRParameter.IS_IGNORE_PAGINATION, Boolean.TRUE);

			String path = ExportarArchivo.getPath("/resources/reports/jxrml/rptProgCumplidasxBus.jasper");
			HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();

			byte[] bytes = ExportarArchivo.exportPdf(path, input, listaProgramacion);

			ExportarArchivo.executePdf(bytes, "reporteProgramaciones.pdf");
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

	public Integer getIdBus() {
		return idBus;
	}

	public void setIdBus(Integer idBus) {
		this.idBus = idBus;
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
	
	
}

