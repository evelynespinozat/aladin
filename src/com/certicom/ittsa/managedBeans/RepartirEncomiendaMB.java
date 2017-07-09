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
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRParameter;

import org.primefaces.context.RequestContext;

import com.certicom.ittsa.domain.Agencia;
import com.certicom.ittsa.domain.Encomienda;
import com.certicom.ittsa.domain.EncomiendaDataModel;
import com.certicom.ittsa.domain.Log;
import com.certicom.ittsa.domain.Personal;
import com.certicom.ittsa.domain.TrackingEncomienda;
import com.certicom.ittsa.services.AgenciaService;
import com.certicom.ittsa.services.EncomiendaServices;
import com.certicom.ittsa.services.MenuServices;
import com.certicom.ittsa.services.OficinaService;
import com.certicom.ittsa.services.PersonalService;
import com.certicom.ittsa.services.TrackingEncomiendaServices;
import com.pe.certicom.ittsa.commons.Constante;
import com.pe.certicom.ittsa.commons.ExportarArchivo;
import com.pe.certicom.ittsa.commons.FacesUtils;
import com.pe.certicom.ittsa.commons.GenericBeans;

@ManagedBean(name="repartirEncoMB")
@ViewScoped
public class RepartirEncomiendaMB extends GenericBeans implements Serializable{

	private List<Encomienda> listaEncomiendasAsig;
	private List<Personal> listaPersonal;
	private Integer idRepartidor;
	private Integer estado;
	private EncomiendaDataModel encomiendaModel;
	private Encomienda[] listaEncomiendaSelected;

	//services
	private MenuServices menuServices;
	private PersonalService personalService;
	private EncomiendaServices encomiendaServices;
	private TrackingEncomiendaServices trackingEncomiendaServices;
	private Encomienda encoObs;
	
	private boolean btnImprimir = true;
	
	//datos Log
    private Log log;
	private LogMB logmb;
	
	public RepartirEncomiendaMB(){;}
	
	@PostConstruct
	public void incia()
	{
		this.listaPersonal = new ArrayList<>();
		
		this.menuServices=new MenuServices();
		this.personalService = new PersonalService();
		this.encomiendaServices = new EncomiendaServices();
		this.trackingEncomiendaServices = new TrackingEncomiendaServices(); 
		
		this.encoObs = new Encomienda();
		
		log = (Log)getSpringBean(Constante.SESSION_LOG);
		logmb = new LogMB();
		//listando
		try {
			this.listaPersonal = this.personalService.listarPersonalActivo();
			int codMenu = menuServices.opcionMenuByPretty("pretty:agencia");
			log.setCod_menu(codMenu);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void listarEncomiendasAsignadas(){
		try {
			this.listaEncomiendasAsig = new ArrayList<>();
			Encomienda enco = new Encomienda();
			enco.setIdPersonalReparto(this.idRepartidor);
			enco.setEstado(this.estado);
			this.listaEncomiendasAsig = this.encomiendaServices.encomiendasxRepartidor(enco);
			this.encomiendaModel = new EncomiendaDataModel(this.listaEncomiendasAsig); 
			if(this.listaEncomiendasAsig.size()>0){
				this.btnImprimir = Boolean.FALSE;
			} else{
				this.btnImprimir = Boolean.TRUE;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void cambiarEstadoEnco(){
		try {
			for(int i= 0; i<this.listaEncomiendaSelected.length;i++){
				Encomienda enco = this.listaEncomiendaSelected[i];
				
				enco.setEstado(5);
			
				if(enco.isDevuelto()){
					enco.setEstado(4);
					enco.setCondicionReparto("DEVUELTO");
					this.encomiendaServices.actualizarTipoEntregaEncomienda(enco);
				}
				this.encomiendaServices.actualizarEstadoDevueltoEncomienda(enco);
				//traking de encomienda
				this.trackingEncomiendaServices.actualizarEstadoAnterior(enco.getIdEncomienda());
				// agregando el nuevo estado
				
				TrackingEncomienda tke = new TrackingEncomienda();
				tke.setIdEncomienda(enco.getIdEncomienda());
				tke.setIdEstado(enco.getEstado());
				tke.setIdBus(enco.getIdBus());
				tke.setEstadoActual(Boolean.TRUE);
				tke.setIdProgramacion(enco.getIdProgramacion());
				
				this.trackingEncomiendaServices.crearTrackingEncomienda(tke);
			}
			listarEncomiendasAsignadas();
			RequestContext context = RequestContext.getCurrentInstance();
			FacesUtils.showFacesMessage("Encomiendas actualizadas correctamente.",Constante.INFORMACION);
			context.update("sms");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void setearObs(Encomienda e){
		System.out.println("e "+ e.isDevuelto());
		this.encoObs = e;
		System.out.println("enco "+ this.encoObs.getComprobante());
		System.out.println("enco "+ this.encoObs.getRemitcompleto());
		System.out.println("enco "+ this.encoObs.isDevuelto());
	}

	public void agregarObserv(){
		RequestContext context = RequestContext.getCurrentInstance();  
   	    context.addCallbackParam("esValido", Boolean.TRUE );
   	    
//		for (Encomienda enc	: this.listaEncomiendasAsig) {
//			if(this.encoObs.getIdEncomienda() == enc.getIdEncomienda()){
//				enc.setObsReparto(this.encoObs.getObsReparto());
//				enc.setDevuelto(this.encoObs.isDevuelto());
//				break;
//			}
//		}
   	    
   	    try {
			this.encomiendaServices.actualizarObsReparto(this.encoObs.getObsReparto(), this.encoObs.getIdEncomienda());
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.encoObs = null;
		FacesUtils.showFacesMessage("Observación actualizada correctamente.",Constante.INFORMACION);
		context.update("sms");
		
	}
	
	
	public void imprimirPDF(){
		String par_repartidor = "",par_estado = "";
		for (int i = 0; i < this.listaPersonal.size(); i++) {
			if(this.listaPersonal.get(i).getIdPersonal() == this.idRepartidor){
				par_repartidor = ""+this.listaPersonal.get(i).getNomcompleto();
				break;
			}
		}
		
		if(this.estado ==6) par_estado = "POR REPARTIR"; 
		if(this.estado == 5) par_estado = "ENTREGADO";
		
		try {
			ServletContext servletContext = (ServletContext) (FacesContext.getCurrentInstance().getExternalContext().getContext());
			
			SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");		
			String fecha = formato.format(new Date());
			
			Integer total = this.listaEncomiendasAsig.size();
			
			String p_logo = ExportarArchivo.getPath("/resources/img/logoittsa.jpg");
			System.out.println("logo ruta:"+ p_logo);
			
			Map<String, Object> input =new  HashMap<String,Object>();
			input.put("P_LOGO", p_logo);
			input.put("P_RESPONSABLE", par_repartidor);
			input.put("P_ESTADO", par_estado);
			input.put("P_TOTAL", total.toString());
			input.put("P_FECHA", fecha);
			input.put(JRParameter.REPORT_LOCALE, new Locale("es"));
			//input.put(JRParameter.IS_IGNORE_PAGINATION, Boolean.TRUE); // no parte la pagina todo lo mete en un A4
			
			String path = ExportarArchivo.getPath("/resources/reports/jxrml/rptEncomiendasAsignadas.jasper");
			HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
			
			
			byte[] bytes;
				bytes = ExportarArchivo.exportPdf(path, input, this.listaEncomiendasAsig);
			ExportarArchivo.executePdf(bytes, "EncomiendasAsignadas.pdf");
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

	public Encomienda[] getListaEncomiendaSelected() {
		return listaEncomiendaSelected;
	}

	public void setListaEncomiendaSelected(Encomienda[] listaEncomiendaSelected) {
		this.listaEncomiendaSelected = listaEncomiendaSelected;
	}

	public List<Encomienda> getListaEncomiendasAsig() {
		return listaEncomiendasAsig;
	}

	public void setListaEncomiendasAsig(List<Encomienda> listaEncomiendasAsig) {
		this.listaEncomiendasAsig = listaEncomiendasAsig;
	}

	public List<Personal> getListaPersonal() {
		return listaPersonal;
	}

	public void setListaPersonal(List<Personal> listaPersonal) {
		this.listaPersonal = listaPersonal;
	}

	public Integer getIdRepartidor() {
		return idRepartidor;
	}

	public void setIdRepartidor(Integer idRepartidor) {
		this.idRepartidor = idRepartidor;
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

	public EncomiendaDataModel getEncomiendaModel() {
		return encomiendaModel;
	}

	public void setEncomiendaModel(EncomiendaDataModel encomiendaModel) {
		this.encomiendaModel = encomiendaModel;
	}

	public Encomienda getEncoObs() {
		return encoObs;
	}

	public void setEncoObs(Encomienda encoObs) {
		this.encoObs = encoObs;
	}

	public Integer getEstado() {
		return estado;
	}

	public void setEstado(Integer estado) {
		this.estado = estado;
	}

	public boolean isBtnImprimir() {
		return btnImprimir;
	}

	public void setBtnImprimir(boolean btnImprimir) {
		this.btnImprimir = btnImprimir;
	}
	
	
	
}

