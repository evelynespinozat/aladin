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

import org.primefaces.context.RequestContext;

import net.sf.jasperreports.engine.JRParameter;

import com.certicom.ittsa.domain.Agencia;
import com.certicom.ittsa.domain.AgenciaAutoparte;
import com.certicom.ittsa.domain.Almacen;
import com.certicom.ittsa.domain.KardexAutoparte;
import com.certicom.ittsa.domain.Log;
import com.certicom.ittsa.domain.Oficina;
import com.certicom.ittsa.domain.PlantillaDetalle;
import com.certicom.ittsa.domain.Programacion;
import com.certicom.ittsa.domain.Salida;
import com.certicom.ittsa.domain.SalidaAutoparte;
import com.certicom.ittsa.services.AgenciaAutoParteService;
import com.certicom.ittsa.services.AgenciaService;
import com.certicom.ittsa.services.AlmacenService;
import com.certicom.ittsa.services.KardexAutoparteService;
import com.certicom.ittsa.services.KardexService;
import com.certicom.ittsa.services.MenuServices;
import com.certicom.ittsa.services.OficinaService;
import com.certicom.ittsa.services.SalidaAutoparteService;
import com.pe.certicom.ittsa.commons.Constante;
import com.pe.certicom.ittsa.commons.ExportarArchivo;
import com.pe.certicom.ittsa.commons.FacesUtils;
import com.pe.certicom.ittsa.commons.GenericBeans;

@ManagedBean(name = "salidaAutoparteMB")
@ViewScoped
public class SalidaAutoparteMB extends GenericBeans{

	private List<Agencia> listaAgencias;
	private List<Oficina> listaOficinas;
	private List<Almacen> listAlmacen;
	private List<SalidaAutoparte> listaSalidaAutoparte; 
	private List<SalidaAutoparte> listRptSalidaAutoparte; 
	private boolean prueba = true;
	private SalidaAutoparte filter;
	
	private MenuServices menuServices;
	private SalidaAutoparteService salidaAutoparteService;
	private AgenciaService agenciaService;
	private OficinaService oficinaService;
	private AlmacenService almacenService;
	private KardexAutoparteService kardexAutoparteService;
	private AgenciaAutoParteService agenciaAutoParteService;
	
	private Log log;
	private LogMB logMB;
	
	@PostConstruct
	public void inicia(){
		
		this.filter = new SalidaAutoparte();
		
		this.menuServices = new MenuServices();
		this.salidaAutoparteService = new SalidaAutoparteService();
		this.agenciaService = new AgenciaService();
		this.oficinaService = new OficinaService();
		this.almacenService = new AlmacenService();
		this.kardexAutoparteService = new KardexAutoparteService();
		this.agenciaAutoParteService = new AgenciaAutoParteService();
		
		this.listRptSalidaAutoparte = new ArrayList<SalidaAutoparte>();
		
		this.log = (Log)getSpringBean(Constante.SESSION_LOG);
		try {
			int codmenu = this.menuServices.opcionMenuByPretty("pretty:salidaAutoparte");
			log.setCod_menu(codmenu);
			this.listaAgencias = this.agenciaService.listaAgenciasActivas();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void consultarAutopartePedidas(){
		try {
			this.listaSalidaAutoparte = this.salidaAutoparteService.listarAutopartePendientes(filter);
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
	
	public void listarAlmacen(){
		try {
			this.listAlmacen = this.almacenService.listAlmacenxOficina(this.filter.getIdOficina());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void agregarAutoparteSalida(SalidaAutoparte s){
		this.listRptSalidaAutoparte.add(s);
	}
	
	public void ordenarSalida(){
		
		try {
			
		for (int i = 0; i < this.listaSalidaAutoparte.size(); i++) {
			SalidaAutoparte s  = this.listaSalidaAutoparte.get(i);
			if(s.isSeleccionado() == true){
				
				PlantillaDetalle pd = new PlantillaDetalle();
				pd.setCantSalida(s.getCantidad());
				pd.setIdagencia(s.getIdAgencia());
				pd.setIdOficina(s.getIdOficina());
				pd.setIdAutoparte(s.getIdAutoparte());
				// actualizando el stock
				this.agenciaAutoParteService.actualizarStockProducto(pd);
					
				//ingresando al kardex
				KardexAutoparte kardex = new KardexAutoparte();
				
				kardex.setIdagencia(s.getIdAgencia());
				kardex.setIdOficina(s.getIdOficina());
				kardex.setIdAutoparte(s.getIdAutoparte());
				kardex.setSalida(s.getCantidad());	
				kardex.setTipo("SALIDA");
				kardex.setFRegistro(new Date());
				kardex.setIdBus(s.getIdBus());
				kardex.setIdMecanico(s.getIdMecanico());
				this.kardexAutoparteService.registrarKardex(kardex);
				
				// actualizando las autopartes en t_salidaautoparte a 2 (atentido)
				
				this.salidaAutoparteService.actualizarEstado(s.getIdSalidaAutoparte(), 2);
				
				//agregando a la lista para el reporte
				this.listRptSalidaAutoparte.add(s);
			}
		}

		// lineas para imprimir
		imprimirPDF();
		
		this.listaSalidaAutoparte = this.salidaAutoparteService.listarAutopartePendientes(filter);
		FacesUtils.showFacesMessage("Se ordenaron las salidas correctamente.",Constante.INFORMACION);
		
		for (int i = 0; i < listRptSalidaAutoparte.size(); i++) {
			this.listRptSalidaAutoparte.remove(i);
		}
		System.out.println("paso"); 
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public  void imprimirPDF() {
		try {
		ServletContext servletContext = (ServletContext) (FacesContext.getCurrentInstance().getExternalContext().getContext());
		SalidaAutoparte sa = this.listRptSalidaAutoparte.get(0);
		
		SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");		
		String fecha = formato.format(sa.getFecPedido());
		
		Integer total = this.listRptSalidaAutoparte.size();
		
		String p_logo = ExportarArchivo.getPath("/resources/img/logoittsa.jpg");
		System.out.println("logo ruta:"+ p_logo);
		
		Map<String, Object> input =new  HashMap<String,Object>();
		input.put("P_LOGO", p_logo);
		input.put("P_NRO_BUS", sa.getNroBus().toString());
		input.put("P_FECHA", fecha);
		input.put("P_MECANICO", sa.getNomMecanico());
		input.put("P_TOTAL", total.toString());
		input.put(JRParameter.REPORT_LOCALE, new Locale("es"));
		input.put(JRParameter.IS_IGNORE_PAGINATION, Boolean.TRUE);
		
		String path = ExportarArchivo.getPath("/resources/reports/jxrml/rptOrdenSalida.jasper");
		HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
		
		
		byte[] bytes;
			bytes = ExportarArchivo.exportPdf(path, input, listRptSalidaAutoparte);
			System.out.println("llego");
		ExportarArchivo.executePdf(bytes, "reporteOrdenSalida.pdf");
		FacesContext.getCurrentInstance().responseComplete();
		System.out.println("llego 2");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public List<SalidaAutoparte> getListaSalidaAutoparte() {
		return listaSalidaAutoparte;
	}


	public SalidaAutoparte getFilter() {
		return filter;
	}

	public void setListaSalidaAutoparte(List<SalidaAutoparte> listaSalidaAutoparte) {
		this.listaSalidaAutoparte = listaSalidaAutoparte;
	}


	public void setFilter(SalidaAutoparte filter) {
		this.filter = filter;
	}


	public List<Agencia> getListaAgencias() {
		return listaAgencias;
	}

	public List<Oficina> getListaOficinas() {
		return listaOficinas;
	}


	public List<Almacen> getListAlmacen() {
		return listAlmacen;
	}


	public void setListaAgencias(List<Agencia> listaAgencias) {
		this.listaAgencias = listaAgencias;
	}


	public void setListaOficinas(List<Oficina> listaOficinas) {
		this.listaOficinas = listaOficinas;
	}


	public void setListAlmacen(List<Almacen> listAlmacen) {
		this.listAlmacen = listAlmacen;
	}

	public List<SalidaAutoparte> getListRptSalidaAutoparte() {
		return listRptSalidaAutoparte;
	}

	public void setListRptSalidaAutoparte(
			List<SalidaAutoparte> listRptSalidaAutoparte) {
		this.listRptSalidaAutoparte = listRptSalidaAutoparte;
	}

	public boolean isPrueba() {
		return prueba;
	}

	public void setPrueba(boolean prueba) {
		this.prueba = prueba;
	}
	
	
	
}
