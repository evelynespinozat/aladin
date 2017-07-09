package com.certicom.ittsa.managedBeans;

import java.io.Serializable;
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
import javax.faces.context.Flash;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRParameter;

import org.primefaces.context.RequestContext;

import com.certicom.ittsa.domain.Agencia;
import com.certicom.ittsa.domain.Almacen;
import com.certicom.ittsa.domain.Log;
import com.certicom.ittsa.domain.Oficina;
import com.certicom.ittsa.domain.Producto;
import com.certicom.ittsa.domain.PuntoVenta;
import com.certicom.ittsa.services.AgenciaService;
import com.certicom.ittsa.services.AlmacenService;
import com.certicom.ittsa.services.MenuServices;
import com.certicom.ittsa.services.OficinaService;
import com.certicom.ittsa.services.PuntoVentaService;
import com.pe.certicom.ittsa.commons.Constante;
import com.pe.certicom.ittsa.commons.ExportarArchivo;
import com.pe.certicom.ittsa.commons.FacesUtils;
import com.pe.certicom.ittsa.commons.GenericBeans;

@ManagedBean(name="oficinaMB")
@ViewScoped
public class OficinaMB extends GenericBeans implements Serializable{
	private String titulo;
	private Oficina oficina;
	private List<Agencia> listaAgencia;
	private List<Oficina> listaOficina;
	private List<Oficina> listaFiltroOficina;
	private List<Almacen> listaAlmacen;
	private Boolean editar;
	
	//services
	private OficinaService oficinaSevice;
	private AgenciaService agenciaService;
	private MenuServices menuServices;
	private PuntoVentaService puntoVentaService;
	private AlmacenService almacenService;
	
	//datos Log
    private Log log;
	private LogMB logmb;
	
	public OficinaMB(){;}
	
	@PostConstruct
	public void incia()
	{
		this.editar = Boolean.FALSE;
		this.listaAlmacen = new ArrayList<>();
	
		this.oficinaSevice = new OficinaService();
		this.agenciaService = new AgenciaService();
		this.menuServices=new MenuServices();
		this.puntoVentaService = new PuntoVentaService();
		this.almacenService = new AlmacenService();
		
		this.oficina = new Oficina();
		
		log = (Log)getSpringBean(Constante.SESSION_LOG);
		logmb = new LogMB();
		//listando
		try {
			this.listaOficina = oficinaSevice.listaOficinas();
			this.listaAgencia = agenciaService.listaAgenciasActivas();
			int codMenu = menuServices.opcionMenuByPretty("pretty:oficina");
			log.setCod_menu(codMenu);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void imprimirPDF(){
		try {
			ServletContext servletContext = (ServletContext) (FacesContext.getCurrentInstance().getExternalContext().getContext());
			
			SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");		
			String fecha = formato.format(new Date());
			
			Integer total = this.listaOficina.size();
			
			String p_logo = ExportarArchivo.getPath("/resources/img/logoittsa.jpg");
			Map<String, Object> input =new  HashMap<String,Object>();
			input.put("P_LOGO", p_logo);
			input.put("P_TOTAL", total.toString());
			input.put("P_FENVIO", fecha);
			input.put(JRParameter.REPORT_LOCALE, new Locale("es"));
			//input.put(JRParameter.IS_IGNORE_PAGINATION, Boolean.TRUE); // no parte la pagina todo lo mete en un A4
			
			String path = ExportarArchivo.getPath("/resources/reports/jxrml/rptOficina.jasper");
			HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
			
			
			byte[] bytes;
				bytes = ExportarArchivo.exportPdf(path, input, this.listaOficina);
			ExportarArchivo.executePdf(bytes, "ReporteOficinaJuancito.pdf");
			FacesContext.getCurrentInstance().responseComplete();
			} catch (Exception e) {
				e.printStackTrace();
			}
	}
	
	public void imprimirXLS(){
		try {
			ServletContext servletContext = (ServletContext) (FacesContext.getCurrentInstance().getExternalContext().getContext());
			
			SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");		
			String fecha = formato.format(new Date());
			
			Integer total = this.listaOficina.size();
			
			String p_logo = ExportarArchivo.getPath("/resources/img/logoittsa.jpg");
			Map<String, Object> input =new  HashMap<String,Object>();
			input.put("P_LOGO", p_logo);
			input.put("P_TOTAL", total.toString());
			input.put("P_FENVIO", fecha);
			input.put(JRParameter.REPORT_LOCALE, new Locale("es"));
			//input.put(JRParameter.IS_IGNORE_PAGINATION, Boolean.TRUE); // no parte la pagina todo lo mete en un A4
			
			String path = ExportarArchivo.getPath("/resources/reports/jxrml/rptOficina.jasper");
			HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
			
			
			byte[] bytes;
				bytes = ExportarArchivo.exportXls(path, input, this.listaOficina, false);
			ExportarArchivo.executeExccel(bytes, "ReporteOficinaJuancito.xls");
			FacesContext.getCurrentInstance().responseComplete();
			} catch (Exception e) {
				e.printStackTrace();
			}
	}
	
	public void cancelar(){
		this.oficina = new Oficina();
	}
	public void newOficina(){
		this.editar = Boolean.FALSE;
		this.oficina = new Oficina();
		this.titulo = "Registrar nueva oficina";
		
	}
	
	public void editarOficina(Oficina of){
		this.oficina = of;
		this.editar = Boolean.TRUE;
		this.titulo = "Modificar oficina";
	}
	
	public String asociarAlmacen(Oficina ofi){
			String outcome = null;
			try {
	    		Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
	    		flash.put("oficina", ofi);
	    		outcome="pretty:oficinaAlmacen";
			} catch (Exception e) {	
				System.out.println("Error:"+e.getMessage());
				e.printStackTrace();
			}
			return outcome;
	}

	public void guardarOficina()
	{
		 Boolean valido=Boolean.TRUE;
		 System.out.println("la oficina es :"+this.oficina.getDireccion());
		 Oficina of = null;
		try {
			of = this.oficinaSevice.findByNombre(this.oficina.getNombre().toUpperCase().trim(),this.oficina.getIdAgencia());
		
		 
		 if( of == null ||of.getIdOficina().equals(this.oficina.getIdOficina()))
		 {
			 RequestContext context = RequestContext.getCurrentInstance();  
		   	 
				
					this.oficina.setNombre(this.oficina.getNombre().toUpperCase().trim());
					if(this.editar)
					{
						
						this.oficina.setIdOficina(this.oficina.getIdOficina());
						String pref = this.oficina.getNombre();
						this.oficina.setPrefijo(pref.substring(0,2));
						this.oficinaSevice.actualizarOficina(oficina);
						 log.setAccion("UPDATE");
			             log.setDescripcion("Se actualiza la oficina: " + oficina.getNombre());
			             logmb.insertarLog(log);
						FacesUtils.showFacesMessage("Oficina actualizada correctamente.",Constante.INFORMACION);
						context.update("sms");
					}else
					{
						//this.oficina.set
						
						String pref = this.oficina.getNombre();
						this.oficina.setPrefijo(pref.substring(0,2));
						this.oficina.setEstado(Boolean.TRUE);
						this.oficinaSevice.crearOficina(oficina);
						 log.setAccion("INSERT");
			             log.setDescripcion("Se inserta la oficina: " + oficina.getNombre());
			             logmb.insertarLog(log);
						FacesUtils.showFacesMessage("Oficina registrada correctamente.",Constante.INFORMACION);
						context.update("sms");
					}
					listaOficina = this.oficinaSevice.listaOficinas();
					

				
			   	context.addCallbackParam("esValido", valido );
			   	 
				this.oficina = new Oficina();
				this.editar =  Boolean.FALSE;
		 }else{
			 
			 FacesUtils.showFacesMessage("Oficina se encuentra ya registrada.",Constante.ERROR);
		 }
		 
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		 
		
	}
	
	public void cambiarEstado(Oficina ofici){
		
		   if(ofici.isEstado()){
			   ofici.setEstado(Boolean.FALSE);
		   }else{
			   ofici.setEstado(Boolean.TRUE);
		   }
		   
		   try {
			   this.oficinaSevice.actualizarOficina(ofici);
			   log.setAccion("CHANGE ESTADO");
	           log.setDescripcion("Se cambio el estado en oficina: " + oficina.getNombre());
	           logmb.insertarLog(log);
			   FacesUtils.showFacesMessage("Estado de oficina actualizado correctamente.",Constante.INFORMACION);
		   } catch (Exception e) {
			   System.out.println("Error:"+e.getMessage());
			   e.printStackTrace();
		   }   
	}
	
	public void eliminarOficina()
	{
		try {
			RequestContext context = RequestContext.getCurrentInstance();
        	
			List<PuntoVenta> list = puntoVentaService.getPventasxOficina(oficina.getIdOficina());
			
			Integer almacenxOficina = this.puntoVentaService.obtenerAlmacen(oficina.getIdOficina());
			
			if(list.size() > 0 || almacenxOficina > 0){
				context.execute("dlgOmiso.show()");
			}else{
				
				this.oficinaSevice.eliminarOficina(oficina.getIdOficina());
				listaOficina.remove(oficina);
				FacesUtils.showFacesMessage("Oficina eliminada correctamente.",Constante.INFORMACION);
				
				log.setAccion("DELETE");
		        log.setDescripcion("Se elimina la Oficina: " + oficina.getNombre());
		        logmb.insertarLog(log);
		        this.oficina =  new Oficina();
		        
			}
	
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	//**set an get 
	public MenuServices getMenuServices() {
		return menuServices;
	}

	public List<Agencia> getListaAgencia() {
		return listaAgencia;
	}

	public void setListaAgencia(List<Agencia> listaAgencia) {
		this.listaAgencia = listaAgencia;
	}

	public List<Oficina> getListaOficina() {
		return listaOficina;
	}

	public void setListaOficina(List<Oficina> listaOficina) {
		this.listaOficina = listaOficina;
	}

	public List<Oficina> getListaFiltroOficina() {
		return listaFiltroOficina;
	}

	public void setListaFiltroOficina(List<Oficina> listaFiltroOficina) {
		this.listaFiltroOficina = listaFiltroOficina;
	}

	public OficinaService getOficinaSevice() {
		return oficinaSevice;
	}

	public void setOficinaSevice(OficinaService oficinaSevice) {
		this.oficinaSevice = oficinaSevice;
	}

	public void setMenuServices(MenuServices menuServices) {
		this.menuServices = menuServices;
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

	public Boolean getEditar() {
		return editar;
	}

	public void setEditar(Boolean editar) {
		this.editar = editar;
	}

	public List<Almacen> getListaAlmacen() {
		return listaAlmacen;
	}

	public void setListaAlmacen(List<Almacen> listaAlmacen) {
		this.listaAlmacen = listaAlmacen;
	}

	public Oficina getOficina() {
		return oficina;
	}

	public void setOficina(Oficina oficina) {
		this.oficina = oficina;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	
}
