package com.certicom.ittsa.managedBeans;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
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

import com.certicom.ittsa.domain.AgenciaAutoparte;
import com.certicom.ittsa.domain.KardexAutoparte;
import com.certicom.ittsa.domain.Log;
import com.certicom.ittsa.services.KardexAutoparteService;
import com.certicom.ittsa.services.MenuServices;
import com.pe.certicom.ittsa.commons.Constante;
import com.pe.certicom.ittsa.commons.ExportarArchivo;
import com.pe.certicom.ittsa.commons.GenericBeans;

@ManagedBean(name="kardexAutoparteMB")
@ViewScoped
public class KardexAutoparteMB extends GenericBeans {
	
	//bean principal
	private AgenciaAutoparte agenciaAutoparte;
	private List<KardexAutoparte> kardexConsolidado;
	private KardexAutoparteService kardexAutoparteService;
	private Integer cantiTotalProd; 
	
	private MenuServices menuServices;
	
	
	private List<KardexAutoparte> listaKardex;
	
	private Integer stockActual =0; 
	private Date fecInicio;
	private Date fecFin;
	
	//datos Log
    private Log log;
	private LogMB logmb;
	
	@PostConstruct
	public void inicia(){

		this.agenciaAutoparte = new AgenciaAutoparte();
		this.fecInicio = new Date();
		this.fecFin = new Date();
		Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
		this.agenciaAutoparte =(AgenciaAutoparte) flash.get("agenciaAutoparte");
		this.kardexConsolidado = new ArrayList<>();
		this.menuServices = new MenuServices();
		this.kardexAutoparteService = new KardexAutoparteService();
		
		log = (Log)getSpringBean(Constante.SESSION_LOG);
		logmb = new LogMB();
		
		try {
			int codMenu = menuServices.opcionMenuByPretty("pretty:kardexAutoparte");
			log.setCod_menu(codMenu);
			this.listaKardex = this.kardexAutoparteService.listarKardexporProducto(this.agenciaAutoparte);
			calcularStockActual();
			
		} catch (Exception e) {
			System.out.println("Error :"+ e.getMessage());
			e.printStackTrace();
		}
		

	}
	
	public void calcularStockActual(){		
		Integer Ing = 0,sal =0;
		
		for (int i = 0; i < this.listaKardex.size(); i++) {			
				KardexAutoparte k = this.listaKardex.get(i);
				  Ing +=k.getIngreso()!=null?k.getIngreso():0;
				  sal += k.getSalida()!=null?k.getSalida():0;
		}
		this.stockActual =Ing - sal;
	}
	
	public void buscarProductosFecha(){
		try {
			this.kardexConsolidado = new ArrayList<>();
			//agregar un dia 
			Calendar cal = new GregorianCalendar();
			cal.setLenient(false);
			cal.setTime(this.fecFin);
			cal.add(Calendar.DAY_OF_MONTH, 1);
			System.out.println("la fecha agregada es " + cal.getTime());
			
		//	this.kardexConsolidado = this.kardexAutoparteService.obtenerListaProductosxFecha(this.fecInicio, cal.getTime(), this.agenciaAutoparte.getIdProducto());	
		    this.cantiTotalProd = 0;
			for(int i=0; i<this.kardexConsolidado.size(); i++){
				KardexAutoparte kar = this.kardexConsolidado.get(i);
				this.cantiTotalProd += 	kar.getCantConsumida();
			}
			  
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void imprimirPDF(){
		try {
			ServletContext servletContext = (ServletContext) (FacesContext.getCurrentInstance().getExternalContext().getContext());
			
			Integer total = this.listaKardex.size();
			
			String p_logo = ExportarArchivo.getPath("/resources/img/logoittsa.jpg");
			System.out.println("logo ruta:"+ p_logo);
			
			Map<String, Object> input =new  HashMap<String,Object>();
			input.put("P_LOGO", p_logo);
			input.put("P_AUTOPARTE", this.agenciaAutoparte.getDesProducto());
			input.put("P_AGENCIA", this.agenciaAutoparte.getDesAgencia());
			input.put("P_OFICINA", this.agenciaAutoparte.getDesOficina());
			input.put("P_MEDIDA", this.agenciaAutoparte.getDesMedida());
			input.put("P_STOCK", getStockActual().toString());
			input.put("P_TOTAL", total.toString());
			input.put(JRParameter.REPORT_LOCALE, new Locale("es"));
			
			String path = ExportarArchivo.getPath("/resources/reports/jxrml/rptKardexAutoparte.jasper");
			HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
			
			
			byte[] bytes;
				bytes = ExportarArchivo.exportPdf(path, input, listaKardex);
			ExportarArchivo.executePdf(bytes, "KardexAutoparte.pdf");
			FacesContext.getCurrentInstance().responseComplete();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	

	public AgenciaAutoparte getAgenciaAutoparte() {
		return agenciaAutoparte;
	}

	public List<KardexAutoparte> getKardexConsolidado() {
		return kardexConsolidado;
	}

	public Integer getCantiTotalProd() {
		return cantiTotalProd;
	}

	public List<KardexAutoparte> getListaKardex() {
		return listaKardex;
	}

	public Integer getStockActual() {
		return stockActual;
	}

	public Date getFecInicio() {
		return fecInicio;
	}

	public Date getFecFin() {
		return fecFin;
	}

	public void setAgenciaAutoparte(AgenciaAutoparte agenciaAutoparte) {
		this.agenciaAutoparte = agenciaAutoparte;
	}

	public void setKardexConsolidado(List<KardexAutoparte> kardexConsolidado) {
		this.kardexConsolidado = kardexConsolidado;
	}

	public void setCantiTotalProd(Integer cantiTotalProd) {
		this.cantiTotalProd = cantiTotalProd;
	}

	public void setListaKardex(List<KardexAutoparte> listaKardex) {
		this.listaKardex = listaKardex;
	}

	public void setStockActual(Integer stockActual) {
		this.stockActual = stockActual;
	}

	public void setFecInicio(Date fecInicio) {
		this.fecInicio = fecInicio;
	}

	public void setFecFin(Date fecFin) {
		this.fecFin = fecFin;
	}
	

	/*#######################---getters y setters----------########################*/

	
	
}
