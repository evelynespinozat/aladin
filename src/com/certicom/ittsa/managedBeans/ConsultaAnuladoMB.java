package com.certicom.ittsa.managedBeans;

import java.io.Serializable;
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
import javax.print.DocFlavor.STRING;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRParameter;

import org.primefaces.context.RequestContext;

import com.certicom.ittsa.domain.Agencia;
import com.certicom.ittsa.domain.Boleto;
import com.certicom.ittsa.domain.Log;
import com.certicom.ittsa.domain.Oficina;
import com.certicom.ittsa.domain.Programacion;
import com.certicom.ittsa.domain.TrackingBoleto;
import com.certicom.ittsa.services.AgenciaService;
import com.certicom.ittsa.services.MenuServices;
import com.certicom.ittsa.services.OficinaService;
import com.certicom.ittsa.services.TrackingBoletoService;
import com.pe.certicom.ittsa.commons.Constante;
import com.pe.certicom.ittsa.commons.ExportarArchivo;
import com.pe.certicom.ittsa.commons.FacesUtils;
import com.pe.certicom.ittsa.commons.GenericBeans;
import com.pe.certicom.ittsa.commons.Utils;

@ManagedBean(name="boletosAnuladoMB")
@ViewScoped
public class ConsultaAnuladoMB extends GenericBeans implements Serializable{

	private TrackingBoleto trackingBoletoFilter;
	private List<Agencia> listaAgenicias;
	private List<Oficina> listaOficinas;
	private Date fechaInicio;
	private Date fechaFin;
	private List<TrackingBoleto> listaBoletosAnulados;
	private List<TrackingBoleto> listaFiltroBoletosAnulados;
	
	private boolean btnImprimir = true;
	
	//services
	private MenuServices menuServices;
	private AgenciaService agenciaService;
	private OficinaService oficinaService;
	private TrackingBoletoService trackingBoletoService;
	
	//datos Log
    private Log log;
	private LogMB logmb;
	private Utils utils;
	
	public ConsultaAnuladoMB(){;}
	
	@PostConstruct
	public void incia()
	{
		this.trackingBoletoFilter = new TrackingBoleto();
		this.listaBoletosAnulados = new ArrayList<>();
		
		this.menuServices = new MenuServices();
		this.agenciaService = new AgenciaService();
		this.oficinaService = new OficinaService();
		this.trackingBoletoService = new TrackingBoletoService();
		
		this.log = (Log)getSpringBean(Constante.SESSION_LOG);
		this.logmb = new LogMB();
		this.utils = new Utils();
		//listando
		try {
			int codMenu = menuServices.opcionMenuByPretty("pretty:agencia");
			log.setCod_menu(codMenu);
			
			this.listaAgenicias = this.agenciaService.listaAgenciasActivas();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//imprimir 
	public void imprimirPDF(){
		try {
			ServletContext servletContext = (ServletContext) (FacesContext.getCurrentInstance().getExternalContext().getContext());
			String desOficina = "";
			String desAgencia = "";
			
			for(int i=0; i<this.listaAgenicias.size(); i++){
				Agencia agencia = this.listaAgenicias.get(i);
				if(agencia.getIdagencia() == this.trackingBoletoFilter.getIdAgencia()){
					desAgencia = agencia.getDescripcion();
				}
			}
			
			for(int j=0; j<this.listaOficinas.size(); j++){
				Oficina oficina = this.listaOficinas.get(j);
				if(oficina.getIdOficina() == this.trackingBoletoFilter.getIdOficina()){
					desOficina = oficina.getNombre();
				}
			}
			
			String p_logo = ExportarArchivo.getPath("/resources/img/logoittsa.jpg");
			Map<String, Object> input =new  HashMap<String,Object>();
			input.put("P_LOGO", p_logo);
			input.put("P_FECH_INICIO", this.fechaInicio);
			input.put("P_FECH_FIN", this.fechaFin);
			input.put("P_AGENCIA", desAgencia);
			input.put("P_OFICINA",  desOficina);
			input.put(JRParameter.REPORT_LOCALE, new Locale("es"));
			//input.put(JRParameter.IS_IGNORE_PAGINATION, Boolean.TRUE); // no parte la pagina todo lo mete en un A4
			
			String path = ExportarArchivo.getPath("/resources/reports/jxrml/rptBoletosAnulados.jasper");
			HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
			
			
			byte[] bytes;
				bytes = ExportarArchivo.exportPdf(path, input, this.listaBoletosAnulados);
			ExportarArchivo.executePdf(bytes, "ReporteBoletosAnulados.pdf");
			FacesContext.getCurrentInstance().responseComplete();
			} catch (Exception e) {
				e.printStackTrace();
			}
	}
	
	public void listarOficinas(){
		try {
			this.listaOficinas = this.oficinaService.getOficinasxAgencia(this.trackingBoletoFilter.getIdAgencia());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void buscarBoletos(){
		
		try {
			
			
			Date fFin = utils.sumaDias(getFechaFin(),1);
			this.listaBoletosAnulados = this.trackingBoletoService.getTicketsCanceled(this.trackingBoletoFilter.getIdOficina(), this.fechaInicio, fFin);
			System.out.println("el tamaño de lista de boletos es " + this.listaBoletosAnulados.size());
			if(this.listaBoletosAnulados.size()>0){
				this.btnImprimir = Boolean.FALSE;
			} else{
				this.btnImprimir = Boolean.TRUE;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//**set an get 
	
	public MenuServices getMenuServices() {
		return menuServices;
	}

	public Date getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public Date getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}

	public List<Agencia> getListaAgenicias() {
		return listaAgenicias;
	}

	public void setListaAgenicias(List<Agencia> listaAgenicias) {
		this.listaAgenicias = listaAgenicias;
	}

	public List<Oficina> getListaOficinas() {
		return listaOficinas;
	}

	public void setListaOficinas(List<Oficina> listaOficinas) {
		this.listaOficinas = listaOficinas;
	}

	public TrackingBoleto getTrackingBoletoFilter() {
		return trackingBoletoFilter;
	}

	public void setTrackingBoletoFilter(TrackingBoleto trackingBoletoFilter) {
		this.trackingBoletoFilter = trackingBoletoFilter;
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

	public List<TrackingBoleto> getListaBoletosAnulados() {
		return listaBoletosAnulados;
	}

	public void setListaBoletosAnulados(List<TrackingBoleto> listaBoletosAnulados) {
		this.listaBoletosAnulados = listaBoletosAnulados;
	}

	public List<TrackingBoleto> getListaFiltroBoletosAnulados() {
		return listaFiltroBoletosAnulados;
	}

	public void setListaFiltroBoletosAnulados(
			List<TrackingBoleto> listaFiltroBoletosAnulados) {
		this.listaFiltroBoletosAnulados = listaFiltroBoletosAnulados;
	}

	public boolean isBtnImprimir() {
		return btnImprimir;
	}

	public void setBtnImprimir(boolean btnImprimir) {
		this.btnImprimir = btnImprimir;
	}
	
	

	
}

