package com.certicom.ittsa.managedBeans;

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

import com.certicom.ittsa.domain.Flota;
import com.certicom.ittsa.domain.SalidaAutoparte;
import com.certicom.ittsa.services.FlotaService;
import com.certicom.ittsa.services.SalidaAutoparteService;
import com.pe.certicom.ittsa.commons.ExportarArchivo;

@ManagedBean(name="rptCostosxBusMB")
@ViewScoped
public class RptCostosxBusMB {
	
	public List<SalidaAutoparte> listaSalidas;
	public List<SalidaAutoparte> listaSalidasFilter;
	public List<Flota> listaFlotas;
	//filtros de busqueda
	public Date fecIni;
	public Date fecFin;
	public Integer nroBus;
	public Double total = 0.0;
	
	private SalidaAutoparteService salidaAutoparteService;
	private FlotaService flotaService;
	
	@PostConstruct
	public void inicio(){
		
		this.listaSalidas = new ArrayList<SalidaAutoparte>();
		this.salidaAutoparteService = new SalidaAutoparteService();
		this.flotaService = new FlotaService();
		
		try {
			this.listaFlotas = this.flotaService.findAll2();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void listarCostosxBus(){
		try {
			this.total = 0.00;
			this.listaSalidas = this.salidaAutoparteService.listarCostosxBus(getFecIni(), getFecIni(), getNroBus());
			
			for (int i = 0; i < listaSalidas.size(); i++) {
				this.total += listaSalidas.get(i).getSubtotal();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public void imprimirPDF(){
		try {
			ServletContext servletContext = (ServletContext) (FacesContext.getCurrentInstance().getExternalContext().getContext());
			
			SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");		
			String desde = "",hasta = "";
				if(this.fecIni!=null) desde =formato.format(this.fecIni);
				if(this.fecFin!=null)  hasta = formato.format(this.fecFin);
			
			String p_logo = ExportarArchivo.getPath("/resources/img/logoittsa.jpg");
			System.out.println("logo ruta:"+ p_logo);
			
			Map<String, Object> input =new  HashMap<String,Object>();
			input.put("P_LOGO", p_logo);
			input.put("P_FEC_DESDE", desde);
			input.put("P_FEC_HASTA", hasta);
			input.put("P_NROBUS", getNroBus());
			input.put("P_TOTAL", this.listaSalidas.size());
			input.put(JRParameter.REPORT_LOCALE, new Locale("es"));
			//input.put(JRParameter.IS_IGNORE_PAGINATION, Boolean.TRUE); // no parte la pagina todo lo mete en un A4
			
			String path = ExportarArchivo.getPath("/resources/reports/jxrml/rptCostoMantxBus.jasper");
			HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
			
			
			byte[] bytes;
				bytes = ExportarArchivo.exportPdf(path, input, this.listaSalidas);
			ExportarArchivo.executePdf(bytes, "CostoMantxBus.pdf");
			FacesContext.getCurrentInstance().responseComplete();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}


	public List<SalidaAutoparte> getListaSalidas() {
		return listaSalidas;
	}


	public void setListaSalidas(List<SalidaAutoparte> listaSalidas) {
		this.listaSalidas = listaSalidas;
	}


	public Date getFecIni() {
		return fecIni;
	}


	public void setFecIni(Date fecIni) {
		this.fecIni = fecIni;
	}


	public Date getFecFin() {
		return fecFin;
	}


	public void setFecFin(Date fecFin) {
		this.fecFin = fecFin;
	}


	public Integer getNroBus() {
		return nroBus;
	}


	public void setNroBus(Integer nroBus) {
		this.nroBus = nroBus;
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

	public List<Flota> getListaFlotas() {
		return listaFlotas;
	}

	public void setListaFlotas(List<Flota> listaFlotas) {
		this.listaFlotas = listaFlotas;
	}

	public List<SalidaAutoparte> getListaSalidasFilter() {
		return listaSalidasFilter;
	}

	public void setListaSalidasFilter(List<SalidaAutoparte> listaSalidasFilter) {
		this.listaSalidasFilter = listaSalidasFilter;
	}
	
	

}
