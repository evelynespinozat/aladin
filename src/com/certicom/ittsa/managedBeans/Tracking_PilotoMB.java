package com.certicom.ittsa.managedBeans;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRParameter;

import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.UploadedFile;

import com.certicom.ittsa.domain.Agencia;
import com.certicom.ittsa.domain.Capacitacion;
import com.certicom.ittsa.domain.Cese;
import com.certicom.ittsa.domain.Datos_Familiares;
import com.certicom.ittsa.domain.Documento;
import com.certicom.ittsa.domain.Evaluacion;
import com.certicom.ittsa.domain.Flota;
import com.certicom.ittsa.domain.Formacion;
import com.certicom.ittsa.domain.Historial_AsigPCT;
import com.certicom.ittsa.domain.Historial_Laboral;
import com.certicom.ittsa.domain.Incidencia;
import com.certicom.ittsa.domain.Log;
import com.certicom.ittsa.domain.Papeleta;
import com.certicom.ittsa.domain.Piloto;
import com.certicom.ittsa.domain.Programacion;
import com.certicom.ittsa.domain.Servicio;
import com.certicom.ittsa.domain.Terramoza;
import com.certicom.ittsa.domain.Ubigeo;
import com.certicom.ittsa.services.AgenciaService;
import com.certicom.ittsa.services.CapacitacionService;
import com.certicom.ittsa.services.CeseService;
import com.certicom.ittsa.services.Datos_FamiliaresService;
import com.certicom.ittsa.services.DocumentoService;
import com.certicom.ittsa.services.EvaluacionService;
import com.certicom.ittsa.services.FlotaService;
import com.certicom.ittsa.services.FormacionService;
import com.certicom.ittsa.services.Historial_AsigPCTServices;
import com.certicom.ittsa.services.Historial_LaboralService;
import com.certicom.ittsa.services.IncidenciaServices;
import com.certicom.ittsa.services.MenuServices;
import com.certicom.ittsa.services.PapeletaService;
import com.certicom.ittsa.services.PilotoService;
import com.certicom.ittsa.services.ProgramacionService;
import com.certicom.ittsa.services.ServicioService;
import com.certicom.ittsa.services.UbigeoService;
import com.pe.certicom.ittsa.commons.Constante;
import com.pe.certicom.ittsa.commons.ExportarArchivo;
import com.pe.certicom.ittsa.commons.FacesUtils;
import com.pe.certicom.ittsa.commons.GenericBeans;
import com.pe.certicom.ittsa.commons.Utiles;


@ManagedBean(name="trackinPMB")
@ViewScoped

public class Tracking_PilotoMB extends GenericBeans implements Serializable{

	private Piloto piloto;
	private List<Piloto> listaPiloto;
	private List<Piloto> listaFiltroPiloto;
	private List<Programacion> listaProgramacion;
	
	private Boolean editar;
	private Boolean editarImagen;
	private Boolean bandBuscar;
	private String lastDNI;
	
	private Date fecInicio;
	private Date fecFin;
	
	@ManagedProperty(value="#{loginMB}")
	private LoginMB login;
	
	//services
	private PilotoService pilotoService;
	private MenuServices menuServices;
	private UbigeoService ubigeoService;
	private ProgramacionService programacionServices;
	private AgenciaService agenciaServices;
	private FlotaService flotaServices;
	private ServicioService servicioServices;

	
	//datos Log
    private Log log;
	private LogMB logmb;
	
	
	private String codigo_Obtener_Foto;
	

	private RequestContext context;
	
	public Tracking_PilotoMB(){;}
	
	@PostConstruct
	public void incia()
	{
		this.editar = Boolean.FALSE;
		this.bandBuscar =  Boolean.FALSE;		
		this.pilotoService = new PilotoService();
		this.menuServices=new MenuServices();		
		this.ubigeoService = new UbigeoService();	
		this.programacionServices = new ProgramacionService();
		this.agenciaServices = new AgenciaService();
		this.flotaServices = new FlotaService();
		this.servicioServices = new ServicioService();
		this.piloto = new Piloto();
		
			
		
		log = (Log)getSpringBean(Constante.SESSION_LOG);
		logmb = new LogMB();
		//listando
		try {
			this.listaPiloto = pilotoService.findAll();
			for(Piloto p : listaPiloto){
				Ubigeo u = ubigeoService.obtenerUbigeoById(p.getSid_ubigeo());
				if(u!=null){
					p.setDepartamento(u.getSdepartamento());
					p.setProvincia(u.getSprovincia());
					p.setDistrito(u.getSdistrito());
				}
			}

			int codMenu = menuServices.opcionMenuByPretty("pretty:piloto");
			log.setCod_menu(codMenu);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void cambiarEstado(Piloto pilot){
		
		   if(pilot.isEstado()){
			   pilot.setEstado(Boolean.FALSE);
		   }else{
			   pilot.setEstado(Boolean.TRUE);
		   }
		   
		   try {
			   this.pilotoService.actualizarPiloto(pilot);
			   log.setAccion("CHANGE ESTADO");
	           log.setDescripcion("Se cambio el estado en Pioto: " + piloto.getNombres());
	           logmb.insertarLog(log);
			   FacesUtils.showFacesMessage("Estado de piloto actualizado correctamente.",Constante.INFORMACION);
		   } catch (Exception e) {
			   System.out.println("Error:"+e.getMessage());
			   e.printStackTrace();
		   }   
	}
	
	public void setearPiloto(Piloto p){
		this.piloto = p;
		this.fecInicio = Utiles.obtenerPrimerDiaDelMes();
		this.fecFin = Utiles.obtenerUltimoDiaDelMes();	
		listaProgramacion = new ArrayList<>();
		this.bandBuscar = Boolean.FALSE;
	}
	
	public void consultarTracking(){
		try {
			listaProgramacion = programacionServices.findProgByFecha_IdPiloto(getFecInicio(), getFecFin(), piloto.getIdPiloto());
			for(Programacion p : listaProgramacion){
				Servicio s = servicioServices.findById(p.getIdServicio());
				p.setDescServicio(s.getDescripcion()); 
				Agencia orig = agenciaServices.findById(p.getOrigen());
				p.setNombOrigen(orig.getDescripcion());
				Agencia dest = agenciaServices.findById(p.getDestino());
				p.setNombDestino(dest.getDescripcion());
				Flota f = flotaServices.findById(p.getIdBus());
				p.setNumeroBus(f.getNumero().toString()); 
				Piloto pil = pilotoService.findById(f.getPiloto());
				p.setNombPiloto(pil.getApellidoPaterno() + " "+pil.getApellidoMaterno()+" "+pil.getNombres()); 
				Piloto cop = pilotoService.findById(f.getCopiloto());
				p.setNombCopiloto(cop.getApellidoPaterno()+" "+cop.getApellidoMaterno()+" "+cop.getNombres()); 
			}
			this.bandBuscar = Boolean.TRUE;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	

	public void cancelar(){
		this.piloto = new Piloto();
	}
		
	public Date sumaDias(Date fecha, int dias) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(fecha);
		cal.add(Calendar.DAY_OF_YEAR, dias);
		return cal.getTime();
	}
	
	
	
	
	public void imprimirPDF() throws Exception {
		//depurador.info("imprimirCompromisoPdf ==>");
		try {
			ServletContext servletContext = (ServletContext) (FacesContext.getCurrentInstance().getExternalContext().getContext());
			
			SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");		
			String fechaInicio_ConFormato = formato.format(getFecInicio());
			String fechaFin_ConFormato = formato.format(getFecFin());

			Map<String, Object> input = new HashMap<String, Object>();
			String p_logo = ExportarArchivo.getPath("/resources/img/logoittsa.jpg");
			System.out.println("logo ruta:"+ p_logo);
			input.put("P_LOGO", p_logo);
			input.put("P_PILOTO", piloto.getApellidos() + " " + piloto.getNombres());
			input.put("P_RANGO_FECHAS", fechaInicio_ConFormato + " - " + fechaFin_ConFormato);
			
			input.put(JRParameter.REPORT_LOCALE, new Locale("es"));
			input.put(JRParameter.IS_IGNORE_PAGINATION, Boolean.TRUE);

			String path = ExportarArchivo.getPath("/resources/reports/jxrml/rptTrackingPiloto.jasper");
			
			HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();

			byte[] bytes = ExportarArchivo.exportPdf(path, input, listaProgramacion);

			ExportarArchivo.executePdf(bytes, "ReporteTrackingPiloto.pdf");
			FacesContext.getCurrentInstance().responseComplete();
		} catch (Exception e) {

			//depurador.info("ERROR ID==>" + e);
			e.printStackTrace();
		}
	}
	
	
	//**set an get 

	public MenuServices getMenuServices() {
		return menuServices;
	}

	
	public Piloto getPiloto() {
		return piloto;
	}

	public void setPiloto(Piloto piloto) {
		this.piloto = piloto;
	}

	public List<Piloto> getListaPiloto() {

		return listaPiloto;
	}

	public void setListaPiloto(List<Piloto> listaPiloto) {
		this.listaPiloto = listaPiloto;
	}


	public PilotoService getPilotoService() {
		return pilotoService;
	}

	public void setPilotoService(PilotoService pilotoService) {
		this.pilotoService = pilotoService;
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


	public LoginMB getLogin() {
		return login;
	}

	public void setLogin(LoginMB login) {
		this.login = login;
	}



	public Boolean getBandBuscar() {
		return bandBuscar;
	}

	public void setBandBuscar(Boolean bandBuscar) {
		this.bandBuscar = bandBuscar;
	}

	/**
	 * @return the lastDNI
	 */
	public String getLastDNI() {
		return lastDNI;
	}

	/**
	 * @param lastDNI the lastDNI to set
	 */
	public void setLastDNI(String lastDNI) {
		this.lastDNI = lastDNI;
	}


	
	public Boolean getEditarImagen() {
		return editarImagen;
	}

	public void setEditarImagen(Boolean editarImagen) {
		this.editarImagen = editarImagen;
	}


	public String getCodigo_Obtener_Foto() {
		return codigo_Obtener_Foto;
	}

	public void setCodigo_Obtener_Foto(String codigo_Obtener_Foto) {
		this.codigo_Obtener_Foto = codigo_Obtener_Foto;
	}



	public UbigeoService getUbigeoService() {
		return ubigeoService;
	}

	public void setUbigeoService(UbigeoService ubigeoService) {
		this.ubigeoService = ubigeoService;
	}

	public List<Piloto> getListaFiltroPiloto() {
		return listaFiltroPiloto;
	}

	public void setListaFiltroPiloto(List<Piloto> listaFiltroPiloto) {
		this.listaFiltroPiloto = listaFiltroPiloto;
	}

	public Date getFecInicio() {
		return fecInicio;
	}

	public void setFecInicio(Date fecInicio) {
		this.fecInicio = fecInicio;
	}

	public Date getFecFin() {
		return fecFin;
	}

	public void setFecFin(Date fecFin) {
		this.fecFin = fecFin;
	}

	public List<Programacion> getListaProgramacion() {
		return listaProgramacion;
	}

	public void setListaProgramacion(List<Programacion> listaProgramacion) {
		this.listaProgramacion = listaProgramacion;
	}

	
}
