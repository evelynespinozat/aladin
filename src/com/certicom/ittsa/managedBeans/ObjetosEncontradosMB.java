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
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRParameter;

import org.primefaces.context.RequestContext;

import com.certicom.ittsa.domain.Agencia;
import com.certicom.ittsa.domain.Destino;
import com.certicom.ittsa.domain.Log;
import com.certicom.ittsa.domain.ObjEncontrados;
import com.certicom.ittsa.domain.Usuario;
import com.certicom.ittsa.services.AgenciaService;
import com.certicom.ittsa.services.DestinoServices;
import com.certicom.ittsa.services.MenuServices;
import com.certicom.ittsa.services.ObjEncontradosService;
import com.pe.certicom.ittsa.commons.Constante;
import com.pe.certicom.ittsa.commons.ExportarArchivo;
import com.pe.certicom.ittsa.commons.FacesUtils;
import com.pe.certicom.ittsa.commons.GenericBeans;

@ManagedBean(name="objetosEncontradosMB")
@ViewScoped
public class ObjetosEncontradosMB extends GenericBeans implements Serializable{

	private ObjEncontrados objEncontradosFilter;
	private List<Agencia> listaAgOrigen;
	private List<Destino> listaAgDestinos;
	private List<ObjEncontrados> listaObjEncontrados;
	private List<ObjEncontrados> listaFilterObjEncontrados;
	
	private MenuServices menuServices;
	private AgenciaService agenciaService;
	private DestinoServices destinoServices;
	private ObjEncontradosService objEncontradosService;
	
	// bean para actualizar el objeto encontrado
	private ObjEncontrados objEncontrados;
	
	@ManagedProperty(value="#{loginMB.usuario}")
	private Usuario usuarioLogin;
	
	//datos Log
    private Log log;
	private LogMB logmb;
	
	public ObjetosEncontradosMB(){;}
	
	@PostConstruct
	public void incia()
	{
		this.objEncontrados = new ObjEncontrados();
		
		this.objEncontradosFilter = new ObjEncontrados();
		this.listaAgOrigen = new ArrayList<>();
		this.listaObjEncontrados = new ArrayList<>();
		//this.listaFilterObjEncontrados = new ArrayList<>();
		
		this.menuServices=new MenuServices();
		this.agenciaService = new AgenciaService();
		this.destinoServices = new DestinoServices();
		this.objEncontradosService = new ObjEncontradosService();
		
		log = (Log)getSpringBean(Constante.SESSION_LOG);
		logmb = new LogMB();
		//listando
		try {
			this.listaAgOrigen = this.agenciaService.listaAgenciasActivas();
			int codMenu = menuServices.opcionMenuByPretty("pretty:agencia");
			log.setCod_menu(codMenu);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void listarAgeDestino(){
		try {
			this.listaAgDestinos = new ArrayList<>(); 
			this.listaAgDestinos = this.destinoServices.obtenerDestino(objEncontradosFilter.getIdOrigen());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void buscarObjetos(){
		try {
			this.listaObjEncontrados = this.objEncontradosService.listarObjetosEncontrados(this.objEncontradosFilter);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public void editarObjEncontrados(ObjEncontrados com){
		this.objEncontrados = com;
	}
	
	public void devolverObjEnc(){
		
		Boolean valido=Boolean.TRUE;
		RequestContext context = RequestContext.getCurrentInstance();  
   	    context.addCallbackParam("esValido", valido );
   	   
   	    try {
		
		this.objEncontrados.setEstado("DEVUELTO");
		this.objEncontrados.setFechaEntrega(new Date());
		this.objEncontrados.setUsuarioEntrega(getUsuarioLogin().getIdusuario()+"");
			this.objEncontradosService.actualizarObjEncontrados(objEncontrados);
		 log.setAccion("UPDATE");
         log.setDescripcion("Se actualiza el objeto encontrado: " + objEncontrados.getIdObjeto() +"-" + objEncontrados.getDescripcion());
         logmb.insertarLog(log);
		FacesUtils.showFacesMessage("Se devolvió el objeto correctamente.",Constante.INFORMACION);
		context.update("sms");
		
   	    } catch (Exception e) {
			e.printStackTrace();
		}
   	    
   	    buscarObjetos();
   	    this.objEncontrados = new ObjEncontrados();
	}
	
	
	public void imprimirPDF(){
		String par_origen ="",par_destino = "";
		
		
		for (int i = 0; i < this.listaAgOrigen.size(); i++) {
			if(this.listaAgOrigen.get(i).getIdagencia() == this.objEncontradosFilter.getIdOrigen()){
				par_origen = this.listaAgOrigen.get(i).getDescripcion(); 
				break;
			}
		}
		
		for (int i = 0; i < this.listaAgDestinos.size(); i++) {
			if(this.listaAgDestinos.get(i).getDestino() == this.objEncontradosFilter.getIdDestino()){
				par_destino = this.listaAgDestinos.get(i).getDesDestino(); 
				break;
			}
		}
		
		try {
			ServletContext servletContext = (ServletContext) (FacesContext.getCurrentInstance().getExternalContext().getContext());
			
			SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");		
			String fecha = formato.format(new Date());
			
			Integer total = this.listaObjEncontrados.size();
			
			String p_logo = ExportarArchivo.getPath("/resources/img/logoittsa.jpg");
			System.out.println("logo ruta:"+ p_logo);
			
			Map<String, Object> input =new  HashMap<String,Object>();
			input.put("P_LOGO", p_logo);
			input.put("P_ORIGEN", par_origen);
			input.put("P_DESTINO", par_destino);
			input.put("P_FECHA", fecha);
			input.put("P_TOTAL", total.toString());
			input.put(JRParameter.REPORT_LOCALE, new Locale("es"));
			//input.put(JRParameter.IS_IGNORE_PAGINATION, Boolean.TRUE); // no parte la pagina todo lo mete en un A4
			
			String path = ExportarArchivo.getPath("/resources/reports/jxrml/rptObjEncontrados.jasper");
			HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
			
			
			byte[] bytes;
				bytes = ExportarArchivo.exportPdf(path, input, this.listaObjEncontrados);
			ExportarArchivo.executePdf(bytes, "ObjEncontrados.pdf");
			FacesContext.getCurrentInstance().responseComplete();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	
	

	//**set an get
	
	public MenuServices getMenuServices() {
		return menuServices;
	}

	public ObjEncontrados getObjEncontradosFilter() {
		return objEncontradosFilter;
	}

	public void setObjEncontradosFilter(ObjEncontrados objEncontradosFilter) {
		this.objEncontradosFilter = objEncontradosFilter;
	}

	public List<Agencia> getListaAgOrigen() {
		return listaAgOrigen;
	}

	public void setListaAgOrigen(List<Agencia> listaAgOrigen) {
		this.listaAgOrigen = listaAgOrigen;
	}

	public List<Destino> getListaAgDestinos() {
		return listaAgDestinos;
	}

	public void setListaAgDestinos(List<Destino> listaAgDestinos) {
		this.listaAgDestinos = listaAgDestinos;
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

	public List<ObjEncontrados> getListaObjEncontrados() {
		return listaObjEncontrados;
	}

	public void setListaObjEncontrados(List<ObjEncontrados> listaObjEncontrados) {
		this.listaObjEncontrados = listaObjEncontrados;
	}

	public List<ObjEncontrados> getListaFilterObjEncontrados() {
		return listaFilterObjEncontrados;
	}

	public void setListaFilterObjEncontrados(
			List<ObjEncontrados> listaFilterObjEncontrados) {
		this.listaFilterObjEncontrados = listaFilterObjEncontrados;
	}

	public ObjEncontrados getObjEncontrados() {
		return objEncontrados;
	}

	public void setObjEncontrados(ObjEncontrados objEncontrados) {
		this.objEncontrados = objEncontrados;
	}

	public Usuario getUsuarioLogin() {
		return usuarioLogin;
	}

	public void setUsuarioLogin(Usuario usuarioLogin) {
		this.usuarioLogin = usuarioLogin;
	}
	
	
	
	
}

