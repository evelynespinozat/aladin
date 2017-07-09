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

import com.certicom.ittsa.domain.Agencia;
import com.certicom.ittsa.domain.Almacen;
import com.certicom.ittsa.domain.DetalleEncomienda;
import com.certicom.ittsa.domain.Encomienda;
import com.certicom.ittsa.domain.Liquidacion_Venta;
import com.certicom.ittsa.domain.Oficina;
import com.certicom.ittsa.services.AgenciaService;
import com.certicom.ittsa.services.AlmacenService;
import com.certicom.ittsa.services.DetalleEncomiendaServices;
import com.certicom.ittsa.services.EncomiendaServices;
import com.certicom.ittsa.services.Liquidacion_VentaServices;
import com.certicom.ittsa.services.OficinaService;
import com.pe.certicom.ittsa.commons.ExportarArchivo;

@ManagedBean(name = "liquidacionDetalladaMB")
@ViewScoped
public class LiquidacionDetalladaMB {
	
	private Liquidacion_Venta filter;
	
	private List<Agencia> listaAgencias;
	private List<Oficina> listaOficinas;
	
	private List<Liquidacion_Venta> listaLiqVenta;
	private List<Liquidacion_Venta> listaLiqVentaFilter;
	
	
	private AgenciaService agenciaService;
	private OficinaService oficinaService;
	private Liquidacion_VentaServices liquidacion_VentaServices;
	
	@PostConstruct
	public void init(){
		
		this.filter = new Liquidacion_Venta();
		this.listaLiqVenta = new ArrayList<>();
		
		this.agenciaService = new AgenciaService();
		this.oficinaService = new OficinaService();
		this.liquidacion_VentaServices = new Liquidacion_VentaServices();
		try {
			this.listaAgencias = agenciaService.listaAgenciasActivas();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}


	public void consultarLiquidacion(){
		try {
			this.listaLiqVenta = this.liquidacion_VentaServices.rptReporteMecanizado(this.filter);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public void getOficinasxAgencia(){
		try {
			this.listaOficinas = this.oficinaService.getOficinasxAgencia(this.filter.getIdAgencia());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
	



	public List<Agencia> getListaAgencias() {
		return listaAgencias;
	}

	public void setListaAgencias(List<Agencia> listaAgencias) {
		this.listaAgencias = listaAgencias;
	}



	public List<Oficina> getListaOficinas() {
		return listaOficinas;
	}


	public void setListaOficinas(List<Oficina> listaOficinas) {
		this.listaOficinas = listaOficinas;
	}


	public void setFilter(Liquidacion_Venta filter) {
		this.filter = filter;
	}


	public List<Liquidacion_Venta> getListaLiqVenta() {
		return listaLiqVenta;
	}


	public void setListaLiqVenta(List<Liquidacion_Venta> listaLiqVenta) {
		this.listaLiqVenta = listaLiqVenta;
	}


	public List<Liquidacion_Venta> getListaLiqVentaFilter() {
		return listaLiqVentaFilter;
	}


	public void setListaLiqVentaFilter(List<Liquidacion_Venta> listaLiqVentaFilter) {
		this.listaLiqVentaFilter = listaLiqVentaFilter;
	}


	public Liquidacion_Venta getFilter() {
		return filter;
	}

	
	
	
	
	
}
