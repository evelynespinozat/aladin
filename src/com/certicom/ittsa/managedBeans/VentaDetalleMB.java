package com.certicom.ittsa.managedBeans;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRParameter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import com.certicom.ittsa.domain.Agencia;
import com.certicom.ittsa.domain.Detalle_Liquidacion;
import com.certicom.ittsa.domain.Flota;
import com.certicom.ittsa.domain.Liquidacion;
import com.certicom.ittsa.domain.Oficina;
import com.certicom.ittsa.domain.Programacion;
import com.certicom.ittsa.domain.PuntoVenta;
import com.certicom.ittsa.domain.Usuario;
import com.certicom.ittsa.domain.VentaDetalle;
import com.certicom.ittsa.services.AgenciaService;
import com.certicom.ittsa.services.AsientoVentaServices;
import com.certicom.ittsa.services.OficinaService;
import com.certicom.ittsa.services.PuntoVentaService;
import com.pe.certicom.ittsa.commons.ExportarArchivo;
import com.pe.certicom.ittsa.commons.GenericBeans;

@ManagedBean(name = "ventaDetalleMB")
@ViewScoped
public class VentaDetalleMB extends GenericBeans implements Serializable {

	public VentaDetalle ventaDetalle;
	public List<VentaDetalle> listaVentaDetalle;
	public List<VentaDetalle> listaVentaDetallefilter;
	public List<Agencia> listaAgencias;
	public List<Oficina> listaOficinas;
	public List<PuntoVenta> listaPuntoVenta;
	public Agencia agencia;
	public Oficina oficina;
	public PuntoVenta puntoVenta;
	public Date fInicio;
	public Date fFin;
	public String montoTotal;
	

	// services
	public OficinaService oficinaService;
	public AgenciaService agenciaService;
	public PuntoVentaService puntoVentaService;
	public AsientoVentaServices asientoVentaService;

	@ManagedProperty(value = "#{loginMB.usuario}")
	private Usuario usuarioLogin;

	@PostConstruct
	public void inicio() {

		this.ventaDetalle = new VentaDetalle();
		this.listaAgencias = new ArrayList<>();
		this.listaOficinas = new ArrayList<>();
		this.listaPuntoVenta = new ArrayList<>();
		this.listaVentaDetalle = new ArrayList<>();
		
		this.agencia = new Agencia();
		this.oficina = new Oficina();
		this.puntoVenta = new PuntoVenta();
	
		// service
		this.agenciaService = new AgenciaService();
		this.oficinaService = new OficinaService();
		this.puntoVentaService = new PuntoVentaService();
		this.asientoVentaService = new AsientoVentaServices();
		
		this.fInicio = new Date();
		this.fFin = new Date();
		
		try {
			this.listaAgencias = agenciaService.listaAgenciasActivas();
		} catch (Exception e) {

		}

	}

	public void obtenerListaOficina() {
		try {
			System.out.println("obtenerListaOficina()");
			System.out.println("ID AGENCIA = " + this.agencia.getIdagencia());
			this.listaOficinas = this.oficinaService
					.getOficinasxAgencia(this.agencia.getIdagencia());
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void obtenerListaPuntoVenta() {
		try {
			System.out.println("ID OFICINA = " + this.oficina.getIdOficina());
			this.listaPuntoVenta = this.puntoVentaService
					.obtenerPuntoVentaxOficina(this.oficina.getIdOficina());
					} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void Buscar() { 
		System.out.println("-------------------------------------");
		System.out.println("FECHA INICIO = " + this.fInicio);
		System.out.println("FECHA FIN = " + this.fFin);
		System.out.println("PUNTO DE VENTA = "
				+ this.puntoVenta.getIdPuntoVenta());
		System.out.println("OFICINA = " + this.oficina.getIdOficina());
		System.out.println("AGENCIA = " + this.agencia.getIdagencia());
		System.out.println("PUNTO DE VENTA = "+this.puntoVenta.getIdPuntoVenta());
		System.out.println("-------------------------------------");
		Double montoTotal = 0.0;
		try {
			this.listaVentaDetalle = this.asientoVentaService.obtenerVentaDetalle(this.fInicio,this.fFin,this.puntoVenta.getIdPuntoVenta().toString());
			
			for(int i=0;i<listaVentaDetalle.size();i++){
				System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
				System.out.println("listaVentaDetalle.get(i).getAgencia() :"+listaVentaDetalle.get(i).getNomOficina());
				System.out.println(listaVentaDetalle.get(i).getMonto());
				montoTotal = montoTotal + Double.parseDouble(listaVentaDetalle.get(i).getMonto());				
			}
			
			System.out.println("Monto Total : "+montoTotal);
			this.montoTotal = "S/"+montoTotal+"0";
			
			
		 if(listaVentaDetalle.size()==0){
			 
			 System.out.println("LISTA VACIA");
		 }
			
		} catch (Exception e) {
			System.out.println("ERRORRRRRRRRRRRRRRRR");
			e.printStackTrace();
		}
		

		

	}
	
	public void imprimirPDF(){
		try {
			
			Buscar();
			ServletContext servletContext = (ServletContext) (FacesContext.getCurrentInstance().getExternalContext().getContext());
			
//			SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");		
//			String fecha = formato.format(new Date());
			List<VentaDetalle> ListaVentaDetalle = new ArrayList<VentaDetalle>();
			ListaVentaDetalle = this.listaVentaDetalle;
			
			
			String p_logo = ExportarArchivo.getPath("/resources/img/logoittsa.jpg");
			Map<String, Object> input =new  HashMap<String,Object>();
			input.put("P_LOGO", p_logo);			
			input.put(JRParameter.REPORT_LOCALE, new Locale("es"));
			//input.put(JRParameter.IS_IGNORE_PAGINATION, Boolean.TRUE); // no parte la pagina todo lo mete en un A4
			
			String path = ExportarArchivo.getPath("/resources/reports/jxrml/rptVentaDetalle.jasper");
			HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
						
			byte[] bytes;
				bytes = ExportarArchivo.exportPdf(path, input,ListaVentaDetalle ); // rptLista 
			ExportarArchivo.executePdf(bytes, "ReporteVentaDetalle.pdf");
			FacesContext.getCurrentInstance().responseComplete();
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("ERROR imprimir pdf:"+e);
			}
	}
	
	public void exportarXLS()
	{
		
	try {
				
				Buscar();
				ServletContext servletContext = (ServletContext) (FacesContext.getCurrentInstance().getExternalContext().getContext());
				
	//			SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");		
	//			String fecha = formato.format(new Date());
				List<VentaDetalle> ListaVentaDetalle = new ArrayList<VentaDetalle>();
				ListaVentaDetalle = this.listaVentaDetalle;
				
				
				String p_logo = ExportarArchivo.getPath("/resources/img/logoittsa.jpg");
				Map<String, Object> input =new  HashMap<String,Object>();
				input.put("P_LOGO", p_logo);			
				input.put(JRParameter.REPORT_LOCALE, new Locale("es"));
				//input.put(JRParameter.IS_IGNORE_PAGINATION, Boolean.TRUE); // no parte la pagina todo lo mete en un A4
				
				String path = ExportarArchivo.getPath("/resources/reports/jxrml/rptVentaDetalle.jasper");
				HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
							
				byte[] bytes;
					bytes = ExportarArchivo.exportXls(path, input,ListaVentaDetalle,false); // rptLista 
				ExportarArchivo.executeExccel(bytes, "ReporteVentaDetalle.xls");
				FacesContext.getCurrentInstance().responseComplete();
				} catch (Exception e) {
					e.printStackTrace();
					System.out.println("ERROR imprimir xls:"+e);
				}
		
	}
	
	// get and set
	public VentaDetalle getVentaDetalle() {
		return ventaDetalle;
	}

	public void setVentaDetalle(VentaDetalle ventaDetalle) {
		this.ventaDetalle = ventaDetalle;
	}

	public Usuario getUsuarioLogin() {
		return usuarioLogin;
	}

	public void setUsuarioLogin(Usuario usuarioLogin) {
		this.usuarioLogin = usuarioLogin;
	}

	public List<VentaDetalle> getListaVentaDetalle() {
		return listaVentaDetalle;
	}

	public void setListaVentaDetalle(List<VentaDetalle> listaVentaDetalle) {
		this.listaVentaDetalle = listaVentaDetalle;
	}

	public List<VentaDetalle> getListaVentaDetallefilter() {
		return listaVentaDetallefilter;
	}

	public void setListaVentaDetallefilter(
			List<VentaDetalle> listaVentaDetallefilter) {
		this.listaVentaDetallefilter = listaVentaDetallefilter;
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

	public Agencia getAgencia() {
		return agencia;
	}

	public void setAgencia(Agencia agencia) {
		this.agencia = agencia;
	}

	public Oficina getOficina() {
		return oficina;
	}

	public void setOficina(Oficina oficina) {
		this.oficina = oficina;
	}

	public OficinaService getOficinaService() {
		return oficinaService;
	}

	public void setOficinaService(OficinaService oficinaService) {
		this.oficinaService = oficinaService;
	}

	public AgenciaService getAgenciaService() {
		return agenciaService;
	}

	public void setAgenciaService(AgenciaService agenciaService) {
		this.agenciaService = agenciaService;
	}

	public List<PuntoVenta> getListaPuntoVenta() {
		return listaPuntoVenta;
	}

	public void setListaPuntoVenta(List<PuntoVenta> listaPuntoVenta) {
		this.listaPuntoVenta = listaPuntoVenta;
	}

	public PuntoVenta getPuntoVenta() {
		return puntoVenta;
	}

	public void setPuntoVenta(PuntoVenta puntoVenta) {
		this.puntoVenta = puntoVenta;
	}

	public PuntoVentaService getPuntoVentaService() {
		return puntoVentaService;
	}

	public void setPuntoVentaService(PuntoVentaService puntoVentaService) {
		this.puntoVentaService = puntoVentaService;
	}

	public Date getfInicio() {
		return fInicio;
	}

	public void setfInicio(Date fInicio) {
		this.fInicio = fInicio;
	}

	public Date getfFin() {
		return fFin;
	}

	public void setfFin(Date fFin) {
		this.fFin = fFin;
	}

	public AsientoVentaServices getAsientoVentaService() {
		return asientoVentaService;
	}

	public void setAsientoVentaService(AsientoVentaServices asientoVentaService) {
		this.asientoVentaService = asientoVentaService;
	}

	/**
	 * @return the montoTotal
	 */
	public String getMontoTotal() {
		return montoTotal;
	}

	/**
	 * @param montoTotal the montoTotal to set
	 */
	public void setMontoTotal(String montoTotal) {
		this.montoTotal = montoTotal;
	}



}
