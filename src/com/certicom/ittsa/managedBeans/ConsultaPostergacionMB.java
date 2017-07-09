package com.certicom.ittsa.managedBeans;

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
import com.certicom.ittsa.domain.Destino;
import com.certicom.ittsa.domain.Oficina;
import com.certicom.ittsa.domain.Persona;
import com.certicom.ittsa.domain.Postergacion;
import com.certicom.ittsa.services.AgenciaService;
import com.certicom.ittsa.services.DestinoServices;
import com.certicom.ittsa.services.OficinaService;
import com.certicom.ittsa.services.PersonaServices;
import com.certicom.ittsa.services.PostergarService;
import com.pe.certicom.ittsa.commons.ExportarArchivo;

@ManagedBean(name ="consultaPostergacionMB")
@ViewScoped
public class ConsultaPostergacionMB {
	
	private boolean btnImprimir = true;
	
	public Postergacion filter; 
	
	private List<Agencia> listaAgencias;
	private List<Oficina> listaOficinas;
	private List<Agencia> listaOrigen;
	private List<Destino> listaDestino;
	private List<Postergacion> listaPostergacion;
	private List<Postergacion> listaPostergacionFilter;
	
	
	private AgenciaService agenciaService;
	private OficinaService oficinaService;
	private DestinoServices destinoServices;
	private PostergarService postergarService;
	private PersonaServices personaServices;
	
	@PostConstruct
	public void inicia(){
		
		this.filter = new Postergacion();
		this.filter.setFecInicio(new Date());
		this.filter.setFecFin(new Date());
		
		this.agenciaService = new AgenciaService();
		this.oficinaService = new OficinaService();
		this.destinoServices = new DestinoServices();
		this.postergarService = new PostergarService();
		this.personaServices = new PersonaServices();
		
		try {
			this.listaAgencias = agenciaService.listaAgenciasActivas();
			this.listaOrigen = this.listaAgencias;
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
	
	public void listarDestinosxOri(){
		try {
			this.listaDestino = this.destinoServices.obtenerDestino(this.filter.getOrigen());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void listarBoletosPostergados(){
		try {
			this.listaPostergacion = this.postergarService.listaPostergacionxFiltros(this.filter);
			if(this.listaPostergacion.size()>0){
				this.btnImprimir = Boolean.FALSE;
			} else{
				this.btnImprimir = Boolean.TRUE;
			}
			
			for (Postergacion pr : this.listaPostergacion) {
				Persona per = this.personaServices.findByNroDocumento(pr.getDocumentopersona());
				if(per!=null){
					pr.setNomPersona(per.getNombres()+" "+per.getAPaterno() +" " + per.getAMaterno());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public void imprimirPDF(){
		String par_origen = "",par_destino = "", par_origen_destino = "";
				
		for (int i = 0; i < this.listaOrigen.size(); i++) {
			Agencia a = this.listaOrigen.get(i);
			if(a.getIdagencia() == this.filter.getOrigen()){
				par_origen = a.getDescripcion();
				break;
			}
		}
		
		for (int i = 0; i < this.listaDestino.size(); i++) {
			Destino d = this.listaDestino.get(i);
			if(d.getDestino() == this.filter.getDestino()){
				par_destino = d.getDesDestino();
				break;
			}
		}
		
		for (int i = 0; i < this.listaOficinas.size(); i++) {
			Oficina o = this.listaOficinas.get(i);
			if(o.getIdOficina() == this.filter.getIdOficina()){
				par_origen_destino = o.getDireccion();
				break;
			}
		}
		
		try {
			ServletContext servletContext = (ServletContext) (FacesContext.getCurrentInstance().getExternalContext().getContext());
			
			SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");		
			String fechaDesde = formato.format(this.filter.getFecInicio());
			String fechaHasta = formato.format(this.filter.getFecFin());
			
			Integer total = this.listaPostergacion.size();
			
			String p_logo = ExportarArchivo.getPath("/resources/img/logoittsa.jpg");
			System.out.println("logo ruta:"+ p_logo);
			
			Map<String, Object> input =new  HashMap<String,Object>();
			input.put("P_LOGO", p_logo);
			input.put("P_FECHA_RANGO", fechaDesde +"-"+fechaHasta);
			input.put("P_ORIGEN", par_origen);
			input.put("P_OFICINA_ORIGEN", par_origen_destino);
			input.put("P_DESTINO", par_destino);
			input.put("P_TOTAL", total);
			input.put(JRParameter.REPORT_LOCALE, new Locale("es"));
			//input.put(JRParameter.IS_IGNORE_PAGINATION, Boolean.TRUE); // no parte la pagina todo lo mete en un A4
			
			String path = ExportarArchivo.getPath("/resources/reports/jxrml/rptBoletosPostergados.jasper");
			HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
			
			
			byte[] bytes;
				bytes = ExportarArchivo.exportPdf(path, input, this.listaPostergacion);
			ExportarArchivo.executePdf(bytes, "BoletosPostergados.pdf");
			FacesContext.getCurrentInstance().responseComplete();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}

	public Postergacion getFilter() {
		return filter;
	}

	public void setFilter(Postergacion filter) {
		this.filter = filter;
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


	public List<Postergacion> getListaPostergacion() {
		return listaPostergacion;
	}


	public void setListaPostergacion(List<Postergacion> listaPostergacion) {
		this.listaPostergacion = listaPostergacion;
	}


	public List<Postergacion> getListaPostergacionFilter() {
		return listaPostergacionFilter;
	}


	public void setListaPostergacionFilter(
			List<Postergacion> listaPostergacionFilter) {
		this.listaPostergacionFilter = listaPostergacionFilter;
	}


	public boolean isBtnImprimir() {
		return btnImprimir;
	}


	public void setBtnImprimir(boolean btnImprimir) {
		this.btnImprimir = btnImprimir;
	}
	
	
	
	
	
	
}
