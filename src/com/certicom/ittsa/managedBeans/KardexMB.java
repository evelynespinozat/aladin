package com.certicom.ittsa.managedBeans;

import java.text.SimpleDateFormat;
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

import org.primefaces.context.RequestContext;

import com.certicom.ittsa.domain.Agencia;
import com.certicom.ittsa.domain.AgenciaProducto;
import com.certicom.ittsa.domain.Almacen;
import com.certicom.ittsa.domain.Kardex;
import com.certicom.ittsa.domain.Log;
import com.certicom.ittsa.domain.Oficina;
import com.certicom.ittsa.domain.Producto;
import com.certicom.ittsa.domain.SalidaAutoparte;
import com.certicom.ittsa.services.AgenciaProductoService;
import com.certicom.ittsa.services.AgenciaService;
import com.certicom.ittsa.services.AlmacenService;
import com.certicom.ittsa.services.KardexService;
import com.certicom.ittsa.services.MenuServices;
import com.certicom.ittsa.services.OficinaService;
import com.pe.certicom.ittsa.commons.Constante;
import com.pe.certicom.ittsa.commons.ExportarArchivo;
import com.pe.certicom.ittsa.commons.FacesUtils;
import com.pe.certicom.ittsa.commons.GenericBeans;

@ManagedBean(name="kardexMB")
@ViewScoped
public class KardexMB extends GenericBeans {
	
	//bean principal
	private AgenciaProducto agenciaProducto;
	private List<Kardex> kardexConsolidado;
	private KardexService kardexService;
	private Integer cantiTotalProd; 
	
	private MenuServices menuServices;
	
	
	private List<Kardex> listaKardex;
	
	private Integer stockActual =0; 
	private Date fecInicio;
	private Date fecFin;
	
	//datos Log
    private Log log;
	private LogMB logmb;
	
	@PostConstruct
	public void inicia(){

		this.agenciaProducto = new AgenciaProducto();
		this.fecInicio = new Date();
		this.fecFin = new Date();
		Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
		this.agenciaProducto =(AgenciaProducto) flash.get("agenciaProducto");
		this.kardexConsolidado = new ArrayList<>();
		this.menuServices = new MenuServices();
		this.kardexService = new KardexService();
		
		log = (Log)getSpringBean(Constante.SESSION_LOG);
		logmb = new LogMB();
		
		try {
			int codMenu = menuServices.opcionMenuByPretty("pretty:kardex");
			log.setCod_menu(codMenu);
			this.listaKardex = this.kardexService.listarKardexporProducto(this.agenciaProducto);
			calcularStockActual();
			
		} catch (Exception e) {
			System.out.println("Error :"+ e.getMessage());
			e.printStackTrace();
		}
		

	}
	
	public void calcularStockActual(){		
		Integer Ing = 0,sal =0;
		
		for (int i = 0; i < this.listaKardex.size(); i++) {			
				Kardex k = this.listaKardex.get(i);
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
			
			this.kardexConsolidado = this.kardexService.obtenerListaProductosxFecha(this.fecInicio, cal.getTime(), this.agenciaProducto.getIdProducto());	
		    this.cantiTotalProd = 0;
			for(int i=0; i<this.kardexConsolidado.size(); i++){
				Kardex kar = this.kardexConsolidado.get(i);
				this.cantiTotalProd += 	kar.getCantConsumida();
			}
			  
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	// 24/03/2014
	public void imprimirDetalle(){
		try {
			ServletContext servletContext = (ServletContext) (FacesContext.getCurrentInstance().getExternalContext().getContext());
			
			Integer total = this.listaKardex.size();
			
			String p_logo = ExportarArchivo.getPath("/resources/img/logoittsa.jpg");
			System.out.println("logo ruta:"+ p_logo);
			
			Map<String, Object> input =new  HashMap<String,Object>();
			input.put("P_LOGO", p_logo);
			input.put("P_PRODUCTO", this.agenciaProducto.getDesProducto());
			input.put("P_AGENCIA", this.agenciaProducto.getDesAgencia());
			input.put("P_OFICINA", this.agenciaProducto.getDesOficina());
			input.put("P_MEDIDA", this.agenciaProducto.getDesMedida());
			input.put("P_STOCK", getStockActual().toString());
			input.put("P_TOTAL", total.toString());
			input.put(JRParameter.REPORT_LOCALE, new Locale("es"));
			
			String path = ExportarArchivo.getPath("/resources/reports/jxrml/rptKardexDet.jasper");
			HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
			
			
			byte[] bytes;
				bytes = ExportarArchivo.exportPdf(path, input, listaKardex);
			ExportarArchivo.executePdf(bytes, "reporteKardexDet.pdf");
			FacesContext.getCurrentInstance().responseComplete();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	public void imprimirConsolidado(){
		try {
			ServletContext servletContext = (ServletContext) (FacesContext.getCurrentInstance().getExternalContext().getContext());

			SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");		
			String fini = formato.format(getFecInicio());
			String ffin = formato.format(getFecFin());
			
			String p_logo = ExportarArchivo.getPath("/resources/img/logoittsa.jpg");
			System.out.println("logo ruta:"+ p_logo);
			
			Map<String, Object> input =new  HashMap<String,Object>();
			input.put("P_LOGO", p_logo);
			input.put("P_PRODUCTO", this.agenciaProducto.getDesProducto());
			input.put("P_MEDIDA", this.agenciaProducto.getDesMedida());
			input.put("P_FINI", fini);
			input.put("P_FFIN", ffin);
			input.put("P_TOTAL", this.cantiTotalProd.toString());
			input.put(JRParameter.REPORT_LOCALE, new Locale("es"));
			
			String path = ExportarArchivo.getPath("/resources/reports/jxrml/rptKardexCon.jasper");
			HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
			
			
			byte[] bytes;
				bytes = ExportarArchivo.exportPdf(path, input, kardexConsolidado);
			ExportarArchivo.executePdf(bytes, "reporteKardexCon.pdf");
			FacesContext.getCurrentInstance().responseComplete();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	

	/*#######################---getters y setters----------########################*/

	

	public AgenciaProducto getAgenciaProducto() {
		return agenciaProducto;
	}

	public Integer getCantiTotalProd() {
		return cantiTotalProd;
	}

	public void setCantiTotalProd(Integer cantiTotalProd) {
		this.cantiTotalProd = cantiTotalProd;
	}

	public List<Kardex> getKardexConsolidado() {
		return kardexConsolidado;
	}

	public void setKardexConsolidado(List<Kardex> kardexConsolidado) {
		this.kardexConsolidado = kardexConsolidado;
	}

	public List<Kardex> getListaKardex() {
		return listaKardex;
	}

	public void setAgenciaProducto(AgenciaProducto agenciaProducto) {
		this.agenciaProducto = agenciaProducto;
	}

	public void setListaKardex(List<Kardex> listaKardex) {
		this.listaKardex = listaKardex;
	}

	public Integer getStockActual() {
		return stockActual;
	}

	public void setStockActual(Integer stockActual) {
		this.stockActual = stockActual;
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
