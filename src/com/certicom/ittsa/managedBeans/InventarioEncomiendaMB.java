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
import com.certicom.ittsa.domain.Almacen;
import com.certicom.ittsa.domain.DetalleEncomienda;
import com.certicom.ittsa.domain.Encomienda;
import com.certicom.ittsa.domain.Oficina;
import com.certicom.ittsa.services.AgenciaService;
import com.certicom.ittsa.services.AlmacenService;
import com.certicom.ittsa.services.DetalleEncomiendaServices;
import com.certicom.ittsa.services.EncomiendaServices;
import com.certicom.ittsa.services.OficinaService;
import com.pe.certicom.ittsa.commons.ExportarArchivo;

@ManagedBean(name = "inventarioEncomiendaMB")
@ViewScoped
public class InventarioEncomiendaMB {
	
	private Encomienda filter;
	private Encomienda encoSelect;
	
	private Integer totalBultos= 0;

	private List<Encomienda> listDetEnco;
	private List<Encomienda> listDetEncoFilter;
	private List<DetalleEncomienda> listDetalle;
	
	private List<Agencia> listaAgencias;
	private List<Oficina> listaOficinas;
	private List<Almacen> listAlmacen;
	
	
	private AgenciaService agenciaService;
	private OficinaService oficinaService;
	private AlmacenService almacenService;
	private EncomiendaServices encomiendaServices;
	private DetalleEncomiendaServices detalleEncomiendaServices;
	
	@PostConstruct
	public void init(){
		
		this.filter = new Encomienda();
		this.encoSelect = new Encomienda();
		
		this.agenciaService = new AgenciaService();
		this.oficinaService = new OficinaService();
		this.almacenService = new AlmacenService();
		this.encomiendaServices = new EncomiendaServices();
		this.detalleEncomiendaServices = new DetalleEncomiendaServices();
		try {
			this.listaAgencias = agenciaService.listaAgenciasActivas();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
	}


	public void consultarEncomienda(){
		try {
			this.listDetEnco = this.encomiendaServices.listarEncomiendasInventario(filter);
			if(listDetEnco!=null){
				this.totalBultos = listDetEnco.size();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void consultarDetalleEnc(Encomienda e){
		String remiteCompleto = "";
		if(e.getTipoPersona().equals("N")){
			remiteCompleto = e.getRemitente() + "-DNI:"+ e.getDniRemitente();
		} else if(e.getTipoPersona().equals("J")){
			remiteCompleto =  e.getRazonSocialRemitente() + "-RUC:"+e.getRucRemitente()+" | "+e.getRemitente() + "-DNI:"+ e.getDniRemitente();
		}
		this.encoSelect = e;
		this.encoSelect.setRemitcompleto(remiteCompleto);
		
		try {
			this.listDetalle = this.detalleEncomiendaServices.findByIdEncomienda(e.getIdEncomienda());
		} catch (Exception e1) {
			e1.printStackTrace();
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
	
	
	
	public void imprimirPDF(){
		
		//variables para capturar la descripcion de los valores de los combos
		String par_agencia ="",par_oficina="",par_almacen="";
		
		for (int i = 0; i < this.listaAgencias.size(); i++) {
			if(this.listaAgencias.get(i).getIdagencia() == this.filter.getIdAgencia()){
				par_agencia = this.listaAgencias.get(i).getDescripcion();
				break;
			}
		}
		
		for (int i = 0; i < this.listaOficinas.size(); i++) {
			if(this.listaOficinas.get(i).getIdOficina() == this.filter.getIdOficina()){
				par_oficina = this.listaOficinas.get(i).getNombre();
				break;
			}
		}
		
		for (int i = 0; i < this.listAlmacen.size(); i++) {
			if(this.listAlmacen.get(i).getIdAlmacen() == this.filter.getIdAlmacen()){
				par_almacen = this.listAlmacen.get(i).getDescripcion();
				break;
			}
		}
		
		
		try {
			ServletContext servletContext = (ServletContext) (FacesContext.getCurrentInstance().getExternalContext().getContext());
			
			SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");		
			String fecha = formato.format(new Date());
			
			Integer total = this.listDetEnco.size();
			
			String p_logo = ExportarArchivo.getPath("/resources/img/logoittsa.jpg");
			System.out.println("logo ruta:"+ p_logo);
			
			Map<String, Object> input =new  HashMap<String,Object>();
			input.put("P_LOGO", p_logo);
			input.put("P_AGENCIA", par_agencia);
			input.put("P_OFICINA", par_oficina);
			input.put("P_ALMACEN", par_almacen);
			input.put("P_FECHA", fecha);
			input.put("P_TOTAL", total.toString());
			input.put(JRParameter.REPORT_LOCALE, new Locale("es"));
			//input.put(JRParameter.IS_IGNORE_PAGINATION, Boolean.TRUE); // no parte la pagina todo lo mete en un A4
			
			String path = ExportarArchivo.getPath("/resources/reports/jxrml/rptInventarioEncomienda.jasper");
			HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
			
			
			byte[] bytes;
				bytes = ExportarArchivo.exportPdf(path, input, this.listDetEnco);
			ExportarArchivo.executePdf(bytes, "InventarioEncomienda.pdf");
			FacesContext.getCurrentInstance().responseComplete();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}

	public Encomienda getFilter() {
		return filter;
	}


	public void setFilter(Encomienda filter) {
		this.filter = filter;
	}


	public List<Encomienda> getListDetEnco() {
		return listDetEnco;
	}


	public List<Encomienda> getListDetEncoFilter() {
		return listDetEncoFilter;
	}


	public void setListDetEnco(List<Encomienda> listDetEnco) {
		this.listDetEnco = listDetEnco;
	}


	public void setListDetEncoFilter(List<Encomienda> listDetEncoFilter) {
		this.listDetEncoFilter = listDetEncoFilter;
	}


	public List<Agencia> getListaAgencias() {
		return listaAgencias;
	}

	public void setListaAgencias(List<Agencia> listaAgencias) {
		this.listaAgencias = listaAgencias;
	}


	public Encomienda getEncoSelect() {
		return encoSelect;
	}

	public void setEncoSelect(Encomienda encoSelect) {
		this.encoSelect = encoSelect;
	}

	public List<DetalleEncomienda> getListDetalle() {
		return listDetalle;
	}

	public void setListDetalle(List<DetalleEncomienda> listDetalle) {
		this.listDetalle = listDetalle;
	}


	public List<Oficina> getListaOficinas() {
		return listaOficinas;
	}


	public void setListaOficinas(List<Oficina> listaOficinas) {
		this.listaOficinas = listaOficinas;
	}


	public List<Almacen> getListAlmacen() {
		return listAlmacen;
	}


	public void setListAlmacen(List<Almacen> listAlmacen) {
		this.listAlmacen = listAlmacen;
	}


	public Integer getTotalBultos() {
		return totalBultos;
	}


	public void setTotalBultos(Integer totalBultos) {
		this.totalBultos = totalBultos;
	}
	
	
	
}
