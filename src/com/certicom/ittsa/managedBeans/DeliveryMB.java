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
import com.certicom.ittsa.domain.AsientoVenta;
import com.certicom.ittsa.domain.Destino;
import com.certicom.ittsa.domain.Log;
import com.certicom.ittsa.domain.Oficina;
import com.certicom.ittsa.domain.Programacion;
import com.certicom.ittsa.domain.Usuario;
import com.certicom.ittsa.services.AgenciaService;
import com.certicom.ittsa.services.AsientoVentaServices;
import com.certicom.ittsa.services.DestinoServices;
import com.certicom.ittsa.services.MenuServices;
import com.certicom.ittsa.services.OficinaService;
import com.certicom.ittsa.services.PersonaServices;
import com.certicom.ittsa.services.ProgramacionService;
import com.pe.certicom.ittsa.commons.BoletoFilter;
import com.pe.certicom.ittsa.commons.Constante;
import com.pe.certicom.ittsa.commons.ConstanteVentas;
import com.pe.certicom.ittsa.commons.ExportarArchivo;
import com.pe.certicom.ittsa.commons.FacesUtils;
import com.pe.certicom.ittsa.commons.GenericBeans;
import com.pe.certicom.ittsa.commons.Utiles;

@ManagedBean(name="deliveryMB")
@ViewScoped
public class DeliveryMB extends GenericBeans implements Serializable{
	@ManagedProperty(value = "#{loginMB.usuario}")
	private Usuario usuarioLogin;

	@ManagedProperty(value = "#{loginMB.desOficina}")
	private String oficinaUser;

	@ManagedProperty(value = "#{loginMB.agencia}")
	private Agencia agenciaUser;
	//services
	private MenuServices menuServices;
	private AsientoVentaServices asientoVentaServices;
	private PersonaServices personaServices;
	private AgenciaService agenciaService;
	private DestinoServices destinoServices;
	private OficinaService oficinaService;
	
	private List<AsientoVenta> listaAsientoVenta;
	private List<AsientoVenta> listaAsientoVentaFilter;
	private List<Agencia> listaAgencias;
	private List<Destino> listaDestino;
	private List<Agencia> listaCmbAgencia;
	private List<Oficina> listaCmbOficina;
	
	
	
	private AsientoVenta asientoVenta;
	private BoletoFilter boletoFilter;
	

	private Date fechaFilter;
	private Integer origenFilter;
	private Integer destinoFilter;
	private boolean btnImprimir = true;
	private Date fechaEntrega;
	
	//datos Log
    private Log log;
	private LogMB logmb;
	
	public DeliveryMB(){;}
	
	@PostConstruct
	public void incia(){
		this.asientoVenta = new AsientoVenta();
		this.fechaFilter= new Date();
		this.listaAsientoVenta = new ArrayList<>();
		
		this.boletoFilter = new BoletoFilter();
		this.boletoFilter.setFinicio(new Date());
		this.boletoFilter.setFfin(new Date());
		
		this.menuServices=new MenuServices();
		this.asientoVentaServices = new AsientoVentaServices();
		this.personaServices = new PersonaServices();
		this.agenciaService = new AgenciaService();
		this.destinoServices = new DestinoServices();
		this.oficinaService = new OficinaService();
		
		this.log = (Log)getSpringBean(Constante.SESSION_LOG);
		this.logmb = new LogMB();
		//listando
		try {
			int codMenu = menuServices.opcionMenuByPretty("pretty:listadelivery");
			log.setCod_menu(codMenu);
			this.listaAgencias = agenciaService.listaAgenciasActivas();
			this.listaCmbAgencia = this.listaAgencias;
			this.listaCmbOficina = oficinaService.listaOficinasXAgencia(agenciaUser.getIdagencia());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void buscarDelivery(){
		try {
			this.listaAsientoVenta = this.asientoVentaServices.obtenerDeliverys(this.boletoFilter);
			
			for(int i=0;i<this.listaAsientoVenta.size();i++){
				AsientoVenta asieVenta = this.listaAsientoVenta.get(i);
				asieVenta.setPersona(this.personaServices.findByNroDocumento(asieVenta.getDocumentoPersona()));
				asieVenta.setPersonaPagador(this.personaServices.findByNroDocumento(asieVenta.getNrodocdelivery()));
			}
			if(this.listaAsientoVenta.size()>0){
				this.btnImprimir = Boolean.FALSE;
			} else{
				this.btnImprimir = Boolean.TRUE;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void getOficinasByAgencia(){
		try {
			this.listaCmbOficina = oficinaService.listaOficinasXAgencia(this.boletoFilter.getIdagencia());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void setearAsientoVenta(AsientoVenta asientoV){
		this.asientoVenta = asientoV;
		this.asientoVenta.setPersonaRecibeDeliv(asientoV.getPersonaPagador().getNombres()+" "+asientoV.getPersonaPagador().getAPaterno()+" "+asientoV.getPersonaPagador().getAMaterno());
		this.fechaEntrega = new Date();
		
	}
	
	public void listarDestinosxOri() {
		try {
			// this.listaDestino = new ArrayList<Destino>();
			this.listaDestino = destinoServices.obtenerDestino(this.boletoFilter.getOrigen());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void imprimirPDF(){
		String par_origen = "",par_destino = "", par_ofi_origen = "";
		
		for (int i = 0; i < this.listaAgencias.size(); i++) {
			Agencia a = this.listaAgencias.get(i);
			if(a.getIdagencia() == this.boletoFilter.getOrigen()){
				par_origen = a.getDescripcion();
				break;
			}
		}
		
		for (int j = 0; j < this.listaDestino.size(); j++) {
			Destino d = this.listaDestino.get(j);
			if(d.getDestino() == this.boletoFilter.getDestino()){
				par_destino = d.getDesDestino();
				break;
			}
		}
		
		for (int i = 0; i < this.listaCmbOficina.size(); i++) {
			Oficina o = this.listaCmbOficina.get(i);
			if(o.getIdOficina() == this.boletoFilter.getIdoficina()){
				par_ofi_origen = o.getDireccion();
				break;
			}
		}
		
		
		
		try {
			ServletContext servletContext = (ServletContext) (FacesContext.getCurrentInstance().getExternalContext().getContext());
			
			SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");		
			String fechaini = formato.format(this.boletoFilter.getFinicio());
			String fechafin = formato.format(this.boletoFilter.getFfin());
			
			Integer total = this.listaAsientoVenta.size();
			
			String p_logo = ExportarArchivo.getPath("/resources/img/logoittsa.jpg");
			System.out.println("logo ruta:"+ p_logo);
			
			Map<String, Object> input =new  HashMap<String,Object>();
			input.put("P_LOGO", p_logo);
			input.put("P_ORIGEN", par_origen);
			input.put("P_DESTINO", par_destino);
			input.put("P_OFICINA_ORIGEN", par_ofi_origen);
			input.put("P_FVENTA", fechaini + "-" + fechafin);
			input.put("P_TOTAL", total);
			input.put(JRParameter.REPORT_LOCALE, new Locale("es"));
			//input.put(JRParameter.IS_IGNORE_PAGINATION, Boolean.TRUE); // no parte la pagina todo lo mete en un A4
			
			String path = ExportarArchivo.getPath("/resources/reports/jxrml/rptBoletosDelivery.jasper");
			HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
			
			
			byte[] bytes;
				bytes = ExportarArchivo.exportPdf(path, input, this.listaAsientoVenta);
			ExportarArchivo.executePdf(bytes, "BoletosDelivery.pdf");
			FacesContext.getCurrentInstance().responseComplete();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	public void actualizarEstadoDelivery(){
		
		Boolean valido=Boolean.TRUE;
		RequestContext context = RequestContext.getCurrentInstance();  
   	    context.addCallbackParam("esValido", valido );
		
		this.asientoVenta.setEstado_delivery(ConstanteVentas.ENTREGADO_DELIVERY);
		this.asientoVenta.setFechaEntregaDelivery(Utiles.agregarFechaAHora(this.asientoVenta.getDesFSalida(), this.fechaEntrega));
		try {
			this.asientoVentaServices.actualizarEstadoDelivery(this.asientoVenta);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		FacesUtils.showFacesMessage("Estado de boleto de delivery actualizado correctamente.",Constante.INFORMACION);
		 context.update("sms");
		
		 buscarDelivery();
		
	}
	public void cambiarEstado(AsientoVenta agen){
		
		   if(agen.getVisible()){
			   //System.out.println("es igual a uno se pone a cero");
			   agen.setVisible(Boolean.FALSE);
			   //sistem.setInd_activo(new Integer(0));
		   }else{
			   //System.out.println("es igual a cero");
			   agen.setVisible(Boolean.TRUE);
			   //sistem.setInd_activo(new Integer(1));
		   }
		   
		   try {
			   this.asientoVentaServices.actualizarEstadoDelivery(agen);
			   log.setAccion("CHANGE ESTADO");
	           log.setDescripcion("Se cambio el estado en Delivery: " + agen.getCodigoBoleta());
	           logmb.insertarLog(log);
			   FacesUtils.showFacesMessage("Estado de delivery actualizado correctamente.",Constante.INFORMACION);
		   } catch (Exception e) {
			   System.out.println("Error:"+e.getMessage());
			   e.printStackTrace();
		   }   
	}	
	//**set an get 
	
	public MenuServices getMenuServices() {
		return menuServices;
	}
	
	public AsientoVenta getAsientoVenta() {
		return asientoVenta;
	}

	public void setAsientoVenta(AsientoVenta asientoVenta) {
		this.asientoVenta = asientoVenta;
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

	public Date getFechaFilter() {
		return fechaFilter;
	}

	public void setFechaFilter(Date fechaFilter) {
		this.fechaFilter = fechaFilter;
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

	public List<Agencia> getListaAgencias() {
		return listaAgencias;
	}

	public void setListaAgencias(List<Agencia> listaAgencias) {
		this.listaAgencias = listaAgencias;
	}

	public List<Destino> getListaDestino() {
		return listaDestino;
	}

	public void setListaDestino(List<Destino> listaDestino) {
		this.listaDestino = listaDestino;
	}

	public Integer getOrigenFilter() {
		return origenFilter;
	}

	public void setOrigenFilter(Integer origenFilter) {
		this.origenFilter = origenFilter;
	}

	public Integer getDestinoFilter() {
		return destinoFilter;
	}

	public void setDestinoFilter(Integer destinoFilter) {
		this.destinoFilter = destinoFilter;
	}

	public boolean isBtnImprimir() {
		return btnImprimir;
	}

	public void setBtnImprimir(boolean btnImprimir) {
		this.btnImprimir = btnImprimir;
	}

	public BoletoFilter getBoletoFilter() {
		return boletoFilter;
	}

	public void setBoletoFilter(BoletoFilter boletoFilter) {
		this.boletoFilter = boletoFilter;
	}

	public Date getFechaEntrega() {
		return fechaEntrega;
	}

	public void setFechaEntrega(Date fechaEntrega) {
		this.fechaEntrega = fechaEntrega;
	}

	public List<Agencia> getListaCmbAgencia() {
		return listaCmbAgencia;
	}

	public void setListaCmbAgencia(List<Agencia> listaCmbAgencia) {
		this.listaCmbAgencia = listaCmbAgencia;
	}

	public List<Oficina> getListaCmbOficina() {
		return listaCmbOficina;
	}

	public void setListaCmbOficina(List<Oficina> listaCmbOficina) {
		this.listaCmbOficina = listaCmbOficina;
	}

	public Usuario getUsuarioLogin() {
		return usuarioLogin;
	}

	public void setUsuarioLogin(Usuario usuarioLogin) {
		this.usuarioLogin = usuarioLogin;
	}

	public String getOficinaUser() {
		return oficinaUser;
	}

	public void setOficinaUser(String oficinaUser) {
		this.oficinaUser = oficinaUser;
	}

	public Agencia getAgenciaUser() {
		return agenciaUser;
	}

	public void setAgenciaUser(Agencia agenciaUser) {
		this.agenciaUser = agenciaUser;
	}
	
	

}

