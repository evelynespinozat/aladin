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
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRParameter;

import com.certicom.ittsa.domain.Agencia;
import com.certicom.ittsa.domain.AsientoVenta;
import com.certicom.ittsa.domain.Destino;
import com.certicom.ittsa.domain.Oficina;
import com.certicom.ittsa.domain.Postergacion;
import com.certicom.ittsa.services.AgenciaService;
import com.certicom.ittsa.services.AsientoVentaServices;
import com.certicom.ittsa.services.DestinoServices;
import com.certicom.ittsa.services.OficinaService;
import com.certicom.ittsa.services.PersonaServices;
import com.certicom.ittsa.services.PersonalService;
import com.pe.certicom.ittsa.commons.ExportarArchivo;
import com.pe.certicom.ittsa.commons.Utils;

@ManagedBean(name = "consultaReservaMB")
@ViewScoped
public class ConsultaReservaMB {

	private Integer idOrigen;
	private Integer idDestino;
	private Integer idAgencia;
	private Integer idOficina;
	private String estado;
	private boolean btnImprimir = true;

	private List<Oficina> listaOficinas;
	private List<Agencia> listaAgencias;
	private List<Agencia> listaOrigen;
	private List<Destino> listaDestino;
	private List<AsientoVenta> listaAsientoVenta;
	private List<AsientoVenta> listaAsientoVentaFilter;

	private DestinoServices destinoServices;
	private AsientoVentaServices asientoVentaServices;
	private PersonaServices personaServices;
	private AgenciaService agenciaService;
	private OficinaService oficinaService;
	private Date fecini;
	private Date fecfin;
	private Utils utils;
	
	@PostConstruct
	public void inicia() {
		this.listaAsientoVenta = new ArrayList<>();
		this.listaAgencias = new ArrayList<>();
		this.listaOficinas = new ArrayList<>();
		
		this.asientoVentaServices = new AsientoVentaServices();
		this.destinoServices = new DestinoServices();
		this.personaServices = new PersonaServices();
		this.agenciaService = new AgenciaService();
		this.oficinaService = new OficinaService();
		this.fecini = new Date();
		this.fecfin = new Date();
		
		this.utils = new Utils();
		
		try {
			this.listaAgencias = agenciaService.listaAgenciasActivas();
			this.listaOrigen = this.listaAgencias;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void getOficinasxAgencia(){
		try {
			this.listaOficinas = this.oficinaService.getOficinasxAgencia(this.idAgencia);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void listarDestinosxOri() {
		try {
			this.listaDestino = this.destinoServices
					.obtenerDestino(this.idOrigen);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void listarReservas() {
		try {
			//Date fFin = utils.sumaDias(this.fecfin,1);
			System.out.println("FECHA FINAL 1 : "+this.fecfin);
			System.out.println("FECHA FINAL 2 : "+getFecfin());
			Date fFin = utils.sumaDias(getFecfin(),0);
			this.listaAsientoVenta = new ArrayList<>();
			
//			Calendar cal = Calendar.getInstance();
//			cal.setTime(getFecfin());
//			cal.add(Calendar.DAY_OF_YEAR, 1);
			//this.listaAsientoVenta = this.asientoVentaServices.obtenerReservasxEstado(this.idOrigen, this.idDestino, this.idOficina, this.fecini,getFecfin());
			this.listaAsientoVenta = this.asientoVentaServices.obtenerReservasxEstado(this.idOrigen, this.idDestino, this.idOficina, this.fecini,fFin);
			if(this.listaAsientoVenta.size()>0){
				this.btnImprimir = Boolean.FALSE;
			} else{
				this.btnImprimir = Boolean.TRUE;
			}
			
			for (int i = 0; i < this.listaAsientoVenta.size(); i++) {
				AsientoVenta asiv = this.listaAsientoVenta.get(i);
				asiv.setPersonaCliente(this.personaServices.findByNroDocumento(asiv.getDocumentoPersona()));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void imprimirPDF(){
		
		
		
		System.out.println(" Entra al metodo con una lista de  " + this.listaAsientoVenta.size());
		
		String par_origen = "",par_destino = "",par_agencia = "",par_oficna = "";
		
		for (int i = 0; i < this.listaOrigen.size(); i++) {
			Agencia a = this.listaOrigen.get(i);
			if(a.getIdagencia() == this.idOrigen){
				par_origen = a.getDescripcion();
				break;
			}
		}
		
		for (int j = 0; j < this.listaDestino.size(); j++) {
			Destino d = this.listaDestino.get(j);
			if(d.getDestino() == this.idDestino){
				par_destino = d.getDesDestino();
				break;
			}
		}
		for (int k = 0; k < this.listaAgencias.size(); k++) {
			Agencia a = this.listaAgencias.get(k);
			if(a.getIdagencia() == this.idAgencia){
				par_agencia = a.getDescripcion();
				break;
			}
		}
		for (int l = 0; l < this.listaOficinas.size(); l++) {
			Oficina o = this.listaOficinas.get(l);
			if(o.getIdOficina() == this.idOficina){
				par_oficna = o.getNombre();
				break;
			}
		}
		
		
		
		try {
			ServletContext servletContext = (ServletContext) (FacesContext.getCurrentInstance().getExternalContext().getContext());
			

			
			Integer total = this.listaAsientoVenta.size();
			
			String p_logo = ExportarArchivo.getPath("/resources/img/logoittsa.jpg");
			System.out.println("logo ruta:"+ p_logo);
			
			Map<String, Object> input =new  HashMap<String,Object>();
			input.put("P_LOGO", p_logo);
			input.put("P_TOTAL", total);
			input.put("P_ORIGEN", par_origen);
			input.put("P_DESTINO", par_destino);
			input.put("P_OFICINA", par_agencia +" - "+ par_oficna);
			input.put(JRParameter.REPORT_LOCALE, new Locale("es"));
			//input.put(JRParameter.IS_IGNORE_PAGINATION, Boolean.TRUE); // no parte la pagina todo lo mete en un A4
			
			String path = ExportarArchivo.getPath("/resources/reports/jxrml/rptReservas.jasper");
			HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
			
			
			byte[] bytes;
				bytes = ExportarArchivo.exportPdf(path, input, this.listaAsientoVenta);
			ExportarArchivo.executePdf(bytes, "ConsultaReservas.pdf");
			FacesContext.getCurrentInstance().responseComplete();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}

	// ------------------- SET AN GET

	public List<Agencia> getListaOrigen() {
		return listaOrigen;
	}

	public void setListaOrigen(List<Agencia> listaOrigen) {
		this.listaOrigen = listaOrigen;
	}

	public List<Destino> getListaDestino() {
		return listaDestino;
	}

	public void setListaDestino(List<Destino> listaDestino) {
		this.listaDestino = listaDestino;
	}

	public Integer getIdOrigen() {
		return idOrigen;
	}

	public void setIdOrigen(Integer idOrigen) {
		this.idOrigen = idOrigen;
	}

	public Integer getIdDestino() {
		return idDestino;
	}

	public void setIdDestino(Integer idDestino) {
		this.idDestino = idDestino;
	}

	public List<AsientoVenta> getListaAsientoVenta() {
		return listaAsientoVenta;
	}

	public void setListaAsientoVenta(List<AsientoVenta> listaAsientoVenta) {
		this.listaAsientoVenta = listaAsientoVenta;
	}

	public List<AsientoVenta> getListaAsientoVentaFilter() {
		return listaAsientoVentaFilter;
	}

	public void setListaAsientoVentaFilter(
			List<AsientoVenta> listaAsientoVentaFilter) {
		this.listaAsientoVentaFilter = listaAsientoVentaFilter;
	}

	public Integer getIdAgencia() {
		return idAgencia;
	}

	public void setIdAgencia(Integer idAgencia) {
		this.idAgencia = idAgencia;
	}

	public Integer getIdOficina() {
		return idOficina;
	}

	public void setIdOficina(Integer idOficina) {
		this.idOficina = idOficina;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public List<Oficina> getListaOficinas() {
		return listaOficinas;
	}

	public void setListaOficinas(List<Oficina> listaOficinas) {
		this.listaOficinas = listaOficinas;
	}

	public List<Agencia> getListaAgencias() {
		return listaAgencias;
	}

	public void setListaAgencias(List<Agencia> listaAgencias) {
		this.listaAgencias = listaAgencias;
	}

	public boolean isBtnImprimir() {
		return btnImprimir;
	}

	public void setBtnImprimir(boolean btnImprimir) {
		this.btnImprimir = btnImprimir;
	}

	public Date getFecini() {
		return fecini;
	}

	public void setFecini(Date fecini) {
		this.fecini = fecini;
	}

	public Date getFecfin() {
		return fecfin;
	}

	public void setFecfin(Date fecfin) {
		this.fecfin = fecfin;
	}
	
	
	
}
