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
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRParameter;

import com.certicom.ittsa.domain.Agencia;
import com.certicom.ittsa.domain.Almacen;
import com.certicom.ittsa.domain.Destino;
import com.certicom.ittsa.domain.Encomienda;
import com.certicom.ittsa.domain.Flota;
import com.certicom.ittsa.domain.Oficina;
import com.certicom.ittsa.domain.OficinaAlmacen;
import com.certicom.ittsa.domain.Programacion;
import com.certicom.ittsa.domain.Usuario;
import com.certicom.ittsa.services.AgenciaService;
import com.certicom.ittsa.services.AlmacenService;
import com.certicom.ittsa.services.EncomiendaServices;
import com.certicom.ittsa.services.OficinaService;
import com.pe.certicom.ittsa.commons.ExportarArchivo;

@ManagedBean(name = "rptAbandonoLegalMB") 
@ViewScoped
public class RptAbandonoLegalMB {

	private Encomienda filterEnc;
	private Encomienda encoSelect;
	
	private Programacion progSelected;

	private List<Agencia> listaAgencias;
	private List<Oficina> listaOficinas;
	private List<Almacen> listAlmacen;
	
	private Map<Integer, Agencia> listaAgenciasMP;
	private Map<Integer, Oficina> listaOficinasMP;
	private Map<Integer, Almacen> listAlmacenMP;
	

	private List<Destino> listaDestino;
	private List<Encomienda> listaEncomienda;
	private List<Encomienda> listaEncomiendaFilter;
	private List<Flota> listaFlotas;
	
	
	
	private List<Encomienda> listaRptEncomienda;
	
	private List<Programacion> listaBuses;
	private List<Programacion> listaBusesFilter;
	private List<OficinaAlmacen> listaAlmacen;

	private AgenciaService agenciaService;
	private EncomiendaServices encomiendaServices;
	private OficinaService  oficinaService;
	private AlmacenService almacenService;
	
	
	@ManagedProperty(value="#{loginMB.usuario}")
	private Usuario usuarioLogin;
	
	
	private boolean btnGuardar = false;
	private boolean btnImprimir = false;

	@PostConstruct
	public void init() {
		
		this.filterEnc = new Encomienda();
		this.progSelected = new Programacion();
		this.encoSelect = new Encomienda();
		
		
		this.listaEncomienda = new ArrayList<Encomienda>();
		this.listaRptEncomienda = new ArrayList<Encomienda>();
		
		this.agenciaService = new AgenciaService();
		this.encomiendaServices = new EncomiendaServices();
		this.oficinaService = new OficinaService();
		this.almacenService = new AlmacenService();

		try {
			this.listaAgencias = agenciaService.listaAgenciasActivas();
			this.listaAgenciasMP =new  HashMap<Integer,Agencia>();
			for (int i = 0; i < listaAgencias.size(); i++) {
				this.listaAgenciasMP.put(this.listaAgencias.get(i).getIdagencia(), this.listaAgencias.get(i));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void getOficinasxAgencia(){
		try {
			this.listaOficinas = this.oficinaService.getOficinasxAgencia(this.filterEnc.getIdAgencia());
			this.listaOficinasMP =new  HashMap<Integer,Oficina>();
			for (int i = 0; i < listaOficinas.size(); i++) {
				this.listaOficinasMP.put(this.listaOficinas.get(i).getIdOficina(), this.listaOficinas.get(i));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
	public void listarAlmacen(){
		try {
			this.listAlmacen = this.almacenService.listAlmacenxOficina(this.filterEnc.getIdOficina());
			this.listAlmacenMP =new  HashMap<Integer,Almacen>();
			for (int i = 0; i < listAlmacen.size(); i++) {
				this.listAlmacenMP.put(this.listAlmacen.get(i).getIdAlmacen(), this.listAlmacen.get(i));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void consultarEncomiendas() {
		try {
			this.listaEncomienda = this.encomiendaServices.rptListarEncomiendasEmbarcadas(this.filterEnc);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void imprimirPDF(){
		try {
			ServletContext servletContext = (ServletContext) (FacesContext.getCurrentInstance().getExternalContext().getContext());
			
			SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");		
			String fecha = formato.format(new Date());
			
			Integer total = this.listaEncomienda.size();
			
			String p_logo = ExportarArchivo.getPath("/resources/img/logoittsa.jpg");
			System.out.println("logo ruta:"+ p_logo);
			
			Map<String, Object> input =new  HashMap<String,Object>();
			input.put("P_LOGO", p_logo);
			input.put("P_AGENCIA", ((Agencia)(listaAgenciasMP.get(this.getFilterEnc().getIdAgencia()))).getDescripcion());
			input.put("P_OFICINA", ((Oficina)(listaOficinasMP.get(this.getFilterEnc().getIdOficina()))).getNombre());
			input.put("P_ALMACEN", ((Almacen)(listAlmacenMP.get(this.getFilterEnc().getIdAlmacen()))).getDescripcion());
			input.put("P_FECHA", fecha);
			input.put(JRParameter.REPORT_LOCALE, new Locale("es"));
			//input.put(JRParameter.IS_IGNORE_PAGINATION, Boolean.TRUE); // no parte la pagina todo lo mete en un A4
			
			String path = ExportarArchivo.getPath("/resources/reports/jxrml/rptEncomiendaAlmacenAL.jasper");
			HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
			
			
			byte[] bytes;
				bytes = ExportarArchivo.exportPdf(path, input, this.listaEncomienda);
			ExportarArchivo.executePdf(bytes, "EncomiendasAlmacenAbandLegal.pdf");
			FacesContext.getCurrentInstance().responseComplete();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	
		
	public List<Agencia> getListaAgencias() {
		return listaAgencias;
	}

	public List<Destino> getListaDestino() {
		return listaDestino;
	}

	public void setListaAgencias(List<Agencia> listaAgencias) {
		this.listaAgencias = listaAgencias;
	}

	public void setListaDestino(List<Destino> listaDestino) {
		this.listaDestino = listaDestino;
	}

	public Encomienda getFilterEnc() {
		return filterEnc;
	}

	public void setFilterEnc(Encomienda filterEnc) {
		this.filterEnc = filterEnc;
	}

	public List<Encomienda> getListaEncomienda() {
		return listaEncomienda;
	}

	public AgenciaService getAgenciaService() {
		return agenciaService;
	}


	public void setListaEncomienda(List<Encomienda> listaEncomienda) {
		this.listaEncomienda = listaEncomienda;
	}

	public void setAgenciaService(AgenciaService agenciaService) {
		this.agenciaService = agenciaService;
	}

	public List<Encomienda> getListaEncomiendaFilter() {
		return listaEncomiendaFilter;
	}

	public void setListaEncomiendaFilter(List<Encomienda> listaEncomiendaFilter) {
		this.listaEncomiendaFilter = listaEncomiendaFilter;
	}

	public List<Programacion> getListaBuses() {
		return listaBuses;
	}

	public void setListaBuses(List<Programacion> listaBuses) {
		this.listaBuses = listaBuses;
	}

	public List<Programacion> getListaBusesFilter() {
		return listaBusesFilter;
	}

	public void setListaBusesFilter(List<Programacion> listaBusesFilter) {
		this.listaBusesFilter = listaBusesFilter;
	}

	public Programacion getProgSelected() {
		return progSelected;
	}

	public void setProgSelected(Programacion progSelected) {
		this.progSelected = progSelected;
	}

	public boolean isBtnGuardar() {
		return btnGuardar;
	}

	public void setBtnGuardar(boolean btnGuardar) {
		this.btnGuardar = btnGuardar;
	}

	public List<Encomienda> getListaRptEncomienda() {
		return listaRptEncomienda;
	}

	public void setListaRptEncomienda(List<Encomienda> listaRptEncomienda) {
		this.listaRptEncomienda = listaRptEncomienda;
	}

	public boolean isBtnImprimir() {
		return btnImprimir;
	}

	public void setBtnImprimir(boolean btnImprimir) {
		this.btnImprimir = btnImprimir;
	}

	public List<Flota> getListaFlotas() {
		return listaFlotas;
	}

	public void setListaFlotas(List<Flota> listaFlotas) {
		this.listaFlotas = listaFlotas;
	}

	public Encomienda getEncoSelect() {
		return encoSelect;
	}

	public void setEncoSelect(Encomienda encoSelect) {
		this.encoSelect = encoSelect;
	}


	public Usuario getUsuarioLogin() {
		return usuarioLogin;
	}

	public void setUsuarioLogin(Usuario usuarioLogin) {
		this.usuarioLogin = usuarioLogin;
	}

	public List<OficinaAlmacen> getListaAlmacen() {
		return listaAlmacen;
	}

	public void setListaAlmacen(List<OficinaAlmacen> listaAlmacen) {
		this.listaAlmacen = listaAlmacen;
	}

	public List<Oficina> getListaOficinas() {
		return listaOficinas;
	}

	public List<Almacen> getListAlmacen() {
		return listAlmacen;
	}

	public void setListaOficinas(List<Oficina> listaOficinas) {
		this.listaOficinas = listaOficinas;
	}

	public void setListAlmacen(List<Almacen> listAlmacen) {
		this.listAlmacen = listAlmacen;
	}

	public Map<Integer, Agencia> getListaAgenciasMP() {
		return listaAgenciasMP;
	}

	public Map<Integer, Oficina> getListaOficinasMP() {
		return listaOficinasMP;
	}

	public Map<Integer, Almacen> getListAlmacenMP() {
		return listAlmacenMP;
	}

	public void setListaAgenciasMP(Map<Integer, Agencia> listaAgenciasMP) {
		this.listaAgenciasMP = listaAgenciasMP;
	}

	public void setListaOficinasMP(Map<Integer, Oficina> listaOficinasMP) {
		this.listaOficinasMP = listaOficinasMP;
	}

	public void setListAlmacenMP(Map<Integer, Almacen> listAlmacenMP) {
		this.listAlmacenMP = listAlmacenMP;
	}

	
	

}
