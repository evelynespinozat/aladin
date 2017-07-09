package com.certicom.ittsa.managedBeans;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
import com.certicom.ittsa.domain.AutoparteAutomotor;
import com.certicom.ittsa.domain.ConsumoCombustible;
import com.certicom.ittsa.domain.Destino;
import com.certicom.ittsa.domain.Flota;
import com.certicom.ittsa.domain.FlotaAutomotor;
import com.certicom.ittsa.domain.FlotaAutoparte;
import com.certicom.ittsa.domain.Log;
import com.certicom.ittsa.domain.Automotor;
import com.certicom.ittsa.domain.Menu;
import com.certicom.ittsa.domain.Programacion;
import com.certicom.ittsa.domain.Sistema;
import com.certicom.ittsa.services.AgenciaService;
import com.certicom.ittsa.services.AutopartesAutomotorService;
import com.certicom.ittsa.services.ConsumoCombustibleService;
import com.certicom.ittsa.services.DestinoServices;
import com.certicom.ittsa.services.FlotaAutomotorService;
import com.certicom.ittsa.services.FlotaAutoparteService;
import com.certicom.ittsa.services.FlotaService;
import com.certicom.ittsa.services.MenuServices;
import com.certicom.ittsa.services.ProgramacionService;
import com.pe.certicom.ittsa.commons.Constante;
import com.pe.certicom.ittsa.commons.ExportarArchivo;
import com.pe.certicom.ittsa.commons.FacesUtils;
import com.pe.certicom.ittsa.commons.GenericBeans;

@ManagedBean(name="consolidadoMB")
@ViewScoped
public class ConsolidadoMB extends GenericBeans {
	
	private static final List<?> listaFilterCombustibl = null;
	//bean principal
	private Flota flota;
	private Date fecInicio;
	private Date fecFin;
	private List<ConsumoCombustible> listaCombustible;
	private List<ConsumoCombustible> listaFilterCombustible;
	private double sumaRecorrido;
	private double sumaConsumo;
	private double promedioKmGal;
	private double costoTotal;
	
	private ConsumoCombustibleService consumoCombustibleService;
	private MenuServices menuServices;
	
	private Date fechaInicioMes ;
	private Date fechaFinMes ;
	
	//datos Log
    private Log log;
	private LogMB logmb;
	
	@PostConstruct
	public void inicia(){
		
		Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
		this.flota =(Flota) flash.get("flota");
		this.consumoCombustibleService = new ConsumoCombustibleService();
		this.fecInicio = new Date();
		this.fecFin = new Date();
		/*fin temporal */
		
		//SERVICES
		this.menuServices = new MenuServices();
		
		log = (Log)getSpringBean(Constante.SESSION_LOG);
		logmb = new LogMB();
		
		mesAnioActual();
		
		try {
			
			int codMenu = menuServices.opcionMenuByPretty("pretty:flotaVehicular");
			log.setCod_menu(codMenu);
			
		} catch (Exception e) {
			System.out.println("Error :"+ e.getMessage());
			e.printStackTrace();
		}
		

	}
	
	public void mesAnioActual(){
		Date fechaActual = new Date();
		Calendar fecIniMes = Calendar.getInstance();
		fecIniMes.setTime(fechaActual);
		fecIniMes.set(Calendar.DAY_OF_MONTH, fecIniMes.getActualMinimum(Calendar.DAY_OF_MONTH));
		
		this.fecInicio = fecIniMes.getTime();
		
		Calendar fecFinMes = Calendar.getInstance();
		fecFinMes.setTime(fechaActual);
		fecFinMes.set(Calendar.DAY_OF_MONTH, fecFinMes.getActualMaximum(Calendar.DAY_OF_MONTH));
		
		this.fecFin = fecFinMes.getTime();
		
	}
	
	public void buscarConsumoCombustible(){
		try {
			this.sumaRecorrido = 0;
			this.sumaConsumo = 0;
			this.promedioKmGal = 0;
			this.listaCombustible = new ArrayList<>();
			
			this.listaCombustible = this.consumoCombustibleService.listarConsumoCombustiblexFechas(this.fecInicio, this.fecFin, this.flota.getIdBus());
			//realizando los calculos
			for(int i=0 ; i<this.listaCombustible.size(); i++){
				ConsumoCombustible csu = this.listaCombustible.get(i);
				this.sumaRecorrido += csu.getKmRecorridos();
				this.sumaConsumo += csu.getConsumo();
			    this.promedioKmGal = this.sumaRecorrido/this.sumaConsumo;
				this.costoTotal  += csu.getCostoTotal();
			}
			
			this.promedioKmGal  = Redondear(this.promedioKmGal);
			this.costoTotal = Redondear(this.costoTotal);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	  
	}
	
	public double Redondear(double numero)
	{
	      return Math.rint(numero*1000)/1000;
	}
	
	public void imprimirPDF(){
		try {
			ServletContext servletContext = (ServletContext) (FacesContext.getCurrentInstance().getExternalContext().getContext());
			
			SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");		
			String desde = "",hasta = "";
				if(this.fecInicio!=null) desde =formato.format(this.fecInicio);
				if(this.fecFin!=null)  hasta = formato.format(this.fecFin);
			
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
			input.put("P_TOTAL_RECORRIDO", this.sumaRecorrido);
			input.put("P_TOTAL_CONSUMO", this.sumaConsumo);
			input.put("P_PROM_KIMGAL", this.promedioKmGal);
			input.put("P_COSTO_TOTAL", this.costoTotal);
			input.put(JRParameter.REPORT_LOCALE, new Locale("es"));
			//input.put(JRParameter.IS_IGNORE_PAGINATION, Boolean.TRUE); // no parte la pagina todo lo mete en un A4
			
			String path = ExportarArchivo.getPath("/resources/reports/jxrml/rptConsKilometrajeBus.jasper");
			HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
			
			
			byte[] bytes;
				bytes = ExportarArchivo.exportPdf(path, input, this.listaCombustible);
			ExportarArchivo.executePdf(bytes, "ConsolidadoKilometraje.pdf");
			FacesContext.getCurrentInstance().responseComplete();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	

	
// set and get

	
	public MenuServices getMenuServices() {
		return menuServices;
	}

	public double getCostoTotal() {
		return costoTotal;
	}

	public void setCostoTotal(double costoTotal) {
		this.costoTotal = costoTotal;
	}

	public double getSumaRecorrido() {
		return sumaRecorrido;
	}

	public void setSumaRecorrido(double sumaRecorrido) {
		this.sumaRecorrido = sumaRecorrido;
	}

	public double getSumaConsumo() {
		return sumaConsumo;
	}

	public void setSumaConsumo(double sumaConsumo) {
		this.sumaConsumo = sumaConsumo;
	}

	public double getPromedioKmGal() {
		return promedioKmGal;
	}

	public void setPromedioKmGal(double promedioKmGal) {
		this.promedioKmGal = promedioKmGal;
	}

	public List<ConsumoCombustible> getListaCombustible() {
		return listaCombustible;
	}

	public void setListaCombustible(List<ConsumoCombustible> listaCombustible) {
		this.listaCombustible = listaCombustible;
	}

	public List<ConsumoCombustible> getListaFilterCombustible() {
		return listaFilterCombustible;
	}

	public void setListaFilterCombustible(
			List<ConsumoCombustible> listaFilterCombustible) {
		this.listaFilterCombustible = listaFilterCombustible;
	}

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

	public Flota getFlota() {
		return flota;
	}

	public void setFlota(Flota flota) {
		this.flota = flota;
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

	public Date getFechaInicioMes() {
		return fechaInicioMes;
	}

	public void setFechaInicioMes(Date fechaInicioMes) {
		this.fechaInicioMes = fechaInicioMes;
	}

	public Date getFechaFinMes() {
		return fechaFinMes;
	}

	public void setFechaFinMes(Date fechaFinMes) {
		this.fechaFinMes = fechaFinMes;
	}
	
	
	
	
}
